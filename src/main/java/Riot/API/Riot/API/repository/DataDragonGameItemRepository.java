package Riot.API.Riot.API.repository;


import Riot.API.Riot.API.aop.executiontimer.ExeTimer;
import Riot.API.Riot.API.dto.datadragon.GameItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataDragonGameItemRepository extends JpaRepository<GameItem,Long> {

    GameItem findByItemCode(int ItemCode);

    @Query("select g from GameItem g")
    List<GameItem> findByItemCodes();

    //@Query("select g from GameItem g where g.itemCode in :itemCodes")
    //List<GameItem> findByitem(@Param("itemCodes") List<Integer> itemCodes);

    @Query("select distinct g from GameItem g left join fetch g.itemTypes where g.itemCode in :itemCodes")
    List<GameItem> findByitem(@Param("itemCodes") List<Integer> itemCodes);

    //@Query("INSERT INTO GameItem g (g., col2) VALUES")
    //Long findByNames(@Param("names") List<String> names);

    //@Query("select m from Member m where m.username in :names")
    //List<Member> findByNames(@Param("names") List<String> names);

    @EntityGraph(attributePaths = {"itemTypes"})
    List<GameItem> findAll();

}
