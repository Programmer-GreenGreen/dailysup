package project.dailysup.account.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilePropertyDto {
    private String title;
    private String description;
}
