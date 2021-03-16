package project.dailysup.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import project.dailysup.item.domain.Item;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ItemResponseDto {
    /**
     * Service 에서 인증된 사용자 정보를 가져오므로
     * Account 정보는 필요없음.
     */

    private Long itemId;
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate latestDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledDate;

    private Integer cycle;

    @Builder
    public ItemResponseDto(Long itemId, String title,
                           LocalDate startDate, LocalDate latestDate,
                           LocalDate scheduledDate, Integer cycle) {

        this.itemId = itemId;
        this.title = title;
        this.startDate = startDate;
        this.latestDate = latestDate;
        this.scheduledDate = scheduledDate;
        this.cycle = cycle;
    }

    public static ItemResponseDto of(Item item, LocalDate startDate, LocalDate latestDate){
        return ItemResponseDto.builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .startDate(startDate)
                .latestDate(latestDate)
                .scheduledDate(item.getScheduledDate())
                .cycle(item.getCycle())
                .build();
    }
}
