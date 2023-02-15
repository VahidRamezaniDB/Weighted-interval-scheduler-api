package personal.vahid.schedulerapi.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SymbolicJob {
    int start;
    int end;
    long profit;
}
