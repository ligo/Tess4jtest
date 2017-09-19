
package com.ming.tess4jtest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class TestGray {
	public void grayImage() throws IOException{
		File imageFile = new File("H:\\1.png");
		BufferedImage image = ImageIO.read(imageFile);
	    
		int width = image.getWidth();  
	    int height = image.getHeight();  
	      
	    BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); 
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = image.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  
	        }  
	    }  
	      
	    File newFile = new File("H:\\gray1.png");  
	    ImageIO.write(grayImage, "png", newFile); 
	}
	
	public void binaryImage() throws IOException{
		File imageFile = new File("H:\\gray1.png");
		BufferedImage image = ImageIO.read(imageFile);
	    
		int width = image.getWidth();  
	    int height = image.getHeight();  
	      
	    BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); 
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = image.getRGB(i, j);  
	        binaryImage.setRGB(i, j, rgb);  
	        }  
	    }  
	      
	    File newFile = new File("H:\\binary1.png");  
	    ImageIO.write(binaryImage, "png", newFile); 
	}

	public static void main(String[] args) {
		TestGray tg = new TestGray();
		try {
			tg.grayImage();
			tg.binaryImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
