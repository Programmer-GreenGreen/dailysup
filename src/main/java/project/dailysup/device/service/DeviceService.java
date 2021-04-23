package project.dailysup.device.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.account.domain.Account;
import project.dailysup.account.service.AccountBaseService;
import project.dailysup.account.service.AccountRegisterService;
import project.dailysup.device.domain.Device;
import project.dailysup.device.domain.DeviceRepository;
import project.dailysup.device.dto.DeviceDto;
import project.dailysup.device.exception.DeviceNotFoundException;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeviceService {

    private final AccountBaseService accountBaseService;

    public void addDevice(String fcmToken){
        Account currentAccount = accountBaseService.getCurrentAccount();
        Device device = new Device(fcmToken, currentAccount);
        currentAccount.addDevice(device);

        log.info(LogFactory.create(LogCode.ADD_DEV, currentAccount.getLoginId()));
    }


    public void removeDevice(String fcmToken) {
        Account currentAccount = accountBaseService.getCurrentAccount();
        if(!currentAccount.removeDevice(fcmToken)){
            throw new DeviceNotFoundException();
        }
        log.info(LogFactory.create(LogCode.RM_DEV, currentAccount.getLoginId()));
    }

    public void removeAllCurrentAccountDevice(){
        Account currentAccount = accountBaseService.getCurrentAccount();
        currentAccount.changeDeviceList(new ArrayList<>());
        log.info(LogFactory.create(LogCode.RM_ALL_DEV, currentAccount.getLoginId()));
    }




}
