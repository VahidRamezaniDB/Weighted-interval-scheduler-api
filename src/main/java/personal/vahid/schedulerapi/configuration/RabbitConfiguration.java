package personal.vahid.schedulerapi.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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

    public static final String SCHEDULER_API_EXCHANGE = "scheduler-api-exchange";
    public static final String SCHEDULER_API_REPLY_QUEUE = "scheduler-api-reply-queue";

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
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(SCHEDULER_API_EXCHANGE);
    }

    @Bean
    public Queue queue(){
        return new Queue(SCHEDULER_API_REPLY_QUEUE, true);
    }

    @Bean
    public Binding binding(FanoutExchange exchange, Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
