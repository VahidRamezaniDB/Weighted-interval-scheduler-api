package personal.vahid.schedulerapi.service;

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
import java.util.OptionalLong;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    RabbitTemplate rabbitTemplate;
    FanoutExchange fanoutExchange;

    public Optional<Long> scheduleSymbolicJobs(List<SymbolicJob> jobs){
        RabbitSymbolicProducerDTO dto = RabbitSymbolicProducerDTO.builder()
                .jobs(jobs)
                .build();
        Object response = rabbitTemplate.convertSendAndReceive(fanoutExchange.getName(), "", dto);
        Long optimalAnswer;
        try {
            optimalAnswer = (Long)response;
        }catch (ClassCastException e){
            log.error("Could not cast response to long. {}", e.getMessage());
            return Optional.empty();
        }
        return Optional.of(optimalAnswer);
    }
}
