package personal.vahid.schedulerapi.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import personal.vahid.schedulerapi.model.configs.RabbitConfig;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class RabbitConfiguration {

    public static final String SCHEDULER_API_EXCHANGE = "scheduler-api";

    @Primary
    @Bean
    public ConnectionFactory connectionFactory(AppConfiguration appConfiguration){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        try{
            RabbitConfig config = appConfiguration.getRabbit();
            connectionFactory.setAddresses(config.getAddresses());
            connectionFactory.setUsername(config.getUsername());
            connectionFactory.setPassword(config.getPassword());
            connectionFactory.setVirtualHost(config.getVirtualHost());
        }catch (Exception e){
            log.error("Error in loading rabbit configuration." + e.getMessage());
        }
        return connectionFactory;
    }

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        return new RabbitTemplate(factory);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(SCHEDULER_API_EXCHANGE, true, false);
    }

}
