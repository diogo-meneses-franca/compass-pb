package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
