package example.service;

import example.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    public List<Post> getAllPosts() {
        // Заглушка: возвращает 2 фиктивных поста
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Первый пост", "Первые строки...", "https://via.placeholder.com/150", List.of("java", "spring"), 2, 10));
        posts.add(new Post(2L, "Второй пост", "Описание второго...", "https://via.placeholder.com/150", List.of("blog"), 0, 5));
        return posts;
    }
}
