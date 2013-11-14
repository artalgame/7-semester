package org.korolchuk.lab5;

import java.math.BigInteger;

public class GarnersAlgorithm 
{
	private Share[] shares;
	private BigInteger secret;
	
	private GarnersAlgorithm(Share[] shares)
	{
		this.shares = shares;
	}
	
	public BigInteger getSecret()
	{
		if (secret == null) secret = restoreSecret();
		return secret;
	}
	
	public static GarnersAlgorithm Create(Share[] shares) throws Exception
	{
		if (shares == null) throw new Exception("Null reference isn't allowed");
		for (Share share : shares)
			if (share == null) throw new Exception("Share can't be null");
		return new GarnersAlgorithm(shares);
	}
	
	private BigInteger[][] getInversesMatrix()
	{
		int amount = this.shares.length;
		BigInteger[][] inverses = new BigInteger[amount][amount];
        for (int i = 0; i < amount; i++)
        	for (int j = 0; j < amount; j++)
        		if (i != j) inverses[i][j] = this.shares[i].getDivider().modInverse(this.shares[j].getDivider());
        return inverses;
	}
	
	private BigInteger[] getVectorX(BigInteger[][] inverses)
	{
		int amount = this.shares.length;
		BigInteger[] vectorX = new BigInteger[amount];
        for (int i = 0; i < amount; i++) 
        {
            vectorX[i] = this.shares[i].getRemainder();
            for (int j = 0; j < i; j++) 
            {
            	vectorX[i] = inverses[j][i].multiply(vectorX[i].subtract(vectorX[j]));
            	vectorX[i] = vectorX[i].mod(this.shares[i].getDivider());
                if (vectorX[i].compareTo(BigInteger.ZERO) == -1) 
                	vectorX[i] = vectorX[i].add(this.shares[i].getDivider());
            }
        }
        return vectorX;
	}
	
	private BigInteger restoreSecret()
	{
		int amount = this.shares.length;
		BigInteger[] vectorX = getVectorX(getInversesMatrix());
		BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < amount; i++)
        {
        	BigInteger temp = vectorX[i];
        	for (int j = 0; j < i; j++)
        		temp = temp.multiply(shares[j].getDivider());
        	result = result.add(temp);
        }
        return result;
	}
}
