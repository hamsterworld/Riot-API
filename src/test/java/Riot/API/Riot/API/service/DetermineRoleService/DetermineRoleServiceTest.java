package Riot.API.Riot.API.service.DetermineRoleService;

import Riot.API.Riot.API.dto.Worst.SummonerRoleDto;
import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.dto.datadragon.ItemType;
import Riot.API.Riot.API.dto.gamerecord.InfoDto;
import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import Riot.API.Riot.API.dto.summoner.SummonerDTO;
import Riot.API.Riot.API.service.DataDragonItemService;
import Riot.API.Riot.API.service.EachSummonerItemListGetService.EachSummonerItemListGetService;
import Riot.API.Riot.API.service.SummonerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class DetermineRoleServiceTest {

    @Autowired
    SummonerService summonerService;

    @Autowired
    EachSummonerItemListGetService eachSummonerItemListGetService;

    HashMap<String,List<GameItem>> SummonersHaveItemList = new LinkedHashMap<String,List<GameItem>>();
    HashMap<String, HashMap<String, Integer>> DetermineRoleVoterMap = new LinkedHashMap<>();

    @Autowired
    DataDragonItemService dataDragonItemService;


    @BeforeAll
    @DisplayName("최신 첫판의 참가자들의 아이템목록만 꺼낸다.")
    void getGameRecord() {

        dataDragonItemService.SaveItemsByDataDragon();

        String summonerName = "9토레스";
        summonerName = summonerName.replaceAll(" ", "%20");

        SummonerDTO summonerResult = summonerService.callRiotAPISummonerByName(summonerName);
        List<String> MatchIdResult = summonerService.callRiotAPISummonerMatchIdBypuuid(summonerResult.getPuuid());

        //1경기 MetaData받아오기
        MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(MatchIdResult.get(0));
        InfoDto gameRecodeInfo = metaData1.getInfo();
        List<ParticipantDto> participants = gameRecodeInfo.getParticipants();
        SummonersHaveItemList = eachSummonerItemListGetService.EachSummonerItemListGet(participants);

        //지금 전부다 받아오기다. 이밑에건
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> DetermineRoleVoter = new LinkedHashMap<>();
            DetermineRoleVoter.put("딜러", 0);
            DetermineRoleVoter.put("탱커", 0);
            DetermineRoleVoter.put("서포터", 0);
            DetermineRoleVoterMap.put(SummonerName, DetermineRoleVoter);
        }

    }
    /**
     *
     * 1.아이템 타입 투표로 정하기
     *
     *
     */


    @Test
    @DisplayName("딜러아이템만 가지고있으면 딜러입니다.")
    void IfHaveDamageItemIsDps(){

        Long dmgVoter = Long.valueOf(0);
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> stringIntegerHashMap = DetermineRoleVoterMap.get(SummonerName);
            List<GameItem> gameItems = SummonersHaveItemList.get(SummonerName);
            for (GameItem gameItem : gameItems) {
                List<ItemType> itemTypes = gameItem.getItemTypes();
                dmgVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Damage") || itemType.getItemType().equals("SpellDamage")).count();
            }
            stringIntegerHashMap.replace("딜러", Math.toIntExact(dmgVoter));
            dmgVoter = Long.valueOf(0);
        }

        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);

    }


    @Test
    @DisplayName("탱커아이템만 가지고있으면 탱커입니다.")
    void IfHaveTankItemIsTank(){

        Long tankVoter = Long.valueOf(0);
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> stringIntegerHashMap = DetermineRoleVoterMap.get(SummonerName);
            List<GameItem> gameItems = SummonersHaveItemList.get(SummonerName);
            for (GameItem gameItem : gameItems) {
                List<ItemType> itemTypes = gameItem.getItemTypes();
                tankVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Armor")).count();
            }
            stringIntegerHashMap.replace("탱커", Math.toIntExact(tankVoter));
            tankVoter = Long.valueOf(0);
        }

        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);

    }

    /**
     * 신화템과 서폿전용템 + armor가 많다면 서폿으로 분류해야겠다.
     */
    @Test
    @DisplayName("서폿아이템만 가지고있으면 서폿입니다.")
    void IfHaveAllSupportItemIsSupport(){

        Long SupportVoter = Long.valueOf(0);
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> stringIntegerHashMap = DetermineRoleVoterMap.get(SummonerName);
            List<GameItem> gameItems = SummonersHaveItemList.get(SummonerName);
            for (GameItem gameItem : gameItems) {
                List<ItemType> itemTypes = gameItem.getItemTypes();
                SupportVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Support")).count();
            }
            stringIntegerHashMap.replace("서포터", Math.toIntExact(SupportVoter));
            SupportVoter = Long.valueOf(0);
        }

        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);

    }

    @Test
    @DisplayName("한번에 전부 비교해보기")
    void 비교해보기(){

        Long dmgVoter = Long.valueOf(0);
        Long tankVoter = Long.valueOf(0);
        Long SupportVoter = Long.valueOf(0);
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> stringIntegerHashMap = DetermineRoleVoterMap.get(SummonerName);
            List<GameItem> gameItems = SummonersHaveItemList.get(SummonerName);
            for (GameItem gameItem : gameItems) {
                List<ItemType> itemTypes = gameItem.getItemTypes();
                dmgVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Damage") || itemType.getItemType().equals("SpellDamage")).count();
                tankVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Armor")).count();
                SupportVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Support")).count();
            }
            stringIntegerHashMap.replace("딜러", Math.toIntExact(dmgVoter));
            stringIntegerHashMap.replace("탱커", Math.toIntExact(tankVoter));
            stringIntegerHashMap.replace("서포터", Math.toIntExact(SupportVoter));
            SupportVoter = Long.valueOf(0);
            dmgVoter = Long.valueOf(0);
            tankVoter = Long.valueOf(0);
        }

        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);

    }


    @Test
    @DisplayName("한번에 가져오고 딜러탱커나누기.")
    void 가져온다음비교해보기(){

        Long dmgVoter = Long.valueOf(0);
        Long tankVoter = Long.valueOf(0);
        Long SupportVoter = Long.valueOf(0);

        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> stringIntegerHashMap = DetermineRoleVoterMap.get(SummonerName);
            List<GameItem> gameItems = SummonersHaveItemList.get(SummonerName);

            for (GameItem gameItem : gameItems) {
                List<ItemType> itemTypes = gameItem.getItemTypes();
                dmgVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Damage") || itemType.getItemType().equals("SpellDamage")).count();
                tankVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Armor")).count();
                SupportVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Support")).count();
            }

            stringIntegerHashMap.replace("딜러", Math.toIntExact(dmgVoter));
            stringIntegerHashMap.replace("탱커", Math.toIntExact(tankVoter));

            //관련 dto를 만들자
            SummonerRoleDto summonerRoleDto = new SummonerRoleDto();
            summonerRoleDto.setSummonerName(SummonerName);
            if(dmgVoter>=tankVoter){
                summonerRoleDto.setRole("딜러");
            } else if(dmgVoter < tankVoter){
                summonerRoleDto.setRole("탱커");
            } else{
                summonerRoleDto.setRole("서포터");
            }

            dmgVoter = Long.valueOf(0);
            tankVoter = Long.valueOf(0);
            SupportVoter = Long.valueOf(0);

            System.out.println("잘 분리하는지 = " + summonerRoleDto.toString());
        }

        //관련 dto를 만들자

        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);


    }

    @Test
    @DisplayName("필요한 애들만 뽑아내기")
    void 비교해보기V1(){

        Long dmgVoter = Long.valueOf(0);
        Long tankVoter = Long.valueOf(0);
        Long SupportVoter = Long.valueOf(0);
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            HashMap<String, Integer> stringIntegerHashMap = DetermineRoleVoterMap.get(SummonerName);
            List<GameItem> gameItems = SummonersHaveItemList.get(SummonerName);
            for (GameItem gameItem : gameItems) {
                List<ItemType> itemTypes = gameItem.getItemTypes();
                dmgVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Damage") || itemType.getItemType().equals("SpellDamage")).count();
                tankVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Armor")).count();
                SupportVoter += (Long)itemTypes.stream().
                        filter(itemType -> itemType.getItemType().equals("Support")).count();
            }
            stringIntegerHashMap.replace("딜러", Math.toIntExact(dmgVoter));
            stringIntegerHashMap.replace("탱커", Math.toIntExact(tankVoter));
            stringIntegerHashMap.replace("서포터", Math.toIntExact(SupportVoter));
            SupportVoter = Long.valueOf(0);
            dmgVoter = Long.valueOf(0);
            tankVoter = Long.valueOf(0);
        }

        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);

    }


    @Test
    @DisplayName("전부 딜러,탱커,서포터 투표소 만들기")
    void CreateDealerTankSupportMap(){

        HashMap<String,HashMap<String,Integer>> DetermineRoleVoterMap = new LinkedHashMap<>();
        HashMap<String, List<GameItem>> summonersHaveItemList = SummonersHaveItemList;

        for (String SummonerName : summonersHaveItemList.keySet()) {
            HashMap<String,Integer> DetermineRoleVoter = new LinkedHashMap<>();
            DetermineRoleVoter.put("딜러",0);
            DetermineRoleVoter.put("탱커",0);
            DetermineRoleVoter.put("서포터",0);
            DetermineRoleVoterMap.put(SummonerName,DetermineRoleVoter);
        }
        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);
    }

    @Test
    @DisplayName("필요한 애들만 딜러,탱커,서포터 투표소 만들기")
    void CreateDealerTankSupportMapV1(){

        HashMap<String,HashMap<String,Integer>> DetermineRoleVoterMap = new LinkedHashMap<>();
        HashMap<String, List<GameItem>> summonersHaveItemList = SummonersHaveItemList;
        ArrayList<String> objects = new ArrayList<>();
        for (String SummonerName : summonersHaveItemList.keySet()) {
            for (String object : objects) {
                if(SummonerName.equals(object)){
                    HashMap<String,Integer> DetermineRoleVoter = new LinkedHashMap<>();
                    DetermineRoleVoter.put("딜러",0);
                    DetermineRoleVoter.put("탱커",0);
                    DetermineRoleVoter.put("서포터",0);
                    DetermineRoleVoterMap.put(SummonerName,DetermineRoleVoter);
                }
            }
        }
        //투표해야하는인원이 2명이상이다?
        System.out.println("투표소 한번 보기" + DetermineRoleVoterMap);
    }


}
