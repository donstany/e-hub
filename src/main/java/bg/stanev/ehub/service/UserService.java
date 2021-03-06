package bg.stanev.ehub.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bg.stanev.ehub.entity.Blog;
import bg.stanev.ehub.entity.Item;
import bg.stanev.ehub.entity.Role;
import bg.stanev.ehub.entity.User;
import bg.stanev.ehub.repository.BlogRepository;
import bg.stanev.ehub.repository.ItemRepository;
import bg.stanev.ehub.repository.RoleRepository;
import bg.stanev.ehub.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int id) {
		return userRepository.findOne(id);
	}
	
	@Transactional
	public User findOneWithBlogs(int id) {
		User user = findOne(id);
		List<Blog> blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
			/*new PageRequest(0, 20, Direction.DESC, "publishedDate") - 0 is first page , 20 is a record on one page -> limit sql, retrieve from Db, 
			 * direction is a sorting  mechanism relevant to order by sql, on which attribute -> "publishedDate"*/
			List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0, 20, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		return user;
	}

	public void save(User user) {
		user.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(roles);

		userRepository.save(user);
	}

	public User findOneWithBlogs(String name) {
		User user = userRepository.findByName(name);
		return findOneWithBlogs(user.getId());
	}

	public void delete(int id) {
		userRepository.delete(id);
	}

	public User findOne(String username) {
		return userRepository.findByName(username);
	}

}
