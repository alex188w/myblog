package example.repository;

import example.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostId(Long postId);
}
