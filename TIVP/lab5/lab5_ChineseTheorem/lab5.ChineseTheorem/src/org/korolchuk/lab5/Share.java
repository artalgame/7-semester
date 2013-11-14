package org.korolchuk.lab5;

import java.math.BigInteger;

public class Share {
	private BigInteger divider;
	private BigInteger remainder;
	private BigInteger p;

	private Share(BigInteger divider, BigInteger remainder, BigInteger p) {
		this.divider = divider;
		this.remainder = remainder;
		this.p = p;
	}

	public static Share Create(BigInteger divider, BigInteger remainder,
			BigInteger p) throws Exception {
		if (divider == null || remainder == null || p == null)
			throw new Exception("Null reference isn't allowed");
		return new Share(divider, remainder, p);
	}

	public BigInteger getDivider() {
		return this.divider;
	}

	public BigInteger getRemainder() {
		return this.remainder;
	}

	public BigInteger getP() {
		return this.p;
	}
}
