package it.exolab.exobank.controller;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import it.exolab.exobank.crud.UserCrud;
import it.exolab.exobank.model.User;
import it.exolab.exobank.mybatis.SQLMapFactory;
import it.exolab.exobank.email.*;


@Stateless(name="UserControllerLocal")
@LocalBean
public class UserController implements UserControllerLocal {

 
	
	private UserCrud crud = UserCrud.getInstance();
	private SendEmail emailSender = new SendEmail();
    
	
	
    public UserController() {


    }




	@Override
	public List<User> findAllUser() {
		SQLMapFactory.instance().openSession();
		SQLMapFactory factory = SQLMapFactory.instance();
		List<User> listaUser = crud.findAllUser(factory);
		factory.closeSession();
 		return listaUser;
	}




	@Override
	public void insertUser(User u) {
		SQLMapFactory factory = SQLMapFactory.instance();
        factory.openSession();
        crud.insertUser(u, factory);
        String emailDestinatario = u.getEmail();
        System.out.println(u.getEmail());
        emailSender.sendEmail(emailDestinatario,"Registrazione exobank", "Sei un bamboccio");
        factory.closeSession();
		
	}




	@Override
	public User findByEmailPassword(User user) {
		SQLMapFactory factory = SQLMapFactory.instance();
		User u = null;
		try {
			 factory.openSession();
		         u = crud.findByEmailPassword(user, factory);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
       finally {
           factory.closeSession();

       }
       return u;
        
	}




	@Override
	public void updateUser(User user) {
		SQLMapFactory factory = SQLMapFactory.instance();
        factory.openSession();
        crud.updateUser(user, factory);
        factory.closeSession();
		
	}
}




	