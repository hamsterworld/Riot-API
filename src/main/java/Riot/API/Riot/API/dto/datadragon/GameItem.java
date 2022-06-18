package Riot.API.Riot.API.dto.datadragon;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="GameItem")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(
        name="gametiem_seq",
        sequenceName = "game_seq",
        initialValue = 1,allocationSize = 200
)
public class GameItem{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gametiem_seq")
    @Column(name = "gameitem_id")
    private Integer id;

    private int itemCode;


    @Column(name="CREAT_DATE")
    @CreatedDate
    private LocalDateTime createDate;

    private String itemName;

    @OneToMany(mappedBy = "gameItem")
    private List<ItemType> itemTypes = new ArrayList<>();


    public GameItem() {
    }

    public GameItem(Item item){
        this.itemCode = item.getItemCode();
        this.itemName = item.getItemName();
    }

}
