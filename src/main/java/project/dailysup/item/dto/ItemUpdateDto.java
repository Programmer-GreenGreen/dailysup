package project.dailysup.item.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ItemUpdateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate changeDate;

}
