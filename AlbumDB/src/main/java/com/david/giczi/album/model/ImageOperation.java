package com.david.giczi.album.model;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class ImageOperation {
	

	private List<File> fileStore = new ArrayList<>();
	private JFileChooser jfc;
	

	public List<File> getFileStore() {
		return fileStore;
	}



	public JFileChooser getJfc() {
		return jfc;
	}



	public void chooseImage() {	
	
		
	jfc =new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()) {
		
		
		
		private static final long serialVersionUID = 1L;

			@Override
			protected JDialog createDialog(Component parent) throws HeadlessException {
	      
	       JDialog dialog = super.createDialog(parent);
	       
	       dialog.setAlwaysOnTop(true);;  
	       
	       return dialog;
	   }
	};
	
	
	jfc.setDialogTitle("Több kép is választható egyszerre");
	
	jfc.setApproveButtonText("Beolvas");
	
	jfc.setMultiSelectionEnabled(true);
	
	jfc.setAcceptAllFileFilterUsed(false);
	
	FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG images", "jpg");
	
	jfc.addChoosableFileFilter(filter);

	int returnValue = jfc.showOpenDialog(null);
	

	if (returnValue == JFileChooser.APPROVE_OPTION) {
	
		fileStore = Arrays.asList(jfc.getSelectedFiles());
		
	}	
	
}

	
	
}	

