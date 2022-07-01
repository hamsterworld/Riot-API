package Riot.API.Riot.API.service.DataDragonItemSerivce;

import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.repository.DataDragonGameItemRepository;
import Riot.API.Riot.API.service.DataDragonItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Rollback(value = false)
public class DataDragonItemServiceTest {


    @Autowired
    DataDragonGameItemRepository dataDragonGameItemRepository;
    @Autowired
    DataDragonItemService dataDragonItemService;

    @BeforeAll
    public void test(){

        dataDragonItemService.SaveItemsByDataDragon();

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
    @DisplayName("6개의 ItemCode를 이용하여 DB에서 소환사의 전체 아이템 정보를 각각 얻어옵니다.")
    public void FindListGameItemByListItemCodev1(){

        List<Integer> itemcodes = new ArrayList<>();
        itemcodes.add(6653);
        itemcodes.add(3157);
        itemcodes.add(3165);
        itemcodes.add(3089);
        itemcodes.add(3020);
        itemcodes.add(3135);

        GameItem byItemCode1 = dataDragonGameItemRepository.findByItemCode(itemcodes.get(0));
        GameItem byItemCode2 = dataDragonGameItemRepository.findByItemCode(itemcodes.get(1));
        GameItem byItemCode3 = dataDragonGameItemRepository.findByItemCode(itemcodes.get(2));
        GameItem byItemCode4 = dataDragonGameItemRepository.findByItemCode(itemcodes.get(3));
        GameItem byItemCode5 = dataDragonGameItemRepository.findByItemCode(itemcodes.get(4));
        GameItem byItemCode6 = dataDragonGameItemRepository.findByItemCode(itemcodes.get(5));

        System.out.println(byItemCode1.getItemName());
        System.out.println(byItemCode2.getItemName());
        System.out.println(byItemCode3.getItemName());
        System.out.println(byItemCode4.getItemName());
        System.out.println(byItemCode5.getItemName());
        System.out.println(byItemCode6.getItemName());

    }

    @Test
    @DisplayName("GameCodeList를통해서 한번에 가져오기.")
    public void FindListGameItemByListItemCodeV2(){

        //@Query("select g from GameItem g where g.itemCode in :itemCodes")
        //List<GameItem> findByitem(@Param("itemCodes") List<Integer> itemCodes);

        /**
         * 유동적으로 가져올수있다.
         */
        List<Integer> itemcodes = new ArrayList<>();
        itemcodes.add(6653);
        itemcodes.add(3157);
        itemcodes.add(3165);


        List<GameItem> byitem = dataDragonGameItemRepository.findByitem(itemcodes);
        System.out.println(byitem.size());

    }

    @Test
    @DisplayName("GameCodeList를통해서 한번에 가져오기 fetch join으로 Lazy없이 가져오기. 1+N문제 해결하기.")
    public void FindListGameItemByListItemCodeV3(){

        //@Query("select distinct g from GameItem g left join fetch g.itemTypes where g.itemCode in :itemCodes")
        //List<GameItem> findByitem(@Param("itemCodes") List<Integer> itemCodes);

        /**
         * 유동적으로 가져올수있다.
         */
        List<Integer> itemcodes = new ArrayList<>();
        itemcodes.add(6653);
        itemcodes.add(3157);
        itemcodes.add(3165);

        List<GameItem> byitem = dataDragonGameItemRepository.findByitem(itemcodes);

    }

}
