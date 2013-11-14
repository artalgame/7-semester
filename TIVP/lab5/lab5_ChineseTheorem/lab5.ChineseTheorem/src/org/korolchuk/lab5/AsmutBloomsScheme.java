package org.korolchuk.lab5;

import java.math.BigInteger;
import java.util.Arrays;

public class AsmutBloomsScheme {
	private final static int powerOfTwo = 521;
	private BigInteger secret;
	private BigInteger secret1;
	private BigInteger p;
	private BigInteger[] dividers;
	private BigInteger[] remainders;
	private int sharesNumber;
	private int participantsNumber;

	private AsmutBloomsScheme(BigInteger secret, int sharesNumber,
			int participantsNumber) {
		this.p = BigInteger.valueOf(2).pow(powerOfTwo).subtract(BigInteger.ONE);
		this.secret = secret;
		this.sharesNumber = sharesNumber;
		this.participantsNumber = participantsNumber;
	}

	public static AsmutBloomsScheme Create(BigInteger secret, int sharesNumber,
			int participantsNumber) throws Exception {
		if (secret == null) {
			throw new Exception("Null reference isn't allowed");
		}
		byte[] arr = secret.toByteArray();
		if (arr.length * 8 >= powerOfTwo) {
			throw new Exception(String.format(
					"Secret must be less than %d bits", powerOfTwo));
		}
		if (secret.compareTo(BigInteger.ZERO) == -1) {
			throw new Exception("Secret must be positive number");
		}
		if (sharesNumber < 1) {
			throw new Exception("Number of shares must be more than 1");
		}
		if (participantsNumber < 1 || participantsNumber > sharesNumber) {
			throw new Exception(
					"Number of participants must be more than 1 and no more than number of shares");
		}
		AsmutBloomsScheme abs = new AsmutBloomsScheme(secret, sharesNumber,
				participantsNumber);
		abs.generate();
		return abs;
	}

	public boolean checkSecret(BigInteger value) {
		return this.secret.compareTo(value) == 0;
	}

	private void generate() {
		try {
			generateDividers();
			generateSecret1();
			generateRemainders();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean generateDividers() {
		try {
			Generator generator = Generator.create(powerOfTwo / 8 + 2);
			BigInteger[] d = generator.getCoprimeNumbers(this.sharesNumber,
					this.p);
			Arrays.sort(d);
			while (!checkCondition3(d)) {
				d = generator.getCoprimeNumbers(this.sharesNumber, this.p);
				Arrays.sort(d);
			}
			this.dividers = d;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean generateSecret1() {
		try {
			BigInteger mult = BigInteger.ONE;
			for (int i = 0; i < this.participantsNumber; i++)
				mult = mult.multiply(this.dividers[i]);
			BigInteger r = mult.subtract(secret).divide(this.p);
			this.secret1 = this.secret.add(this.p.multiply(r
					.subtract(BigInteger.ONE)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean generateRemainders() {
		try {
			this.remainders = new BigInteger[this.sharesNumber];
			for (int i = 0; i < this.remainders.length; i++)
				this.remainders[i] = this.secret1.mod(this.dividers[i]);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean checkCondition3(BigInteger[] coprimes) {
		int n = coprimes.length;
		BigInteger mult1 = BigInteger.ONE;
		for (int i = 0; i < this.participantsNumber; i++)
			mult1 = mult1.multiply(coprimes[i]);
		BigInteger mult2 = this.p;
		for (int i = n - this.participantsNumber + 1; i < n; i++)
			mult2 = mult2.multiply(coprimes[i]);
		return mult1.compareTo(mult2) == 1;
	}

	public Share[] getShares() throws Exception {
		Share[] res = new Share[this.sharesNumber];
		for (int i = 0; i < this.sharesNumber; i++)
			res[i] = Share.Create(this.dividers[i], this.remainders[i], this.p);
		return res;
	}

	public BigInteger restoreSecret(Share[] shares) {
		try {
			GarnersAlgorithm ga = GarnersAlgorithm.Create(shares);
			BigInteger s1 = ga.getSecret();
			return s1.mod(shares[0].getP());
		} catch (Exception e) {
			return null;
		}
	}

}
