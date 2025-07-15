package summer_project.llm_chatbot.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        SecurityScheme bearerTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("로컬 서버");

        Server prodServer = new Server()
                .url("https://api.example.com")
                .description("운영 서버 (Production)");

        return new OpenAPI()
                .info(new Info()
                              .title("LLM Chatbot")
                              .version("1.0"))
                .components(new Components()
                                    .addSecuritySchemes("bearerAuth", bearerTokenScheme))
                .servers(List.of(localServer, prodServer));
    }
}
