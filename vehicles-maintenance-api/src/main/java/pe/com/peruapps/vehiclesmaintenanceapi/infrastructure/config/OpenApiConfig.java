package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Vehicles Maintenance API",
            version = "v1.0.0",
            description =
                "API REST para la gestión de vehículos y sus mantenimientos. "
                    + "Permite registrar vehículos (automóviles y camiones), programar mantenimientos, "
                    + "y realizar seguimiento del estado de los vehículos. "
                    + "Las validaciones se documentan automáticamente usando JSR-303 Bean Validation."))
public class OpenApiConfig {}
