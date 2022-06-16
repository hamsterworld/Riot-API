package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.ParticipantDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EachSummonerItemListGetService {

    private HashMap<String, List<Integer>> SummonersHaveItemList = new HashMap<String,List<Integer>>();

    public HashMap<String, List<Integer>> EachSummonerItemListGet(List<ParticipantDto> participants){

        for (ParticipantDto participant : participants) {
            List<Integer> list = new ArrayList<>();
            list.add(participant.getItem0());
            list.add(participant.getItem1());
            list.add(participant.getItem2());
            list.add(participant.getItem3());
            list.add(participant.getItem4());
            list.add(participant.getItem5());
            list.add(participant.getItem6());
            SummonersHaveItemList.put(participant.getSummonerName(),list);

        }

        return SummonersHaveItemList;

    }


}
