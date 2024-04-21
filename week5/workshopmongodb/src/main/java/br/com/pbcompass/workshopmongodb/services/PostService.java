package br.com.pbcompass.workshopmongodb.services;

import br.com.pbcompass.workshopmongodb.domain.Post;
import br.com.pbcompass.workshopmongodb.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(String id){
        return postRepository.findById(id).get();
    }

    public List<Post> findByTitleContaining(String text){
        return postRepository.findByTitleContainingIgnoreCase(text);
    }
}
