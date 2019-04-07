package log.services.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
//import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import log.services.LoggerService;
import log.vo.PropertyLoaderVO;

@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, })
public class LoggerServiceImpl implements LoggerService{

	private void writeLogsToDB(String content, String namespace, String level, PropertyLoaderVO propertyVO) {
		System.out.println("timestamp : " + propertyVO.getTimeStampFormat());
		String timeStamp = createTimestampFormat(propertyVO.getTimeStampFormat());
		String level1 = "";

		if("ERROR".equals(level))
			level1 = "E";
		else if("FATAL".equals(level))
			level1 = "F";

		//Format of Message to be saved : [timeStamp]:[level]:[namespace]:message
		String commitMessage = "["+timeStamp+"]"+":["+level1+"]"+":["+namespace+"]:"+content;
		System.out.println("MongoURI : " + propertyVO.getMongouri());

		MongoClientURI uri = new MongoClientURI(propertyVO.getMongouri());

		System.out.println("Connection created successfully");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("logs");
		MongoCollection<Document> collection = database.getCollection("logLevel");

		System.out.println("Document count: " +collection.countDocuments());
		Document document = new Document("title", "MongoDB") 
				.append("level", level) 
				.append("logs", commitMessage); 
		collection.insertOne(document); 

		mongoClient.close();

	}

	private void writeLogsToFile(String content, String namespace, String level, PropertyLoaderVO propertyVO) {
		String timeStamp = createTimestampFormat(propertyVO.getTimeStampFormat());
		String level1 = "";

		if("INFO".equals(level))
			level1 = "I";
		else if("DEBUG".equals(level))
			level1 = "D";
		else if("WARN".equals(level))
			level1 = "W";

		//Format of Message to be saved : [timeStamp]:[level]:message
		String commitMessage = "["+timeStamp+"]"+":["+level1+"]"+":["+namespace+"]:"+content;
		try{

			File file = new File(propertyVO.getFileLocation());
			if(!file.exists()) {

				file.createNewFile();	
				FileWriter fw=new FileWriter(file, false);
				fw.write(commitMessage);    
				fw.close();

			}else {

				BufferedWriter writer = new BufferedWriter(new FileWriter(propertyVO.getFileLocation(), true)); 
				writer.newLine();   
				writer.write(commitMessage);
				writer.close();

			}

		}catch(Exception e){
			System.out.println(e);
		}       
	}

	private String createTimestampFormat(String timeFormat) {

		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);  
		return dateFormat.format(date);

	}

	@Override
	public void info(String content, String level, String namespace) {
		PropertyLoaderVO propertyVO = loadProperties(level);
		writeLogsToFile(content, namespace, level, propertyVO);

	}

	@Override
	public void debug(String content, String level, String namespace) {
		PropertyLoaderVO propertyVO = loadProperties(level);
		writeLogsToFile(content, namespace, level, propertyVO);

	}

	@Override
	public void warn(String content, String level, String namespace) {
		PropertyLoaderVO propertyVO = loadProperties(level);
		writeLogsToFile(content, namespace, level, propertyVO);

	}

	@Override
	public void fatal(String content, String level, String namespace) {
		PropertyLoaderVO propertyVO = loadProperties(level);
		writeLogsToFile(content, namespace, level, propertyVO);

	}

	@Override
	public void error(String content, String level, String namespace) {
		PropertyLoaderVO propertyVO = loadProperties(level);
		writeLogsToDB(content, namespace, level, propertyVO);

	}

	private PropertyLoaderVO loadProperties(String level) {
		PropertyLoaderVO propertyVO = new PropertyLoaderVO();
		if("INFO".equals(level) || "DEBUG".equals(level) || "WARN".equals(level)) {
			loadFileSinkProperties(propertyVO);
		}else
			loadDBSinkProperties(propertyVO);

		return propertyVO;
	}

	private void loadDBSinkProperties(PropertyLoaderVO propertyVO) {
		/*Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("dbsink.properties");
			prop.load(input);

			propertyVO.setTimeStampFormat(prop.getProperty("ts_format"));
			propertyVO.setLogLevel(prop.getProperty("log_level"));
			propertyVO.setSinkType(prop.getProperty("sink_type"));
			propertyVO.setMongouri(prop.getProperty("mongouri"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}*/

		propertyVO.setTimeStampFormat("dd:mm:yyyy hh:mm:ss");
		propertyVO.setLogLevel("ERROR");
		propertyVO.setSinkType("DB");
		propertyVO.setMongouri("mongodb+srv://loguser:newloguser@loggingservice-ecyz0.mongodb.net/logs?retryWrites=true");


	}

	private void loadFileSinkProperties(PropertyLoaderVO propertyVO) {
		/*Properties prop = new Properties();
		InputStream input = null;


		try {
			input = new FileInputStream("filesink.properties");
			prop.load(input);

			propertyVO.setTimeStampFormat(prop.getProperty("ts_format"));
			propertyVO.setLogLevel(prop.getProperty("log_level"));
			propertyVO.setSinkType(prop.getProperty("sink_type"));
			propertyVO.setFileLocation(prop.getProperty("file_location"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}*/

		propertyVO.setTimeStampFormat("dd-MM-yyyy-hh-mm-ss");
		propertyVO.setLogLevel("INFO");
		propertyVO.setSinkType("FILE");
		propertyVO.setFileLocation("info.log");

	}

	@Override
	public void logMsg(String content, String level, String namespace) {
		if("INFO".equals(level))
			this.info(content, level, namespace);
		else if("DEBUG".equals(level))
			this.debug(content, level, namespace);
		else if("WARN".equals(level))
			this.warn(content, level, namespace);
		else if("ERROR".equals(level))
			this.error(content, level, namespace);
		else if("FATAL".equals(level))
			this.fatal(content, level, namespace);
	}

}
