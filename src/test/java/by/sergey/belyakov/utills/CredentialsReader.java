package by.sergey.belyakov.utills;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CredentialsReader {

	public static String[] getCredentials(String filePath) {
		String[] credentials = new String[2];

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			//TODO пропуск первой строчки файла
			String line = br.readLine();
			line = br.readLine();

			if (line != null) {
				String[] values = line.split(",");
				if (values.length == 2) {
					credentials[0] = values[0].trim();
					credentials[1] = values[1].trim();
				} else {
					throw new IllegalArgumentException("Неверный формат строки в CSV: " + line);
				}
			} else {
				throw new IllegalArgumentException("Файл пуст или не содержит данных: " + filePath);
			}
		} catch (IOException e) {
			throw new RuntimeException("Ошибка при чтении файла: " + filePath);
		}
		return credentials;
	}

}
