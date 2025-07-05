package example.controller;

import example.model.Post;
import example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    public record Paging(int pageSize, int pageNumber, boolean hasNext, boolean hasPrevious) {
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        // Добавляем "заглушку" для paging
        Paging paging = new Paging(10, 1, false, false);
        model.addAttribute("paging", paging);

        return "posts";
    }
}
