/**
 * @author fmiceli
 * 
 */

package com.metro.atwater.rest;

import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metro.atwater.data.jmeter.JmeterDao;
import com.metro.atwater.data.jmeter.JmeterDaoImpl;

@Controller
public class IsAliveController {
	
	private static final Logger logger = LoggerFactory.getLogger(IsAliveController.class);
	
	// TODO - there exists a better way to set the bean .. see Post
	ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	JmeterDao jmeterDao = (JmeterDaoImpl) context.getBean("jmeterDao"); 
	
	@RequestMapping(value = "/isAlive", method = RequestMethod.GET)
	@ResponseBody
	public String isAlive() {
		// TODO: add a thread model and test for threads and db connectivity (connection pooling, etc .. ) 
		// TODO: need to test for all Datasource beans
		
		logger.info("isAlive()");
		
		String rc = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		String formattedDate = dateFormat.format(date);
				
		// TODO: carefully here, assumption is that idx 0 is always present
		rootNode.put("isAlive", jmeterDao.isAlive());
		rootNode.put("server-time", formattedDate);
		//rootNode.put("isAlive", true);

		try {
			rc = mapper.writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("processing count completed " + rc );

		return rc;
	}
	
}
