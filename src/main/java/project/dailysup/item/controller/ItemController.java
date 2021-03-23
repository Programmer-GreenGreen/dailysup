package project.dailysup.item.controller;

import com.google.firebase.database.annotations.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.service.AccountService;
import project.dailysup.item.dto.*;
import project.dailysup.item.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getItems(){

        // TODO: Paging Query 로 변경하기
        List<ItemResponseDto> all = itemService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Long itemId){
        ItemResponseDto findItemDto = itemService.findItem(itemId);
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


    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Long itemId){
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/picture/{itemId}")
    public ResponseEntity<Resource> getPicture(@PathVariable Long itemId){

        byte[] profilePicture = itemService.getItemPicture(itemId);
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;

        String mimeType = new Tika().detect(profilePicture);
        rs = new ByteArrayResource(profilePicture);
        header.setContentType(MediaType.parseMediaType(mimeType));

        return new ResponseEntity<>(rs, header, HttpStatus.OK);
    }

    @PostMapping("/picture/{itemId}")
    public ResponseEntity<?> changePicture(@NotNull @RequestParam("file") MultipartFile file,
                                           @PathVariable Long itemId){
        itemService.changeItemPicture(itemId, file);
        return ResponseEntity.ok().build();
    }

}
