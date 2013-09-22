package com.artalgame.lab2;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matrix zeroLinkedMatrix = LinkedMatrix.getZeroMatrix(3);
		Matrix.drawMatrix(zeroLinkedMatrix, "Zero LinkedMatrix");
		
		Matrix zeroArrayMatrix = ArrayMatrix.getZeroMatrix(3);
		Matrix.drawMatrix(zeroArrayMatrix, "Zero ArrayMatrix");
		
		Matrix identityLinkedMatrix = LinkedMatrix.getIdentityMatrix(3);
		Matrix.drawMatrix(identityLinkedMatrix, "Identity LinkedMatrix");
		
		Matrix identityArrayMatrix = ArrayMatrix.getIdentityMatrix(3);
		Matrix.drawMatrix(identityArrayMatrix, "Identity ArrayMatrix");
		
		Matrix randomLinkedMatrix = LinkedMatrix.getRandomMatrix(4, 3);
		Matrix.drawMatrix(randomLinkedMatrix, "random LinkedMatrix");
		
		Matrix randomArrayMatrix = ArrayMatrix.getRandomMatrix(4, 3);
		Matrix.drawMatrix(randomArrayMatrix, "random ArrayMatrix");
		
		Matrix res1 = randomLinkedMatrix.multiplyBy(identityArrayMatrix);
		Matrix.drawMatrix(res1, "res1");
		
		Matrix res2 = randomArrayMatrix.multiplyBy(identityLinkedMatrix);
		Matrix.drawMatrix(res2, "res2");
		
		Matrix res3 = randomLinkedMatrix.multiplyBy(zeroArrayMatrix);
		Matrix.drawMatrix(res3, "res3");
		
		Matrix res4 = randomArrayMatrix.multiplyBy(zeroLinkedMatrix);
		Matrix.drawMatrix(res4, "res4");
		
		Matrix random1 = LinkedMatrix.getRandomMatrix(100, 100);
		Matrix random2 = ArrayMatrix.getRandomMatrix(100, 100);
		
		Matrix res = random1.multiplyBy(random2);

	}

}
