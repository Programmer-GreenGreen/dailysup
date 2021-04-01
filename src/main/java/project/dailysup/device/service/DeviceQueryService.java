package project.dailysup.device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.account.domain.Account;
import project.dailysup.account.service.AccountBaseService;
import project.dailysup.device.domain.Device;
import project.dailysup.device.domain.DeviceRepository;
import project.dailysup.device.dto.DeviceDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceQueryService {

    private final AccountBaseService accountBaseService;
    private final DeviceRepository deviceRepository;

    @Transactional(readOnly = true)
    public List<DeviceDto> findAll(){
        Account currentAccount = accountBaseService.getCurrentAccount();
        List<Device> devices = deviceRepository.findByAccount(currentAccount);
        return devices.stream().map(DeviceDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<Device> findByAccountList(List<Account> accountList){
        return deviceRepository.findByAccountList(accountList);
    }
}
