package com.david.giczi.album.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.giczi.album.dao.ImageDAO;
import com.david.giczi.album.model.Image;


@WebServlet("/SaveImage")
public class SaveImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public SaveImage() {
       
    }

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String option = request.getParameter("option");
			
			List<Image> store = new ArrayList<>();
			
			EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
			
			EntityManager em = emf.createEntityManager();
			
			ImageDAO dao = new ImageDAO(em);
			
			
			if( "all".equals( option ) ) {
				
				store = dao.getAllImages();
				
			}
			else {
				
				store = dao.getImagesByText(option);
				
			}
				
			for (Image img : store) {
			
			 byte[] data = img.getImage();
				
		     ByteArrayInputStream input_stream = new ByteArrayInputStream(data);
		     BufferedImage buffered_image = ImageIO.read(input_stream);
		     ImageIO.write(buffered_image , "jpg", new File("C:\\Users\\Judut\\Pictures\\Saved Pictures\\"+img.getFileName()) );
		     													
		}
		
			request.setAttribute("saving", store.size());
			request.setAttribute("images", store);
			request.setAttribute("list", 1);
			request.getRequestDispatcher("images.jsp").forward(request, response);
			
	}


		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request, response);
	}

}
