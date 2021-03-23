package project.dailysup.push;


import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import project.dailysup.account.domain.Account;
import project.dailysup.device.domain.Device;
import project.dailysup.device.service.DeviceService;
import project.dailysup.item.domain.Item;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class MessageProvider {

    private final DeviceService deviceService;

    private static final String NOTIFICATION_TITLE = "교체일 알림";
    private static final String NOTIFICATION_BODY = "교체 예정 용품이 있습니다.";

    public List<Message> getFcmMessages(List<Item> scheduledItems){

        MultiValueMap<Account, Item> accountItem = new LinkedMultiValueMap<>();
        MultiValueMap<Account, String> accountDeviceToken = new LinkedMultiValueMap<>();

        scheduledItems
                .forEach(item -> accountItem.add(item.getAccount(), item));

        List<Account> accountList = new ArrayList<>(accountItem.keySet());
        List<Device> deviceList = deviceService.findByAccountList(accountList);

        deviceList
                .forEach(device -> accountDeviceToken.add(device.getAccount(), device.getFcmToken()));

        List<Message> messages = new ArrayList<>();

        for(Account account : accountList){
            List<String> tokens = accountDeviceToken.get(account);
            List<Item> items = accountItem.get(account);


            for(String token : tokens){
                Notification notification = new Notification(NOTIFICATION_TITLE, NOTIFICATION_BODY);
                Message.Builder builder = Message.builder()
                                                    .setNotification(notification)
                                                    .setToken(token);
                for(Item item : items){
                    builder
                            .putData(item.getId().toString(), item.getTitle());
                }
                messages.add(builder.build());
            }
        }

        return messages;

    }



}
