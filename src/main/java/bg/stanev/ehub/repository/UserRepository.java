package bg.stanev.ehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.stanev.ehub.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByName(String name);


}
