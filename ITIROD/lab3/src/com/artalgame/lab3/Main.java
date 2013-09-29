package com.artalgame.lab3;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// part A
		Map<Character, Integer> charFrequency = new HashMap<Character, Integer>();
        
		int length = 0;
        try {
            InputStream fileStream = new FileInputStream("sample.txt");
            length = fileStream.available();
            while (fileStream.available() > 0) {
                char c = (char) fileStream.read();
                if (!charFrequency.containsKey(c))
                    charFrequency.put(c, 1);
                else
                    charFrequency.put(c, charFrequency.get(c) + 1);
            }

            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Character> keys = new ArrayList<Character>();
        keys.addAll(charFrequency.keySet());
        Collections.sort(keys);
        for (char key : keys) {
            System.out.printf("%s: %.5f%%\n", key, charFrequency.get(key) * 1f / length);
        }
        //End of part A
        
        //Part B
        Matrix matrix = ArrayMatrix.getRandomMatrix(3, 4);
        matrix.writeToFile("matrix.txt");
        Matrix.drawMatrix(matrix, "this matrix was written to file");
        Matrix fileMatrix = new ArrayMatrix(0,0).readFromFile("matrix.txt");
        Matrix.drawMatrix(fileMatrix, "this matrix was read from file");
        
        Matrix matrix1 = ArrayMatrix.getRandomMatrix(3, 4);
        matrix1.serializeToFile("matrix_serialize.txt");
        Matrix.drawMatrix(matrix1, "this matrix was serialized");
        Matrix deserMatrix = new ArrayMatrix(0,0).deserializeFromFile("matrix_serialize.txt");
        Matrix.drawMatrix(deserMatrix, "this matrix was deserialized");
	}

}
