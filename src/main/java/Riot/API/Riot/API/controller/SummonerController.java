package Riot.API.Riot.API.controller;

import Riot.API.Riot.API.dto.MatchIdDto;
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

        List<String> apiResult = summonerService.callRiotAPISummonerGameRecord(ppuid);

        return apiResult;
    }


}
