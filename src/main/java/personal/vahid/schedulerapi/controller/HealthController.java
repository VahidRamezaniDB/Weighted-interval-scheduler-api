package personal.vahid.schedulerapi.controller;

import jakarta.persistence.EntityManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    EntityManager entityManager;

    @GetMapping(produces = "application/json")
    public Boolean health(){
        return (Boolean) entityManager.createNativeQuery("SELECT true")
                .getSingleResult();
    }
}
