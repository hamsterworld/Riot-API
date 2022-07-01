package Riot.API.Riot.API.service.WhoIsWorstSerivce;

import Riot.API.Riot.API.dto.Worst.SummonerRoleDto;
import Riot.API.Riot.API.dto.Worst.WorstDto;
import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.dto.datadragon.ItemType;
import Riot.API.Riot.API.dto.gamerecord.InfoDto;
import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import Riot.API.Riot.API.dto.summoner.SummonerDTO;
import Riot.API.Riot.API.service.DataDragonItemService;
import Riot.API.Riot.API.service.EachSummonerItemListGetService.EachSummonerItemListGetService;
import Riot.API.Riot.API.service.SummonerService;
import com.jayway.jsonpath.internal.function.numeric.Sum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WhoIsWorstServiceTest {

    SummonerService summonerService;
    List<MetaData1> list = new ArrayList<>();
    @Autowired
    EachSummonerItemListGetService eachSummonerItemListGetService;
    HashMap<String,List<GameItem>> SummonersHaveItemList = new LinkedHashMap<String,List<GameItem>>();
    HashMap<String, HashMap<String, Integer>> DetermineRoleVoterMap = new LinkedHashMap<>();
    List<String> MatchIdResult = new ArrayList<>();
    @Autowired
    DataDragonItemService dataDragonItemService;

    @BeforeAll
    void getGameRecord(){

        dataDragonItemService.SaveItemsByDataDragon();

        summonerService = new SummonerService();

        String summonerName = "햄스터제국";
        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO summonerResult = summonerService.callRiotAPISummonerByName(summonerName);
        MatchIdResult = summonerService.callRiotAPISummonerMatchIdBypuuid(summonerResult.getPuuid());
        for (String s : MatchIdResult) {
            MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(s);
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
     * --> kda로 대체할수있긴하네. + 킬관여율
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
     *
     * 한명만 뽑는다. Worst
     *
     */

    /**
     *
     * 먼저 kda와 킬관여율이 제일낮은사람을 찾는다.
     * 두개다 공통으로 있다면 이사람이 Worst
     * 만약에 2개다공통으로 간사람이 없다면?
     *
     */
    //블루팀 100 레드팀 200
    @Test
    @DisplayName("진팀만 멤버를 찾기")
    void whoIsAsshole2() {
        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();

        //진팀만 찾기.
        for (MetaData1 metaData1 : list) {
            List<ParticipantDto> gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }
        System.out.println(objects);

    }

    @Test
    @DisplayName("진팀의 전체 킬수 구하기")
    void whoIsAsshole3() {
        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();
        List<ParticipantDto> gameParticipants = null;
        Long teamAllkills = Long.valueOf(0);

        for (MetaData1 metaData1 : list) {
            gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }
        List<ParticipantDto> participantDtos = objects.get(0);
        for (ParticipantDto gameParticipant : participantDtos) {
            teamAllkills += Long.valueOf(gameParticipant.getKills());
        }
        System.out.println(teamAllkills);
    }

    @Test
    @DisplayName("각팀원들의 킬관여율 구하기")
    void whoIsAsshole4() {

        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();
        List<ParticipantDto> gameParticipants = null;

        Long teamAllkills = Long.valueOf(0);

        for (MetaData1 metaData1 : list) {
            gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }
        List<ParticipantDto> participantDtos = objects.get(0);
        for (ParticipantDto gameParticipant : participantDtos) {
            teamAllkills += Long.valueOf(gameParticipant.getKills());
        }

        WorstDto worstDto = new WorstDto();
        for (ParticipantDto gameParticipant : participantDtos) {
            Long totalKillsAndAssists =
                    Long.valueOf(gameParticipant.getKills() + gameParticipant.getAssists());
            Long killAssociation =
                    Long.valueOf((long) ((Double.valueOf(totalKillsAndAssists)/teamAllkills)*100));
            System.out.println(killAssociation);
            if(worstDto.getSummonerName() == null){
                worstDto.setKillAssociation(killAssociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            } else if(worstDto.getKillAssociation() > killAssociation) {
                worstDto.setKillAssociation(killAssociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            }

        }

        System.out.println("킬관여율 꼴찌는? = " + worstDto.getSummonerName());

    }

    @Test
    @DisplayName("각팀원들의 킬관여율 구하기 그러나 킬관여율이 같은 사람이 여러명이 있을 수 있다.")
    void whoIsAsshole5() {

        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();
        List<ParticipantDto> gameParticipants = null;
        List<Long> killAssociationsList = new ArrayList<>();
        Long teamAllkills = Long.valueOf(0);

        for (MetaData1 metaData1 : list) {
            gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }

        List<ParticipantDto> participantDtos = objects.get(2);
        for (ParticipantDto gameParticipant : participantDtos) {
            teamAllkills += Long.valueOf(gameParticipant.getKills());
        }

        WorstDto worstDto = new WorstDto();

        for (ParticipantDto gameParticipant : participantDtos) {

            Long totalKillsAndAssists =
                    Long.valueOf(gameParticipant.getKills() + gameParticipant.getAssists());
            Long killAssociation =
                    Long.valueOf((long) ((Double.valueOf(totalKillsAndAssists)/teamAllkills)*100));
            System.out.println(killAssociation);

            killAssociationsList.add(killAssociation);

            if(worstDto.getSummonerName() == null){
                worstDto.setKillAssociation(killAssociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            } else if(worstDto.getKillAssociation() > killAssociation) {
                worstDto.setKillAssociation(killAssociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            }

        }

        Long aLongs = killAssociationsList.stream().min(Comparator.comparing(x -> x)).get();
        long count = killAssociationsList.stream().filter(x -> x == aLongs).count();
        ArrayList<Object> killAssociationsWorstDtoList = new ArrayList<>();
        System.out.println("한명 = " + worstDto.getSummonerName());
        if(count >= 2){

            for (ParticipantDto participantDto : participantDtos) {
                Long totalKillsAndAssists =
                        Long.valueOf(participantDto.getKills() + participantDto.getAssists());
                Long killAssociation =
                        Long.valueOf((long) ((Double.valueOf(totalKillsAndAssists)/teamAllkills)*100));
                if(aLongs == killAssociation){
                    WorstDto worstDto1 = new WorstDto();
                    worstDto1.setKillAssociation(killAssociation);
                    worstDto1.setSummonerName(participantDto.getSummonerName());
                    killAssociationsWorstDtoList.add(worstDto1);
                }
            }
        }

        System.out.println("될까? = " + killAssociationsWorstDtoList);
        //여기 리펙토링가능 = count만 미리 받아서 해결하면된다. killass 매서드와 순위정하기매서드를 분리하자.
    }

    @Test
    @DisplayName("각팀원들의 kda 구하기")
    void whoIsAsshole6() {

        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();
        List<ParticipantDto> gameParticipants = null;
        List<Float> kdaList = new ArrayList<>();
        Long teamAllkills = Long.valueOf(0);

        for (MetaData1 metaData1 : list) {
            gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }

        List<ParticipantDto> participantDtos = objects.get(0);
        for (ParticipantDto gameParticipant : participantDtos) {
            teamAllkills += Long.valueOf(gameParticipant.getKills());
        }

        WorstDto worstDto = new WorstDto();

        for (ParticipantDto gameParticipant : participantDtos) {
            Float kda = ((float) (gameParticipant.getKills() + gameParticipant.getAssists())) / gameParticipant.getDeaths();
            System.out.println(kda);

            kdaList.add(kda);

            if(worstDto.getSummonerName() == null){
                worstDto.setKda(kda);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            } else if(worstDto.getKda() > kda) {
                worstDto.setKda(kda);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            }

        }

        Float aLongs = Float.valueOf(kdaList.stream().min(Comparator.comparing(x -> x)).get());
        long count = kdaList.stream().filter(x -> x == aLongs).count();

        ArrayList<Object> kdaWorstDtoList = new ArrayList<>();

        System.out.println("한명 = " + worstDto.getSummonerName());
        if(count >= 2){

            for (ParticipantDto participantDto : participantDtos) {
                Float kda = ((float) (participantDto.getKills() + participantDto.getAssists())) / participantDto.getDeaths();
                if(aLongs == kda){
                    WorstDto worstDto1 = new WorstDto();
                    worstDto1.setKda(kda);
                    worstDto1.setSummonerName(participantDto.getSummonerName());
                    kdaWorstDtoList.add(worstDto1);
                }
            }
        }

        System.out.println("될까? = " + kdaWorstDtoList);
        //여기 리펙토링가능 = count만 미리 받아서 해결하면된다. killass 매서드와 순위정하기매서드를 분리하자.

    }

    @Test
    @DisplayName("그냥 애초에 *해서 제일낮은애를 찾아.")
    void whoIsAsshole7() {

        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();
        List<ParticipantDto> gameParticipants = null;
        List<Float> kdaList = new ArrayList<>();
        List<String> kdaSummonerList = new ArrayList<>();
        Long teamAllkills = Long.valueOf(0);

        for (MetaData1 metaData1 : list) {
            gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }

        List<ParticipantDto> participantDtos = objects.get(0);
        for (ParticipantDto gameParticipant : participantDtos) {
            teamAllkills += Long.valueOf(gameParticipant.getKills());
        }

        WorstDto worstDto = new WorstDto();

        for (ParticipantDto gameParticipant : participantDtos) {

            Float kda = ((float) (gameParticipant.getKills() + gameParticipant.getAssists())) / gameParticipant.getDeaths();

            System.out.println("kda 입니다 = " + kda);

            Long totalKillsAndAssists =
                    Long.valueOf(gameParticipant.getKills() + gameParticipant.getAssists());
            Long killAssociation =
                    Long.valueOf((long) ((Double.valueOf(totalKillsAndAssists)/teamAllkills)*100));

            System.out.println("킬기여도 입니다 = " + killAssociation);

            System.out.println("곱하기 입니다. = " + (kda*((float)killAssociation/100)));

            Float totalkdakillassociation = kda*((float)killAssociation/100);

            kdaList.add(totalkdakillassociation);
            kdaSummonerList.add(gameParticipant.getSummonerName());

            if(worstDto.getSummonerName() == null){
                worstDto.setKda(totalkdakillassociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            } else if(worstDto.getKda() > totalkdakillassociation) {
                worstDto.setKda(totalkdakillassociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            }

        }

        Float aFloat = kdaList.stream().min(Comparator.comparing(x -> x)).get();
        long count = kdaList.stream().filter(x -> x == aFloat).count();
        ArrayList<String> objects1 = new ArrayList<>();

        //1경기 MetaData받아오기
        MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(MatchIdResult.get(0));
        InfoDto gameRecodeInfo = metaData1.getInfo();
        List<ParticipantDto> participants = gameRecodeInfo.getParticipants();
        SummonersHaveItemList = eachSummonerItemListGetService.EachSummonerItemListGet(participants);


        for(int i =0;i<kdaList.size(); i++) {
            if(kdaList.get(i) == aFloat) {
                objects1.add(kdaSummonerList.get(i));
            }
        }

        System.out.println("잘나왔나? = " + objects1);

        for (String SummonerName : SummonersHaveItemList.keySet()) {
            for (String object : objects1) {
                if (SummonerName.equals(object)) {
                    HashMap<String, Integer> DetermineRoleVoter = new LinkedHashMap<>();
                    DetermineRoleVoter.put("딜러", 0);
                    DetermineRoleVoter.put("탱커", 0);
                    DetermineRoleVoter.put("서포터", 0);
                    DetermineRoleVoterMap.put(SummonerName, DetermineRoleVoter);
                }
            }
        }

        Long dmgVoter = Long.valueOf(0);
        Long tankVoter = Long.valueOf(0);
        Long SupportVoter = Long.valueOf(0);

        if(true) {

            for(int i =0;i<kdaList.size(); i++){

                if(kdaList.get(i) == aFloat){

                    for (String SummonerName : SummonersHaveItemList.keySet()) {

                        if(SummonerName.equals(kdaSummonerList.get(i))){

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
                    }
                }
            }
            System.out.println("작동");
        }// count if문 끝.

        //딜러진끼리 후보2명이상
        //딜러진중 둘중 딜 낮은애가 벌레

        //딜러+탱커끼리 후보2명이상
        //딜러진중 딜량꼴지가 벌레
        //꼴찌가없다? 탱커가 벌레

        //탱커끼리 후보2명이상
        //딜량비교해서 낮은애가 벌레레



        System.out.println("아이템을 보자 = "+ DetermineRoleVoterMap);
        System.out.println("bug = " + worstDto.getSummonerName());
    }

    @Test
    @DisplayName("그냥 애초에 *해서 제일낮은애를 찾아.")
    void whoIsAsshole8() {

        ArrayList<List<ParticipantDto>> objects = new ArrayList<>();
        List<ParticipantDto> gameParticipants = null;
        List<Float> kdaList = new ArrayList<>();
        List<String> kdaSummonerList = new ArrayList<>();
        Long teamAllkills = Long.valueOf(0);

        //한게임게임마다 participant의 게임정보를 가져온다.
        for (MetaData1 metaData1 : list) {
            gameParticipants = metaData1.getInfo().getParticipants().stream().
                    filter(participantDto -> !participantDto.isWin()).collect(Collectors.toList());
            objects.add(gameParticipants);
        }

        //가져온 5개의게임중 5번째 게임에대한 참가자들에 정보를 가져온다.
        List<ParticipantDto> participantDtos = objects.get(4);
        for (ParticipantDto gameParticipant : participantDtos) {
            teamAllkills += Long.valueOf(gameParticipant.getKills());
        }

        WorstDto worstDto = new WorstDto();

        //kda와 킬관여율을 계산한다.
        for (ParticipantDto gameParticipant : participantDtos) {

            Float kda = ((float) (gameParticipant.getKills() + gameParticipant.getAssists())) / gameParticipant.getDeaths();

            System.out.println("kda 입니다 = " + kda);

            Long totalKillsAndAssists =
                    Long.valueOf(gameParticipant.getKills() + gameParticipant.getAssists());
            Long killAssociation =
                    Long.valueOf((long) ((Double.valueOf(totalKillsAndAssists)/teamAllkills)*100));

            System.out.println("킬기여도 입니다 = " + killAssociation);

            System.out.println("곱하기 입니다. = " + (kda*((float)killAssociation/100)));

            Float totalkdakillassociation = kda*((float)killAssociation/100);

            kdaList.add(totalkdakillassociation);
            kdaSummonerList.add(gameParticipant.getSummonerName());

            if(worstDto.getSummonerName() == null){
                worstDto.setKda(totalkdakillassociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            } else if(worstDto.getKda() > totalkdakillassociation) {
                worstDto.setKda(totalkdakillassociation);
                worstDto.setSummonerName(gameParticipant.getSummonerName());
            }

        }

        //만약에 kda*킬관여율이 동률인지아닌지 확인한다.
        //맞다면 count가 2이상일거다.
        Float aFloat = kdaList.stream().min(Comparator.comparing(x -> x)).get();
        long count = kdaList.stream().filter(x -> x == aFloat).count();
        ArrayList<String> objects1 = new ArrayList<>();

        //1경기 MetaData받아오기
        //그다음 거기서 각 참가자들의 아이템가지고있는 리스트를 얻어오기
        MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(MatchIdResult.get(4));
        InfoDto gameRecodeInfo = metaData1.getInfo();
        List<ParticipantDto> participants = gameRecodeInfo.getParticipants();
        SummonersHaveItemList = eachSummonerItemListGetService.EachSummonerItemListGet(participants);


        for(int i =0;i<kdaList.size(); i++) {
            if(kdaList.get(i) == aFloat) {
                objects1.add(kdaSummonerList.get(i));
            }
        }

        System.out.println("잘나왔나? = " + objects1);
        //count가 2이상이면 아마 투표소가 2개이상만들어질거다.
        for (String SummonerName : SummonersHaveItemList.keySet()) {
            for (String object : objects1) {
                if (SummonerName.equals(object)) {
                    HashMap<String, Integer> DetermineRoleVoter = new LinkedHashMap<>();
                    DetermineRoleVoter.put("딜러", 0);
                    DetermineRoleVoter.put("탱커", 0);
                    DetermineRoleVoter.put("서포터", 0);
                    DetermineRoleVoterMap.put(SummonerName, DetermineRoleVoter);
                }
            }
        }

        Long dmgVoter = Long.valueOf(0);
        Long tankVoter = Long.valueOf(0);
        Long SupportVoter = Long.valueOf(0);

        ArrayList<SummonerRoleDto> summonerRoleDtoArrayList = new ArrayList<>();
        //투표소안에 그걸로 탱커인지서포터인지판단한다.
        if(true) {

            for(int i =0;i<kdaList.size(); i++){

                if(kdaList.get(i) == aFloat){

                    for (String SummonerName : SummonersHaveItemList.keySet()) {

                        if(SummonerName.equals(kdaSummonerList.get(i))){

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

                            SummonerRoleDto summonerRoleDto = new SummonerRoleDto();
                            summonerRoleDto.setSummonerName(SummonerName);

                            //voter가 전부 0이면 그냥 벌레취급

                            if(dmgVoter>=tankVoter){
                                summonerRoleDto.setRole("딜러");
                            } else if(dmgVoter < tankVoter){
                                summonerRoleDto.setRole("탱커");
                            } else{
                                summonerRoleDto.setRole("서포터");
                            }

                            summonerRoleDtoArrayList.add(summonerRoleDto);

                            SupportVoter = Long.valueOf(0);
                            dmgVoter = Long.valueOf(0);
                            tankVoter = Long.valueOf(0);
                        }
                    }
                }
            }
            System.out.println("작동");
        }// count if문 끝.


        //그냥아예 딜량순위를 정하자. kda*킬관여율꼴지인애들만 골라서. 딜량비교를한다.
        for (ParticipantDto gameParticipant : participantDtos) {
            for (SummonerRoleDto summonerRoleDto : summonerRoleDtoArrayList){
                if (gameParticipant.getSummonerName().equals(summonerRoleDto.getSummonerName())){
                    summonerRoleDto.setDealing((long) gameParticipant.getTotalDamageDealtToChampions());
                }
            }
        }
        SummonerRoleDto summonerRoleDto1 = new SummonerRoleDto();
        SummonerRoleDto summonerRoleDto2 = new SummonerRoleDto();
        summonerRoleDto1.setDealing(3000L);
        summonerRoleDto1.setRole("탱커");
        summonerRoleDto1.setSummonerName("중간햄스터");
        summonerRoleDto2.setDealing(20000L);
        summonerRoleDto2.setRole("탱커");
        summonerRoleDto2.setSummonerName("존나강한햄스터");
        summonerRoleDtoArrayList.add(summonerRoleDto1);
        summonerRoleDtoArrayList.add(summonerRoleDto2);

        Collections.sort(summonerRoleDtoArrayList);

        //딜러진끼리 후보2명이상
        //딜러진중 둘중 딜 낮은애가 벌레

        //딜러+탱커끼리 후보2명이상
        //딜러진중 딜량꼴지가 벌레
        //꼴찌가없다? 탱커가 벌레

        //탱커끼리 후보2명이상
        //딜량비교해서 낮은애가 벌레레
        System.out.println("아이템을 보자 = "+ DetermineRoleVoterMap);

        if(summonerRoleDtoArrayList.stream().filter(summonerRoleDto -> summonerRoleDto.getRole().equals("딜러")).findFirst() != null){
            if(summonerRoleDtoArrayList.get(0).getRole().equals("딜러")){
                worstDto.setSummonerName(summonerRoleDtoArrayList.get(0).getSummonerName());
            }else{
                worstDto.setSummonerName(summonerRoleDtoArrayList.stream().
                        filter(summonerRoleDto -> summonerRoleDto.getRole().equals("딜러")).findFirst().get().getSummonerName());
            }
        }else {
            worstDto.setSummonerName(summonerRoleDtoArrayList.get(0).getSummonerName());
        }

        System.out.println("딜량까지 잘들어왓나 = " + summonerRoleDtoArrayList.toString());
        System.out.println("아이템을 보자 = "+ DetermineRoleVoterMap);
        System.out.println("bug = " + worstDto.getSummonerName());
    }

}