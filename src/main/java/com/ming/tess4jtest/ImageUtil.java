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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.Buffer;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.lowagie.text.html.simpleparser.Img;

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
	
	private void findTextRegion(BufferedImage image) {
		String region;
	}
	
	/**
	 * 该方法用于对图片预处理，进行灰度化、二值化、腐蚀、膨胀等，便于后续识别文字区域
	 * @param image 待进行预处理的图片
	 * @return 返回处理好的图片
	 */
	private BufferedImage preprocess(BufferedImage image) {
		Size size1 = new Size(30,9);
		Size size2 = new Size(24,6);
		Mat mat = BufferedImageToMat(image, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC1);
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
		Imgproc.threshold(mat, mat, 30, 255, Imgproc.THRESH_BINARY);
		
		
		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size1);
		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size2);
		Imgproc.dilate(mat, mat, element2);
		Imgproc.erode(mat, mat, element1);
		Imgproc.dilate(mat, mat, element2);
		image = MatToBufferedImage(mat);
		return image;
	}
	
	/**
	 * 将BufferedImage转换为Mat
	 * @param bfImg 待转换的BufferedImage
	 * @param imgType BufferedImage的类型
	 * @param matType Mat的类型
	 * @return 返回Mat
	 */
	private Mat BufferedImageToMat(BufferedImage bfImg, int imgType, int matType) {
		BufferedImage original = bfImg;
		int itype = imgType;
		int mtype = matType;
		
		if (original == null) {
			throw new IllegalArgumentException("original == null");
		}
		if (original.getType() != itype) {
			BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), itype);
			Graphics2D g = image.createGraphics();
			try {
				g.setComposite(AlphaComposite.Src);
				g.drawImage(original, 0, 0, null);
			} finally {
				g.dispose();
			}
		}
		byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
		Mat mat = Mat.eye(original.getHeight(), original.getWidth(), mtype);
		mat.put(0, 0, pixels);
		return mat;
	}
	
	private BufferedImage MatToBufferedImage(Mat mat) {
		BufferedImage bufferedImage = null;
		return bufferedImage;
	}
}
