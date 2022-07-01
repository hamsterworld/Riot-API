package Riot.API.Riot.API.service.WhoIsWorstSerivce;

import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static Riot.API.Riot.API.domain.TEAMId.BLUE;
import static Riot.API.Riot.API.domain.TEAMId.RED;

public class WhoIsWorstService {

    public ParticipantDto FindWorst(List<MetaData1> list) {


        for (MetaData1 metaData1 : list) {

            //참가한 10명의 사람들에대한 정보
            List<ParticipantDto> participants = metaData1.getInfo().getParticipants();

            FindWorstThroughMinimumDamage(participants);
        }

        return null;
    }


    public String FindWorstThroughMinimumDamage(List<ParticipantDto> participants){


            //블루팀 벌레
            Optional<ParticipantDto> maxdeathparticipant1 = participants.stream().filter(p -> p.getTeamId() == BLUE)
                    .min(Comparator.comparing(p -> p.getTotalDamageDealtToChampions()));

            //레드팀 벌레
            Optional<ParticipantDto> maxdeathparticipant2 = participants.stream().filter(p -> p.getTeamId() == RED)
                    .min(Comparator.comparing(p -> p.getTotalDamageDealtToChampions()));;

        System.out.println("블루팀 대왕벌레의 이름 = " + maxdeathparticipant1.get().getSummonerName() +" 최소 딜량 = " + maxdeathparticipant1.get().getTotalDamageDealtToChampions());
        System.out.println("레드팀 대왕벌레의 이름 = " + maxdeathparticipant2.get().getSummonerName() +" 최소 딜량 = " + maxdeathparticipant2.get().getTotalDamageDealtToChampions());



        return "햄스터제국";

    }


}
