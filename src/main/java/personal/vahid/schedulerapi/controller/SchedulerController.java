package personal.vahid.schedulerapi.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.vahid.schedulerapi.model.SymbolicJob;
import personal.vahid.schedulerapi.model.dto.RequestSymbolicScheduleDTO;
import personal.vahid.schedulerapi.service.ScheduleService;

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
}
