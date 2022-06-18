package Riot.API.Riot.API.dto.datadragon;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="ItemType")
@SequenceGenerator(
        name="tiemtpye_seq",
        sequenceName = "itemtypppe_seq",
        initialValue = 1,allocationSize = 200
)
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "tiemtpye_seq")
    @Column(name = "itemtype_id")
    private Long id;

    private String ItemType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameItem gameItem;

    public ItemType(String itemType) {
        ItemType = itemType;
    }

}
