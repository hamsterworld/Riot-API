package Riot.API.Riot.API.test;

import Riot.API.Riot.API.dto.datadragon.GameItem;
import Riot.API.Riot.API.dto.datadragon.ItemType;
import Riot.API.Riot.API.dto.test.Member;
import Riot.API.Riot.API.dto.test.Team;
import Riot.API.Riot.API.repository.DataDragonGameItemRepository;
import Riot.API.Riot.API.repository.DataDragonItemTypeRepository;
import Riot.API.Riot.API.repository.MemberRepository;
import Riot.API.Riot.API.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class test {

    @Autowired
    DataDragonGameItemRepository dataDragonGameItemRepository;
    @Autowired
    DataDragonItemTypeRepository dataDragonItemTypeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void test1(){


        GameItem gameItem = new GameItem();
        gameItem.setItemCode(1001);
        gameItem.setItemName("햄스터의검");

        ItemType itemType1 = new ItemType();
        itemType1.setItemType("존나게아픔");
        itemType1.setGameItem(gameItem);

        ItemType itemType2 = new ItemType();
        itemType2.setItemType("커여움");
        itemType2.setGameItem(gameItem);

        ItemType itemType3 = new ItemType();
        itemType3.setItemType("깜찍함");
        itemType3.setGameItem(gameItem);

        ArrayList<ItemType> objects = new ArrayList<>();
        objects.add(itemType1);
        objects.add(itemType2);
        objects.add(itemType3);

        dataDragonGameItemRepository.save(gameItem);
        dataDragonItemTypeRepository.saveAll(objects);

        System.out.println("완료");

    }

    @Test
    void test2(){


        Team team = new Team();
        team.setTeamname("햄스터팀");

        Member member1 = new Member();
        member1.setName("골든햄스터");
        member1.setTeam(team);
        Member member2 = new Member();
        member2.setName("드워프햄스터");
        member2.setTeam(team);
        Member member3 = new Member();
        member3.setName("페디그리햄스터");
        member3.setTeam(team);

        ArrayList<Member> objects = new ArrayList<>();
        objects.add(member1);
        objects.add(member2);
        objects.add(member3);

        teamRepository.save(team);
        memberRepository.saveAll(objects);


        System.out.println("완료");

    }

    @Test
    void test3(){

        Team team = new Team();
        team.setTeamname("햄스터팀");

        Member member1 = new Member();
        member1.setName("골든햄스터");
        member1.setTeam(team);
        Member member2 = new Member();
        member2.setName("드워프햄스터");
        member2.setTeam(team);
        Member member3 = new Member();
        member3.setName("페디그리햄스터");
        member3.setTeam(team);
        Member member4 = new Member();
        member4.setName("뚠뚠이햄스터");
        member4.setTeam(team);

        em.persist(team);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

    }




}
