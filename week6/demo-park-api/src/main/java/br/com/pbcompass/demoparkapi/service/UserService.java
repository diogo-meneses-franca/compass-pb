package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.User;
import br.com.pbcompass.demoparkapi.exception.UsernameUniqueViolationException;
import br.com.pbcompass.demoparkapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException(String.format("Username %s already exists", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public void updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords don't match");
        }
        User user = findById(id);
        if (!currentPassword.equals(user.getPassword())) {
            throw new RuntimeException("Wrong current password");
        }
        user.setPassword(newPassword);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
