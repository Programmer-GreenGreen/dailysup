package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
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

    public List<ItemResponseDto> findAll() {

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        List<Item> findAllItems = itemRepository.findAllByAccount(currentAccountId);

        return findAllItems.stream()
                .map(i -> ItemResponseDto.of(i, historyQueryService.getStartDate(i), historyQueryService.getLatestDate(i)))
                .collect(Collectors.toList());
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
