import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

public class IBANValidator {

	public static void main(String[] args) {
		IBANCheckDigit c = new IBANCheckDigit();

		try (BufferedReader br = new BufferedReader(new FileReader("C:/tmp/ssv.csv"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(";");
				if (split.length == 7 || split.length == 8) {
					// System.out.println(line);
					String iban = split[2];
					if (iban.length() > 22) {
						printInvalidIBAN("zu lang", split, iban);
					} else if (iban.length() < 22) {
						printInvalidIBAN("zu kurz", split, iban);
					} else if (!c.isValid(iban)) {
						printInvalidIBAN("ungueltig", split, iban);
					}
				} else {
					// System.out.println("Ueberspringe Zeile: " + split.length
					// + " --" + line);
					// System.out.println("---");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printInvalidIBAN(String text, String[] split, String iban) {
		System.out.println("IBAN ist " + text + ": " + iban);
		// System.out.println(iban.length());
		System.out.println(split[0] + ", " + split[1]);
		System.out.println("---");
	}

}
