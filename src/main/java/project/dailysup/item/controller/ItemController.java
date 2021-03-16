package project.dailysup.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.item.dto.*;
import project.dailysup.item.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<?> getItems(){

        // TODO: Paging Query 로 변경하기
        List<ItemResponseDto> all = itemService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id){
        ItemResponseDto findItemDto = itemService.findItem(id);
        return ResponseEntity.ok(findItemDto);
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemCreateDto dto){
        ItemIdDto itemIdDto = itemService.addItem(dto);
        return ResponseEntity.ok(itemIdDto);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<?> changeItemBasicInfo(@PathVariable Long itemId, @RequestBody ItemBasicInfoDto dto){
        ItemIdDto itemIdDto = itemService.changeItemBasicInfo(itemId, dto);
        return ResponseEntity.ok(itemIdDto);
    }

    @PostMapping("/update/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateDto dto){
        ItemIdDto itemIdDto = itemService.updateItem(itemId, dto);
        return ResponseEntity.ok(itemIdDto);
    }



    @DeleteMapping
    public ResponseEntity<?> deleteItem(@RequestBody ItemDeleteDto dto){
        Long deletedItemId = itemService.deleteItem(dto.getItemId());
        ItemIdDto itemIdDto = new ItemIdDto(deletedItemId);
        return ResponseEntity.ok(itemIdDto);
    }

}
