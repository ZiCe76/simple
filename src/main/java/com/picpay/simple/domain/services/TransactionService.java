package com.picpay.simple.domain.services;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.picpay.simple.domain.DTOs.TransactionDTO;
import com.picpay.simple.domain.transaction.Transaction;
import com.picpay.simple.domain.user.User;
import com.picpay.simple.domain.user.UserType;
import com.picpay.simple.repository.TransactionRepository;
import com.picpay.simple.repository.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalNotificationservice externalNotificationservice;

    

    public Transaction processTransaction(TransactionDTO transaction) throws Exception {
        User payer = this.userService.findById(transaction.payerId());
        User payee = this.userService.findById(transaction.payeeId());

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amount());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setTimestamp(LocalDateTime.now());

        // validar se o user é um lojista
        if (payer.getUserType() == UserType.MERCHANT) {
            throw new RuntimeException("Lojistas não podem efetuar transferências.");
        }

        // corpo do codigo
        if (payer.getBalance() >= newTransaction.getAmount()) {
            // autorizador externo
            if (externalAuthorization(payer, newTransaction.getAmount()) == true) {
                // efetuar transferencia
                payer.setBalance(payer.getBalance() - newTransaction.getAmount());
                payee.setBalance(payee.getBalance() + newTransaction.getAmount());

                userRepository.save(payer);
                userRepository.save(payee);
                transactionRepository.save(newTransaction);

                // enviar a notificaçao
                externalNotificationservice.sendNotificationservice(payee.getEmail(),
                        "transação recebida no valor de R$" + newTransaction.getAmount());

                // retornar a transação
                return newTransaction;
            } else {
                throw new RuntimeException("transação nao autorizada pelo serviço externo");
            }
        } else {
            throw new RuntimeException("Saldo insuficiente para realizar a transação.");
        }
    }

    public boolean externalAuthorization(User payer, Double amount) {
        ResponseEntity<String> authotizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", String.class);
        
        if (authotizationResponse.getStatusCode() == HttpStatus.OK) {
            String responseBody = authotizationResponse.getBody();
            if (responseBody != null && responseBody.contains("\"message\": \"Autorizado\"")) {
                return true;
            }
        }
        return false;
    }

    public List<Transaction> getAllTransactions(){
        return this.transactionRepository.findAll();
    }
}
