package personal.vahid.schedulerapi.model.configs;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitConfig {
    String addresses;
    String username;
    String password;
    String virtualHost;
}
