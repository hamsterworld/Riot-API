package Riot.API.Riot.API.dto.datadragon;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="ItemType")
public class ItemType {
    @Id
    @GeneratedValue
    @Column(name = "ItemTypeId")
    private Long id;
    private String ItemType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameItem gameItem;

    public ItemType(String itemType) {
        ItemType = itemType;
    }

}
