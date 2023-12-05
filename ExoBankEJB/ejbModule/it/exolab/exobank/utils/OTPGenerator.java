package it.exolab.exobank.utils;

import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.codec.binary.Base32;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class OTPGenerator {
	
	 private static final OTPGenerator instance = new OTPGenerator();

	    private OTPGenerator() {
	        // Costruttore privato per impedire l'istanziazione diretta
	    }

	    public static OTPGenerator getInstance() {
	        return instance;
	    }

	private long counter = 0;
	private long maxCounter = 10_000; // Numero massimo di login prima di reimpostare il contatore
	private long otpValidityDuration = 3 * 60; // Durata di validità dell'OTP in secondi (3 minuti)

	private long lastOTPGenerationTime = 0;
	private long currentTime =0;

	// Genera una chiave segreta casuale
	public String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] buffer = new byte[10];
		random.nextBytes(buffer);
		Base32 codec = new Base32();
		byte[] secretKeyBytes = codec.encode(buffer);
		return new String(secretKeyBytes);
	}

	public String generateOTP(String secretKey) {
		
		   currentTime = System.currentTimeMillis() / 1000; // Tempo corrente in secondi
		if (counter >= maxCounter) {
			// Reimposta il contatore o genera un nuovo OTP se scaduto
			counter = 0;
		}

		try {
			byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
			byte[] counterBytes = new byte[8];

			for (int i = 0; i < 8; i++) {
				counterBytes[7 - i] = (byte) (counter & 0xFF);
				counter >>= 8;
			}

			SecretKeySpec key = new SecretKeySpec(keyBytes, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(key);
			byte[] result = mac.doFinal(counterBytes);

			// Calcola l'offset e genera l'OTP a 6 cifre
			int offset = result[result.length - 1] & 0x0F;
			int binary = ((result[offset] & 0x7F) << 24) | ((result[offset + 1] & 0xFF) << 16)
					| ((result[offset + 2] & 0xFF) << 8) | (result[offset + 3] & 0xFF);
			int otpValue = binary % 1_000_000;

			return String.format("%06d", otpValue);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
			return null;
		} finally {
			counter++;
			setLastOTPGenerationTime(currentTime);
		}
	}

	public long getLastOTPGenerationTime() {
		return lastOTPGenerationTime;
	}

	public void setLastOTPGenerationTime(long lastOTPGenerationTime) {
		this.lastOTPGenerationTime = lastOTPGenerationTime;
	}

	public long getOtpValidityDuration() {
		return otpValidityDuration;
	}

	public void setOtpValidityDuration(long otpValidityDuration) {
		this.otpValidityDuration = otpValidityDuration;
	}

}
