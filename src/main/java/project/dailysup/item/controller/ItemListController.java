package project.dailysup.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.item.dto.*;
import project.dailysup.item.service.ItemCRUDService;
import project.dailysup.item.service.ItemQueryService;

import java.util.List;

// TODO: Dto 서비스 단으로 넘기지 말기.
// TODO: Dto static class 로 가져오기.
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemListController {

    private final ItemQueryService itemQueryService;
    private final ItemCRUDService itemCRUDService;

    @GetMapping
    public ResponseEntity<?> getItems(){

        // TODO: Paging Query 로 변경하기
        List<ItemResponseDto> all = itemQueryService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemCreateDto dto){
        ItemIdDto itemIdDto = itemCRUDService.addItem(dto);
        return ResponseEntity.ok(itemIdDto);
    }

}
