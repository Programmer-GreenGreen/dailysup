package project.dailysup.histroy.controller;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.histroy.dto.HistoryResponseDto;
import project.dailysup.histroy.service.HistoryCRUDService;
import project.dailysup.histroy.service.HistoryQueryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryQueryService historyQueryService;
    private final HistoryCRUDService historyCRUDService;

    @GetMapping("/{itemId}")
    public ResponseEntity<?> findAllHistory(@PathVariable Long itemId){
        List<HistoryResponseDto> allHistory = historyQueryService.findAllHistory(itemId);
        return ResponseEntity.ok(allHistory);
    }

    @PutMapping
    public ResponseEntity<?> changeHistory(@RequestBody HistoryChangeDto dto){
        historyCRUDService.changeHistoryDate(dto.getHistoryId(),dto.getReplaceDate());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{historyId}")
    public ResponseEntity<?> deleteHistory(@PathVariable Long historyId){
        historyCRUDService.deleteHistory(historyId);
        return ResponseEntity.ok().build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class HistoryChangeDto {

        private Long historyId;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate replaceDate;
    }
}
