/**
 * @author fmiceli
 * 
 */


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.atwater.data.jmeter.Jmeter;
import com.metro.atwater.data.jmeter.JmeterDaoImpl;
import com.metro.atwater.rest.ResultController;


public class Main {

	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		JmeterDaoImpl jmeterResultDao = (JmeterDaoImpl) context.getBean("jmeterDao"); 
		
		System.out.println("Adding data...");
		String transaction = "{\"server\": \"test-server1\",\"epoch\": 1531694299101,\"name\": \"QryLp HTTP Request\",\"elapsed\": 823, \"responseCode\": 200, \"responseMsg\": \"OK\", \"success\": true,\"latency\": 1434,\"idle\": 744,\"connectTime\": 822 }";
		
		Jmeter result = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(transaction, Jmeter.class);
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jmeterResultDao.addTuple(result);
		
		System.out.println("Retrieving data..");
		for(Jmeter qryResult : jmeterResultDao.selectAll()){
			try {
				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(qryResult));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
}