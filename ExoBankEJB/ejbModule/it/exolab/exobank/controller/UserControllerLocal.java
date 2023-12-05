package it.exolab.exobank.controller;

import java.util.List;

import javax.ejb.Local;

import org.apache.ibatis.session.SqlSession;

import it.exolab.exobank.model.User;

@Local
public interface UserControllerLocal {

	List<User> findAllUser();
	void insertUser(User u);
	User findByEmailPassword(User u);
	void updateUser(User user);

}
