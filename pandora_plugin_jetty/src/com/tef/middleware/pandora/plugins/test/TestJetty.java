package com.tef.middleware.pandora.plugins.test;

import javax.management.MBeanServerConnection;

import com.tef.middleware.pandora.plugins.jetty.GestorConnection;
import com.tef.middleware.pandora.plugins.jetty.model.Server;

import junit.framework.TestCase;

public class TestJetty extends TestCase {

	public TestJetty(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	/*
	 * Test to test connection to jetty
	 */
	public void test1() throws Exception
	{
		MBeanServerConnection con = GestorConnection.getInstancia().getConnection();
		assertNotNull(con);
		String[] domains = con.getDomains();
		assertEquals(19 , domains.length);
		
	}
	/*
	 * Test to fill JettyServer with data
	 */
	public void test2() throws Exception
	{
		Server s = new Server();
		s.populate();
		assertTrue(s.getStatus());
		assertEquals(6, s.getIdleThreads());
		assertEquals(200, s.getMaxThreads());
		assertEquals(10, s.getThreads());
		assertTrue(s.getCpuLoad() > 0);
		assertTrue(s.getRatioOldUsed() == 0);
		assertTrue(s.getRatioPermUsed() > 0);
		assertTrue(s.getRatioHeapUsed() >= 0);
	}
	
	public void test3() throws Exception
	{
		Server s = new Server();
		s.populate();
		s.parse();
	}

}
