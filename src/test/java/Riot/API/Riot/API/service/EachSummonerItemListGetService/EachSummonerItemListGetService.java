package Riot.API.Riot.API.service.EachSummonerItemListGetService;

import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.dto.gamerecord.ParticipantDto;
import Riot.API.Riot.API.repository.DataDragonGameItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
        List<GameItem> gameItems = dataDragonGameItemRepository.findAll();

        for (Map.Entry<String, List<Integer>> summonerhaveitems : SummonersHaveItemList.entrySet()) {

            List<Integer> value = summonerhaveitems.getValue();
            ArrayList<GameItem> gameList = new ArrayList<>();
            for (Integer itemcode : value) {
                Optional<GameItem> gameItem1 = gameItems.stream().filter(gameItem -> gameItem.getItemCode() == itemcode)
                        .findFirst();
                GameItem gameItem2 = gameItem1.get();
                gameList.add(gameItem2);
            }
            SummonersHaveGameItemList.put(summonerhaveitems.getKey(), gameList);
        }
        return SummonersHaveGameItemList;
    }

}
