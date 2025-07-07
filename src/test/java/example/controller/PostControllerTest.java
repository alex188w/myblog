package example.controller;

import example.entity.PostEntity;
import example.service.PostService;
import example.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PostControllerTest {

    private MockMvc mockMvc;
    private PostService postService;
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        postService = mock(PostService.class);
        commentService = mock(CommentService.class);

        PostController controller = new PostController(postService);
        // Подставляем commentService (если поле сделано protected)
        controller.commentService = commentService;

        // Добавляем ViewResolver, чтобы избежать ошибки Circular View
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAllPosts() throws Exception {
        PostEntity post = new PostEntity();
        post.setId(1L);
        post.setTitle("Заголовок");

        when(postService.getAllPostsWithComments()).thenReturn(List.of(post));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts"));
    }
}
