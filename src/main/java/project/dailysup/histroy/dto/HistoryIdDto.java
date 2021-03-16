package project.dailysup.histroy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryIdDto {
    private Long historyId;

    public HistoryIdDto(Long historyId) {
        this.historyId = historyId;
    }
}
