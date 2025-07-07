package example.repository;

import example.config.JpaConfig;
import example.config.AppConfig;
import example.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaConfig.class})
@TestPropertySource(locations = "classpath:app.properties")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testSaveAndFind() {
        PostEntity post = new PostEntity();
        post.setTitle("Test title");
        post.setPreview("Test preview");
        post.setText("Test text");
        post.setLikes(5);
        post.setCommentsCount(0);
        post.setTags(List.of("java", "spring"));

        PostEntity saved = postRepository.save(post);
        assertNotNull(saved.getId());

        PostEntity found = postRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Test title", found.getTitle());
        assertTrue(found.getTags().contains("spring"));
    }

    @Test
    public void testFindAllWithComments() {
        List<PostEntity> posts = postRepository.findAllWithComments();
        assertNotNull(posts);
    }

    @Test
    public void testFindByTagContainingIgnoreCase() {
        Page<PostEntity> page = postRepository.findByTagContainingIgnoreCase("java", PageRequest.of(0, 10));
        assertNotNull(page);
    }
}