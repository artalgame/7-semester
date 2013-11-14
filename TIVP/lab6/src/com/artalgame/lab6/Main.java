package com.artalgame.lab6;

import java.math.BigInteger;

public class Main {

	/**
	 * @param args
	 */
	int r=50;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean isPrime(BigInteger m){
		if(!m.testBit(m.bitCount()-1)) return false;
		
		//	Представить m − 1 в виде 2s·t, где t нечётно, можно сделать последовательным делением m - 1 на 2.
		//цикл А: повторить r раз:
		BigInteger m1 = m.subtract(BigInteger.ONE);
		int s=1;
		int p=2;
		BigInteger t;
		while (true)
		{
			t = m1.shiftRight(s);
			if(!t.testBit(t.bitCount()-1))
			{
				s++;
				p*=2;
			}
			else
				break;
		}

	   //Выбрать случайное целое число a в отрезке [2, m − 2]
		
	   x ← at mod m, вычисляется с помощью алгоритма возведения в степень по модулю (англ.)
	   если x = 1 или x = m − 1, то перейти на следующую итерацию цикла А
	   цикл B: повторить s − 1 раз
	      x ← x2 mod m
	      если x = 1, то вернуть составное
	      если x = m − 1, то перейти на следующую итерацию цикла А
	   вернуть составное
	вернуть вероятно простое
	}

}
