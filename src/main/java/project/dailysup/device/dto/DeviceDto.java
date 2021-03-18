package project.dailysup.device.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.dailysup.device.domain.Device;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeviceDto {

    private String fcmToken;

    public DeviceDto(Device device){
        fcmToken = device.getFcmToken();
    }

}
