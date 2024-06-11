package com.metro.atwater.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

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
import com.metro.atwater.data.serverinfo.ServerInfo;
import com.metro.atwater.data.serverinfo.ServerInfoDao;
import com.metro.atwater.data.serverinfo.ServerInfoDaoImpl;


@Controller
@RequestMapping(path="/data")
public class ServerInfoController {
	private static final Logger logger = LoggerFactory.getLogger(ServerInfoController.class);
	
	// TODO - there exists a better way to set the bean .. see below
	ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	ServerInfoDao serverInfoDao = (ServerInfoDaoImpl) context.getBean("ServerInfoDao"); 
	
	// POST a json body representing a transaction 
	@RequestMapping(value = "/server", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String process(@RequestBody final String body) throws Exception {
		
		logger.info("processing body received: " + body);
		String rc = null;
		
		ServerInfo result = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		try {
			result = mapper.readValue(body, ServerInfo.class);
			
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

		serverInfoDao.addTuple(result);
		
		rootNode.putPOJO("request", (ObjectNode) mapper.convertValue(result, JsonNode.class));
		rootNode.put("status", true);
		
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing body completed: " + rc);

		return rc;
	}

	@RequestMapping(value = "/server", method = RequestMethod.DELETE)
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
		serverInfoDao.deleteAll();
		
		rootNode.put("completd", formattedDate);
		
		rc = mapper.writeValueAsString(rootNode);
		
		logger.debug("processing delete completed: " + rc);

		return rc;

	}
	
	@RequestMapping(value = "/server", method = RequestMethod.GET)
	@ResponseBody
	public String queryAll() throws Exception {
		
		String rc = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();

		List<ServerInfo> servers = serverInfoDao.selectAll();
		
		for(ServerInfo server : servers ) {
			rootNode.putPOJO("request", (ObjectNode) mapper.convertValue(server, JsonNode.class));
		}
		rootNode.put("status", true);
		
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing body completed: " + rc);

		return rc;
	}
}
