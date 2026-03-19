package com.cibertec; // Ajusta a tu paquete

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esto le dice a Spring: "Si ves una ruta que empieza por /fotos/, 
        // búscala físicamente en la carpeta del disco".
        registry.addResourceHandler("/fotos/**")
                .addResourceLocations("file:src/main/resources/static/fotos/");
    }
}