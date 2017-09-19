
package com.ming.tess4jtest;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class TestOpenCV {
    public static void main(String[] args) {
    	
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	File f = new File("C:\\Users\\MING\\Desktop\\1");
    	File[] files = f.listFiles();
    	Mat image = null;
    	int i = 1;
    	for (File file : files) {
    		image = Imgcodecs.imread(file.getAbsolutePath());
    		image = ImageUtil.preprocess(ImageUtil.MatToBufferedImage(image));
    		//图像灰度化
//    		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
//    		//自定义阈值的二值化
//    		Imgproc.threshold(image, image, 10, 255, Imgproc.THRESH_BINARY_INV);
    		//自适应阈值的二值化
//    		Imgproc.adaptiveThreshold(image, image, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 25, 50);
    		//图像腐蚀，用3*3的图像进行腐蚀
//    		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
//    		Imgproc.erode(image,image,element);
    		
//    		Size size1 = new Size(3,3);
//    		Size size2 = new Size(3,3);
//    		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size1);
//    		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size2);
//    		Imgproc.dilate(image, image, element2);
//    		Imgproc.erode(image, image, element1);
//    		Imgproc.dilate(image, image, element2);
    		
    		//图像输出
    		Imgcodecs.imwrite("C:\\Users\\MING\\Desktop\\new\\"+ i +".png", image);
    		i ++;
    	}
		System.out.println("ok!");
	}
}
