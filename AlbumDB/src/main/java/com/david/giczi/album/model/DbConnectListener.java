package com.david.giczi.album.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class DbConnectListener implements ServletContextListener {

    public DbConnectListener() {
       
    }

    public void contextDestroyed(ServletContextEvent e)  { 
        
    	
    	  EntityManagerFactory emf =
  	            (EntityManagerFactory)e.getServletContext().getAttribute("emf");
  	        emf.close();
    	
    }

    public void contextInitialized(ServletContextEvent e)  { 
         
    	
   	 EntityManagerFactory emf =
   	            Persistence.createEntityManagerFactory("AlbumDB");
   	        e.getServletContext().setAttribute("emf", emf);
    	
    }
	
}
