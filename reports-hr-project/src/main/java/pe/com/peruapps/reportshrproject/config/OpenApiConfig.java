package pe.com.peruapps.reportshrproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Reportes HR",
            version = "v1",
            description = "Documentacion simple de los reportes de RRHH"))
public class OpenApiConfig {
}
