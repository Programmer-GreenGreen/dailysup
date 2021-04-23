package project.dailysup.device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<DeviceDto> findAll(Pageable pageable){
        Account currentAccount = accountBaseService.getCurrentAccount();
        Page<Device> devices = deviceRepository.findByAccount(currentAccount, pageable);
        return devices.map(DeviceDto::new);
    }
    @Transactional(readOnly = true)
    public List<Device> findByAccountList(List<Account> accountList){
        return deviceRepository.findByAccountList(accountList);
    }
}
