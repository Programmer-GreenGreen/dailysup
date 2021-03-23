package project.dailysup.item.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = {"account"})
    @Query("select i from Item i where i.account.loginId = :currentAccountId")
    List<Item> findAllByAccount(@Param("currentAccountId") String currentAccountId);

    @Query("select i " +
            "from Item i " +
            "join fetch i.account a " +
            "where i.scheduledDate <= :scheduledDate")
    List<Item> findScheduledItems(@Param("scheduledDate") LocalDate scheduledDate);

    @EntityGraph(attributePaths = {"account"})
    @Query("select i " +
            "from Item i " +
            "where i.account.loginId = :currentAccountId " +
                "and i.id = :itemId")
    Optional<Item> findOneById(@Param("currentAccountId") String currentAccountId,
                               @Param("itemId") Long itemId);

    @EntityGraph(attributePaths = {"account"})
    @Query("delete " +
            "from Item i " +
            "where i.account.loginId = :currentAccountId " +
            "and i.id = :itemId")
    Long deleteOneById(@Param("currentAccountId") String currentAccountId,
                       @Param("itemId") Long itemId);
}
