package com.artalgame.lab3;

import java.util.ArrayList;
import java.util.Random;

public class ArrayMatrix extends Matrix {
	
	public ArrayMatrix(int widht, int height){
		super(widht, height);
		matrix = new ArrayList<Float>();
		for(int i=0; i<width*height; i++)
		{
			matrix.add(i, 0f);
		}
	}
	@Override
	public Matrix multiplyBy(Matrix b) {
		Matrix res = new ArrayMatrix(this.getWidth(), b.getHeigh());
		for(int i=0; i<this.getWidth(); i++)
			for(int j=0; j<b.getHeigh(); j++)
			{
				float sum = 0;
				for (int r = 0; r<this.height;r++ )
					sum += this.get(i, r)*b.get(r, j);
				
				res.set(i, j, sum);
			}
		return res;
	}
	public static Matrix getRandomMatrix(int x, int y)
	{
		Matrix res = new ArrayMatrix(x, y);
		Random r = new Random();
		for(int i=0; i< x; i++)
			for(int j=0; j<y; j++)
			{
				res.set(i, j, Math.round(r.nextFloat()*10));
			}
		return res;
	}
	public static Matrix getIdentityMatrix(int size)
	{
		Matrix res = new ArrayMatrix(size, size);
		for(int i=0; i< size; i++)
			{
				res.set(i, i, 1);
			}
		return res;
	}
	public static Matrix getZeroMatrix(int size)
	{
		return new ArrayMatrix(size, size);
	}
	
}
