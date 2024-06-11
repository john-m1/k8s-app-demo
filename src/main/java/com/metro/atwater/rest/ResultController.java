package com.metro.atwater.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metro.atwater.data.jmeter.Jmeter;
import com.metro.atwater.data.jmeter.JmeterDao;
import com.metro.atwater.data.jmeter.JmeterDaoImpl;

@Controller
@RequestMapping(path="/data")
public class ResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);
	
	// TODO - there exists a better way to set the bean .. see below
	ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	JmeterDao jmeterDao = (JmeterDaoImpl) context.getBean("jmeterDao"); 

//  TODO: use setter injection but for some reason it is not working, and a NPE is thrown
//		  worry about it later
//
//	JmeterDao jmeterDao;
//	
//	@Required
//	public void setJmeterDao(JmeterDao jmeterDao) {
//		logger.info("set the dao");
//		this.jmeterDao = jmeterDao;
//	}
	
	// POST a json body representing a transaction 
	@RequestMapping(value = "/result", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String process(@RequestBody final String body) throws Exception {
		
		logger.info("processing body received: " + body);
		String rc = null;
		
		Jmeter result = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		try {
			result = mapper.readValue(body, Jmeter.class);
			
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

		jmeterDao.addTuple(result);
		
		rootNode.putPOJO("request", (ObjectNode) mapper.convertValue(result, JsonNode.class));
		rootNode.put("status", true);
		
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing body completed: " + rc);

		return rc;
	}

	@RequestMapping(value = "/result", method = RequestMethod.DELETE)
	@ResponseBody
	public String processDelete() throws Exception {
		
		logger.debug("processing delete request");
		String rc = null;
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		String formattedDate = dateFormat.format(date);
		
		// TODO: verify delete 
		jmeterDao.deleteAll();
		
		rootNode.put("completd", formattedDate);
		
		rc = mapper.writeValueAsString(rootNode);
		
		logger.debug("processing delete completed: " + rc);

		return rc;

	}
}



