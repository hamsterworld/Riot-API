package Riot.API.Riot.API.dto;

import lombok.Data;

@Data
public class ParticipantDto {

    private String summonerName;
    private int kills;
    private int assists;
    private int deaths;
    private int teamId;
    private int doubleKills;
    private int tripleKills;
    private int quadraKills;
    private int pentaKills;
    private String championName;
    private int totalDamageDealtToChampions;
    private boolean win;

}
