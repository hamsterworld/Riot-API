package Riot.API.Riot.API.repository;


import Riot.API.Riot.API.dto.datadragon.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DataDragonGameItemRepository extends JpaRepository<GameItem,Long> {

    GameItem findByItemCode(int ItemCode);

    @Query("select g from GameItem g")
    List<GameItem> findByItemCodes();

    //@Query("select g from GameItem g where g.itemCode in :itemCodes")
    //List<GameItem> findByitem(@Param("itemCodes") List<Integer> itemCodes);

    @Query("select distinct g from GameItem g left join fetch g.itemTypes where g.itemCode in :itemCodes")
    List<GameItem> findByitem(@Param("itemCodes") List<Integer> itemCodes);

}
