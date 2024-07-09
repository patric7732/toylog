package org.example.toylog.domain.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.post.repository.PostRepository;
import org.example.toylog.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
