package Riot.API.Riot.API.dto;

import lombok.Data;

import java.util.List;

@Data
public class MetadataDto {

    private String dataVersion;
    private String matchId;
    private List<String> participants;

}
