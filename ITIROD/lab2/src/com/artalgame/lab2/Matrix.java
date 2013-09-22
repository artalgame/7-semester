package com.artalgame.lab2;

import java.io.Console;
import java.util.AbstractList;

public abstract class Matrix {
	public int width;
	public int height;
	protected AbstractList<Float> matrix;
	protected Matrix(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeigh(){
		return height;
	}
	
	public float get(int x, int y){
		return matrix.get(getIndex(x, y));
	}
	
	public void set(int x, int y, float v){
		matrix.set(getIndex(x, y), v);
	}
	
	protected int getIndex(int x, int y) {
		return (height) * x + y;
	}
	
	public Matrix multiplyBy(Matrix a){
		return null;
	}
	
	public static void drawMatrix(Matrix matrix, String message){
		System.out.println(message);
		for(int i=0; i<matrix.getWidth(); i++){
			for(int j=0; j<matrix.getHeigh(); j++)
				System.out.print(matrix.get(i,j)+" ");
			System.out.println("");
		}
		System.out.println("");
	}
	
}

