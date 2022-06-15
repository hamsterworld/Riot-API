package Riot.API.Riot.API.controller;

import Riot.API.Riot.API.dto.MetaData1;
import Riot.API.Riot.API.dto.MetadataDto;
import Riot.API.Riot.API.dto.SummonerDTO;
import Riot.API.Riot.API.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
