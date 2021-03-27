package project.dailysup.histroy.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.dailysup.common.BaseEntity;
import project.dailysup.item.domain.Item;

import javax.persistence.*;
import java.time.LocalDate;
@JsonIgnoreType
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class History extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "history_id")
    private Long id;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Column(nullable = false)
    private LocalDate replacementDate;

    public History(Item item, LocalDate replacementDate) {
        this.addHistoryToItem(item);
        this.replacementDate = replacementDate;
    }


    public void changeReplaceDate(LocalDate changeDate){
        this.replacementDate = changeDate;
    }

    //연관관계편의메서드
    public void addHistoryToItem(Item item){
        this.item = item;
        item.getHistory().add(this);
    }
}
