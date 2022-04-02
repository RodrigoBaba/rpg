package nefelus.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nefelus.rpg.model.CharacterModel;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterModel, Long>{

}
