package club.jxau.shixun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.jxau.shixun.mapper.UserMapper;
import club.jxau.shixun.pojo.User;
import club.jxau.shixun.service.UserService;
import club.jxau.shixun.util.PasswordEncoder;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	

	@Override
	public boolean addUser(User user) {
		if(user==null||user.getPassword()==null||user.getUsername()==null) {
			return false;
		}
		if(user.getPassword().length()<8||user.getUsername().length()<6) {
			return false;
		}
		if(this.userMapper.selectUserByUsername(user.getUsername())!=null) {
			return false;
		}
		user.setPassword(PasswordEncoder.encodeStr(user.getPassword()));
		if(this.userMapper.addUser(user)!=0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean login(User user) {
		user.setPassword(PasswordEncoder.encodeStr(user.getPassword()));
		if(this.userMapper.login(user)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public User findUser(Integer id) {
		return this.userMapper.selectUserById(id);
	}

	@Override
	public User findUser(String username) {
		return this.userMapper.selectUserByUsername(username);
	}

	@Override
	public boolean finUser(String username) {
		if(this.userMapper.selectUserByUsername(username)!=null)
			return true;
		return false;
	}
	
}
