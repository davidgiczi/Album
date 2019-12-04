package com.david.giczi.album.dao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.david.giczi.album.model.Image;

public class ImageDAO {

	
	private EntityManager em;
	
	
	
	public ImageDAO(EntityManager em) {
		
		this.em = em;
	}
	
	
	public List<Image> getAllImages() {
		
		
		List<Image>	imageResultStore;
		
		try {
		
			TypedQuery<Image> query =em.createQuery("SELECT i FROM Image i", Image.class);
			
			List<Image> images = query.getResultList();
			
			imageResultStore = new ArrayList<>();
			
			if( !images.isEmpty() ) {
				
				Collections.sort(images);
				
				imageResultStore = addCodeStringToImage(images);
				
			}
			
	} 
			
			finally {
				
				if(em.getTransaction().isActive()) {
					
					 em.getTransaction().rollback();
				        
					
				}
		           
				
			}
		
		
		return imageResultStore;
	}
	
	
	public List<Image> getImagesByTopic(String topic) {
		
		
		List<Image> imageResultStore;
		
		try {
			
			imageResultStore = new ArrayList<>();
			
			TypedQuery<Image> query =em.createQuery("SELECT i FROM Image i WHERE i.topic LIKE \'"+topic+"\'", Image.class);
			
			List<Image> images = query.getResultList();
			
			if( !images.isEmpty() ) {
				
				imageResultStore = addCodeStringToImage(images);
				
			}
	
			
		}
		
		
		
		finally {
			
			if(em.getTransaction().isActive()) {
				
				 em.getTransaction().rollback();
			        
				
			}
	           
			
		}
		
		
		return imageResultStore;
		
	}
	
	
	public List<Image> getImagesByText(String text) {
		
		
		List<Image> imageResultStore;
		
		try {
			
			imageResultStore = new ArrayList<>();
			
			List<Image> allImages = getAllImages();
		
			
			for (Image image : allImages) {
				
				
				if ( image.getFileName().toLowerCase().contains( text.toLowerCase() ) ||  image.getComment().toLowerCase().contains( text.toLowerCase() ) ||
						
					 image.getTopic().equals( text ) || String.valueOf( image.getMeta() ).equals( text )  ||
					 
					 String.valueOf( image.getIsVisible() ).equals( text ) || image.getDate().equals( text )) {
					
							
					imageResultStore.add(image);
					
				}
			}
			
			
		}
			finally {
				
				if(em.getTransaction().isActive()) {
					
					 em.getTransaction().rollback();
				        
					
				}
		           
				
			}
		
		return imageResultStore;
	}
	
	
	public List<String> getAllTopics() {
		
		List<String> topicStore;
		
		try {	
			
			topicStore = new ArrayList<>();
			
			List<Image> images=  getAllImages();
			
			if( !images.isEmpty() ) {
			
			topicStore.add(images.get(0).getTopic());
			
			for(int i = 1; i < images.size(); i++ ) {
				
				if( !images.get(i).getTopic().equals( images.get(i-1).getTopic() ) ) {
					
					topicStore.add( images.get(i).getTopic() );
					
					}
				
				}
			}
		}
			finally {
				
				if(em.getTransaction().isActive()) {
					
					 em.getTransaction().rollback();
				        
					
				}
		           
				
			}
			
		
		return topicStore;
		
	}
	
	
	public boolean putImage(Image modifiedImage) {
		
	try {
		
		Image image = em.find(Image.class, modifiedImage.getId());
		
		em.getTransaction().begin();
		
		image.setImage(modifiedImage.getImage());
		image.setFileName(modifiedImage.getFileName());
		image.setComment(modifiedImage.getComment());
		image.setTopic(modifiedImage.getTopic());
		image.setIsVisible(modifiedImage.getIsVisible());
		image.setMeta(modifiedImage.getMeta());
		image.setDate(modifiedImage.getDate());
		
		em.getTransaction().commit();
		
	}
	catch (Exception e) {
		
		return false;
		
	}
	finally {
		
		if(em.getTransaction().isActive()) {
			
			 em.getTransaction().rollback();
		        
			
		}
           
	
	}
	
		
		return true;
	}
	
	public boolean persistImages(List<Image> store) {
		
		
	try {
		
		em.getTransaction().begin();
		
		for (Image image : store) {
			
			image.setCodeString(null);
			
			em.persist(image);
		}
		
		em.getTransaction().commit();
		
	} catch (Exception e) {
			
			return false;
		}
		
finally {
			
			if(em.getTransaction().isActive()) {
				
				 em.getTransaction().rollback();
			        
				
			}
	           
			
		}
		
		
		return true;
	}
	  
	
	
	public List<Image> addCodeStringToImage(List<Image> imagesStore) {
		
		for (Image image : imagesStore) {
			
			byte [] data = image.getImage();
			
			image.setCodeString(Base64.getEncoder().encodeToString(data));
	
	}
	
		return imagesStore;
}

	
}