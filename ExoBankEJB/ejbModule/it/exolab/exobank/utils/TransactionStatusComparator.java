package it.exolab.exobank.utils;
import java.util.Comparator;

import it.exolab.exobank.model.*;
public class TransactionStatusComparator  implements Comparator<Transactions>{

	@Override
	public int compare(Transactions t1, Transactions t2) {
		
		return t1.getTransactionStatusID().getTransactionStatusName().compareTo(t2.getTransactionStatusID().getTransactionStatusName());
	}

}
