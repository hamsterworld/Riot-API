package Riot.API.Riot.API.controller;

import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.summoner.SummonerDTO;
import Riot.API.Riot.API.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    @ResponseBody
    public SummonerDTO callSummonerByName(String summonerName){

        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);

        return apiResult;
    }

    @PostMapping(value = "/gamerecordbyname")
    @ResponseBody
    public ArrayList<MetaData1> callGameRecordByName(String summonerName){

        summonerName = summonerName.replaceAll(" ","%20");
        ArrayList<MetaData1> list = new ArrayList<>();

        SummonerDTO summonerResult = summonerService.callRiotAPISummonerByName(summonerName);
        List<String> MatchIdResult = summonerService.callRiotAPISummonerMatchIdBypuuid(summonerResult.getPuuid());
        for (String s : MatchIdResult) {
            MetaData1 metaData1 = summonerService.callRiotAPIGameRecordByMatchId(s);
            list.add(metaData1);
        };

        //여기는 entity로 보여주면안되고 dto로 보내줘야한다.
        //첫번째로 뭐냐면 말그대로 게임에대한 전적조회기능이다.
        //최근 5경기만 조회할수있게 해보자.

        return list;
    }



    @PostMapping(value = "/matchidByppuid")
    @ResponseBody
    public List<String> callMatchIdByPpuid(String ppuid){

        List<String> apiResult = summonerService.callRiotAPISummonerMatchIdBypuuid(ppuid);

        return apiResult;
    }

    @PostMapping(value = "/matchid")
    @ResponseBody
    public MetaData1 callMatchIdBymatchId(String matchid){

        MetaData1 apiResult = summonerService.callRiotAPIGameRecordByMatchId(matchid);

        return apiResult;
    }


}
