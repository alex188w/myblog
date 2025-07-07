package example.service;

import example.entity.CommentEntity;
import example.entity.PostEntity;
import example.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepo;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentsByPostId() {
        PostEntity post = new PostEntity();
        post.setId(1L);

        CommentEntity comment1 = new CommentEntity();
        comment1.setText("Комментарий 1");
        comment1.setPost(post);

        CommentEntity comment2 = new CommentEntity();
        comment2.setText("Комментарий 2");
        comment2.setPost(post);

        when(commentRepo.findByPostId(1L)).thenReturn(Arrays.asList(comment1, comment2));

        List<CommentEntity> comments = commentService.getCommentsByPostId(1L);

        assertEquals(2, comments.size());
        assertEquals("Комментарий 1", comments.get(0).getText());
        verify(commentRepo).findByPostId(1L);
    }

    @Test
    void testSave() {
        CommentEntity comment = new CommentEntity();
        comment.setText("Новый комментарий");

        when(commentRepo.save(comment)).thenReturn(comment);

        CommentEntity saved = commentService.save(comment);

        assertNotNull(saved);
        assertEquals("Новый комментарий", saved.getText());
        verify(commentRepo).save(comment);
    }

    @Test
    void testDelete() {
        commentService.delete(5L);
        verify(commentRepo).deleteById(5L);
    }

    @Test
    void testGetByIdExists() {
        CommentEntity comment = new CommentEntity();
        comment.setId(42L);
        comment.setText("Комментарий по id");

        when(commentRepo.findById(42L)).thenReturn(Optional.of(comment));

        CommentEntity result = commentService.getById(42L);

        assertNotNull(result);
        assertEquals(42L, result.getId());
        assertEquals("Комментарий по id", result.getText());
    }

    @Test
    void testGetByIdNotExists() {
        when(commentRepo.findById(999L)).thenReturn(Optional.empty());

        CommentEntity result = commentService.getById(999L);

        assertNull(result);
    }
}
