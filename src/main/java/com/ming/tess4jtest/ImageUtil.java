/**
 * All rights Reserved, Designed By 云安宝
 * @Title ImageUtil.java
 * @Description 该类用于图像的二值化处理 
 * @author 深圳云安宝科技
 * @date 2017年8月9日 上午11:59:20
 * @version V3.0
 * @Copyright 2017 www.yunanbao.com.cn Inc. All rights reserved.
 *
 * 注意：本内容仅限于深圳云安宝科技有限公司内部传阅，禁止外泄以及用于其他商业目的 
 */
package com.ming.tess4jtest;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;



/**
 * 
 * @Description 该类是一个工具类，用于图像的二值化处理
 * @author MING XING
 * @date 2017年8月9日 下午3:26:35
 */
public class ImageUtil {

	/**
	 * 静态方法，输入为原图像,输出为二值化之后的图像
	 * 其中，灰度的通用算法为Gray= 0.299R+0.587G+0.114B，方法中的算法为加快了速度的移位方法
	 * @param image
	 * @param threshold
	 * @return 返回一个BufferedImage类型的二值化图像
	 */
	public static BufferedImage tranferImgToBinary(BufferedImage image) throws Exception {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// OCR识别下，阈值为10效果较好
		int threshold = 10;
		int width = image.getWidth();
		int height = image.getHeight();
		int type = image.getType();
		int[][] grayArray = new int[width][height];
		int newPixel;
		BufferedImage outImage = new BufferedImage(width, height, type);
		return outImage;
	}
	
	private void findTextRegion(Mat in) {
		MatOfPoint cnt = null;
		double area = 0;
		List<MatOfPoint> contour = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(in, contour, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		for(int i = 0; i < contour.size(); i++) {
			cnt = contour.get(i);
			MatOfPoint2f newcnt = new MatOfPoint2f(cnt.toArray());
			area = Imgproc.contourArea(cnt);
			if (area < 1000) {
				continue;
			}
			RotatedRect rect = Imgproc.minAreaRect(newcnt);
			Point[] vertices = new Point[4];
			rect.points(vertices);
		}
		String region;
	}
	
	/**
	 * 该方法用于对图片预处理，进行灰度化、二值化、腐蚀、膨胀等，便于后续识别文字区域
	 * @param image 待进行预处理的图片
	 * @return 返回处理好的图片
	 */
	private Mat preprocess(BufferedImage image) {
		// 定义腐蚀和膨胀的核函数
		Size size1 = new Size(30,9);
		Size size2 = new Size(24,6);
		Mat mat = BufferedImageToMat(image);
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
		Imgproc.threshold(mat, mat, 30, 255, Imgproc.THRESH_BINARY);
		
		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size1);
		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size2);
		Imgproc.dilate(mat, mat, element2);
		Imgproc.erode(mat, mat, element1);
		Imgproc.dilate(mat, mat, element2);
		return mat;
	}
	
	/**
	 * 该方法用于把BufferedImage转换为Mat
	 * @param bfImg 输入参数为待转换的BufferedImage
	 * @return 输出为转换后的Mat
	 */
	private Mat BufferedImageToMat(BufferedImage bfImg) {
		 Mat out;
         byte[] data;
         int r, g, b;

         if(bfImg.getType() == BufferedImage.TYPE_INT_RGB)
         {
             out = new Mat(bfImg.getHeight(), bfImg.getWidth(), CvType.CV_8UC3);
             data = new byte[bfImg.getHeight() * bfImg.getWidth() * (int)out.elemSize()];
             int[] dataBuff = bfImg.getRGB(0, 0, bfImg.getWidth(), bfImg.getHeight(), null, 0, bfImg.getWidth());
             for(int i = 0; i < dataBuff.length; i++)
             {
                 data[i*3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                 data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                 data[i*3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
             }
         }
         else
         {
             out = new Mat(bfImg.getHeight(), bfImg.getWidth(), CvType.CV_8UC1);
             data = new byte[bfImg.getHeight() * bfImg.getWidth() * (int)out.elemSize()];
             int[] dataBuff = bfImg.getRGB(0, 0, bfImg.getWidth(), bfImg.getHeight(), null, 0, bfImg.getWidth());
             for(int i = 0; i < dataBuff.length; i++)
             {
               r = (byte) ((dataBuff[i] >> 16) & 0xFF);
               g = (byte) ((dataBuff[i] >> 8) & 0xFF);
               b = (byte) ((dataBuff[i] >> 0) & 0xFF);
               data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
             }
          }
          out.put(0, 0, data);
          return out;
	}
	
	/**
	 * 把Mat转换为BufferedImage
	 * @param in 输入参数为待转换的Mat
	 * @return 输出为转换后的BufferedImage
	 */
	private BufferedImage MatToBufferedImage(Mat in) {
		BufferedImage out;
        byte[] data = new byte[in.height() * in.width() * (int)in.elemSize()];
        int type;
        in.get(0, 0, data);

        if(in.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(in.width(), in.height(), type);

        out.getRaster().setDataElements(0, 0, out.getWidth(), out.getHeight(), data);
        return out;
	}
}
