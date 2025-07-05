package example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTestController {
    // Для тестирования подключения к БД PostgreSQL

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/db-check")
    public String checkDb() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "DB OK: " + result;
        } catch (Exception e) {
            return "DB ERROR: " + e.getMessage();
        }
    }
}
