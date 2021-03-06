package bg.stanev.ehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bg.stanev.ehub.service.ItemService;

@Controller
public class IndexController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("items", itemService.getItems());
		// return "index" must be the same like in defs/general.xml <definition name="index" ...
		return "index";
	}
}
