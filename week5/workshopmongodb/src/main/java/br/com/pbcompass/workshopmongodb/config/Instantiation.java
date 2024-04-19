package br.com.pbcompass.workshopmongodb.config;

import br.com.pbcompass.workshopmongodb.domain.Post;
import br.com.pbcompass.workshopmongodb.domain.User;
import br.com.pbcompass.workshopmongodb.repository.PostRepository;
import br.com.pbcompass.workshopmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class Instantiation implements CommandLineRunner {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public Instantiation(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();
        User maria = new User(null, "maria", "maria@gmail.com");
        User John = new User(null, "john", "john@gmail.com");
        User frank = new User(null, "frank", "frank@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, John, frank));

        postRepository.deleteAll();
        Post p1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem!", "Vou viajar para São Paulo, abraços!", maria);
        Post p2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia!", "Acordei feliz hoje!", maria);
        postRepository.saveAll(Arrays.asList(p1, p2));
    }
}
