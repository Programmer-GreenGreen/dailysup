package project.dailysup.item.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.item.dto.ItemBasicInfoDto;
import project.dailysup.item.dto.ItemIdDto;
import project.dailysup.item.dto.ItemResponseDto;
import project.dailysup.item.service.ItemCRUDService;
import project.dailysup.item.service.ItemQueryService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items/{itemId}")
public class ItemController {

    private final ItemQueryService itemQueryService;
    private final ItemCRUDService itemCRUDService;


    @GetMapping
    public ResponseEntity<?> getItem(@PathVariable Long itemId){
        ItemResponseDto findItemDto = itemQueryService.findItem(itemId);
        return ResponseEntity.ok(findItemDto);
    }

    @PostMapping
    public ResponseEntity<?> changeItemBasicInfo(@PathVariable Long itemId,
                                                 @RequestBody ItemBasicInfoDto dto){
        ItemIdDto itemIdDto = itemCRUDService.changeItemBasicInfo(itemId, dto);
        return ResponseEntity.ok(itemIdDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Long itemId){
        itemCRUDService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/changeDay")
    public ResponseEntity<?> updateItem(@PathVariable Long itemId,
                                        @RequestBody ItemUpdateDto dto){
        ItemIdDto itemIdDto = itemCRUDService.updateItem(itemId, dto.getChangeDate());
        return ResponseEntity.ok(itemIdDto);
    }

    @Data
    public static class ItemUpdateDto {

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate changeDate;

    }
}
