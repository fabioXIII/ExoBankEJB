package it.exolab.exobank.controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import it.exolab.exobank.crud.AccountStatusCrud;
import it.exolab.exobank.model.AccountStatus;
import it.exolab.exobank.mybatis.SQLMapFactory;

/**
 * Session Bean implementation class AccountStatusController
 */
@Stateless
@LocalBean
public class AccountStatusController implements AccountStatusControllerLocal {

	private AccountStatusCrud crud = AccountStatusCrud.getInstance();
    /**
     * Default constructor. 
     */
    public AccountStatusController() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void updateAccountStatus(AccountStatus a, Integer bankAccountID) {

		SQLMapFactory factory = SQLMapFactory.instance();
        factory.openSession();
        crud.updateAccountStatus(a, bankAccountID, factory);
        factory.closeSession();
        
	}

}
