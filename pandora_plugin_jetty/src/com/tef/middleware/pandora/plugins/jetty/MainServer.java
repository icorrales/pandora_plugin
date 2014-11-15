package com.tef.middleware.pandora.plugins.jetty;

import com.tef.middleware.pandora.plugins.jetty.model.Server;

public class MainServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server s = new Server();
		try {
			s.populate();
			s.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
