package com.david.giczi.album.model;


import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Image implements Serializable, Comparable<Image> {

	
	private static final long serialVersionUID = 1L;
	
	public Image() {
		
	}


	public Image(byte[] image, String fileName, String topic, Boolean isVisible, Meta meta, String date,
			String comment) {
	
		this.image = image;
		this.fileName = fileName;
		this.topic = topic;
		this.isVisible = isVisible;
		this.meta = meta;
		this.date = date;
		this.comment = comment;
		this.codeString=null;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private byte[] image;
	private String fileName;
	private String topic;
	private Boolean isVisible;
	private Meta meta;
	private String date;
	private String comment;
	private String codeString;
	
	

	public Long getId() {
		return id;
	}
	
	

	public void setId(Long id) {
		this.id = id;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public Boolean getIsVisible() {
		return isVisible;
	}


	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}


	public Meta getMeta() {
		return meta;
	}


	public void setMeta(Meta meta) {
		this.meta = meta;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	public String getCodeString() {
		return codeString;
	}


	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((isVisible == null) ? 0 : isVisible.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (isVisible == null) {
			if (other.isVisible != null)
				return false;
		} else if (!isVisible.equals(other.isVisible))
			return false;
		if (meta != other.meta)
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Image [id=" + id + ", fileName=" + fileName + ", topic=" + topic + ", isVisible=" + isVisible
				+ ", meta=" + meta + ", date=" + date + ", comment=" + comment + "]";
	}


	@Override
	public int compareTo(Image o) {
	
		
	if(	this.getTopic().compareTo( o.getTopic() ) < 0 ) {
		
		
		return -1;
	
	}
	else if ( this.getTopic().compareTo( o.getTopic() ) == 0) {
		
		
		return this.getId() < o.getId() ? -1 : this.getId() == o.getId() ? 0 : 1;
 		
		
	}
		
		
		return 1;
	}
	
	
	
	
}
