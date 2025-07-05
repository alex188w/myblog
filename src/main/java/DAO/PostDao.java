package DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countPosts() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM posts", Integer.class);
    }
}
