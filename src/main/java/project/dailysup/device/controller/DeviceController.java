package project.dailysup.device.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.dailysup.device.domain.Device;
import project.dailysup.device.domain.DeviceRepository;
import project.dailysup.device.dto.DeviceDto;
import project.dailysup.device.service.DeviceQueryService;
import project.dailysup.device.service.DeviceService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final DeviceQueryService deviceQueryService;

    @GetMapping
    public ResponseEntity<?> findDevice(Pageable pageable){
        Page<DeviceDto> all = deviceQueryService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addDevice(@RequestBody DeviceDto dto){
        deviceService.addDevice(dto.getFcmToken());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeDevice(@RequestBody DeviceDto dto){
        deviceService.removeDevice(dto.getFcmToken());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/all")
    public ResponseEntity<?> removeAll(){
        deviceService.removeAllCurrentAccountDevice();
        return ResponseEntity.ok().build();
    }


}
