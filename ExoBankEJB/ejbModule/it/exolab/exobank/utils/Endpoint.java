package it.exolab.exobank.utils;

public class Endpoint {
	public static final String DOWNLOAD_PDF ="/downloadPdf";
	//USERS
	public static final String FIND_ALL_USERS ="/findAllUser";
	public static final String LOGIN= "/login";
	public static final String VERIFY_OTP ="/verifyOTP";
	public static final String REGISTER="/register";
	public static final String UPDATE_USER="/updateUser";
	public static final String USER_REST="/UserRest";
	//TRANSACTIONS
	public static final String TRANSACTIONS_REST ="/TransactionsRest";
	public static final String UPDATE_TRANSACTION_STATUS ="/updateTransactionStatus";
    public static final String INSERT_TRANSACTIONS ="/insertTransaction";
    public static final String FIND_USER_TRANSACTIONS="/findUserTransactions";
    //BANKACCOUNT
    public static final String BANK_ACCOUNT_REST ="/BankAccountRest";
    public static final String INSERT_BANK_ACCOUNT ="/insertBankAccount";
    public static final String FIND_BANKACCOUNT_BY_USERID="/findBankAccountByUserId";
    public static final String FIND_ALL_BANKACCOUNT= "/findAllBankAccount";
    public static final String UPDATE_BANKACCOUNT_STATUS="/updateBankAccountStatus";
}
