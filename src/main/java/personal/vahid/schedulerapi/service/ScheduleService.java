package personal.vahid.schedulerapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import personal.vahid.schedulerapi.model.SymbolicJob;
import personal.vahid.schedulerapi.model.dto.rabbit.RabbitSymbolicProducerDTO;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    RabbitTemplate rabbitTemplate;
    FanoutExchange fanoutExchange;
    ObjectMapper objectMapper;

    public Optional<Long> scheduleSymbolicJobs(List<SymbolicJob> jobs){
        RabbitSymbolicProducerDTO producerDTO = RabbitSymbolicProducerDTO.builder()
                .jobs(jobs)
                .build();
        byte[] dto;
        try {
            dto = objectMapper.writeValueAsBytes(producerDTO);
        } catch (JsonProcessingException e) {
            log.error("Could not map dto to bytes. {}", e.getMessage());
            return Optional.empty();
        }
        Object response = rabbitTemplate.convertSendAndReceive(fanoutExchange.getName(), "", dto);
        Long optimalAnswer;
        try {
            optimalAnswer = (Long)response;
        }catch (ClassCastException e){
            log.error("Could not cast response to long. {}", e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(optimalAnswer);
    }
}
