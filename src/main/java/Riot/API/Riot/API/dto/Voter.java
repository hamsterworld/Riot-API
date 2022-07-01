package Riot.API.Riot.API.dto;

import lombok.Data;

@Data
public class Voter {

    private String summonerName;
    private Long dealer;
    private Long tank;
    private Long support;

}
