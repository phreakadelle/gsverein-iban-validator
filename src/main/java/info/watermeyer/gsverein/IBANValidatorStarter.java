package info.watermeyer.gsverein;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class IBANValidatorStarter {

	private final static Logger LOGGER = Logger.getLogger(IBANValidatorStarter.class);

	public static void main(String[] args) {
		int retVal = -1;
		LOGGER.info("Start GSVerein IBAN Validator");
		LOGGER.info("Version: " + IBANValidatorStarter.class.getPackage().getImplementationVersion());
		LOGGER.info("watermeyer IT");
		LOGGER.info("Stephan Watermeyer <stephan@phreakadelle.de>");
		LOGGER.info("---");

		final Options options = initOptions();

		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("help")) {
				printHelp(options);
			} else {
				final String input = cmd.getOptionValue("input");
				final String output = cmd.getOptionValue("output");
				
				final IBANValidator v = new IBANValidator();
				v.validate(input, output);
			}
		} catch (ParseException e) {
			LOGGER.warn(e.getMessage());
			printHelp(options);
			retVal = -1;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			retVal = -2;
		} finally {
			LOGGER.log((retVal == 0 ? Level.INFO : Level.ERROR), "beendet: " + retVal);
		}
		System.exit(retVal);
	}

	static Options initOptions() {
		Options retVal = new Options();

		// Mandatory
		retVal.addOption(Option.builder().longOpt("input").hasArg(true).required().desc("Pfad zur CSV Datei").build());

		// Optional
		retVal.addOption(Option.builder().longOpt("output").hasArg(true).optionalArg(true)
				.desc("Ausgabe der Ergebnisse").build());

		return retVal;
	}

	private static int printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar <jarname>", options);
		return 0;

	}
}
