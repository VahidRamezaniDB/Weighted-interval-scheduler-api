package personal.vahid.schedulerapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import personal.vahid.schedulerapi.model.RealWorldJob;

import java.util.List;

@Builder
public record RequestRealWorldScheduleDTO(@JsonProperty("jobs")List<RealWorldJob> jobs) {
}
