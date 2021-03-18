package project.dailysup.item.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.dailysup.account.domain.Account;
import project.dailysup.common.BaseEntity;
import project.dailysup.histroy.domain.History;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private Integer cycle;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<History> history = new ArrayList<>();


    @Builder
    public Item(Account account, String title,
                LocalDate scheduledDate, Integer cycle,
                History history) {
        this.account = account;
        this.title = title;
        this.scheduledDate = scheduledDate;
        this.cycle = cycle;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeCycle(Integer cycle){
        this.cycle = cycle;
    }


    public void changeScheduledDate(LocalDate scheduledDate){this.scheduledDate = scheduledDate;}

    public void addHistory(History history){
        // Item은 연관관계의 주인이 아님을 유의.
        history.addHistoryToItem(this);
    }

}
