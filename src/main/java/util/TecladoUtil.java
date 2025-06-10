package util;

import java.util.Scanner;

public class TecladoUtil {
	private static final Scanner scanner = new Scanner(System.in);

	public static String leitura() {
		return scanner.nextLine();
	}

	public static long leituraLong(String mensagem) {
		while (true) {
			try {
				System.out.print(mensagem);
				return Long.parseLong(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Atenção: Você deve digitar um valor numérico!");
			}
		}
	}

	public static boolean confirmar(String mensagem) {
		System.out.print(mensagem + " (S/N)? ");
		String resposta = scanner.nextLine().trim().toUpperCase();
		return resposta.equals("S");
	}
}