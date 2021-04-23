package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.histroy.service.HistoryQueryService;
import project.dailysup.item.domain.Item;
import project.dailysup.item.domain.ItemRepository;
import project.dailysup.item.dto.*;
import project.dailysup.item.exception.ItemNotFoundException;
import project.dailysup.util.SecurityUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryService {

    private final ItemRepository itemRepository;
    private final HistoryQueryService historyQueryService;

    public Page<ItemResponseDto> findAll(Pageable pageable) {

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Page<Item> findAllItems = itemRepository.findAllByAccount(currentAccountId, pageable);

        return findAllItems
                .map(i -> ItemResponseDto.of(i, historyQueryService.getStartDate(i), historyQueryService.getLatestDate(i)));
    }

    public ItemResponseDto findItem(Long itemId){

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Item findItem = itemRepository
                .findOneById(currentAccountId,itemId)
                .orElseThrow(ItemNotFoundException::new);

        return ItemResponseDto.of(findItem, historyQueryService.getStartDate(findItem), historyQueryService.getLatestDate(findItem));
    }


    public List<Item> getScheduledItems(LocalDate scheduledDate){
        return itemRepository.findScheduledItems(scheduledDate);
    }




}
