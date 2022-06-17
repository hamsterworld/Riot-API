package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.gamerecord.InfoDto;
import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import Riot.API.Riot.API.dto.summoner.SummonerDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class EachSummonerItemListGetServiceTest {



    static SummonerService summonerService;
    static List<Integer> list = new ArrayList<>();
    static HashMap<String, List<Integer>> SummonersHaveItemList = new HashMap<String,List<Integer>>();
    static List<ParticipantDto> participants;

    @BeforeAll
    @DisplayName("각참가자들의 경기MetaData 가져오기")
    static void getGameRecord(){

        summonerService = new SummonerService();

        String summonerName = "9토레스";
        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO summonerResult = summonerService.callRiotAPISummonerByName(summonerName);
        List<String> MatchIdResult = summonerService.callRiotAPISummonerMatchIdBypuuid(summonerResult.getPuuid());
        for (String s : MatchIdResult) {

            MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(s);
            InfoDto gameRecodeInfo = metaData1.getInfo();
            participants = gameRecodeInfo.getParticipants();

        };


    }

    @Test
    @DisplayName("아이템 목록 가져오기")
    void EachSummonerItemListGet(){

        for (ParticipantDto participant : participants) {
            List<Integer> list = new ArrayList<>();
            System.out.println("-----------------------------------------");
            System.out.println(" 닉네임 = " + participant.getSummonerName());
            list.add(participant.getItem0());
            list.add(participant.getItem1());
            list.add(participant.getItem2());
            list.add(participant.getItem3());
            list.add(participant.getItem4());
            list.add(participant.getItem5());
            list.add(participant.getItem6());
            System.out.println(" 이번판 가진 아이템 = " + list);
            System.out.println("-----------------------------------------");
            SummonersHaveItemList.put(participant.getSummonerName(),list);

        }
        System.out.println("==========경기============");
        System.out.println(SummonersHaveItemList);
        System.out.println("=========================");

    }

}
