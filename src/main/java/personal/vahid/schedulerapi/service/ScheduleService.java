package personal.vahid.schedulerapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import personal.vahid.schedulerapi.model.RealWorldJob;
import personal.vahid.schedulerapi.model.SimpleRealWorldJob;
import personal.vahid.schedulerapi.model.SymbolicJob;
import personal.vahid.schedulerapi.model.dto.rabbit.RabbitRealWorldProducerDTO;
import personal.vahid.schedulerapi.model.dto.rabbit.RabbitSymbolicProducerDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    RabbitTemplate rabbitTemplate;
    DirectExchange directExchange;
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
        Object response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), "sym", dto);
        Long optimalAnswer;
        try {
            optimalAnswer = (Long)response;
        }catch (ClassCastException e){
            log.error("Could not cast response to long. {}", e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(optimalAnswer);
    }

    public Optional<Long> scheduleRealWorldJobs(List<RealWorldJob> jobs){
        RabbitRealWorldProducerDTO producerDTO = RabbitRealWorldProducerDTO.builder()
                .jobs(jobs)
                .build();
        byte[] dto;
        try {
            dto = objectMapper.writeValueAsBytes(producerDTO);
        } catch (JsonProcessingException e) {
            log.error("Could not map dto to bytes. {}", e.getMessage());
            return Optional.empty();
        }
        Object response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), "rw", dto);
        Long optimalAnswer;
        try {
            optimalAnswer = (Long)response;
        }catch (ClassCastException e){
            log.error("Could not cast response to long. {}", e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(optimalAnswer);
    }

    public Optional<Long> scheduleSimpleRealWorldJobs(List<SimpleRealWorldJob> jobs){

        List<RealWorldJob> realWorldJobs = jobs.stream()
                .map(job -> {
                    ChronoUnit unit =switch (job.getUnitSlug()){
                        case "day" -> ChronoUnit.DAYS;
                        case "hour" -> ChronoUnit.HOURS;
                        case "minute" -> ChronoUnit.MINUTES;
                        default -> ChronoUnit.SECONDS;
                    };
                    LocalDateTime now = LocalDateTime.now();
                    return RealWorldJob.builder()
                            .start(now.plus(job.getStart(), unit))
                            .end(now.plus(job.getEnd(), unit))
                            .profit(job.getProfit())
                            .build();
                })
                .toList();
        return scheduleRealWorldJobs(realWorldJobs);
    }
}
