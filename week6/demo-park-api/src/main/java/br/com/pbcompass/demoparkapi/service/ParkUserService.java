package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.ParkUser;
import br.com.pbcompass.demoparkapi.exception.UsernameUniqueViolationException;
import br.com.pbcompass.demoparkapi.repository.ParkUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ParkUserService {

    private final ParkUserRepository parkUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ParkUser save(ParkUser parkUser) {
        try {
            parkUser.setPassword(passwordEncoder.encode(parkUser.getPassword()));
            return parkUserRepository.save(parkUser);
        }catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException(String.format("Username %s already exists", parkUser.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public ParkUser findById(Long id) {
        return parkUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public void updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords don't match");
        }
        ParkUser user = findById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Wrong current password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional(readOnly = true)
    public List<ParkUser> findAll() {
        return parkUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ParkUser findByUsername(String username) {
        return parkUserRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Username not found"));
    }
}
