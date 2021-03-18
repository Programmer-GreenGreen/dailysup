package project.dailysup.histroy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryRequestDto {

    private Long historyId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate replaceDate;
}
