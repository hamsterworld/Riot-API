package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.dto.gamerecord.InfoDto;
import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import Riot.API.Riot.API.dto.summoner.SummonerDTO;
import Riot.API.Riot.API.repository.DataDragonGameItemRepository;
import Riot.API.Riot.API.repository.DataDragonItemTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EachSummonerItemListGetServiceTest {

    @Autowired
    DataDragonGameItemRepository dataDragonGameItemRepository;
    @Autowired
    DataDragonItemTypeRepository dataDragonItemTypeRepository;

    SummonerService summonerService;
    List<Integer> list = new ArrayList<>();
    HashMap<String, List<Integer>> SummonersHaveItemList = new LinkedHashMap<String,List<Integer>>();
    HashMap<String, List<GameItem>> SummonersHaveGameItemList = new LinkedHashMap<String,List<GameItem>>();
    List<ParticipantDto> participants;

    @BeforeAll
    @DisplayName("각참가자들의 경기MetaData 가져오기")
    void getGameRecord(){

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

    @BeforeAll
    public void test(){
        DataDragonItemService dataDragonService = new DataDragonItemService(dataDragonGameItemRepository,dataDragonItemTypeRepository);
        dataDragonService.SaveItemsByDataDragon();
    }

    @Test
    @DisplayName("ItemCode를 이용하여 Db에서 하나의 아이템정보를 얻어옵니다.")
    public void FindGameItemByItemCode(){

        int ItemCode = 6035;
        GameItem byItemCode = dataDragonGameItemRepository.findByItemCode(ItemCode);
        System.out.println(byItemCode.getItemName());
        System.out.println(byItemCode.getItemCode());

    }

    @Test
    @DisplayName("아이템 목록 가져오기")
    void EachSummonerItemListGetV1(){

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
            System.out.println(" 이번판 가진 아이템 = " + list);
            System.out.println("-----------------------------------------");
            SummonersHaveItemList.put(participant.getSummonerName(),list);

        }
        System.out.println("==========경기============");
        System.out.println(SummonersHaveItemList);
        System.out.println("=========================");

    }


    @Test
    @DisplayName("아이템 목록 가져온 아이템 코드에서 GameType으로 치환하기")
    void EachSummonerItemListGetV2(){

        for (ParticipantDto participant : participants) {

            List<Integer> list = new ArrayList<>();
            if(participant.getItem0() != 0)
            list.add(participant.getItem0());
            if(participant.getItem1() != 0)
            list.add(participant.getItem1());
            if(participant.getItem2() != 0)
            list.add(participant.getItem2());
            if(participant.getItem3() != 0)
            list.add(participant.getItem3());
            if(participant.getItem4() != 0)
            list.add(participant.getItem4());
            if(participant.getItem5() != 0)
            list.add(participant.getItem5());
            SummonersHaveItemList.put(participant.getSummonerName(),list);

        }
        System.out.println("==========경기============");
        System.out.println(SummonersHaveItemList);
        System.out.println("=========================");


        /**
         * 여기서 10번의 루프를 돌아서 select가 10번나가는데 이걸 1번으로 줄일수있지 않을까?
         * 왜 이짓을 해야하냐면 이짓을안하면 10경기에대한 정보를 받아오면 10x10의 I/O를 하게되기때문이다.
         */
        for (Map.Entry<String, List<Integer>> summonerhaveitems : SummonersHaveItemList.entrySet()) {

            List<Integer> value = summonerhaveitems.getValue();
            List<GameItem> byitem = dataDragonGameItemRepository.findByitem(value);
            SummonersHaveGameItemList.put(summonerhaveitems.getKey(),byitem);

        }

        System.out.println("정보를 잘받아왔는가? "+SummonersHaveGameItemList.get("9토레스").get(0).getItemName());

    }

}
