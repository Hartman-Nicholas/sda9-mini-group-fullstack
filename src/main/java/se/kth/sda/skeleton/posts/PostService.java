package se.kth.sda.skeleton.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.sda.skeleton.exception.ResourceNotFoundException;
import se.kth.sda.skeleton.user.User;
import se.kth.sda.skeleton.user.UserService;

import java.security.Principal;

@Service
public class PostService {

    PostRepository postRepository;
    UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post updatePost(Long id, Post updatedPost, Principal principal) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        String userName = principal.getName();
        User user = userService.findUserByEmail(userName);
        // Security measure to ensure a logged in User doesnt access the update Route
        // and update someone elses post.
        if (!userName.equals(post.getPostOwner().getEmail())) {
            throw new ResourceNotFoundException();

        }
        updatedPost = post.setUpdatePostValues(updatedPost);
        updatedPost.setId(id);
        updatedPost.setPostOwner(user);
        postRepository.save(updatedPost);
        return updatedPost;
    }

    public Post deletePost(Long id, Principal principal) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        String userName = principal.getName();
        // Security measure to ensure a logged in User doesnt access the update Route
        // and update someone elses post.
        if (!userName.equals(post.getPostOwner().getEmail())) {
            throw new ResourceNotFoundException();
        }
        return post;
    }
}
