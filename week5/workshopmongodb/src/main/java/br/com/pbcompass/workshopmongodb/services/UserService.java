package br.com.pbcompass.workshopmongodb.services;

import br.com.pbcompass.workshopmongodb.domain.User;
import br.com.pbcompass.workshopmongodb.dto.UserDTO;
import br.com.pbcompass.workshopmongodb.repository.UserRepository;
import br.com.pbcompass.workshopmongodb.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isEmpty()){
            throw new ObjectNotFoundException("User not found");
        }else {
            return user.get();
        }
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public void delete(String id) {
            User user = findById(id);
            userRepository.delete(user);
    }

    public User update(User newUser) {
        User oldUser = userRepository.findById(newUser.getId()).get();
        updateData(newUser, oldUser);
        return userRepository.save(oldUser);
    }

    public User fromDTO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }

    private void updateData(User newUser, User oldUser) {
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
    }
}
