package project.dailysup.device.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.dailysup.device.domain.Device;
import project.dailysup.device.domain.DeviceRepository;
import project.dailysup.device.dto.DeviceDto;
import project.dailysup.device.service.DeviceService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<?> findDevice(){
        List<DeviceDto> all = deviceService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addDevice(@RequestBody DeviceDto deviceDto){
        deviceService.addDevice(deviceDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeDevice(@RequestBody DeviceDto deviceDto){
        deviceService.removeDevice(deviceDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/all")
    public ResponseEntity<?> removeAll(){
        deviceService.removeAllCurrentAccountDevice();
        return ResponseEntity.ok().build();
    }


}
