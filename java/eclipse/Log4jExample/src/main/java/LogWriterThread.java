import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
	

public class LogWriterThread extends Thread {

	private static final Logger logger = LogManager.getLogger(LogWriterThread.class);

	public void run() {
		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.fatal("I couldn't sleep!", e);
			}
			logger.trace("We've just greeted the user!");
			logger.debug("We've just greeted the user!");
			logger.info("We've just greeted the user!");
			logger.warn("We've just greeted the user!");
			logger.error("We've just greeted the user!");
			logger.fatal("We've just greeted the user!");
		}
	}
	
}
