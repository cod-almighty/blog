package com.website.blog.Controller;

import com.website.blog.Exceptions.CannotDeletePostException;
import com.website.blog.Exceptions.PostNotFoundException;
import com.website.blog.Models.Post;
import com.website.blog.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
public class ApiControllers {

    @Autowired
    private PostRepo postRepo;

    @GetMapping("/")
    public String getPage() {
        return "Welcome to Donnie's blog :)";
    }

    @GetMapping("/read")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<List<Post>>(postRepo.findAll(), HttpStatus.OK);
    }

//    @GetMapping("/read")
//    public List<Post> getPost(@RequestParam(value = "id", required = false) Long id) {
//        if (id == null) {
//            return postRepo.findAll();
//        } else {
//            Post post = postRepo.findById(id)
//                    .orElseThrow(() -> new PostNotFoundException(id));
//            return (List<Post>) post;
//        }
//    }

    @GetMapping("/single")
    public ResponseEntity<Post> getSinglePost(@RequestParam long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        Post newPost = post;
        postRepo.save(newPost);

        return new ResponseEntity<String>(post.toString(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> replacePost(@RequestParam long id, @RequestBody Post post) {
        Post replacedPost = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        replacedPost.setTitle(post.getTitle());
        replacedPost.setBody((post.getBody()));
        postRepo.save(replacedPost);

        return new ResponseEntity<String>(replacedPost.toString(), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<String> editPost(@RequestParam long id, @RequestBody Map<Object, Object> fields) {
        Post updatedPost = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        fields.forEach((k,v) -> {
            Field field = ReflectionUtils.findField(Post.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, updatedPost, v);
        });
        postRepo.save(updatedPost);

        return new ResponseEntity<String>(updatedPost.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestParam long id) {
        Post deletedPost = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        try {
            postRepo.delete(deletedPost);
        } catch (Exception e) {
            throw new CannotDeletePostException(id, e);
        }

        return new ResponseEntity<String>("id " + id + " has been deleted successfully", HttpStatus.OK);
    }
}