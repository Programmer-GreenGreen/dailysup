package project.dailysup.push;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendPushScheduledAlarm(){
        try {
            notificationService.send(LocalDate.now());
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }
    }
}
