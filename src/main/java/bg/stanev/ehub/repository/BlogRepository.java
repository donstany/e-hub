package bg.stanev.ehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.stanev.ehub.entity.Blog;
import bg.stanev.ehub.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

	List<Blog> findByUser(User user);
}
