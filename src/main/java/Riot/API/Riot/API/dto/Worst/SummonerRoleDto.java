package Riot.API.Riot.API.dto.Worst;

import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import lombok.Data;

@Data
public class SummonerRoleDto implements Comparable<SummonerRoleDto>{

    private String SummonerName;
    private String Role;
    private Long dealing;

    @Override
    public int compareTo(SummonerRoleDto o) {

        if (o.dealing < dealing) {
            return 1;
        }
        else if (o.dealing > dealing) {
            return -1;
        }
        return 0;
    }
}
