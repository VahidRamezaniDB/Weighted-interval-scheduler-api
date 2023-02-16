package personal.vahid.schedulerapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import personal.vahid.schedulerapi.model.SimpleRealWorldJob;

import java.util.List;

@Builder
public record RequestSRWScheduleDTO(@JsonProperty("jobs") List<SimpleRealWorldJob> jobs) {
}
