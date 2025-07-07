package example.service;

import example.entity.PostEntity;
import example.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PostEntity save(PostEntity post) {
        return repository.save(post);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<PostEntity> getAllPostsWithComments() {
        return repository.findAllWithComments();
    }

    public Page<PostEntity> findByTag(String tag, Pageable pageable) {
        if (tag == null || tag.isBlank()) {
            return repository.findAllWithComments(pageable);
        }
        return repository.findByTagContainingIgnoreCase(tag, pageable);
    }
}
