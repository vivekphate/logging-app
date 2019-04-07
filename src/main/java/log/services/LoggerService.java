package log.services;

import org.springframework.stereotype.Service;

@Service
public interface LoggerService {
	
	public void logMsg(String content, String level, String namespace);
	
	public void info(String content, String level, String namespace);
	
	public void debug(String content, String level, String namespace);
	
	public void warn(String content, String level, String namespace);
	
	public void fatal(String content, String level, String namespace);
	
	public void error(String content, String level, String namespace);

}
