package project.dailysup.item.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ItemCreateDto {
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private Integer cycle;

    @Builder
    public ItemCreateDto(String title, LocalDate startDate, Integer cycle) {
        this.title = title;
        this.startDate = startDate;
        this.cycle = cycle;
    }
}
