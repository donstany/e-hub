package bg.stanev.ehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import bg.stanev.ehub.service.UserService;

@Controller
@RequestMapping("/users")
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users";
	}

	/*{id} is a path variable dinamic part of url , front controller convert to integer*/
	@RequestMapping("/{id}")
	public String detail(Model model, @PathVariable int id) {
		// with method findOneWithBlogs(name) we correct lazy initialization exception
		model.addAttribute("user", userService.findOneWithBlogs(id));
		return "user-detail";
	}

	@RequestMapping("/remove/{id}")
	public String removeUser(@PathVariable int id) {
		userService.delete(id);
		return "redirect:/users.html";
	}

}
