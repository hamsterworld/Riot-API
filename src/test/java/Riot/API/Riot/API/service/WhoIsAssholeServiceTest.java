package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.MetaData1;
import Riot.API.Riot.API.dto.ParticipantDto;
import Riot.API.Riot.API.dto.SummonerDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class WhoIsAssholeServiceTest {

    static SummonerService summonerService;
    static List<MetaData1> list = new ArrayList<>();

    @BeforeAll
    static void getGameRecord(){

        summonerService = new SummonerService();

        String summonerName = "9토레스";
        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO summonerResult = summonerService.callRiotAPISummonerByName(summonerName);
        List<String> MatchIdResult = summonerService.callRiotAPISummonerMatchIdBypuuid(summonerResult.getPuuid());
        for (String s : MatchIdResult) {
            MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(s);
            System.out.println(metaData1);
            list.add(metaData1);
        };
    }

    @Test
    @DisplayName("BeforeAll에서 데이터를 잘가져오는지?")
    void whoIsAsshole1() {

        System.out.println("Service = " + summonerService);
        for (MetaData1 metaData1 : list) {
            System.out.println(metaData1);
        }

    }

    /**
     * 최다 데스 --> 무조건 벌레 이유여하를 막론하고 적에게 돈을 줬기때문에 쌉벌레.
     * 최소 킬수 --> 딜러진에 해당 탱커와 서포터는 산정에서 제외.
     * 최소 어시 --> 딜도 못했는데 어시도적다? 게임에 관여하지않음 그냥 벌레.
     * 최소 딜량 --> 딜러진에 해당 (번외로 탱커와 서포터보다 딜을 못넣엇다 쌉벌레.)
     * 딜러인지 탱커인지 어떻게 판별하는 템트리도 필요하다.
     * -->라이엇에서 분류해놓은 아이템분류를따라서 관련된아이템을 많이간다면
     * 그해당포지션으로 분류한다. 만약에 동등하게 간다면?
     * 제압골드를 얼마나 먹었는가?
     * = 내가 벌어들인 골드양 - (킬수*180 + 어시스트*25 + 미니언갯수*30 + 시간당들어오는골드수*게임경과시간)
     *  더블킬,트리플,쿼드라,펜타킬 여부
     *  칼바람에서 사기챔프인가? 사기챔프를 잡고도 졌는지?
     *  템을 얼마나 병신같이 갔는지?
     */

    /**
     * 각 등급마다 1명씩 무조건정해져야하는가?
     * 아니면 같은등급의 경우도 나올수있는가?
     */

    /**
     *
     * 어떻게 등급을 부여할것인가?
     * 특정조건을 몇개이상 만족하느냐?
     * 아니면 포인트제도로 하는가?
     *
     */




    @Test
    @DisplayName("최다데스한 사람은 대왕벌레일 가능성이 높다.")
    void whoIsAsshole2() {

        //게임하나에대한 참가자들의정보
        for (MetaData1 metaData1 : list) {
            List<ParticipantDto> participants = metaData1.getInfo().getParticipants();
            //참가자한 사람들의 10개의 정보.

            //블루팀 벌레
            Optional<ParticipantDto> maxdeathparticipant1 = participants.stream().filter(p -> p.getTeamId() == 100)
                    .max(Comparator.comparing(p -> p.getDeaths()));


            //레드팀 벌레
            Optional<ParticipantDto> maxdeathparticipant2 = participants.stream().filter(p -> p.getTeamId() == 200)
                    .max(Comparator.comparing(p -> p.getDeaths()));

            System.out.println("블루팀 대왕벌레의 이름 = " + maxdeathparticipant1.get().getSummonerName() +" 데스 = " + maxdeathparticipant1.get().getDeaths());
            System.out.println("레드팀 대왕벌레의 이름 = " + maxdeathparticipant2.get().getSummonerName() +" 데스 = " + maxdeathparticipant2.get().getDeaths());

        }


    }

    @Test
    @DisplayName("최소킬수한 사람은 대왕벌레일 가능성이 높다.")
    void whoIsAsshole3() {

        //게임하나에대한 참가자들의정보
        for (MetaData1 metaData1 : list) {
            List<ParticipantDto> participants = metaData1.getInfo().getParticipants();
            //참가자한 사람들의 10개의 정보.

            //블루팀 벌레
            Optional<ParticipantDto> maxdeathparticipant1 = participants.stream().filter(p -> p.getTeamId() == 100)
                    .min(Comparator.comparing(p -> p.getKills()));


            //레드팀 벌레
            Optional<ParticipantDto> maxdeathparticipant2 = participants.stream().filter(p -> p.getTeamId() == 200)
                    .min(Comparator.comparing(p -> p.getKills()));

            System.out.println("블루팀 대왕벌레의 이름 = " + maxdeathparticipant1.get().getSummonerName() +" 최소 킬수 = " + maxdeathparticipant1.get().getKills());
            System.out.println("레드팀 대왕벌레의 이름 = " + maxdeathparticipant2.get().getSummonerName() +" 최소 킬수 = " + maxdeathparticipant2.get().getKills());

        }


    }

    @Test
    @DisplayName("최소어시한 사람은 대왕벌레일 가능성이 높다.")
    void whoIsAsshole4() {

        //게임하나에대한 참가자들의정보
        for (MetaData1 metaData1 : list) {
            List<ParticipantDto> participants = metaData1.getInfo().getParticipants();
            //참가자한 사람들의 10개의 정보.

            //블루팀 벌레
            Optional<ParticipantDto> maxdeathparticipant1 = participants.stream().filter(p -> p.getTeamId() == 100)
                    .min(Comparator.comparing(p -> p.getAssists()));


            //레드팀 벌레
            Optional<ParticipantDto> maxdeathparticipant2 = participants.stream().filter(p -> p.getTeamId() == 200)
                    .min(Comparator.comparing(p -> p.getAssists()));

            System.out.println("블루팀 대왕벌레의 이름 = " + maxdeathparticipant1.get().getSummonerName() +" 최소 어시 = " + maxdeathparticipant1.get().getAssists());
            System.out.println("레드팀 대왕벌레의 이름 = " + maxdeathparticipant2.get().getSummonerName() +" 최소 어시 = " + maxdeathparticipant2.get().getAssists());

        }


    }

    @Test
    @DisplayName("최소딜량한 사람은 대왕벌레일 가능성이 높다.")
    void whoIsAsshole5() {

        //게임하나에대한 참가자들의정보
        for (MetaData1 metaData1 : list) {
            List<ParticipantDto> participants = metaData1.getInfo().getParticipants();
            //참가자한 사람들의 10개의 정보.

            //블루팀 벌레
            Optional<ParticipantDto> maxdeathparticipant1 = participants.stream().filter(p -> p.getTeamId() == 100)
                    .min(Comparator.comparing(p -> p.getTotalDamageDealtToChampions()));


            //레드팀 벌레
            Optional<ParticipantDto> maxdeathparticipant2 = participants.stream().filter(p -> p.getTeamId() == 200)
                    .min(Comparator.comparing(p -> p.getTotalDamageDealtToChampions()));

            System.out.println("블루팀 대왕벌레의 이름 = " + maxdeathparticipant1.get().getSummonerName() +" 최소 딜량 = " + maxdeathparticipant1.get().getTotalDamageDealtToChampions());
            System.out.println("레드팀 대왕벌레의 이름 = " + maxdeathparticipant2.get().getSummonerName() +" 최소 딜량 = " + maxdeathparticipant2.get().getTotalDamageDealtToChampions());

        }


    }

    @Test
    @DisplayName("최소딜량한 사람은 대왕벌레일 가능성이 높다.")
    void whoIsAsshole6() {

        //게임하나에대한 참가자들의정보
        for (MetaData1 metaData1 : list) {
            List<ParticipantDto> participants = metaData1.getInfo().getParticipants();
            //참가자한 사람들의 10개의 정보.

            //블루팀 벌레
            Optional<ParticipantDto> maxdeathparticipant1 = participants.stream().filter(p -> p.getTeamId() == 100)
                    .min(Comparator.comparing(p -> p.getTotalDamageDealtToChampions()));


            //레드팀 벌레
            Optional<ParticipantDto> maxdeathparticipant2 = participants.stream().filter(p -> p.getTeamId() == 200)
                    .min(Comparator.comparing(p -> p.getTotalDamageDealtToChampions()));

            System.out.println("블루팀 대왕벌레의 이름 = " + maxdeathparticipant1.get().getSummonerName() +" 최소 딜량 = " + maxdeathparticipant1.get().getTotalDamageDealtToChampions());
            System.out.println("레드팀 대왕벌레의 이름 = " + maxdeathparticipant2.get().getSummonerName() +" 최소 딜량 = " + maxdeathparticipant2.get().getTotalDamageDealtToChampions());

        }


    }


}