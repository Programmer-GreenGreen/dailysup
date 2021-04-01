package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.service.AccountBaseService;
import project.dailysup.item.domain.Item;
import project.dailysup.item.domain.ItemRepository;
import project.dailysup.item.exception.ItemNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemPictureService {

    private final AccountBaseService accountBaseService;
    private final ItemRepository itemRepository;
    private final ItemPictureFileService itemPictureFileService;

    @Transactional(readOnly = true)
    public byte[] getItemPicture(Long itemId){
        String loginId = accountBaseService.getCurrentAccount().getLoginId();
        Item findItem = itemRepository.findOneById(loginId, itemId)
                .orElseThrow(() -> new ItemNotFoundException("아이템을 찾을 수 없습니다."));
        return itemPictureFileService.download(findItem.getItemPictureUrl());
    }

    public void changeItemPicture(Long itemId, MultipartFile itemPicture){
        String loginId = accountBaseService.getCurrentAccount().getLoginId();
        Item findItem = itemRepository.findOneById(loginId, itemId)
                .orElseThrow(() -> new ItemNotFoundException("아이템을 찾을 수 없습니다."));
        String oldItemPictureUrl = findItem.getItemPictureUrl();
        String uploadedPictureUrl = null;
        if(!StringUtils.hasText(oldItemPictureUrl)){
            uploadedPictureUrl = itemPictureFileService.upload(itemPicture);
            findItem.changeItemPicture(uploadedPictureUrl);
        } else{
            itemPictureFileService.modify(oldItemPictureUrl, itemPicture);
        }
    }

    public void deleteItemPicture(Long itemId){
        String loginId = accountBaseService.getCurrentAccount().getLoginId();
        Item findItem = itemRepository.findOneById(loginId, itemId)
                .orElseThrow(() -> new ItemNotFoundException("아이템을 찾을 수 없습니다."));
        String itemPictureUrl = findItem.getItemPictureUrl();
        if(StringUtils.hasText(itemPictureUrl)){
            itemPictureFileService.delete(itemPictureUrl);
            findItem.changeItemPicture("");
        }
    }
}
