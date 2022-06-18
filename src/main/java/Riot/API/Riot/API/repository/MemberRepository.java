package Riot.API.Riot.API.repository;


import Riot.API.Riot.API.dto.test.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
