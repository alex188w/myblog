package example.repository;

import example.entity.PostEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // Для предварительной загрузки комментриев
    @Query("SELECT p FROM PostEntity p LEFT JOIN FETCH p.comments")
    List<PostEntity> findAllWithComments();

    // Для пагинации на странице
    // 1. С поиском по тегу
    @Query(value = """
            SELECT DISTINCT p FROM PostEntity p
            LEFT JOIN FETCH p.comments
            LEFT JOIN p.tags t
            WHERE LOWER(t) LIKE LOWER(CONCAT('%', :tag, '%'))
            """, countQuery = """
            SELECT COUNT(DISTINCT p) FROM PostEntity p
            LEFT JOIN p.tags t
            WHERE LOWER(t) LIKE LOWER(CONCAT('%', :tag, '%'))
            """)
    Page<PostEntity> findByTagContainingIgnoreCase(@Param("tag") String tag, Pageable pageable);

    // 2. Без фильтра (все посты)
    @Query(value = """
            SELECT DISTINCT p FROM PostEntity p
            LEFT JOIN FETCH p.comments
            """, countQuery = """
            SELECT COUNT(p) FROM PostEntity p
            """)
    Page<PostEntity> findAllWithComments(Pageable pageable);
}
