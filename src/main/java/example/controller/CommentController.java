package example.controller;

import example.entity.CommentEntity;
import example.entity.PostEntity;
import example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestParam Long postId, @RequestParam String text) {
        CommentEntity comment = new CommentEntity();
        PostEntity post = new PostEntity();
        post.setId(postId); // proxy reference

        comment.setText(text);
        comment.setPost(post);

        commentService.save(comment);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{id}/edit")
    public String editComment(@PathVariable Long id, @RequestParam String text) {
        CommentEntity comment = commentService.getById(id);
        if (comment != null) {
            comment.setText(text);
            commentService.save(comment);
        }
        return "redirect:/posts/" + comment.getPost().getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        CommentEntity comment = commentService.getById(id);
        if (comment != null) {
            Long postId = comment.getPost().getId();
            commentService.delete(id);
            return "redirect:/posts/" + postId;
        }
        return "redirect:/posts";
    }
}
