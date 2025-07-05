package example.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // 👇 Добавляем поддержку multipart-запросов здесь
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                null, 10_000_000, 10_000_000, 1_000_000
        );
        dispatcher.setMultipartConfig(multipartConfig);
    }
}
