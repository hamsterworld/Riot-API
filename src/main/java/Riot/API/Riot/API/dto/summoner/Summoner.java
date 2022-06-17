package Riot.API.Riot.API.dto.summoner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Id;

@Data
public class Summoner {

    @Id
    private String accountId;

    private int profileIconId;

    private Long revisionDate;
    @JsonProperty("name")
    private String summonerName;
    @JsonProperty("id")
    private String summonerId;

    private String puuid;

    private Long summonerLevel;

}
