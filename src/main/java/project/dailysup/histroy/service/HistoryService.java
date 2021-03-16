package project.dailysup.histroy.service;


import lombok.RequiredArgsConstructor;
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
@Transactional
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<HistoryResponseDto> findAllHistory(Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        List<History> historyList = historyRepository.findAllByItem(item);
        return historyList.stream()
                .map(HistoryResponseDto::of)
                .collect(Collectors.toList());
    }

    public void changeHistoryDate(Long historyId, LocalDate localDate){
        History findHistory = historyRepository
                                    .findById(historyId)
                                    .orElseThrow(HistoryNotFoundException::new);
        findHistory.changeReplaceDate(localDate);
    }

    public void deleteHistory(Long historyId){
        historyRepository.deleteById(historyId);
    }



    public void addHistory(Item item, LocalDate addDate){
        History history = new History(item, addDate);
        historyRepository.save(history);
    }

    @Transactional(readOnly = true)
    public LocalDate getStartDate(Item item){
        List<History> historyList = historyRepository.findAllByItem(item);
        historyList.sort(Comparator.comparing(History::getReplacementDate));
        LocalDate startDate = historyList.get(0).getReplacementDate();
        return startDate;
    }

    @Transactional(readOnly = true)
    public LocalDate getLatestDate(Item item){
        List<History> historyList = historyRepository.findAllByItem(item);
        int size = historyList.size();
        historyList.sort(Comparator.comparing(History::getReplacementDate));
        LocalDate latestDate = historyList.get(size-1).getReplacementDate();
        return latestDate;
    }

    public void changeStartDate(Item item, LocalDate startDate){
        List<History> historyList = historyRepository.findAllByItem(item);
        historyList.sort(Comparator.comparing(History::getReplacementDate));
        historyList.get(0).changeReplaceDate(startDate);
    }
}
