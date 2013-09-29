package com.artalgame.lab3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
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
	
	public void writeToFile(String fileName){
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(String.valueOf(getWidth())+"\n");
			writer.write(String.valueOf(getHeigh())+"\n");
			for(int i = 0; i < getWidth(); i++)
			{
				for(int j = 0; j < getHeigh(); j++)
				{
					writer.write(String.valueOf(get(i, j))+" ");
				}
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Matrix deserializeFromFile(String fileName){
		try{
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			int w = ois.readInt();
			int h = ois.readInt();
			Matrix matrix = this.getClass().getConstructor(int.class, int.class).newInstance(w,h);
			for(int i=0; i< w; i++)
			{
				for(int j=0; j< h; j++)
				{
					matrix.set(i, j, ois.readFloat());	
				}
			}
			ois.close();
			return matrix;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void serializeToFile(String fileName){
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeInt(getWidth());
			oos.writeInt(getHeigh());
			for(int i=0; i< getWidth(); i++)
			{
				for(int j=0; j< getHeigh(); j++)
					oos.writeFloat(get(i,j));
			}
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	        
	}
	
	public Matrix readFromFile(String fileName){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			int w = Integer.parseInt(reader.readLine());
			int h = Integer.parseInt(reader.readLine());
			Matrix matrix = this.getClass().getConstructor(int.class, int.class).newInstance(w,h);
			for(int i=0; i< w; i++)
			{
				String[] matrixString = reader.readLine().split(" ");
				for(int j=0; j< h; j++)
				{
					matrix.set(i, j, Float.parseFloat(matrixString[j]));	
				}
			}
			reader.close();
			return matrix;
		}
		catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return null;
	}
}


