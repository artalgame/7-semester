package org.korolchuk.lab5;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			System.out.println("Number of parts the secret:");
			int n = Integer.parseInt(scanner.nextLine());
			System.out
					.println("The number of partners for the restoration the secret:");
			int t = Integer.parseInt(scanner.nextLine());
			byte[] bytes = new byte[32];
			new Random().nextBytes(bytes);
			BigInteger secret = new BigInteger(bytes).abs();
			System.out.println(String.format("Secret:\n%s", secret));
			AsmutBloomsScheme abs = AsmutBloomsScheme.Create(secret, n, t);
			Share[] shares = abs.getShares();
			PrintInformation(shares);
			while (true) {
				System.out.println("Number of partners:");
				t = Integer.parseInt(scanner.nextLine());
				if (t >= shares.length || t < 1) {
					System.out.println("Invalid number of partners");
					continue;
				}
				Share[] s = new Share[t];
				for (int i = 0; i < t; i++) {
					s[i] = shares[i];
				}
				BigInteger posSecret = abs.restoreSecret(s);
				String str = String.format("Secret %s (%s)", abs
						.checkSecret(posSecret) ? "is restored"
						: "isn't restored", posSecret);
				System.out.println(str);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public static void PrintInformation(Share[] shares) throws Exception {
		System.out.println(String.format("P:\n%s", shares[0].getP()));
		for (int i = 0; i < shares.length; i++)
			System.out.println(String.format(
					"Divisor # %d:\n%s\nResidue # %d\n%s", i + 1,
					shares[i].getDivider(), i + 1, shares[i].getRemainder()));
	}
}
