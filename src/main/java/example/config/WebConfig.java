package example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// для загрузки изображений
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Путь URL, по которому будут доступны файлы из папки uploads
        registry.addResourceHandler("/uploads/**")
                // Путь на диске, где лежат файлы (обязательно заканчивается на /)
                .addResourceLocations("file:C:/myapp/uploads/");
    }
}
