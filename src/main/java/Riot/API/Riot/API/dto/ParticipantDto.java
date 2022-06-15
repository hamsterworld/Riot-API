package Riot.API.Riot.API.dto;

import lombok.Data;

@Data
public class ParticipantDto {

    private String summonerName;
    private int kills;
    private int assists;
    private int deaths;
    private int totalDamageDealtToChampions;
    private boolean win;
}
