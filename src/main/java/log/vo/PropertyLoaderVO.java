package log.vo;

public class PropertyLoaderVO {

	private String timeStampFormat;
	
	private String logLevel;
	
	private String sinkType;
	
	private String fileLocation;
	
	private String dbHost;
	
	private String dbPort;
	
	private String mongouri;

	public String getMongouri() {
		return mongouri;
	}

	public void setMongouri(String mongouri) {
		this.mongouri = mongouri;
	}

	public String getTimeStampFormat() {
		return timeStampFormat;
	}

	public void setTimeStampFormat(String timeStampFormat) {
		this.timeStampFormat = timeStampFormat;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getSinkType() {
		return sinkType;
	}

	public void setSinkType(String sinkType) {
		this.sinkType = sinkType;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	
}
