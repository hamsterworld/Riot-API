package Riot.API.Riot.API.dto.datadragon;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="GameItem")
public class GameItem {

    @Id
    @GeneratedValue
    @Column(name="GameItemId")
    private int id;

    private int itemCode;

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
