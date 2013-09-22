package com.artalgame.lab2;

import java.util.LinkedList;
import java.util.Random;

public class LinkedMatrix extends Matrix {
	public LinkedMatrix(int widht, int height){
		super(widht, height);
		matrix = new LinkedList<Float>();
		for(int i=0; i<width*height; i++)
		{
			matrix.add(i, 0f);
		}
	}
	@Override
	public Matrix multiplyBy(Matrix b) {
		Matrix res = new LinkedMatrix(this.getWidth(), b.getHeigh());
		for(int i = 0; i < this.getWidth(); i++)
			for(int j = 0; j < b.getHeigh(); j++)
			{
				float sum = 0;
				for (int r = 0; r < this.height;r++ )
					sum += this.get(i, r)*b.get(r, j);
				
				res.set(i, j, sum);
			}
		return res;
	}
	public static Matrix getRandomMatrix(int x, int y)
	{
		Matrix res = new LinkedMatrix(x, y);
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
		Matrix res = new LinkedMatrix(size, size);
		for(int i=0; i< size; i++)
			{
				res.set(i, i, 1);
			}
		return res;
	}
	public static Matrix getZeroMatrix(int size)
	{
		return new LinkedMatrix(size, size);
	}
}