package project.dailysup.histroy.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.dailysup.histroy.domain.History;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryResponseDto {

    private Long historyId;
    private LocalDate replacementDate;

    public HistoryResponseDto(Long historyId, LocalDate replacementDate) {
        this.historyId = historyId;
        this.replacementDate = replacementDate;
    }

    public static HistoryResponseDto of (History history){
        return new HistoryResponseDto(history.getId(), history.getReplacementDate());
    }
}
