package it.exolab.exobank.controller;

import javax.ejb.Local;

import it.exolab.exobank.model.AccountStatus;

@Local
public interface AccountStatusControllerLocal {
	
	public void updateAccountStatus(AccountStatus a , Integer bankAccountID);

}
