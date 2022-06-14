package Riot.API.Riot.API.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Summoner {

    @Id
    private String accountId;

    private int profileIconId;

    private Long revisionDate;

    private String name;

    private String id;

    private String puuid;

    private Long summonerLevel;

}