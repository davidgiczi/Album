package com.david.giczi.album.controller;


import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.giczi.album.dao.ImageDAO;
import com.david.giczi.album.model.Image;
import com.david.giczi.album.model.Meta;

@WebServlet("/GetImage")
public class GetImage extends HttpServlet {
	
	

	private static final long serialVersionUID = 1L;
	private List<Image> imageResultStore;
	private String savedOption;
	private List<String> topicStore;
	
    public GetImage() {
        
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String modAll = (String) request.getAttribute("modAll");
		String modText = (String) request.getAttribute("modText");
		String all = request.getParameter("all");
		String inputRecordNumber = request.getParameter("recordNumber");
		String text = request.getParameter("search");

		
		
		if ( "all".equals(all) || "all".equals(modAll)) {
			
			
			getAllImages();
			
			displayImages(request, response);
			
		
		}
		else if ( all == null && text != null ) {
			
			
			getSearchedImages(text);
			
			
			if( !imageResultStore.isEmpty() && !"".equals(text)) {
				
				displayImages(request, response);
				
				}
			
			else {
					
				request.setAttribute("notfound",  1);
				request.getRequestDispatcher("config.jsp").forward(request, response);
					
				}
			
			
		}
		
		else if ( all == null && text == null && inputRecordNumber != null ) {
			
			
			setTopicStore();
			
			validateInputData(request, response, inputRecordNumber);
				
		
		}
		
		else if ( all == null && text == null && inputRecordNumber == null && modText !=null ) {
			
			
			getSearchedImages(savedOption);	
			displayImages(request, response);
			
		
		}
		
		else {
			
			
				displayImages(request, response);
				
	
			
		}
		
		
	}

	
	
	
	private void displayImages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setAttribute("list",  1);
		request.setAttribute("saving", -1);
		request.setAttribute("images", imageResultStore);
		request.setAttribute("option", savedOption);
		request.getRequestDispatcher("images.jsp").forward(request, response);
		
		
		
	}
	
	
	
	private void getAllImages() {
		
		savedOption = "all";
		
		ImageDAO dao = new ImageDAO();
		
		imageResultStore = dao.getAllImages();
		
		dao.getEm().close();
		dao.getEmf().close();
	
	}
	
	private void validateInputData(HttpServletRequest request, HttpServletResponse response, String inputRecordNumber) throws ServletException, IOException {
			
			
			try {
				
				int inputIndex = Integer.parseInt(inputRecordNumber);
				
				if(  inputIndex >= 1 && inputIndex <= imageResultStore.size() ) {
					
					Image modiyImage = imageResultStore.get(inputIndex-1);
					
					String title = modiyImage.getFileName().substring(0, modiyImage.getFileName().length()-4);
					
					request.setAttribute("modifyImage", modiyImage);
					request.setAttribute("title", title);
					request.setAttribute("topics", topicStore);
					request.setAttribute("option", savedOption);
					request.setAttribute("meta", Meta.values());
					request.getRequestDispatcher("modify.jsp").forward(request, response);
					
				}
				else {
					
					throw new NumberFormatException();
					
				}
					
					
				
			} catch (NumberFormatException e) {
				
			
					request.setAttribute("images", imageResultStore);
					request.setAttribute("list", 1);
					request.setAttribute("saving", -1);
					request.setAttribute("invalid", 1);
					request.getRequestDispatcher("images.jsp").forward(request, response);
					
					
				}
					
			
				
	}
	
	
	private void getSearchedImages(String text)  {
		
		savedOption = text;
		
		ImageDAO dao = new ImageDAO();
		
		imageResultStore = dao.getImagesByText( text );
			  
		dao.getEm().close();
		dao.getEmf().close();
	}
	
	
	private void setTopicStore() {
		
		
		ImageDAO dao = new ImageDAO();
		
		topicStore = dao.getAllTopics();
		
		dao.getEm().close();
		dao.getEmf().close();
	
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
