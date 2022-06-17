package Riot.API.Riot.API.dto.summoner;

import lombok.Data;

@Data
public class SummonerDTO {

    private String accountId;

    private int profileIconId;

    private Long revisionDate;

    private String name;

    private String id;

    private String puuid;

    private Long summonerLevel;

}
