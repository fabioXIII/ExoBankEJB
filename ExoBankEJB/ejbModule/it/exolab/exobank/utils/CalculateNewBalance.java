package it.exolab.exobank.utils;
import it.exolab.exobank.model.*;
import it.exolab.exobank.mybatis.SQLMapFactory;
import it.exolab.exobank.crud.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculateNewBalance {
	
	BankAccountCrud bankCrud = BankAccountCrud.getInstance();
	
	public  double calculateNewBalance(Transactions s, BankAccount bankAccount , SQLMapFactory factory) {
	    double newBalance = bankAccount.getBalance();
	    String transactionTypeName = s.getTransactionTypeID().getTransactionTypeName();
	    Integer bankAccountId = s.getBankAccountID().getBankAccountID();
	    
	    DecimalFormat decimalFormat = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));



	    if (transactionTypeName.equalsIgnoreCase("DEPOSIT")) {
	    	
	        newBalance += s.getAmount();
	        double formattedBalance = Double.parseDouble(decimalFormat.format(newBalance));
	    } else if (transactionTypeName.equalsIgnoreCase("WITHDRAWAL")) {
	        newBalance -= s.getAmount();
	        double formattedBalance = Double.parseDouble(decimalFormat.format(newBalance));

	    } else if (transactionTypeName.equalsIgnoreCase("BANK TRANSFER")) {
	        Integer targetBankAccountId = s.getTargetBankAccountID().getBankAccountID();
	        BankAccount targetBankAccount = bankCrud.findBankAccountById(targetBankAccountId, factory);

	        if (targetBankAccount != null) {
	        	
	            double newBalanceOrigin = bankAccount.getBalance() - s.getAmount();
	            double newBalanceDestination = targetBankAccount.getBalance() + s.getAmount();
	            
		        double formattedBalanceOrigin = Double.parseDouble(decimalFormat.format(newBalanceOrigin));
		        double formattedBalanceDestination = Double.parseDouble(decimalFormat.format(newBalanceDestination));

	            bankCrud.updateBankAccountBalance(bankAccountId, formattedBalanceOrigin, factory);
	            bankCrud.updateBankAccountBalance(targetBankAccountId, formattedBalanceDestination, factory);

	            return -1; // Indica che l'aggiornamento del saldo è stato gestito separatamente
	        } else {
	            System.out.println("Target Bank Account not found");
	        }
	    }

	    return newBalance;
	}

}
