package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.domain.Account;
import project.dailysup.account.service.AccountBaseService;
import project.dailysup.histroy.domain.History;
import project.dailysup.histroy.service.HistoryService;
import project.dailysup.item.controller.ItemController;
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
    private final HistoryService historyService;

    public List<ItemResponseDto> findAll() {

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        List<Item> findAllItems = itemRepository.findAllByAccount(currentAccountId);

        return findAllItems.stream()
                .map(i -> ItemResponseDto.of(i, historyService.getStartDate(i), historyService.getLatestDate(i)))
                .collect(Collectors.toList());
    }

    public ItemResponseDto findItem(Long itemId){

        String currentAccountId = SecurityUtils.getCurrentLoginId();
        Item findItem = itemRepository
                .findOneById(currentAccountId,itemId)
                .orElseThrow(ItemNotFoundException::new);

        return ItemResponseDto.of(findItem,historyService.getStartDate(findItem), historyService.getLatestDate(findItem));
    }


    public List<Item> getScheduledItems(LocalDate scheduledDate){
        return itemRepository.findScheduledItems(scheduledDate);
    }




}
