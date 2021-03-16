package project.dailysup.histroy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import project.dailysup.item.domain.Item;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByItem(Item item);

    boolean existsByItem(Item item);

    @Modifying(clearAutomatically = true)
    Long deleteAllByItem(Item item);

}
