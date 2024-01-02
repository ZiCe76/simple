package com.picpay.simple.domain.DTOs;

import org.modelmapper.ModelMapper;
import com.picpay.simple.domain.transaction.Transaction;
import com.picpay.simple.domain.user.User;

public class EntityDTOConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static TransactionDTO convertToDTO(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public static User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public static Transaction convertToEntity(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);
    }
}
