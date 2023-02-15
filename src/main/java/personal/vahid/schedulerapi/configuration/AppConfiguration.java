package personal.vahid.schedulerapi.configuration;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import personal.vahid.schedulerapi.model.configs.RabbitConfig;

@Configuration
@ConfigurationProperties("app")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AppConfiguration {
    boolean readOnly;
    RabbitConfig rabbit = new RabbitConfig();
}
