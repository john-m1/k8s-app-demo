package com.metro.atwater.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metro.atwater.data.cpu.CpuDao;
import com.metro.atwater.data.cpu.CpuDaoImpl;
import com.metro.atwater.data.cpu.CpuResultSummary;
import com.metro.atwater.data.jmeter.JmeterDao;
import com.metro.atwater.data.jmeter.JmeterDaoImpl;
import com.metro.atwater.data.load.LoadDao;
import com.metro.atwater.data.load.LoadDaoImpl;
import com.metro.atwater.data.load.LoadResultSummary;
import com.metro.atwater.data.process.ProcessDao;
import com.metro.atwater.data.process.ProcessDaoImpl;
import com.metro.atwater.data.process.ProcessResultSummary;
import com.metro.atwater.stats.ResultStats;

@Controller
@RequestMapping(path="/stats")
public class StatsController {
	
	private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

	// TODO - there exists a better way to set the bean .. see Post
	ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	JmeterDao jmeterDao = (JmeterDaoImpl) context.getBean("jmeterDao"); 
	CpuDao cpuDao = (CpuDaoImpl) context.getBean("cpuDao");
	LoadDao loadDao = (LoadDaoImpl) context.getBean("loadDao");
	ProcessDao processDao = (ProcessDaoImpl) context.getBean("processDao");
	
	@RequestMapping(value="/jmeter", method = RequestMethod.GET)
	@ResponseBody
	public String process(@RequestParam(name="column",required=false) String column) throws Exception {
		
		logger.info("processing stats request");
		String rc = null;
		HashMap<String,ResultStats> msg = new HashMap<String,ResultStats>();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		if (column == null) {
			// TODO: 
			String[] fields = {"elapsed","latency","connectTime","idle"};
			for ( String field : fields ) {
				for ( ResultStats rs : jmeterDao.stats(field) ) {
					msg.put(field, rs);
				}
			}
		} else {
			logger.info("processing stats request for column=" + column);
			for ( ResultStats rs : jmeterDao.stats(column) ) {
				msg.put(column, rs);
			}
		}
		
		// old way
		// rc = mapper.writeValueAsString(msg)
		rootNode = mapper.valueToTree(msg);
		rc = mapper.writeValueAsString(rootNode);
		
		logger.info("processing body completed: " + rc);

		return rc;

	}

	@RequestMapping(value="/jmeter/size", method=RequestMethod.GET)
	@ResponseBody
	public String size() throws Exception {
		
		logger.info("processing count request");
		
		String rc = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		ObjectNode resultNode = mapper.createObjectNode();
		
		// TODO: carefully here, assumption is that idx 0 is always present
		resultNode.put("size", jmeterDao.counts().get(0));
		rootNode.set("result", resultNode);
		
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing count completed " + rc );
		
		return rc;
	}
	
	@RequestMapping(value="/cpu", method = RequestMethod.GET)
	@ResponseBody
	public String processCpuServerStats(@RequestParam(name="server",required=false) String server,
			@RequestParam(name="column",required=false) String column) throws Exception {
		
		logger.info("processing stats request");
		
		String rc = null;
		List<CpuResultSummary> crs = new ArrayList<CpuResultSummary>();
		HashMap<String,ResultStats> msg = new HashMap<String,ResultStats>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		if (column == null) {
			// TODO: exception handling
			String[] fields = {"usr","sys","wio","idle"};
			for ( String field : fields ) {
				for ( ResultStats rs : cpuDao.stats(server,field) ) {
					msg.put(field, rs);
				}
			}
		} else {
			// TODO: exception handling
			logger.info("processing stats request for column=" + column);
			for ( ResultStats rs : cpuDao.stats(server,column) ) {
				msg.put(column, rs);
			}
		}
		
		crs.add(new CpuResultSummary());
		crs.get(crs.size()-1).setServer(server);
		crs.get(crs.size()-1).setResults(msg);

		rc = mapper.writeValueAsString(crs);
		
		logger.info("processing cpu stats completed: " + rc);

		return rc;

	}

	@RequestMapping(value="/cpu/size", method=RequestMethod.GET)
	@ResponseBody
	public String size(@RequestParam(name="server",required=false) String server) throws Exception {
		
		logger.info("processing count request");
		
		String rc = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		ObjectNode resultNode = mapper.createObjectNode();
		
		// TODO: careful here!!!, assumption is that idx 0 is always present
		// FIXME: fix assumption above
		resultNode.put("size", cpuDao.counts(server).get(0));
		rootNode.put("server", server);
		rootNode.set("result", resultNode);
				
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing count completed " + rc );
		
		return rc;
	}
	
	@RequestMapping(value="/load", method = RequestMethod.GET)
	@ResponseBody
	public String processLoadServerStats(@RequestParam(name="server",required=false) String server,
			@RequestParam(name="column",required=false) String column) throws Exception {
		
		logger.info("processing stats request");
		
		String rc = null;
		List<LoadResultSummary> lrs = new ArrayList<LoadResultSummary>();
		HashMap<String,ResultStats> msg = new HashMap<String,ResultStats>();
		ObjectMapper mapper = new ObjectMapper();
		
		if (column == null) {
			// TODO: exception handling
			String[] fields = {"1min","5min","15min"};
			for ( String field : fields ) {
				// FIXME
				for ( ResultStats rs : loadDao.stats(server,field) ) {
					msg.put(field, rs);
				}
			}
		} else {
			// TODO: exception handling
			logger.info("processing stats request for column=" + column);
			for ( ResultStats rs : loadDao.stats(server,column) ) {
				msg.put(column, rs);
			}
		}
		
		lrs.add(new LoadResultSummary());
		lrs.get(lrs.size()-1).setServer(server);
		lrs.get(lrs.size()-1).setResults(msg);

		rc = mapper.writeValueAsString(lrs);
		
		logger.info("processing load stats completed: " + rc);

		return rc;

	}

	@RequestMapping(value="/load/size", method=RequestMethod.GET)
	@ResponseBody
	public String sizeOfLoad(@RequestParam(name="server",required=false) String server) throws Exception {
		
		logger.info("processing count request");
		
		String rc = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		ObjectNode resultNode = mapper.createObjectNode();

		// TODO: careful here!!!, assumption is that idx 0 is always present
		// FIXME: fix assumption above
		resultNode.put("size", loadDao.counts(server).get(0));
		rootNode.put("server", server);
		rootNode.set("result", resultNode);
				
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing count completed " + rc );
		
		return rc;
	}
	
	@RequestMapping(value="/process", method = RequestMethod.GET)
	@ResponseBody
	public String processProcessServerStats(@RequestParam(name="server",required=false) String server,
			@RequestParam(name="process", required=false) String process,
			@RequestParam(name="column",required=false) String column) throws Exception {
		
		logger.info("processing stats request");
		
		String rc = null;
		HashMap<String,ResultStats> msg = new HashMap<String,ResultStats>();
		List<ProcessResultSummary> prs = new ArrayList<ProcessResultSummary>();
		ObjectMapper mapper = new ObjectMapper();

		// TODO tidy this up, 2 params was straight fwd .. 3 params in the rest call seems messy
		if (column == null || server == null || process == null) {
			// TODO: exception handling
			String[] fields = {"cpu", "thread", "vszmb", "rssmb"};
			HashMap<String,List<String>> indicies = processDao.index();
			
			for (String s : indicies.keySet()) {
				prs.add(new ProcessResultSummary());
				prs.get(prs.size()-1).setServer(s);
				
				for (String p : indicies.get(s)) {
					prs.get(prs.size()-1).setProcess(p);

					for ( String field : fields ) {
						for ( ResultStats rs : processDao.stats(s,p, field)) {
							msg.put(field, rs);
						}
						prs.get(prs.size()-1).setResults(msg);
					}
				}
			}
		} else {
			// TODO: exception handling
			logger.info("processing stats request for column=" + column);
			for ( ResultStats rs : processDao.stats(server,process,column) ) {
				msg.put(column, rs);
			}
			prs.add(new ProcessResultSummary());
			prs.get(prs.size()-1).setServer(server);
			prs.get(prs.size()-1).setProcess(process);
			prs.get(prs.size()-1).setResults(msg);			
		}
		
		rc = mapper.writeValueAsString(prs);
		
		logger.info("processing proc stats completed: " + rc);

		return rc;

	}

	@RequestMapping(value="/process/size", method=RequestMethod.GET)
	@ResponseBody
	public String sizeOfProcess(@RequestParam(name="server",required=false) String server) throws Exception {
		
		logger.info("processing count request");
		
		String rc = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		ObjectNode resultNode = mapper.createObjectNode();
		
		// TODO: careful here!!!, assumption is that idx 0 is always present
		// FIXME: fix assumption above
		resultNode.put("size", processDao.counts(server).get(0));
		rootNode.put("server", server);
		rootNode.set("result", resultNode);
				
		rc = mapper.writeValueAsString(rootNode);
		logger.info("processing count completed " + rc );
		
		return rc;
	}
}



