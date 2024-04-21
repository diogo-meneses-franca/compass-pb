package br.com.pbcompass.workshopmongodb.resources;

import br.com.pbcompass.workshopmongodb.domain.Post;
import br.com.pbcompass.workshopmongodb.resources.util.URL;
import br.com.pbcompass.workshopmongodb.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostResource {

    private PostService postService;

    @Autowired
    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        List<Post> list = postService.findByTitleContaining(text);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(value = "minDate", required = false, defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", required = false, defaultValue = "") String maxDate) {
        text = URL.decodeParam(text);
        Date min = URL.decodeDate(minDate, new Date(0L));
        Date max = URL.decodeDate(maxDate, new Date());
        List<Post> list = postService.fullSearch(text, min, max);
        return ResponseEntity.ok().body(list);
    }
}
