package bg.stanev.ehub.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

@Transactional
@Service
public class InitDbService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;

	//This will execute immediately after run application. 
	//In dev mode execute after every restart the server. HSQL is inmemory database
	//In prod mode if there is no data in empty database postgree, oracle , etc. 
	@PostConstruct
	public void init() {
		// check if in developer mode and there is role admin don't seed, populate bellow data!
		if (roleRepository.findByName("ROLE_ADMIN") == null) {
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepository.save(roleUser);

			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);

			User userAdmin = new User();
			userAdmin.setEnabled(true);
			userAdmin.setName("admin");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userAdmin.setPassword(encoder.encode("admin"));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);

			Blog blogNakov = new Blog();
			blogNakov.setName("NakovBlog");
			// url must contain xml with rss feed  
			// all other link can't expected result. Not will throw exception but put only link in database.
			blogNakov
					.setUrl("http://www.nakov.com/feed/");
			blogNakov.setUser(userAdmin);
			blogRepository.save(blogNakov);

			// Item item1 = new Item();
			// item1.setBlog(blogJ);
			// item1.setTitle("first");
			// item1.setLink("http://feeds.bbci.co.uk/news/world/rss.xml");
			// item1.setPublishedDate(new Date());
			// itemRepository.save(item1);
			//
			// Item item2 = new Item();
			// item2.setBlog(blogV);
			// item2.setTitle("second");
			// item2.setLink("http://feeds.bbci.co.uk/news/world/rss.xml");
			// item2.setPublishedDate(new Date());
			// itemRepository.save(item2);
		}

	}
}
