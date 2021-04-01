package project.dailysup.device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.account.domain.Account;
import project.dailysup.account.service.AccountRegisterService;
import project.dailysup.device.domain.Device;
import project.dailysup.device.domain.DeviceRepository;
import project.dailysup.device.dto.DeviceDto;
import project.dailysup.device.exception.DeviceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceService {

    private final AccountRegisterService accountRegisterService;
    private final DeviceRepository deviceRepository;

    @Transactional(readOnly = true)
    public List<DeviceDto> findAll(){
        Account currentAccount = accountRegisterService.findCurrentAccount();
        List<Device> devices = deviceRepository.findByAccount(currentAccount);
        return devices.stream().map(DeviceDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<Device> findByAccountList(List<Account> accountList){
        return deviceRepository.findByAccountList(accountList);
    }

    public void addDevice(DeviceDto deviceDto){
        Account currentAccount = accountRegisterService.findCurrentAccount();
        Device device = new Device(deviceDto.getFcmToken(), currentAccount);
        currentAccount.addDevice(device);
    }


    public void removeDevice(DeviceDto deviceDto) {
        Account currentAccount = accountRegisterService.findCurrentAccount();
        if(!currentAccount.removeDevice(deviceDto.getFcmToken())){
            throw new DeviceNotFoundException();
        }
    }

    public void removeAllCurrentAccountDevice(){
        Account currentAccount = accountRegisterService.findCurrentAccount();
        currentAccount.changeDeviceList(new ArrayList<>());
    }




}
