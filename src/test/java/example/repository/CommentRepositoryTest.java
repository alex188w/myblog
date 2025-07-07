package example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import example.config.JpaConfig;
import example.entity.CommentEntity;
import example.entity.PostEntity;
import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaConfig.class})
@TestPropertySource(locations = "classpath:app.properties")
@Transactional
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testFindByPostId() {
        // создаём и сохраняем пост
        PostEntity post = new PostEntity();
        post.setTitle("Тестовый пост");
        post.setText("Текст поста");
        post = postRepository.save(post);

        // создаём и сохраняем комментарии
        CommentEntity c1 = new CommentEntity();
        c1.setText("Комментарий 1");
        c1.setPost(post);

        CommentEntity c2 = new CommentEntity();
        c2.setText("Комментарий 2");
        c2.setPost(post);

        commentRepository.saveAll(List.of(c1, c2));

        // тестируем метод
        List<CommentEntity> comments = commentRepository.findByPostId(post.getId());

        assertEquals(2, comments.size());
        assertTrue(comments.stream().anyMatch(c -> c.getText().equals("Комментарий 1")));
        assertTrue(comments.stream().anyMatch(c -> c.getText().equals("Комментарий 2")));
    }
}
