package project.dailysup.item.dto;

import lombok.Data;

@Data
public class ItemIdDto {
    private Long itemId;

    public ItemIdDto(Long itemId) {
        this.itemId = itemId;
    }

    public static ItemIdDto of (Long itemId){
        return new ItemIdDto(itemId);
    }
}
