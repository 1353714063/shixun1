package club.jxau.shixun.mapper;

import club.jxau.shixun.pojo.User;

public interface UserMapper {
	User selectUserByUsername(String username);
	User login(User user);
	Integer addUser(User user);
	User selectUserById(Integer id);
}
