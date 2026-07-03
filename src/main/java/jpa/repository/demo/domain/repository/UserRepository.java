package jpa.repository.demo.domain.repository;

import jpa.repository.demo.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByNome(String nome)throws UsernameNotFoundException;
}
