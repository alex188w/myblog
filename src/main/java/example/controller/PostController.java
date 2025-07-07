package example.controller;

import example.entity.CommentEntity;
import example.entity.PostEntity;
import example.service.PostService;
import example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    protected CommentService commentService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    public record Paging(int pageSize, int pageNumber, boolean hasNext, boolean hasPrevious) {
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<PostEntity> posts = postService.getAllPostsWithComments();
        model.addAttribute("posts", posts);
        model.addAttribute("paging");
        return "posts";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostEntity post = postService.findById(id);
        model.addAttribute("post", post);
        return "post"; // шаблон post.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("post", new PostEntity()); // id будет null → создание
        return "add-post";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        PostEntity post = postService.getPostById(id);
        model.addAttribute("post", post); // id != null → редактирование
        return "add-post"; // Используем тот же шаблон
    }

    @PostMapping
    public String savePost(@ModelAttribute PostEntity post,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        postService.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute PostEntity post) {
        post.setId(id);
        postService.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    // обработка лайков
    @PostMapping("/{id}/like")
    public String likePost(@PathVariable Long id, @RequestParam boolean like) {
        PostEntity post = postService.getPostById(id);
        int current = post.getLikes();
        post.setLikes(like ? current + 1 : Math.max(0, current - 1));
        postService.save(post);
        return "redirect:/posts/" + id;
    }

    // добавление комментария
    @PostMapping("/{postId}/comments")
    public String addComment(@PathVariable Long postId, @RequestParam("text") String text) {
        PostEntity post = postService.getPostById(postId);
        CommentEntity comment = new CommentEntity();
        comment.setPost(post);
        comment.setText(text);
        commentService.save(comment); // <--- Используем CommentService
        return "redirect:/posts/" + postId;
    }

    // Обновление комментария
    @PostMapping("/{postId}/comments/{commentId}")
    public String updateComment(@PathVariable Long postId, @PathVariable Long commentId,
            @RequestParam("text") String newText) {
        CommentEntity comment = commentService.getById(commentId);
        if (comment != null && comment.getPost().getId().equals(postId)) {
            comment.setText(newText);
            commentService.save(comment);
        }
        return "redirect:/posts/" + postId;
    }

    // Удаление комментария
    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        CommentEntity comment = commentService.getById(commentId);
        if (comment != null && comment.getPost().getId().equals(postId)) {
            commentService.delete(commentId);
        }
        return "redirect:/posts/" + postId;
    }

    // Загрузка изображений
    @PostMapping(value = "/uploadImage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "C:/myapp/uploads/";
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            System.out.println("Файл: " + filename);
            Path filepath = Paths.get(uploadDir, filename);
            Files.createDirectories(filepath.getParent());
            file.transferTo(filepath);
            String fileUrl = "/uploads/" + filename;
            return Map.of("url", fileUrl);
        } catch (Exception e) {
            e.printStackTrace(); // Вывод полной ошибки
            throw new RuntimeException("Ошибка загрузки файла: " + e.getMessage(), e);
        }
    }

    // Для пагинации и поиска
    @GetMapping("/posts")
    public String listPosts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<PostEntity> page = postService.findByTag(search, pageRequest);

        model.addAttribute("posts", page.getContent());
        model.addAttribute("paging", page); // для кнопок пагинации
        model.addAttribute("search", search); // чтобы вернуть строку поиска
        return "posts";
    }
}
