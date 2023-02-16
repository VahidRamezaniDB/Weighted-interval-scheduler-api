package personal.vahid.schedulerapi.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.vahid.schedulerapi.model.SymbolicJob;
import personal.vahid.schedulerapi.model.dto.RequestRealWorldScheduleDTO;
import personal.vahid.schedulerapi.model.dto.RequestSRWScheduleDTO;
import personal.vahid.schedulerapi.model.dto.RequestSymbolicScheduleDTO;
import personal.vahid.schedulerapi.service.ScheduleService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SchedulerController {

    ScheduleService scheduleService;

    @PostMapping("/sym")
    public ResponseEntity<Long> getOptimalSolution(@RequestBody RequestSymbolicScheduleDTO requestDto){
        Optional<Long> answer =  scheduleService.scheduleSymbolicJobs(requestDto.jobs());
        if(answer.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(answer);
    }

    @PostMapping("/rw")
    public ResponseEntity<Long> getOptimalRWSolution(@RequestBody RequestRealWorldScheduleDTO requestDto){
        Optional<Long> answer = scheduleService.scheduleRealWorldJobs(requestDto.jobs());
        if(answer.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(answer);
    }

    @PostMapping("/rw-simple")
    public ResponseEntity<Long> getOptimalSRWSolution(@RequestBody RequestSRWScheduleDTO requestDto){
        Optional<Long> answer = scheduleService.scheduleSimpleRealWorldJobs(requestDto.jobs());
        if(answer.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(answer);
    }

    @GetMapping("/test")
    public ResponseEntity<LocalDateTime> test(){
        return ResponseEntity.ok(LocalDateTime.now());
    }
}
