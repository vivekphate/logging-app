package log.test;
import org.junit.Test;

import log.services.impl.LoggerServiceImpl;

public class LoggerServiceTest {
	
	@Test
	public void testInfoLoggerService() {
		LoggerServiceImpl serviceImpl = new LoggerServiceImpl();
		serviceImpl.info("Test Application", "INFO", "VIP");
	}
	
	@Test
	public void testErrorLoggerService() {
		LoggerServiceImpl serviceImpl = new LoggerServiceImpl();
		serviceImpl.error("Test Application is running fine", "ERROR", "VIP");
	}
	
	@Test
	public void testLoggerService() {
		LoggerServiceImpl serviceImpl = new LoggerServiceImpl();
		serviceImpl.logMsg("Test Application is running fine", "ERROR", "VIP");
	}

}
