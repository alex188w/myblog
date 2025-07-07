package example.repository;

import example.config.AppConfig;
import example.config.JpaConfig;
import example.entity.PostEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, JpaConfig.class})
@TestPropertySource("classpath:app.properties")
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        PostEntity post1 = new PostEntity();
        post1.setTitle("Первый пост");
        post1.setText("Содержимое первого поста");
        post1.setTags(List.of("java", "spring"));

        PostEntity post2 = new PostEntity();
        post2.setTitle("Второй пост");
        post2.setText("Содержимое второго поста");
        post2.setTags(List.of("java"));

        postRepository.save(post1);
        postRepository.save(post2);
    }

    @Test
    void testFindAllWithComments() {
        List<PostEntity> posts = postRepository.findAllWithComments();
        assertFalse(posts.isEmpty());
    }

    @Test
    void testFindByTagContainingIgnoreCase() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<PostEntity> page = postRepository.findByTagContainingIgnoreCase("spring", pageable);
        assertEquals(1, page.getTotalElements());
        assertEquals("Первый пост", page.getContent().get(0).getTitle());
    }

    @Test
    void testFindAllWithCommentsPageable() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<PostEntity> page = postRepository.findAllWithComments(pageable);
        assertEquals(2, page.getTotalElements());
    }
}