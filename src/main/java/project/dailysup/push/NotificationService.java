package project.dailysup.push;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import project.dailysup.item.domain.Item;
import project.dailysup.item.service.ItemService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final ItemService itemService;
    private final MessageProvider messageProvider;

    @Async("basicThreadPool")
    public void send(LocalDate now) throws FirebaseMessagingException {
        List<Item> scheduledItems = itemService.getScheduledItems(now);
        List<Message> fcmMessages = messageProvider.getFcmMessages(scheduledItems);
        BatchResponse response = FirebaseMessaging.getInstance().sendAll(fcmMessages);

        log.info("Failure Count: "+ response.getFailureCount());

    }
}
