// package example.service;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// import example.config.AppConfig;
// import example.config.JpaConfig;
// import jakarta.transaction.Transactional;

// import org.springframework.test.context.TestPropertySource;

// import javax.sql.DataSource;
// import java.sql.Connection;
// import java.sql.SQLException;

// import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(SpringExtension.class)
// @ContextConfiguration(classes = {AppConfig.class, JpaConfig.class})
// @TestPropertySource(locations = "classpath:app.properties")
// @Transactional
// public class H2ConnectionTest {

//     @Autowired
//     private DataSource dataSource;

//     @Test
//     void h2ShouldBeAvailable() throws SQLException {
//         assertNotNull(dataSource);
//         try (Connection conn = dataSource.getConnection()) {
//             System.out.println("✅ H2 подключена: " + conn.getMetaData().getURL());
//         }
//     }
// }
