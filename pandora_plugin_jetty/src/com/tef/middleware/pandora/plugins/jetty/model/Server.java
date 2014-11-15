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
	
	private ObjectName threadPoolServerName;
	private int idleThreads; 

	
	private int maxThreads;
	private int threads;
	private double cpuLoad;
	private long ratioUsed;
	private long ratioOldUsed;
	private long ratioPermUsed; 
	
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
	
	

}