package project.dailysup.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getItems(Pageable pageable){

        Page<ItemResponseDto> all = itemQueryService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemCreateDto dto){
        ItemIdDto itemIdDto = itemCRUDService.addItem(dto);
        return ResponseEntity.ok(itemIdDto);
    }

}
