/**
 * All rights Reserved, Designed By 云安宝
 * @Title TestOpenCV.java
 * @Package com.ming.tess4jtest
 * @Description TODO(用一句话描述该文件做什么) 
 * @author 深圳云安宝科技
 * @date 2017年7月17日 下午4:56:49
 * @version V3.0
 * @Copyright 2017 www.yunanbao.com.cn Inc. All rights reserved.
 *
 * 注意：本内容仅限于深圳云安宝科技有限公司内部传阅，禁止外泄以及用于其他商业目的 
 */
package com.ming.tess4jtest;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
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
    		//图像灰度化
    		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
//    		//自定义阈值的二值化
    		Imgproc.threshold(image, image, 30, 255, Imgproc.THRESH_BINARY);
    		//自适应阈值的二值化
//    		Imgproc.adaptiveThreshold(image, image, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 25, 50);
    		//图像腐蚀，用3*3的图像进行腐蚀
//    		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
//    		Imgproc.erode(image,image,element);
    		//图像输出
    		Imgcodecs.imwrite("C:\\Users\\MING\\Desktop\\new\\"+ i +".png", image);
    		i ++;
    	}
		System.out.println("ok!");
	}
}
