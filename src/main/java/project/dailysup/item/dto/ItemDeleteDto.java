package project.dailysup.item.dto;

import lombok.Data;

@Data
public class ItemDeleteDto {

    private Long ItemId;

    public ItemDeleteDto(Long itemId) {
        ItemId = itemId;
    }
}
