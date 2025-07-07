package example.service;

import example.entity.PostEntity;
import example.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private PostRepository repository;
    private PostService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PostRepository.class);
        service = new PostService(repository);
    }

    @Test
    void testGetAllPosts() {
        List<PostEntity> mockPosts = Arrays.asList(new PostEntity(), new PostEntity());
        when(repository.findAll()).thenReturn(mockPosts);

        List<PostEntity> posts = service.getAllPosts();
        assertEquals(2, posts.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        PostEntity mockPost = new PostEntity();
        mockPost.setId(1L);
        mockPost.setComments(List.of());
        when(repository.findById(1L)).thenReturn(Optional.of(mockPost));

        PostEntity result = service.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    @Test
    void testSave() {
        PostEntity post = new PostEntity();
        when(repository.save(post)).thenReturn(post);

        PostEntity saved = service.save(post);
        assertNotNull(saved);
        verify(repository).save(post);
    }

    @Test
    void testDelete() {
        service.delete(42L);
        verify(repository).deleteById(42L);
    }

    @Test
    void testFindByTagEmpty() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<PostEntity> page = new PageImpl<>(List.of());
        when(repository.findAllWithComments(pageable)).thenReturn(page);

        Page<PostEntity> result = service.findByTag("", pageable);
        assertEquals(0, result.getContent().size());
    }

    @Test
    void testFindByTagNonEmpty() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<PostEntity> page = new PageImpl<>(List.of(new PostEntity()));
        when(repository.findByTagContainingIgnoreCase("tag", pageable)).thenReturn(page);

        Page<PostEntity> result = service.findByTag("tag", pageable);
        assertEquals(1, result.getContent().size());
    }
}
