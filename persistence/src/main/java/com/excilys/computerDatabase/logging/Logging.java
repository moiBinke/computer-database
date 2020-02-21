package com.excilys.computerDatabase.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.PropertyConfigurator;

public class Logging {
 private static final Logger LOGGER =LoggerFactory.getLogger(Logging.class);
 
 public static void afficherMessage(String message) {
	 PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4j.properties"));

     LOGGER.info(message);
 }
 public static void afficherMessageError(String message) {
	 PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4j.properties"));

     LOGGER.error(message);
 }
 public static void afficherMessageDebug(String message) {
	 PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4j.properties"));

     LOGGER.debug(message);
 }
}
