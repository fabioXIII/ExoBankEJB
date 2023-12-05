package it.exolab.exobank.controller;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import it.exolab.exobank.crud.BankAccountCrud;
import it.exolab.exobank.model.BankAccount;
import it.exolab.exobank.model.User;
import it.exolab.exobank.mybatis.SQLMapFactory;

@Stateless(name="BankAccountControllerLocal")
@LocalBean
public class BankAccountController implements BankAccountControllerLocal {

	private BankAccountCrud crud = BankAccountCrud.getInstance();

	public BankAccountController() {

	}

	@Override
	public void insertBankAccount(BankAccount b) {
		// TODO Auto-generated method stub
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		crud.insertBankAccount(b, factory);
		factory.closeSession();

	}

	@Override
	public BankAccount findBankAccountByUserId(Integer id) {

		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		BankAccount b = crud.findBankAccountByUserId(id, factory);
		factory.closeSession();
		return b;
	}

	@Override
	public List<BankAccount> findAllBankAccount() {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		List<BankAccount> listaUser = crud.findAllBankAccount(factory);
		factory.closeSession();

		return listaUser;
	}

	@Override
	public void updateBankAccountStatus(BankAccount b) {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		crud.updateBankAccountStatus(b, factory);
		factory.closeSession();

	}

	@Override
	public void updateBankAccountBalance(Integer bankAccountID, double newBalance) {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		crud.updateBankAccountBalance(bankAccountID, newBalance, factory);

	}

	@Override
	public BankAccount findBankAccountById(Integer id) {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		BankAccount b = crud.findBankAccountById(id, factory);
		factory.closeSession();
		return b;
	}

}
