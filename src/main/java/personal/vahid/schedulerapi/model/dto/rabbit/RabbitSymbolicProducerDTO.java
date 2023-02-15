package personal.vahid.schedulerapi.model.dto.rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import personal.vahid.schedulerapi.model.SymbolicJob;

import java.util.List;

@Builder
public record RabbitSymbolicProducerDTO(@JsonProperty("jobs") List<SymbolicJob> jobs) {
}
