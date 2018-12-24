package club.jxau.shixun.service;

import club.jxau.shixun.pojo.User;

public interface UserService {
	boolean login(User user);
	User findUser(Integer id);
	User findUser(String userName);
	boolean addUser(User user);
	boolean finUser(String username);
}
