package nefelus.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nefelus.rpg.model.AttributeModel;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeModel, Long>{

}
