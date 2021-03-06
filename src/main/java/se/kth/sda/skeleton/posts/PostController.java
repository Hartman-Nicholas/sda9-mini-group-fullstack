package se.kth.sda.skeleton.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.sda.skeleton.comments.CommentRepository;
import se.kth.sda.skeleton.exception.ResourceNotFoundException;
import se.kth.sda.skeleton.user.User;
import se.kth.sda.skeleton.user.UserService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/posts")
@RestController
public class PostController {

    PostRepository postRepository;
    PostService postService;
    UserService userService;
    CommentRepository commentRepository;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService, UserService userService) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.userService = userService;
    }

    // Return all posts.
    @GetMapping
    public List<Post> listAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    // Return a specific post based on the postId.
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(post);
    }

    // Create a new post on User given by Logged In User
    @PostMapping
    public ResponseEntity<Post> createUserPost(@RequestBody Post post, Principal principal) {
        String userName = principal.getName();
        User user = userService.findUserByEmail(userName);
        post.setPostOwner(user);
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);

    }

    // Update the post based on the provided postId
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost, Principal principal) {
        Post post = postService.updatePost(id, updatedPost, principal);
        return ResponseEntity.ok(post);
    }

    // Delete the post based on the provided postId.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id, Principal principal) {
        Post post = postService.deletePost(id, principal);
        postRepository.delete(post);
    }
}
