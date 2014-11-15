package com.tef.middleware.pandora.plugins.jetty;


import java.io.IOException;
import java.util.Properties;

/**
*
* 
*/
public class Configuration {

   Properties properties = null;

   /** Configuration file name */
   public final static String CONFIG_FILE_NAME = "configuration.properties";

   private Configuration() {
       this.properties = new Properties();
       try {
           properties.load(Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
       } catch (IOException ex) {
           ex.printStackTrace();
       }
   }//Configuration

   /**
    * Implementando Singleton
    *
    * @return
    */
   public static Configuration getInstance() {
	   
	   if (INSTANCE == null )
	   {
		   INSTANCE = new Configuration();
	   }
	   return INSTANCE;	   
   }

   private static Configuration INSTANCE = null;

   /**
    * Retorna la propiedad de configuración solicitada
    *
    * @param key
    * @return
    */
   public String getProperty(String key) {
       return this.properties.getProperty(key);
   }//getProperty
}