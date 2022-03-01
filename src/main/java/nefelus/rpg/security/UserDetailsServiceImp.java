package nefelus.rpg.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nefelus.rpg.model.UserModel;
import nefelus.rpg.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

	private @Autowired UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserModel> user = userRepository.findByEmail(email);
		
		if(user.isPresent()) {
			return new UserDetailsImpl(user.get());
		} else {
			throw new UsernameNotFoundException("Email Inexistente!");
		}		
	}
}
