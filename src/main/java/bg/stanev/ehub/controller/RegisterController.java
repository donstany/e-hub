package bg.stanev.ehub.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.stanev.ehub.entity.User;
import bg.stanev.ehub.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;

	//spring create a object user from form:form tag in .jsp -> auto binding
	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}
	
	//this method open register form
	@RequestMapping
	public String showRegister() {
		return "user-register";
	}

	//from opened register form take all value of the field and put it in object user -> @ModelAttribute("user") User user
	/*insert into app_user (id, email, enabled, name, password) values (default, ?, ?, ?, ?)
	  insert into app_user_Role (users_id, roles_id) values (?, ?)*/
	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if (result.hasErrors()) {
			return "user-register";
		}
		//"behind scene" insert sql statement into User
		userService.save(user);
		return "redirect:/register.html?success=true";
	}
	
	@RequestMapping("/available")
	@ResponseBody
	public String available(@RequestParam String username) {
		Boolean available = userService.findOne(username) == null;
		return available.toString();
	}

}
