package com.brokenfdreams.skeleton.tests.service.impl;

import com.brokenfdreams.skeleton.tests.dao.UserDAO;
import com.brokenfdreams.skeleton.tests.dto.UpdateUserDTO;
import com.brokenfdreams.skeleton.tests.dto.UserDTO;
import com.brokenfdreams.skeleton.tests.exception.UserNotFoundException;
import com.brokenfdreams.skeleton.tests.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserDAO userDAO;

    public UserServiceImpl(@NonNull UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @NonNull
    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll();
    }

    @NonNull
    @Override
    public UserDTO getUserById(long userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @NonNull
    @Override
    public UserDTO createUser(@NonNull UpdateUserDTO updateUserDTO) {
        return userDAO.createUserAndReturn(updateUserDTO);
    }

    @NonNull
    @Override
    public UserDTO updateUser(long userId, @NonNull UpdateUserDTO updateUserDTO) {
        if (userDAO.updateUser(userId, updateUserDTO)) {
            return userDAO.getUserById(userId);
        }
        throw new UserNotFoundException(userId);
    }

    @Override
    public void deleteUser(long userId) {
        if (!userDAO.deleteUser(userId)) {
            throw new UserNotFoundException(userId);
        }
    }
}
