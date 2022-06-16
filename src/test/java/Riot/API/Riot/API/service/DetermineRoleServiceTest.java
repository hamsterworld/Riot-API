package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.InfoDto;
import Riot.API.Riot.API.dto.MetaData1;
import Riot.API.Riot.API.dto.ParticipantDto;
import Riot.API.Riot.API.dto.SummonerDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


public class DetermineRoleServiceTest {


    static SummonerService summonerService;
    static EachSummonerItemListGetService eachSummonerItemListGetService;
    //static List<Integer> list = new ArrayList<>();
    static HashMap<String,List<Integer>> SummonersHaveItemList = new HashMap<String,List<Integer>>();

    @BeforeAll
    @DisplayName("각참가자들의 아이템목록까지 꺼낸다.")
    static void getGameRecord(){

        summonerService = new SummonerService();
        eachSummonerItemListGetService = new EachSummonerItemListGetService();

        String summonerName = "9토레스";
        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO summonerResult = summonerService.callRiotAPISummonerByName(summonerName);
        List<String> MatchIdResult = summonerService.callRiotAPISummonerMatchIdBypuuid(summonerResult.getPuuid());
        for (String s : MatchIdResult) {

            MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(s);
            InfoDto gameRecodeInfo = metaData1.getInfo();
            List<ParticipantDto> participants = gameRecodeInfo.getParticipants();
            SummonersHaveItemList = eachSummonerItemListGetService.EachSummonerItemListGet(participants);

        };


    }

    @Test
    @DisplayName("딜러아이템만 가지고있으면 딜러입니다.")
    void IfHaveAllDamageItemIsDps(){


    }

    @Test
    @DisplayName("탱커아이템만 가지고있으면 탱커입니다.")
    void IfHaveAllTankItemIsTank(){

    }

    @Test
    @DisplayName("서폿아이템만 가지고있으면 서폿입니다.")
    void IfHaveAllSupportItemIsSupport(){

    }


}
