package br.com.pbcompass.workshopmongodb.config;

import br.com.pbcompass.workshopmongodb.domain.Post;
import br.com.pbcompass.workshopmongodb.domain.User;
import br.com.pbcompass.workshopmongodb.dto.AuthorDTO;
import br.com.pbcompass.workshopmongodb.dto.CommentDTO;
import br.com.pbcompass.workshopmongodb.repository.PostRepository;
import br.com.pbcompass.workshopmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


import java.text.SimpleDateFormat;
import java.util.Arrays;

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
        User john = new User(null, "john", "john@gmail.com");
        User frank = new User(null, "frank", "frank@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, john, frank));

        postRepository.deleteAll();
        Post p1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem!", "Vou viajar para São Paulo, abraços!", new AuthorDTO(maria));
        Post p2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia!", "Acordei feliz hoje!", new AuthorDTO(maria));


        CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(john));
        CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("22/03/2018"), new AuthorDTO(frank));
        CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(john));
        p1.getComments().addAll(Arrays.asList(c1, c2));
        p2.getComments().add(c3);
        postRepository.saveAll(Arrays.asList(p1, p2));

        maria.getPosts().addAll(Arrays.asList(p1, p2));
        userRepository.save(maria);
    }
}
