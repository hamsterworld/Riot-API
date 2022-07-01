package Riot.API.Riot.API.dto.gamerecord;

import lombok.Data;

@Data
public class ParticipantDto{

    private String summonerName;
    private int kills;
    private int assists;
    private int deaths;
    private int teamId;
    private int doubleKills;
    private int tripleKills;
    private int quadraKills;
    private int pentaKills;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private String championName;
    private int totalDamageDealtToChampions;
    private boolean win;


}
