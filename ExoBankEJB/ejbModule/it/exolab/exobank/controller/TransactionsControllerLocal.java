package it.exolab.exobank.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

import it.exolab.exobank.model.Transactions;
import it.exolab.exobank.model.User;
import it.exolab.exobank.model.UserTransactions;

@Local
public interface TransactionsControllerLocal {
	List<Transactions> findAllTransactions();
	void updateTransactionStatus(Transactions s);
	void insertTransaction(Transactions t);
	List<Transactions> findUserTransactions(Integer bankId);
	byte[] generatePdf(List<Transactions> transazioni, User user) throws DocumentException, IOException;
    List<UserTransactions> findUserTransactions2(Integer bankId);
}
