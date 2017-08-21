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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;



/**
 * 
 * @Description 该类是一个工具类，用于提取待进行OCR识别的图片文字区域，并且对其进行二值化处理
 * @author MING XING
 * @date 2017年8月9日 下午3:26:35
 */
public class ImageUtil {

	/**
	 * 该方法是进行图像预处理的方法，输入包含待检测文字的图片，输出二值化后的待检测文字区域。
	 * @param src 原始图片
	 * @return 返回为只包含待处理文字区域的二值化图片
	 * @throws Exception
	 */
	public static BufferedImage tranferImgToBinary(BufferedImage src) throws Exception {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	// 对输入图片进行二值化、膨胀等预处理
    	Mat preprocessMat = preprocess(src);
    	// 检测出图片中的文字区域，返回二值化后的待检测文字区域图片
    	BufferedImage ocrRegion = findTextRegion(src, preprocessMat);
		return ocrRegion;
	}
	
	/**
	 * 该方法用于检测文字区域，并返回图片中第一行的文字区域用于ocr识别
	 * @param src 输入参数为待t
	 * @param preproceeMat
	 * @return
	 */
	private static BufferedImage findTextRegion(BufferedImage src, Mat preproceeMat) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(preproceeMat, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint2f newMtx = new MatOfPoint2f(contours.get(i).toArray());
			RotatedRect rotRect = Imgproc.minAreaRect(newMtx);
			Point[] vertices = new Point[4];
			rotRect.points(vertices);
			List<Point> rectArea = new ArrayList<Point>();
			for (int n = 0; n < 4; n++) {
				Point temp = new Point();
				temp.x = vertices[n].x;
				temp.y = vertices[n].y;
				rectArea.add(temp);
			}
			Mat rectMat = Converters.vector_Point_to_Mat(rectArea);
			double minRectArea = Imgproc.contourArea(rectMat);
			Point center = new Point();
			float radius[] = {0};
			Imgproc.minEnclosingCircle(newMtx, center, radius);
			if (Imgproc.contourArea(contours.get(i)) < 1000 
					|| minRectArea < radius[0] * radius[0] * 1.57) {
				contours.remove(i);
			}
		}
		BufferedImage outImage = MatToBufferedImage(preproceeMat);
		return outImage;
	}
	
	/**
	 * 该方法用于对图片预处理，进行灰度化、二值化、腐蚀、膨胀等，便于后续识别文字区域
	 * @param src 原始图片
	 * @return 返回为进行过灰度、二值化，腐蚀膨胀处理的Mat格式图片
	 */
	public static Mat preprocess(BufferedImage src) {
		// 定义腐蚀和膨胀的核函数
		Size size1 = new Size(30,9);
		Size size2 = new Size(24,6);
		// 转换图片格式，便于处理
		Mat mat = BufferedImageToMat(src);
		// 进行灰度化、二值化处理
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
		Imgproc.threshold(mat, mat, 20, 255, Imgproc.THRESH_BINARY_INV);
		// 进行腐蚀和膨胀处理，突出文字区域
		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size1);
		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, size2);
		Imgproc.dilate(mat, mat, element2);
		Imgproc.erode(mat, mat, element1);
		Imgproc.dilate(mat, mat, element2);
		// 返回膨胀后的Mat格式图片
		return mat;
	}
	
	/**
	 * 该方法用于把BufferedImage转换为Mat
	 * @param bfImg 输入参数为待转换的BufferedImage
	 * @return 返回为转换后的Mat
	 */
	public static Mat BufferedImageToMat(BufferedImage bfImg) {
		int imgType = BufferedImage.TYPE_3BYTE_BGR;
		int matType = CvType.CV_8UC3;
		if (bfImg == null) {
			throw new IllegalArgumentException("bfImg == null");
		}
        if (bfImg.getType() != imgType) {
            BufferedImage image = new BufferedImage(bfImg.getWidth(),bfImg.getHeight(), imgType);
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(bfImg, 0, 0, null);
            } finally {
                g.dispose();
            }
        }
        byte[] pixels = ((DataBufferByte) bfImg.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(bfImg.getHeight(), bfImg.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
	}
	
	/**
	 * 把Mat转换为BufferedImage
	 * @param in 输入参数为待转换的Mat
	 * @return 返回为转换后的BufferedImage
	 */
	public static BufferedImage MatToBufferedImage(Mat in) {
		MatOfByte mob = new MatOfByte();
		String fileExtension = ".png";
		Imgcodecs.imencode(fileExtension, in, mob);
		byte[] byteArray = mob.toArray();
        BufferedImage out = null;
        try {
             InputStream inputStream = new ByteArrayInputStream(byteArray);
             out = ImageIO.read(inputStream);
         } catch (Exception e) {
        	 e.printStackTrace();
         }
        return out;
	}
}
