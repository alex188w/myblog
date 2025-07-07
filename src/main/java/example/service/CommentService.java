package example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import example.entity.CommentEntity;
import example.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    public List<CommentEntity> getCommentsByPostId(Long postId) {
        return commentRepo.findByPostId(postId);
    }

    public CommentEntity save(CommentEntity comment) {
        return commentRepo.save(comment);
    }

    public void delete(Long id) {
        commentRepo.deleteById(id);
    }

    public CommentEntity getById(Long id) {
        return commentRepo.findById(id).orElse(null);
    }
}