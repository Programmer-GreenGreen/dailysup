package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.domain.Account;
import project.dailysup.account.service.AccountBaseService;
import project.dailysup.account.service.AccountRegisterService;
import project.dailysup.histroy.domain.History;
import project.dailysup.histroy.service.HistoryService;
import project.dailysup.item.domain.Item;
import project.dailysup.item.domain.ItemRepository;
import project.dailysup.item.dto.*;
import project.dailysup.item.exception.ItemNotFoundException;
import project.dailysup.util.SecurityUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


// TODO: ItemService 쪼개기
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final AccountBaseService accountBaseService;
    private final HistoryService historyService;
    private final ItemPictureFileService itemPictureFileService;

    @Transactional(readOnly = true)
    public List<ItemResponseDto> findAll() {

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        List<Item> findAllItems = itemRepository.findAllByAccount(currentAccountId);

        return findAllItems.stream()
                .map(i -> ItemResponseDto.of(i, historyService.getStartDate(i), historyService.getLatestDate(i)))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ItemResponseDto findItem(Long itemId){

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Item findItem = itemRepository
                .findOneById(currentAccountId,itemId)
                .orElseThrow(ItemNotFoundException::new);

        return ItemResponseDto.of(findItem,historyService.getStartDate(findItem), historyService.getLatestDate(findItem));
    }


    public ItemIdDto addItem(ItemCreateDto dto){
        Account findAccount = accountBaseService.getCurrentAccount();

        Item item = Item.builder()
                .account(findAccount)
                .title(dto.getTitle())
                .scheduledDate(dto.getStartDate().plusDays(dto.getCycle()))
                .cycle(dto.getCycle())
                .build();

        //add item with first history
        item.addHistory(new History(item,dto.getStartDate()));
        Item savedItem = itemRepository.save(item);

        return ItemIdDto.of(savedItem.getId());
    }


    public Long deleteItem(Long itemId){
        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Long numOfDeleted =  itemRepository.deleteOneById(currentAccountId, itemId);
        if(numOfDeleted == 0){
            throw new ItemNotFoundException();
        }else{
            return numOfDeleted;
        }
    }



    public ItemIdDto changeItemBasicInfo(Long itemId, ItemBasicInfoDto dto){

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Item findItem = itemRepository.findOneById(currentAccountId, itemId)
                                        .orElseThrow(ItemNotFoundException::new);

        findItem.changeTitle(dto.getTitle());
        findItem.changeScheduledDate(findItem.getScheduledDate().plusDays(dto.getCycle()-findItem.getCycle()));
        findItem.changeCycle(dto.getCycle());
        historyService.changeStartDate(findItem, dto.getStartDate());


        return ItemIdDto.of(findItem.getId());
    }

    public ItemIdDto updateItem(Long itemId, ItemUpdateDto dto) {
        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Item findItem = itemRepository.findOneById(currentAccountId, itemId)
                .orElseThrow(ItemNotFoundException::new);

        historyService.addHistory(findItem, dto.getChangeDate());

        findItem.changeScheduledDate(dto.getChangeDate().plusDays(findItem.getCycle()));

        return ItemIdDto.of(findItem.getId());
    }

    public List<Item> getScheduledItems(LocalDate scheduledDate){
        return itemRepository.findScheduledItems(scheduledDate);
    }

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


}
