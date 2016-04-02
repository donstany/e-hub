package bg.stanev.ehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.stanev.ehub.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByName(String name);

}
