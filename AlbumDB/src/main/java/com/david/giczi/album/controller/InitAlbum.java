package com.david.giczi.album.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.giczi.album.dao.ImageDAO;
import com.david.giczi.album.model.Color;
import com.david.giczi.album.model.Image;
import com.david.giczi.album.model.IntroText;
import com.david.giczi.album.model.Meta;





@WebServlet("/InitAlbum")
public class InitAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public InitAlbum() {
      
       
    }
    
    private List<Image> imageResultStore;
    private List<String> topicStore;
    private List<String> titleStore;
    private List<String> codeStringStore;
    private List<Meta> metaDataStore;
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String chosenTopic = request.getParameter("topic");
		
		
		if( chosenTopic == null || "Összes".equals(chosenTopic)) {
			
			chosenTopic = "Összes";
			
			getAllImages();
			
			initTopicStore();
			
		}
		else {
		
			getTopicImages(chosenTopic);
			
		}
		
	
		
	if( !imageResultStore.isEmpty() ) {
		
		
		setupStoresForImages();
		
		request.setAttribute("chosenTopic", chosenTopic);
		request.setAttribute("titles", titleStore);
		request.setAttribute("codes", codeStringStore);
		request.setAttribute("meta", metaDataStore);
		
	}
	
		
		request.setAttribute("topics", topicStore);
		request.setAttribute("intro", Arrays.asList(IntroText.TEXT));
		request.setAttribute("color", Arrays.asList(Color.BACKGROUND_COLOR));
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		
	}

	
	
	
	private void getAllImages() {
		
	
		ImageDAO dao = new ImageDAO();
		
		imageResultStore = dao.getAllImages();
		
		dao.getEm().close();
		dao.getEmf().close();
		
	}
	
	private void initTopicStore() {

		
		ImageDAO dao = new ImageDAO();
		
		topicStore = new ArrayList<>();
		
		topicStore.add("Összes");
		
		
		for (String topic : dao.getAllTopics()) {
		
			topicStore.add(topic);
				
		}
		
		
		for ( int i = topicStore.size()-1; i > 0;  i-- ) {
			
			
			if ( !isAvailableTopics( topicStore.get(i) ) ) {
				
				topicStore.remove(i);
				
			}
					
		}
		
		dao.getEm().close();
		dao.getEmf().close();
		
	}
	
	
	private boolean isAvailableTopics(String topic) {
		
		
		int topicPcs = 0;
		int invisibleTopicPcs = 0;
		
		for (Image image : imageResultStore) {
			
			if ( image.getTopic().equals(topic) ) {
				
				topicPcs++;
					
			}
			
		}
			
		for (Image image : imageResultStore) {
			
			if ( image.getTopic().equals(topic) && !image.getIsVisible() ) {
				
				invisibleTopicPcs++;
					
			}
			
		}
		
		if ( topicPcs == invisibleTopicPcs ) {
			
			return false;
			
		}
		
		
		return true;
	}
	
	
	
	private void getTopicImages(String topic) {
		
	
		ImageDAO dao = new ImageDAO();
		
		imageResultStore = dao.getImagesByTopic(topic);
		
		dao.getEm().close();
		dao.getEmf().close();
	}
	
	
	private void setupStoresForImages() {
		
		
		titleStore = new ArrayList<>();
		codeStringStore = new ArrayList<>();
		metaDataStore = new ArrayList<>();
		
		
		for ( int i = imageResultStore.size()-1; i >= 0 ; i-- ) {
			
			if( !imageResultStore.get(i).getIsVisible() ) {
				
				imageResultStore.remove(i);
				
			}
		
		}
	
		
	for (Image image : imageResultStore) {
			
			codeStringStore.add(image.getCodeString());
			titleStore.add(image.getFileName().substring(0, image.getFileName().length()-4));
			metaDataStore.add(image.getMeta());
			
		}
		
		
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
