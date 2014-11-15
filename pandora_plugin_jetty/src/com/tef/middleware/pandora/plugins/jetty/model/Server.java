package com.tef.middleware.pandora.plugins.jetty.model;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;

import com.tef.middleware.pandora.plugins.jetty.GestorConnection;

public class Server {

	private boolean status = false;
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	String jettyServerObject = "org.eclipse.jetty.server:type=server,id=0";
	String javaSOObject = "java.lang:type=OperatingSystem";
	String javaOldGenObject = "java.lang:type=MemoryPool,name=PS Old Gen";
	String javaOldPermObject = "java.lang:type=MemoryPool,name=PS Perm Gen";
	String javaHeapObject = "java.lang:type=Memory";
	
	private ObjectName threadPoolServerName;
	private int idleThreads; 

	
	private int maxThreads;
	private int threads;
	private double cpuLoad;
	private long ratioUsed;
	private long ratioOldUsed;
	private long ratioPermUsed;
	private long ratioHeapUsed; 
	
	public Server()
	{
		
	}
	
	/*
	 * Fill object Jetty Server with data get by JMX 
	 */
	public void populate() throws Exception
	{
		MBeanServerConnection con = GestorConnection.getInstancia().getConnection();
		ObjectName oServer = new ObjectName(jettyServerObject);
		status = (Boolean) con.getAttribute(oServer, "running");
		threadPoolServerName = (ObjectName) con.getAttribute(oServer, "threadPool");
		idleThreads = (Integer) con.getAttribute(threadPoolServerName, "idleThreads");
		maxThreads = (Integer) con.getAttribute(threadPoolServerName, "maxThreads");
		threads = (Integer) con.getAttribute(threadPoolServerName, "threads");
		ObjectName oSO = new ObjectName(javaSOObject);
		cpuLoad = (Double) con.getAttribute(oSO, "ProcessCpuLoad");
		ObjectName oOldGen = new ObjectName(javaOldGenObject);
		CompositeDataSupport oldData = (CompositeDataSupport) con.getAttribute(oOldGen, "CollectionUsage");
		long olMax = (Long) oldData.get("max");
		long olUsed = (Long) oldData.get("used");
		ratioOldUsed = ((olUsed * 100) / olMax);
		ObjectName oPermGen = new ObjectName(javaOldPermObject);
		CompositeDataSupport permData = (CompositeDataSupport) con.getAttribute(oPermGen, "CollectionUsage");
		long permMax = (Long) permData.get("max");
		long permUsed = (Long) permData.get("used");
		ratioPermUsed = ((permUsed * 100) / permMax);
		ObjectName oHeap = new ObjectName(javaHeapObject);
		CompositeDataSupport heapData = (CompositeDataSupport) con.getAttribute(oHeap, "HeapMemoryUsage");
		long heapMax = (Long) heapData.get("max");
		long heapUsed = (Long) heapData.get("used");
		ratioHeapUsed = ((heapUsed * 100) / heapMax);

		
		
	}
	
	public ObjectName getThreadPoolServerName() {
		return threadPoolServerName;
	}

	public void setThreadPoolServerName(ObjectName threadPoolServerName) {
		this.threadPoolServerName = threadPoolServerName;
	}

	public int getIdleThreads() {
		return idleThreads;
	}

	public void setIdleThreads(int idleThreads) {
		this.idleThreads = idleThreads;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public double getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(double cpuLoad) {
		this.cpuLoad = cpuLoad;
	}

	public long getRatioUsed() {
		return ratioUsed;
	}

	public void setRatioUsed(long ratioUsed) {
		this.ratioUsed = ratioUsed;
	}

	public long getRatioOldUsed() {
		return ratioOldUsed;
	}

	public void setRatioOldUsed(long ratioOldUsed) {
		this.ratioOldUsed = ratioOldUsed;
	}

	public long getRatioPermUsed() {
		return ratioPermUsed;
	}

	public void setRatioPermUsed(long ratioPermUsed) {
		this.ratioPermUsed = ratioPermUsed;
	}

	public long getRatioHeapUsed() {
		return ratioHeapUsed;
	}

	public void setRatioHeapUsed(long ratioHeapUsed) {
		this.ratioHeapUsed = ratioHeapUsed;
	}
	
	public void parse()
	{
		writeModuleBoolean("status", "Jetty Server Status",status);
		writeModule("cpuLoad","Jetty CPU Consumption CPU",String.valueOf(cpuLoad));
		writeModule("Threads","Jetty Threads used",String.valueOf(threads));
		writeModule("Max Threads","Jetty Max Threads",String.valueOf(maxThreads));
		writeModule("Idle Threads","Jetty Idle Threads",String.valueOf(idleThreads));
		writeModule("Ratio Heap Used","Jetty Heap Used",String.valueOf(ratioHeapUsed));
		writeModule("Ratios Old Generation Used","Jetty Heap used by old generation",String.valueOf(ratioOldUsed));
		writeModule("Ratios Perm Generation Heap Used","Jetty Heap used by perm generation",String.valueOf(ratioPermUsed));				
	}

	private void writeModule(String nombre, String description, String dato) {
		System.out.println("<module>");		
		System.out.println("<name><![CDATA[Jetty:" + nombre + " ]]></name>");		
		System.out.println("<description><![CDATA["+ description  +"]]></description>");
		System.out.println("<type>generic_data</type>");
		System.out.println("<min>0</min>");
		System.out.println("<disabled>0</disabled>");
		System.out.println("<data><![CDATA["+ dato +"]]></data>");				
		System.out.println("</module>");
	}

	
	private void writeModuleBoolean(String nombre, String description, boolean dato) {
		System.out.println("<module>");		
		System.out.println("<name><![CDATA[Jetty:" + nombre + " ]]></name>");		
		System.out.println("<description><![CDATA["+ description  +"]]></description>");
		System.out.println("<type>generic_data</type>");
		System.out.println("<min>0</min>");
		System.out.println("<disabled>0</disabled>");
		if (dato) 
			{ System.out.println("<data><![CDATA[1]]></data>");}
		else
			{ System.out.println("<data><![CDATA[1]]></data>"); }			
		System.out.println("</module>");
	}
	
	
	
	

}
