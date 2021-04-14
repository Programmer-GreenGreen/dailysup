package project.dailysup.histroy.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.dailysup.item.domain.Item;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Page<History> findAllByItem(Item item, Pageable pageable);



    boolean existsByItem(Item item);

    @Modifying(clearAutomatically = true)
    Long deleteAllByItem(Item item);

}
