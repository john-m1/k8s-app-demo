package com.metro.atwater.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metro.atwater.data.load.Load;
import com.metro.atwater.data.load.LoadDao;
import com.metro.atwater.data.load.LoadDaoImpl;


@Controller
@RequestMapping(path="/data")
public class LoadController {
	private static final Logger logger = LoggerFactory.getLogger(LoadController.class);
	
	// TODO - there exists a better way to set the bean .. see below
	ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	LoadDao loadDao = (LoadDaoImpl) context.getBean("loadDao"); 
	
	// POST a json body representing a transaction 
	@RequestMapping(value = "/load", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String process(@RequestBody final String body) throws Exception {
		
		logger.info("processing body received: " + body);
		String rc = null;
		
		Load result = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		try {
			result = mapper.readValue(body, Load.class);
			
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

		loadDao.addTuple(result);
		
		rootNode.putPOJO("request", (ObjectNode) mapper.convertValue(result, JsonNode.class));
		rootNode.put("status", true);
		
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing body completed: " + rc);

		return rc;
	}

	@RequestMapping(value = "/load", method = RequestMethod.DELETE)
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
		loadDao.deleteAll();
		
		rootNode.put("completd", formattedDate);
		
		rc = mapper.writeValueAsString(rootNode);
		
		logger.debug("processing delete completed: " + rc);

		return rc;

	}

}
