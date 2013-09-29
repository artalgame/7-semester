package com.artalgame.lab2;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matrix zeroLinkedMatrix = LinkedMatrix.getZeroMatrix(4);
		
		Matrix zeroArrayMatrix = ArrayMatrix.getZeroMatrix(4);
		
		Matrix identityLinkedMatrix = LinkedMatrix.getIdentityMatrix(4);
		
		Matrix identityArrayMatrix = ArrayMatrix.getIdentityMatrix(4);
		
		Matrix randomLinkedMatrix = LinkedMatrix.getRandomMatrix(4, 4);
		Matrix.drawMatrix(randomLinkedMatrix, "random LinkedMatrix = m1");
		
		Matrix randomArrayMatrix = ArrayMatrix.getRandomMatrix(4, 4);
		Matrix.drawMatrix(randomArrayMatrix, "random ArrayMatrix = m2");
		
		Matrix res1 = randomLinkedMatrix.multiplyBy(identityArrayMatrix);
		Matrix.drawMatrix(res1, "multiply mat1 to identityMatrix");	
		
		Matrix res4 = randomArrayMatrix.multiplyBy(zeroLinkedMatrix);
		Matrix.drawMatrix(res4, "multiply m2 to zeroMatrix");
		
		Matrix res5 = randomArrayMatrix.multiplyBy(randomLinkedMatrix);
		Matrix.drawMatrix(res5, "multily mat2 to mat1");
		
		Matrix random1 = LinkedMatrix.getRandomMatrix(100, 100);
		Matrix random2 = LinkedMatrix.getRandomMatrix(100, 100);
		
		long startTime = System.nanoTime();
		random1.multiplyBy(random2);
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println("LinkedMatrixes multiply duration have been " + duration/1000000000 +"."+ duration%1000000000 + "s");
		
		random1 = ArrayMatrix.getRandomMatrix(100, 100);
		random2 = ArrayMatrix.getRandomMatrix(100, 100);
		
		startTime = System.nanoTime();
		random1.multiplyBy(random2);
		endTime = System.nanoTime();
		duration = endTime - startTime;
		System.out.println("ArrayMatrixes multiply duration have been " + duration/1000000000 +"."+ duration%1000000000 + "s");
	}

}
