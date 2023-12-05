package it.exolab.exobank.utils;



import it.exolab.exobank.model.Transactions;

public class CheckBalance {
	
	public boolean checkTransaction(Transactions transaction,double amount ) {
		 double balance = transaction.getBankAccountID().getBalance();
		 
		if(transaction.getTransactionTypeID().getTransactionTypeName().equalsIgnoreCase("WITHDRAWAL")
				|| transaction.getTransactionTypeID().getTransactionTypeName().equalsIgnoreCase("BANK TRANSFER")
				&& transaction.getAmount()<=balance){
			System.out.println("Il saldo permette la transazione");
			return true;
		}
		else {
			System.out.println("Saldo insufficiente");
			return false;
		}
	
		
	}

}
