package edu.fudan.config;

import edu.fudan.repository.UserRepository;
import edu.fudan.rest.AuthorizationInterceptor;
import edu.fudan.rest.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final UserRepository userRepository;

    private final AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    public MvcConfig(UserRepository userRepository, AuthorizationInterceptor authorizationInterceptor) {
        this.userRepository = userRepository;
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserMethodArgumentResolver(userRepository));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

//    @Bean
//    public WebMvcConfigurer resourceConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/**")
//                        .addResourceLocations("file:"+ Constants.FILE_PATH, Constants.LECTURE_PATH);
//            }
//        };
//    }


}
