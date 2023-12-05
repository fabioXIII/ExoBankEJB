package it.exolab.exobank.utils;
import java.util.concurrent.ThreadLocalRandom;
public class RandomOTP {
        
	
	
    public int createOTP() {

    	 int min = 100000;
    	 int max = 999999;

    	    return ThreadLocalRandom.current().nextInt(min, max + 1);

    }
    



}
