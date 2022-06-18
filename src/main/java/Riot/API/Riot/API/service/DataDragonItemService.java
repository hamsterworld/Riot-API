package Riot.API.Riot.API.service;

import Riot.API.Riot.API.aop.executiontimer.ExeTimer;
import Riot.API.Riot.API.aop.executiontimer.ExecutionTimer;
import Riot.API.Riot.API.dto.datadragon.*;
import Riot.API.Riot.API.repository.DataDragonGameItemRepository;
import Riot.API.Riot.API.repository.DataDragonItemTypeRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Riot.API.Riot.API.service.ItemToItemList.ItemToItemList;


@Service
@RequiredArgsConstructor
public class DataDragonItemService {

    private final DataDragonGameItemRepository dataDragonGameItemRepository;
    private final DataDragonItemTypeRepository dataDragonItemTypeRepository;

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @ExeTimer
    public List<GameItem> SaveItemsByDataDragon(){

        Items result;
        List<Item> items = null;
        List<GameItem> gameItemList = null;
        List<ItemType> arrayList = new ArrayList<>();
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://ddragon.leagueoflegends.com/cdn/12.11.1/data/ko_KR/item.json");
            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }
            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), Items.class);
            items = ItemToItemList(result);
            gameItemList = items.stream().map(GameItem::new).collect(Collectors.toList());
            for(int i =0; i<items.size(); i++){
                if(items.get(i).getItemCode() == gameItemList.get(i).getItemCode()){
                    for (String tag : items.get(i).getTags()) {
                        ItemType itemType = new ItemType();
                        itemType.setItemType(tag);
                        itemType.setGameItem(gameItemList.get(i));
                        arrayList.add(itemType);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        dataDragonGameItemRepository.saveAll(gameItemList);
        dataDragonItemTypeRepository.saveAll(arrayList);
        return gameItemList;
    }

    public GameItem FindGameItemByItemCode(int ItemCode){

        return dataDragonGameItemRepository.findByItemCode(ItemCode);

    }

    public List<GameItem> FindListGameItemByListItemCode(List<Integer> itemCodes){
        return null;
        //dataDragonGameItemRepository

    }

}
