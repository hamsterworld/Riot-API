package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.MetaData1;
import Riot.API.Riot.API.dto.ParticipantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhoIsAssholeService {

    public List whoIsAsshole(List<MetaData1> AssholeList){

        //첫번째 게임에 관한것.
        List<ParticipantDto> participants = AssholeList.get(0).getInfo().getParticipants();


        return null;

    }

}
