package com.artalgame.lab6;

import java.io.Console;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Main {
	static int r=3;
	static int seedlen = 20;
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		Random random = new Random();
		MessageDigest md;
		byte[] bytes = new byte[seedlen];
		md = MessageDigest.getInstance("SHA-1");
		
		while(true)
		{	
			random.nextBytes(bytes);
			
			BigInteger seed = new BigInteger(1,bytes);
			md.update(seed.toByteArray());
			BigInteger s1 = new BigInteger(1, md.digest());
			
			BigInteger seed1 = (seed.add(BigInteger.ONE)).mod((new BigInteger("2")).pow(seedlen));
			md.update(seed1.toByteArray());
			BigInteger s2 = new BigInteger(1, md.digest());
			
			BigInteger u = s1.xor(s2);
			if(isPrime(u))
			{
				String str = String.format("Prime: (%s)", u);
				System.out.println(str);
				System.in.read();
				break;
			}
		}
	}
	
	public static boolean isPrime(BigInteger m){
		try{
			
		
		//check is odd
		if(m.mod(new BigInteger("2")).compareTo(BigInteger.ZERO)==0) return false;
		
		//Представить m − 1 в виде 2^s·t, где t нечётно, можно сделать последовательным делением m - 1 на 2.
		BigInteger m1 = m.subtract(BigInteger.ONE);
		int s=1;
		int p=2;
		BigInteger t;
		while (true)
		{
			t = m1.shiftRight(s);
			if(t.mod(new BigInteger("2")).compareTo(BigInteger.ZERO)==0)
			{
				s++;
				p*=2;
			}
			else
				break;
		}
		
		
		Random random = new Random();
		byte[] bytes = new byte[seedlen];
		BigInteger a;
		for(int j=0; j<r; j++)
		{
			//Выбрать случайное целое число a в отрезке [2, m − 2]
			while(true)
			{
				random.nextBytes(bytes);
				a = new BigInteger(1,bytes);
				if(m.compareTo(a)==1 )
				{
					break;
				}
			}
		
			
		BigInteger x = a.modPow(t, m);
	    
		// если x = 1 или x = m − 1, то перейти на следующую итерацию цикла А
	    if((x.compareTo(BigInteger.ONE)==0)|| (x.compareTo(m.subtract(BigInteger.ONE))==0))
	    {
	    	continue;
	    }
	    else
	    {
	    	//цикл B: повторить s − 1 раз
	          //x ← x^2 mod m
	          //если x = 1, то вернуть составное
	          //если x = m − 1, то перейти на следующую итерацию цикла А
	        //вернуть составное
	    	int i=0;
	    	for(i=0; i < s-1; i++)
	    	{
	    		x = x.modPow(new BigInteger("2"), m);
	    		if(x.compareTo(BigInteger.ONE)==0)
	    		{
	    			return false;
	    		}
	    		if(x.compareTo(m.subtract(BigInteger.ONE))==0)
	    		{
	    			break;
	    		}
	    	}
	    	
	    	if(i<s-1) continue;
	    	return false;
	    }
		}
	   return true;
	}
		catch(Exception ex){
			return false;
		}
	}
}
