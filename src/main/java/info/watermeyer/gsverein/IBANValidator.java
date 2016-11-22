package info.watermeyer.gsverein;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.apache.log4j.Logger;

import fr.marcwrobel.jbanking.bic.Bic;

public class IBANValidator {

	private final static Logger LOGGER = Logger.getLogger(IBANValidator.class);

	public IBANValidator() {
	}

	public List<String> validate(final String pFilePath, final String pOut) throws Exception {
		if (pFilePath == null) {
			throw new IllegalArgumentException("Die Pfadangabe ist ungueltig " + pFilePath);
		}

		final File pFile = new File(pFilePath);
		if (pFile.exists() == false || pFile.isFile() == false) {
			throw new IllegalArgumentException("Die Datei konnte nicht gefunden werden: " + pFile.getAbsolutePath());
		}

		int counter = 1;
		IBANCheckDigit c = new IBANCheckDigit();
		final List<String> retVal = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(pFile)); OutputLog out = new OutputLog(pOut)) {
			String line = null;
			while ((line = br.readLine()) != null) {
				final String validationResult = handleLine(c, line);
				if (validationResult != null) {
					LOGGER.info(validationResult);
					retVal.add(validationResult);
					out.write(validationResult);
				}
				counter++;
			}
		} catch (Exception e) {
			throw new Exception("Fehler beim Lesen der Datei: " + pFilePath, e);
		} finally {
			LOGGER.info("Ueberpruefte Datensaetze. Anzahl: " + counter + " Fehlerhaft: " + retVal.size());
		}
		return retVal;
	}

	String handleLine(final IBANCheckDigit pValidator, final String pLine) {
		String retVal = null;
		String[] split = pLine.split(";");
		try {
			// System.out.println(line);
			if (split.length < 3) {
				retVal = createMessage(pLine, "Zeile ungueltig. Keine IBAN in Feld 3");
			} else 	if (split.length < 4) {
				retVal = createMessage(pLine, "Zeile ungueltig. Keine BIC in Feld 4");
			} else {
				final String iban = split[2];
				if (iban.length() > 22) {
					retVal = createMessage(pLine, "zu lang");
				} else if (iban.length() < 22) {
					retVal = createMessage(pLine, "zu kurz");
				} else if (!pValidator.isValid(iban)) {
					retVal = createMessage(pLine, "ungueltig");
				} else if(!Bic.isValid(split[3])) {
					retVal = createMessage(pLine, "BIC falsch");
				}
			}
		} catch (Exception e) {
			LOGGER.warn("Fehler beim Verarbeiten der Zeile: '" + pLine + "' Fehler: " + e.getMessage(), e);
			retVal = "Fehler beim Verarbeiten der Zeile: '" + pLine + "' Fehler: " + e.getMessage();
		}
		return retVal;
	}

	String createMessage(final String pLine, final String pErrorMessage) {
		final StringBuilder sb = new StringBuilder();

		sb.append(pLine);
		if (!pLine.endsWith(";")) {
			sb.append(";");
		}
		sb.append(pErrorMessage).append(";");
		return sb.toString();
	}
}

class OutputLog implements AutoCloseable {

	final static String LINESEP = System.getProperty("line.separator");
	final static Logger LOGGER = Logger.getLogger(OutputLog.class);

	final String mOutPath;
	BufferedWriter mOut;

	public OutputLog(final String pOutPath) {
		mOutPath = pOutPath;
	}

	public void write(final String pLine) throws IOException {
		if (mOutPath == null) {
			return;
		}

		if (mOut == null) {
			final File file = new File(mOutPath);
			mOut = new BufferedWriter(new FileWriter(file));
			LOGGER.info("Ausgabe-Datei: " + file.getAbsolutePath());
		}

		mOut.write(pLine);
		mOut.write(LINESEP);
	}

	@Override
	public void close() throws Exception {
		if (mOut == null) {
			return;
		}

		mOut.close();
	}

}
