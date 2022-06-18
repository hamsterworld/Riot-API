package Riot.API.Riot.API.service;

import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import Riot.API.Riot.API.repository.DataDragonGameItemRepository;
import Riot.API.Riot.API.repository.DataDragonItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


public class EachSummonerItemListGetService {

    @Autowired
    DataDragonGameItemRepository dataDragonGameItemRepository;

    HashMap<String, List<Integer>> SummonersHaveItemList = new LinkedHashMap<String,List<Integer>>();
    HashMap<String, List<GameItem>> SummonersHaveGameItemList = new LinkedHashMap<String,List<GameItem>>();

    public HashMap<String, List<GameItem>> EachSummonerItemListGet(List<ParticipantDto> participants){

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

        for (Map.Entry<String, List<Integer>> stringListEntry : SummonersHaveItemList.entrySet()) {

            List<Integer> value = stringListEntry.getValue();
            List<GameItem> byitem = dataDragonGameItemRepository.findByitem(value);
            SummonersHaveGameItemList.put(stringListEntry.getKey(),byitem);

        }

        return SummonersHaveGameItemList;
    }

}
