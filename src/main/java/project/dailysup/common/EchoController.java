package project.dailysup.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EchoController {

    @GetMapping("/api/echo/{message}")
    public ResponseEntity<?> echo(@PathVariable String message){
        log.info("echo: " + message);
        return ResponseEntity.ok(message);
    }
}
