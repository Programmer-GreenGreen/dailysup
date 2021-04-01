package project.dailysup.histroy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.histroy.domain.History;
import project.dailysup.histroy.domain.HistoryRepository;
import project.dailysup.histroy.exception.HistoryNotFoundException;
import project.dailysup.item.domain.Item;
import project.dailysup.item.domain.ItemRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryCRUDService {


    private final HistoryRepository historyRepository;
    private final ItemRepository itemRepository;

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

    public void changeStartDate(Item item, LocalDate startDate){
        List<History> historyList = historyRepository.findAllByItem(item);
        historyList.sort(Comparator.comparing(History::getReplacementDate));
        historyList.get(0).changeReplaceDate(startDate);
    }
}
