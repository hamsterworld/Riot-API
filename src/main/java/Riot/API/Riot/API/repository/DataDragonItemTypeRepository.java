package Riot.API.Riot.API.repository;

import Riot.API.Riot.API.dto.datadragon.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataDragonItemTypeRepository extends JpaRepository<ItemType,Long> {
}
