package it.exolab.exobank.controller;

import java.util.List;

import javax.ejb.Local;

import it.exolab.exobank.mapper.BankAccountMapper;
import it.exolab.exobank.model.BankAccount;
import it.exolab.exobank.model.User;

@Local
public interface BankAccountControllerLocal {
	
	void insertBankAccount(BankAccount b);
	
	BankAccount findBankAccountByUserId(Integer id);
	
	BankAccount findBankAccountById(Integer id);
	
	List <BankAccount> findAllBankAccount();
	
	void updateBankAccountStatus(BankAccount b);
	
	void updateBankAccountBalance(Integer bankAccountID, double newBalance);
	

}
