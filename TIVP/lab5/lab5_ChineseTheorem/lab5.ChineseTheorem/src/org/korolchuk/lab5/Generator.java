package org.korolchuk.lab5;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Generator {
	private Random random;
	private byte[] bytes;
	
	private Generator(int dimension)
	{
		random = new Random();
		bytes = new byte[dimension];
	}
	
	public static Generator create(int dimension) throws Exception
	{
		if (dimension <= 0) throw new Exception("Incorrect 'dimension' value");
		return new Generator(dimension);
	}
	
	public BigInteger[] getCoprimeNumbers(int count, BigInteger minValue) throws Exception
    {
        if (count <= 0) throw new Exception("Incorrect 'count' value");
        ArrayList<BigInteger> res = new ArrayList<BigInteger>();
        for (int i = 0; i < count; i++)
        {
        	random.nextBytes(bytes);
            BigInteger r = new BigInteger(bytes).abs();
            while (!isCoprime(res, r) || !(r.compareTo(minValue) == 1))
            {
            	random.nextBytes(bytes);
            	r = new BigInteger(bytes).abs();
            }
            res.add(r);
        }
        return res.toArray(new BigInteger[res.size()]);
    }

    private boolean isCoprime(ArrayList<BigInteger> coprimes, BigInteger number) throws Exception
    {
        if (coprimes == null || number == null) throw new Exception("Null reference isn't allowed");
        for (BigInteger n : coprimes)
        	if (n.gcd(number).compareTo(BigInteger.ONE) == 1) return false;
        return true;
    }

}
