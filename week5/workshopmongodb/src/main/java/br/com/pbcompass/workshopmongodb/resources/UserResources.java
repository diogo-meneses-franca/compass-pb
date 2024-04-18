package br.com.pbcompass.workshopmongodb.resources;

import br.com.pbcompass.workshopmongodb.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResources {

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        User user = new User("1", "Maria Silva", "maria@gmail.com");
        User user2 = new User("2", "Jose Silva", "jose@gmail.com");
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);
        return ResponseEntity.ok().body(users);
    }
}
