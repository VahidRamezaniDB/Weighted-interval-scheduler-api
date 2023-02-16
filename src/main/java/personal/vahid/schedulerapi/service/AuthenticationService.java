package personal.vahid.schedulerapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    RabbitTemplate rabbitTemplate;
    DirectExchange directExchange;
    ObjectMapper objectMapper;

    public boolean authenticate(String token){
        byte[] tokenByte;
        try{
            tokenByte = objectMapper.writeValueAsBytes(token);
        }catch (Exception e){
            log.error("Error in mapping token. {}", e.getMessage());
            return false;
        }

        Object response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), "auth", tokenByte);
        if(response == null){
            return false;
        }
        try {
            return (boolean) response;
        }catch (Exception e){
            log.error("Error in casting response. {}", e.getMessage());
            return false;
        }
    }
}
