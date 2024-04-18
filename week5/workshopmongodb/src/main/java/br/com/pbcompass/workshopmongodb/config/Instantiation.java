package br.com.pbcompass.workshopmongodb.config;

import br.com.pbcompass.workshopmongodb.domain.User;
import br.com.pbcompass.workshopmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    private UserRepository userRepository;

    @Autowired
    public Instantiation(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        
        User maria = new User(null, "maria", "maria@gmail.com");
        User John = new User(null, "john", "john@gmail.com");
        User frank = new User(null, "frank", "frank@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, John, frank));
    }
}
