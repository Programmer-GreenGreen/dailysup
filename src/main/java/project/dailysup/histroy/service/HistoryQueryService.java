package project.dailysup.histroy.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.histroy.domain.History;
import project.dailysup.histroy.domain.HistoryRepository;
import project.dailysup.histroy.dto.HistoryResponseDto;
import project.dailysup.histroy.exception.HistoryNotFoundException;
import project.dailysup.item.domain.Item;
import project.dailysup.item.domain.ItemRepository;
import project.dailysup.item.exception.ItemNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryQueryService {

    private final HistoryRepository historyRepository;
    private final ItemRepository itemRepository;

    public Page<History> findAllHistory(Long itemId, Pageable pageable){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
        Page<History> historyPage = historyRepository.findAllByItem(item, pageable);

        return historyPage;
    }

    public LocalDate getStartDate(Item item){
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "replacementDate"));
        History history = historyRepository.findAllByItem(item,pageRequest).getContent().get(0);
        LocalDate startDate = history.getReplacementDate();
        return startDate;
    }

    public LocalDate getLatestDate(Item item){
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "replacementDate"));
        History history = historyRepository.findAllByItem(item,pageRequest).getContent().get(0);
        LocalDate latestDate = history.getReplacementDate();
        return latestDate;
    }


}
