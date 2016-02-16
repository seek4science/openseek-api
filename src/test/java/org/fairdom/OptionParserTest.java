package org.fairdom;

/**
 * Created by quyennguyen on 19/02/15.
 */
import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class OptionParserTest {
	
	@Test
	public void testAccount() throws Exception {
		String account = "{\"username\":\"test\", \"password\":\"test\"}";
		String[] args = new String[] { "-account", account};
		OptionParser p = new OptionParser(args);	
		JSONObject accountArgs = p.getAccount();
		assertEquals("test", accountArgs.get("username"));
		assertEquals("test", accountArgs.get("password"));
	}
	
	@Test
	public void testEndpoints() throws Exception {
		String endpoints = "{\"as\":\"http://as.example.com\", \"dss\":\"http://dss.example.com\"}";
		String[] args = new String[] { "-endpoints", endpoints};
		OptionParser p = new OptionParser(args);
		JSONObject endpointsArgs = p.getEndpoints();
		assertEquals("http://as.example.com", endpointsArgs.get("as"));
		assertEquals("http://dss.example.com", endpointsArgs.get("dss"));
	}
	
	@Test
	public void testQuery() throws Exception {
		String query = "{\"entityType\":\"Experiment\", \"property\":\"SEEK_STUDY_ID\", \"propertyValue\":\"Study_1\"}";
		String[] args = new String[] { "-query", query};
		OptionParser p = new OptionParser(args);	
		JSONObject queryArgs = p.getQuery();
		assertEquals("Experiment", queryArgs.get("entityType"));
		assertEquals("SEEK_STUDY_ID", queryArgs.get("property"));
		assertEquals("Study_1", queryArgs.get("propertyValue"));
	}
	
	@Test
	public void testDownload() throws Exception {
		String download = "{\"type\":\"file\", \"permID\":\"ID100\", \"source\":\"original/testfile\", \"dest\":\"/home/test/testfile\"}";
		String[] args = new String[] { "-download", download};
		OptionParser p = new OptionParser(args);
		JSONObject downloadArgs = p.getDownload();
		assertEquals("file", downloadArgs.get("type"));
		assertEquals("ID100", downloadArgs.get("permID"));
		assertEquals("original/testfile", downloadArgs.get("source"));
		assertEquals("/home/test/testfile", downloadArgs.get("dest"));
	}
	
	@Test
	public void testMultipleArgs() throws Exception {		
		String account = "{\"username\":\"test\", \"password\":\"test\"}";
		String endpoints = "{\"as\":\"http://as.example.com\", \"dss\":\"http://dss.example.com\"}";
		String query = "{\"entityType\":\"Experiment\", \"property\":\"SEEK_STUDY_ID\", \"propertyValue\":\"Study_1\"}";
		String download = "{\"type\":\"file\", \"permID\":\"ID100\", \"source\":\"original/testfile\", \"dest\":\"/home/test/testfile\"}";

		String[] args = new String[] { "-account", account, "-endpoints", endpoints, "-query", query, "-download", download};
		OptionParser p = new OptionParser(args);
		assertEquals("test", p.getAccount().get("username"));
		assertEquals("http://as.example.com", p.getEndpoints().get("as"));
		assertEquals("Experiment", p.getQuery().get("entityType"));
		assertEquals("file", p.getDownload().get("type"));
	}

	@Test(expected = InvalidOptionException.class)
	public void testInvalidArg() throws Exception {
		String[] args = new String[] { "-ss", "something" };
		new OptionParser(args);
	}

	@Test(expected = InvalidOptionException.class)
	public void testEmptyOptionValue() throws Exception {
		String[] args = new String[] { "-account", "", "-endpoints", "   ", "-query", null };
		new OptionParser(args);
	}
	
	@Test(expected = ParseException.class)
	public void testInvalidJsonString() throws Exception {
		String[] args = new String[] { "-account", "{'username':'test'}" };
		new OptionParser(args);
	}
	
	
}