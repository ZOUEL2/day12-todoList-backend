package oocl.example.todolistbackend.agent;

import oocl.example.todolistbackend.service.TodoService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MCPConfig {

    @Bean
    public ToolCallbackProvider myTools(TodoService todoService) {
        return MethodToolCallbackProvider.builder().toolObjects(todoService).build();

    }
}
