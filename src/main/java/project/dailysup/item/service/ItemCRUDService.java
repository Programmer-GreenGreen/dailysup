package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.account.domain.Account;
import project.dailysup.account.service.AccountBaseService;
import project.dailysup.histroy.domain.History;
import project.dailysup.histroy.service.HistoryService;
import project.dailysup.item.domain.Item;
import project.dailysup.item.domain.ItemRepository;
import project.dailysup.item.dto.ItemBasicInfoDto;
import project.dailysup.item.dto.ItemCreateDto;
import project.dailysup.item.dto.ItemIdDto;
import project.dailysup.item.exception.ItemNotFoundException;
import project.dailysup.util.SecurityUtils;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class ItemCRUDService {

    private final AccountBaseService accountBaseService;
    private final ItemRepository itemRepository;
    private final HistoryService historyService;

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

    public ItemIdDto updateItem(Long itemId, LocalDate changeDate) {
        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Item findItem = itemRepository.findOneById(currentAccountId, itemId)
                .orElseThrow(ItemNotFoundException::new);

        historyService.addHistory(findItem, changeDate);

        findItem.changeScheduledDate(changeDate.plusDays(findItem.getCycle()));

        return ItemIdDto.of(findItem.getId());
    }
}
