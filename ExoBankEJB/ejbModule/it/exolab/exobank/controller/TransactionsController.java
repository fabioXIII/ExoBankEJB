package it.exolab.exobank.controller;

import it.exolab.exobank.utils.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import it.exolab.exobank.pdf.*;
import it.exolab.exobank.crud.*;
import it.exolab.exobank.model.BankAccount;
import it.exolab.exobank.model.Transactions;
import it.exolab.exobank.model.User;
import it.exolab.exobank.model.UserTransactions;
import it.exolab.exobank.mybatis.SQLMapFactory;

/**
 * Session Bean implementation class TransactionsController
 */
@Stateless(name="TransactionsControllerLocal")
@LocalBean
public class TransactionsController implements TransactionsControllerLocal {

	private TransactionsCrud crud = TransactionsCrud.getInstance();
	private BankAccountCrud bankCrud = BankAccountCrud.getInstance();
	
	private CalculateNewBalance balanceCalculator = new CalculateNewBalance();
	

    /**
     * Default constructor. 
     */
    public TransactionsController() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Transactions> findAllTransactions() {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		List<Transactions> transactionsList =crud.findAllTransactions(factory);
		factory.closeSession();
		return transactionsList;
	}

	@Override
	
	public void updateTransactionStatus(Transactions s) {
		 SQLMapFactory factory = SQLMapFactory.instance();
		    factory.openSession();

		    // Recupero l'id del bank account tramite la transazione passata
		    Integer bankAccountId = s.getBankAccountID().getBankAccountID();
		    // Utilizzo l'id trovato per trovare il bankAccount
		    BankAccount bankAccount = bankCrud.findBankAccountById(bankAccountId, factory);
		    
		    double newBalance = 0;

		    if (bankAccount != null) {
		         newBalance = balanceCalculator.calculateNewBalance(s, bankAccount, factory);
		         crud.updateTransactionStatus(factory, s);
                   //se mi torna -1 vuol dire che era un transfer che e stato gia aggiornato nel metodo
		        if (newBalance != -1) {
		        	// se NON mi e tornato -1 vuol dire che bisogna ancora fare gli aggiornamenti
		            bankCrud.updateBankAccountBalance(bankAccountId, newBalance, factory);
		            crud.updateTransactionStatus(factory, s);
		        }
		    }

		    factory.closeSession();
		}
	@Override
	public void insertTransaction(Transactions t) {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		crud.insertTransaction(factory, t);
		factory.closeSession();
		
	}

	@Override
	public List<Transactions> findUserTransactions(Integer bankAccountId) {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		List<Transactions> transactionsUser =crud.findUserTransactions(factory,bankAccountId);
		factory.closeSession();
		
		//ordino le transazioni secondo lo status 
		Collections.sort(transactionsUser, new TransactionStatusComparator());
		
		return transactionsUser;
	}
	@Override
	public byte[] generatePdf(List<Transactions> transazioni, User user) throws DocumentException, IOException {
		PdfGenerator pdfGenerator = new PdfGenerator();
		byte[] transactionsPdf =pdfGenerator.generatePdf(transazioni, user);
		return transactionsPdf;
		
	}

	@Override
	public List<UserTransactions> findUserTransactions2(Integer bankId) {
		SQLMapFactory factory = SQLMapFactory.instance();
		factory.openSession();
		List<UserTransactions> userTransactions = crud.findUserTransactions2(factory, bankId);
		return userTransactions;
	}

}
