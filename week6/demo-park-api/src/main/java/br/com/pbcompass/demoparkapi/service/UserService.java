package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.User;
import br.com.pbcompass.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
}
