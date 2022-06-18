package Riot.API.Riot.API.repository;

import Riot.API.Riot.API.dto.test.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
