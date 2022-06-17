package Riot.API.Riot.API.dto.datadragon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.persistence.ElementCollection;
import java.util.List;


@Data
public class Item {


    private int itemCode;

    @JsonProperty("name")
    private String itemName;

    private List<String> tags;

}
