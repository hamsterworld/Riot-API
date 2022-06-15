package Riot.API.Riot.API.dto;

import lombok.Data;

import java.util.List;

@Data
public class InfoDto {

    private String gameMode;
    private List<ParticipantDto> participants;
    private List<TeamDto> teams;

}
