package com.david.giczi.album.controller;




import java.io.IOException;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.giczi.album.dao.ImageDAO;
import com.david.giczi.album.model.Image;
import com.david.giczi.album.model.Meta;


@WebServlet("/ModifyImage")
public class ModifyImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ModifyImage() {
       
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
	
		String option = request.getParameter("option");
		
		String id = request.getParameter("id");
		String codeString = request.getParameter("image");
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String topic = request.getParameter("top");
		String visible = request.getParameter("visible");
		String meta = request.getParameter("meta");
		String date = request.getParameter("date");
		
	
		Boolean isVisible = false;
		
		
		if( "true".equals(visible) ) {
			
			isVisible = true;
		}
		
		
		if( title.length() > 255 ) {
			
			title = title.substring(0,251);
		}
		
		if( comment.length() > 255 ) {
			
			comment = comment.substring(0,255);
		}
		
		
		Meta inputMeta = null;
		
		
		if( "NORMAL".equals(meta) ) {
			
			
			inputMeta = Meta.NORMAL;
			
		}
		else if( "FORGATAS_BALRA".equals(meta) ) {
			
			
			inputMeta = Meta.FORGATAS_BALRA;
			
		}
		else if( "FORGATAS_JOBBRA".equals(meta) ) {
			
			
			inputMeta = Meta.FORGATAS_JOBBRA;
			
		}
		else if( "ALLO_KERET".equals(meta) ) {
			
			
			inputMeta = Meta.ALLO_KERET;
			
		}
		else if( "PANORAMA".equals(meta) ) {
			
			
			inputMeta = Meta.PANORAMA;
			
		}
		
		
		Image modifyImage = new Image(Base64.getDecoder().decode(codeString), title+".jpg", topic, isVisible, inputMeta, date, comment);
	
		modifyImage.setCodeString(codeString);
		
		
		
		if( "add".equals( option ) )  {
			
			request.setAttribute("modImage", modifyImage);
			request.getRequestDispatcher("AddImage").forward(request, response);
			return;
		}
		
		EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
		
		EntityManager em = emf.createEntityManager();
		
		ImageDAO dao = new ImageDAO(em);
		
		
			modifyImage.setId(Long.valueOf(id));
		
		 	dao.putImage(modifyImage);
		
		
		
		if ( "all".equals( option ) ) {
			
			request.setAttribute("modAll", option);
			request.getRequestDispatcher("GetImage").forward(request, response);
			return;
		}
		else {
			
			request.setAttribute("modText", option);
			request.getRequestDispatcher("GetImage").forward(request, response);

			
		}
		
			
		em.close();
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
