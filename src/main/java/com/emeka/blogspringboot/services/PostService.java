package com.emeka.blogspringboot.services;

import com.emeka.blogspringboot.ResourceNotFoundException;
import com.emeka.blogspringboot.models.Author;
import com.emeka.blogspringboot.models.Post;
import com.emeka.blogspringboot.repositories.AuthorRepository;
import com.emeka.blogspringboot.repositories.CommentRepository;
import com.emeka.blogspringboot.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByAuthor(int authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public String createPost(Post post, int authorId)  {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("AuthorId %s not found", authorId)));
        post.setAuthor(author);
        postRepository.save(post);
        return "Post was created successfully";
    }

    public String editPost(Post post, int postId) {
        Post thePost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        thePost.setBody(post.getBody());
        thePost.setTitle(post.getTitle());
        postRepository.save(thePost);

        return "Post was edited successfully";
    }

    @Transactional
    public String deletePost(int postId) {
        commentRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
        return "Post deleted successfully";
    }
}
