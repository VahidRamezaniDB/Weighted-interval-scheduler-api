package personal.vahid.schedulerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SchedulerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApiApplication.class, args);
    }

}
