package nefelus.rpg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nefelus.rpg.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

	public Optional<UserModel> findByEmail(String email);
	
	public Optional<UserModel> findByUser(String user);
}
