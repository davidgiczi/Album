package com.david.giczi.album.model;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Compress {
	
		

	    	   
	    public static File compressImage(File input) throws IOException {
	      
	    
	        BufferedImage image = ImageIO.read(input);
	       
	        String inputURL = input.getAbsolutePath().substring(0, input.getAbsolutePath().length()-4);
	       
	        File output = new File(inputURL+"_beolv.jpg");

	        
	        OutputStream out = new FileOutputStream(output);

	        ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
	        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
	        writer.setOutput(ios);

	        ImageWriteParam param = writer.getDefaultWriteParam();
	        if (param.canWriteCompressed()){
	            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	            param.setCompressionQuality(0.3f);
	        }

	        writer.write(null, new IIOImage(image, null, null), param);

	        out.close();
	        ios.close();
	        writer.dispose();
	     
	        return output;
	        
	    }

}
