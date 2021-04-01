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
import project.dailysup.item.service.ItemPictureService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemPictureController {

    private final ItemPictureService itemPictureService;

    @GetMapping("/{itemId}/picture")
    public ResponseEntity<Resource> getPicture(@PathVariable Long itemId){

        byte[] profilePicture = itemPictureService.getItemPicture(itemId);
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;

        String mimeType = new Tika().detect(profilePicture);
        rs = new ByteArrayResource(profilePicture);
        header.setContentType(MediaType.parseMediaType(mimeType));

        return new ResponseEntity<>(rs, header, HttpStatus.OK);
    }

    @PostMapping("/{itemId}/picture")
    public ResponseEntity<?> changePicture(@NotNull @RequestParam("file") MultipartFile file,
                                           @PathVariable Long itemId){
        itemPictureService.changeItemPicture(itemId, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemId}/picture")
    public ResponseEntity<?> deletePicture(@PathVariable Long itemId){
        itemPictureService.deleteItemPicture(itemId);
        return ResponseEntity.ok().build();
    }
}
