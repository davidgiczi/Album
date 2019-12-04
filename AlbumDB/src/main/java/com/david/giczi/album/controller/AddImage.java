package com.david.giczi.album.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Base64;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.giczi.album.dao.ImageDAO;
import com.david.giczi.album.model.Compress;
import com.david.giczi.album.model.Image;
import com.david.giczi.album.model.ImageOperation;
import com.david.giczi.album.model.Meta;
import com.david.giczi.album.model.TimeStamp;




@WebServlet("/AddImage")
public class AddImage extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
   
	private  ImageOperation  op;
	private List<Image> inputImageStore;
	private List<String> topicStore;
	private int savedIndex;
		
    public AddImage() {
    	
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String input = request.getParameter("input");
		String persist = request.getParameter("persist");
		String back = request.getParameter("back");
		String inputRecordNumber = request.getParameter("recordNumber");
		Image modifiedImage = (Image) request.getAttribute("modImage");
		
		
		if ( "input".equals(input) ) {
			

			loadingImageFromPC(request, response);
			
			
		}
		else if ( input == null && "persist".equals(persist) ) {
			
			
			persistImage(request, response);
			
			
		}
		else if( input == null && persist == null && "back".equals(back) ) {
			
			
			request.getRequestDispatcher("config.jsp").forward(request, response);
			
			
		}
		else if( input == null && persist == null && back == null && inputRecordNumber != null) {
			
			setTopicStore();
	
			validateInputData(request, response, inputRecordNumber);
			
		}
		
		else if ( input == null && persist == null && back == null && inputRecordNumber == null && modifiedImage != null ) {
			
			modifyImage(modifiedImage, savedIndex);
			
			displayImage(request, response);
			
		}

		else {
			
			displayImage(request, response);
			
		}
			
	}
	
	
	private void loadingImageFromPC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		inputImageStore = new ArrayList<>();	
			
		if ( op != null )  {
			
			op.getJfc().cancelSelection();
		}
		
		op = new ImageOperation();
		
		 op.chooseImage();
		
		if( op.getFileStore().isEmpty() ) {
			
			request.getRequestDispatcher("config.jsp").forward(request, response);
			
			return;
		}
		
		
		for (File file : op.getFileStore()) {
			
			
			if( file.length() >= 1024*1024*1.3 ) {
				
				file = Compress.compressImage(file);
				
			}
			
			if( file.length() < 1024*1024*1.3 )  {
			
			byte[] store = new byte[(int) file.length()];
			
			FileInputStream fileInputStream = new FileInputStream(file);
			
			fileInputStream.read(store);
			
			fileInputStream.close();
			
			Image inputImage = new Image(store, file.getName(), "Egyéb", true, Meta.NORMAL, TimeStamp.timeStamp(), "");
			
			inputImage.setCodeString(Base64.getEncoder().encodeToString(store));
			
			inputImageStore.add(inputImage);
			
		}
	}	
		
		displayImage(request, response);
		

		
	}
	
	
	private void displayImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("saving", -1);
		request.setAttribute("images", inputImageStore);
		request.setAttribute("add", 1);
		request.getRequestDispatcher("images.jsp").forward(request, response);
		
	}
	
	
	private void persistImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
		
		EntityManager em = emf.createEntityManager();
		
		
		try {
		
			
		ImageDAO dao = new ImageDAO(em);
		
		dao.persistImages(inputImageStore);	
		
		
		request.setAttribute("ok", 1);
		request.setAttribute("inputImagesNumber", inputImageStore.size());
		request.getRequestDispatcher("config.jsp").forward(request, response);
	
		
		} catch (ServletException | IOException e) {
		
			
		request.setAttribute("ok", -1);
		request.setAttribute("inputImagesNumber", inputImageStore.size());
		request.getRequestDispatcher("config.jsp").forward(request, response);
				
		}
		finally {
			
			
			em.close();
		}
		
		
	}

		private void validateInputData(HttpServletRequest request, HttpServletResponse response, String inputRecordNumber) throws ServletException, IOException {
			
			
				try {
					
					int inputIndex = Integer.parseInt(inputRecordNumber);
					
					if(  inputIndex >= 1 && inputIndex <= inputImageStore.size() ) {
						
						Image modiyImage = inputImageStore.get(inputIndex-1);
						
						savedIndex = inputIndex-1;
						
						String title = modiyImage.getFileName().substring(0, modiyImage.getFileName().length()-4);
						
						request.setAttribute("modifyImage", modiyImage);
						request.setAttribute("title", title);
						request.setAttribute("topics", topicStore);
						request.setAttribute("meta",  Meta.values() );
						request.setAttribute("option", "add");
						request.getRequestDispatcher("modify.jsp").forward(request, response);
						
					}
					else {
						
						throw new NumberFormatException();
						
					}
						
						
					
				} catch (NumberFormatException e) {
					
					
					try {
						
						request.setAttribute("saving", -1);
						request.setAttribute("images", inputImageStore);
						request.setAttribute("add", 1);
						request.setAttribute("invalid", 1);
						request.getRequestDispatcher("images.jsp").forward(request, response);
				
						
					} catch (ServletException | IOException e1) {
						
						e1.printStackTrace();
					}
					
					
				}
			
			
			
		}
		
		
		
		private void modifyImage(Image modifiedImage, int index) throws ServletException, IOException {
			
			
			inputImageStore.set(index, modifiedImage);
		
			
		}
		
		
		
		private void setTopicStore() {
		
		
			EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
			
			EntityManager em = emf.createEntityManager();
			
			ImageDAO dao = new ImageDAO(em);
			
			topicStore = dao.getAllTopics();
			
			em.close();
		}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
