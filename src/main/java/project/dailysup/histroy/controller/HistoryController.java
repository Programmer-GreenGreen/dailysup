package project.dailysup.histroy.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.histroy.dto.HistoryIdDto;
import project.dailysup.histroy.dto.HistoryRequestDto;
import project.dailysup.histroy.dto.HistoryResponseDto;
import project.dailysup.histroy.service.HistoryService;
import project.dailysup.item.dto.ItemIdDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{itemId}")
    public ResponseEntity<?> findAllHistory(@PathVariable Long itemId){
        List<HistoryResponseDto> allHistory = historyService.findAllHistory(itemId);
        return ResponseEntity.ok(allHistory);
    }

    @PutMapping
    public ResponseEntity<?> changeHistory(@RequestBody HistoryRequestDto dto){
        historyService.changeHistoryDate(dto.getHistoryId(),dto.getReplaceDate());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{historyId}")
    public ResponseEntity<?> deleteHistory(@PathVariable Long historyId){
        historyService.deleteHistory(historyId);
        return ResponseEntity.ok().build();
    }

}
