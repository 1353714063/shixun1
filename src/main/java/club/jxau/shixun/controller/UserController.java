package club.jxau.shixun.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.jxau.shixun.config.WebSecurityConfig;
import club.jxau.shixun.pojo.User;
import club.jxau.shixun.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	} 
	
	@GetMapping("login")
	public String login(Model model,String comefrom) {
		if(comefrom==null||comefrom.equals("")) {
			model.addAttribute("comefrom","index");
		}else {
			model.addAttribute("comefrom", comefrom);
		}
		return "login";
	}
	@PostMapping("login")
	public String dologin(User user,String comefrom,HttpSession session,Model model) {
		if(this.userService.login(user)) {
			session.setAttribute(WebSecurityConfig.SESSION_KEY, user);
			return "redirect:/"+comefrom;
		}
		model.addAttribute("msg","用户名或密码错误");
		return "login";
	}
	
	@GetMapping("register")
	public String register(String comefrom,Model model) {
		if(comefrom==null||comefrom.equals("")) {
			model.addAttribute("comefrom","login");
		}else {
			model.addAttribute("comefrom", comefrom);
		}
		return "register";
	}
	
	@PostMapping("register")
	public String checkregister(String comefrom,User user,HttpSession session,Model model) {
		if(this.userService.addUser(user)) {
			model.addAttribute("msg","注册成功，请登录！");
			session.setAttribute(WebSecurityConfig.SESSION_KEY, user);
			return "redirect:/"+comefrom;
		}
		model.addAttribute("msg","注册失败，请重新注册！");
		return "register";
	}
	
	@PostMapping("checkUsername")
	@ResponseBody
	public String checkUsername(String username) {
		JSONObject json=new JSONObject();
		if(username.length()<6) {
			json.append("statu", "长度不能小于6");
			return json.toString();
		}if(this.userService.finUser(username)) {
			json.append("statu", "该用户名已存在");
			return json.toString();
		}
		json.append("statu", "成功");
		return json.toString();
	}
}
