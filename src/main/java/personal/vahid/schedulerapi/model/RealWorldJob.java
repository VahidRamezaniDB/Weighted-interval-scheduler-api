package personal.vahid.schedulerapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RealWorldJob {
    @JsonProperty("start")
    LocalDateTime start;
    @JsonProperty("end")
    LocalDateTime end;
    @JsonProperty("profit")
    long profit;
}
