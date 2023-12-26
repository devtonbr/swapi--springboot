package br.com.swapi.api.logger.aspects;

import org.apache.logging.log4j.Logger;

public enum LevelLogger {
	
	INFO {

		@Override
		public void log(Logger logger, String message) {
			logger.info(message);			
		}
	},DEBUG {

		@Override
		public void log(Logger logger, String message) {
			logger.debug(message);	
		}
	},ERROR {

		@Override
		public void log(Logger logger, String message) {
			logger.error(message);	
			
		}
	},FATAL {
		@Override
		public void log(Logger logger, String message) {
			logger.fatal(message);
			
		}
	};
	public abstract void log(Logger logger, String message);
	

}
