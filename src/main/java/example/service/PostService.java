package example.service;

import example.entity.PostEntity;
import example.repository.PostRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostEntity> getAllPosts() {
        return repository.findAll();
    }

    @Transactional
    public PostEntity findById(Long id) {
        PostEntity post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));

        // Принудительно загружаем ленивые коллекции
        post.getComments().size(); // инициализация
        
        return post;
    }

    public PostEntity getPostById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // public PostEntity findById(Long id) {
    //     return repository.findById(id).orElseThrow(() -> new RuntimeException("Пост не найден"));
    // }

    public PostEntity save(PostEntity post) {
        return repository.save(post);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
