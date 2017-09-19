package com.ming.tess4jtest;

import java.awt.image.BufferedImage;

import org.opencv.imgproc.Imgproc;

/**
 * 
 * @Description 该类是一个工具类，用于图像的二值化处理
 * @author MING XING
 * @date 2017年8月9日 下午3:26:35
 */
public class ImageUtilold {
	/**
	 * 静态方法，用于把r,g,b和透明度alpha转化为一个表示argb的值
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return 返回值为表示argb的值
	 */
	private static int colorToRGB(int alpha, int red, int green, int blue) {
		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;
		return newPixel;
	}

	/**
	 * 静态方法，输入为原图像和阈值，输出为二值化之后的图像
	 * 其中，灰度的通用算法为Gray= 0.299R+0.587G+0.114B，方法中的算法为加快了速度的移位方法
	 * @param image
	 * @param threshold
	 * @return 返回一个BufferedImage类型的二值化图像
	 */
	public static BufferedImage tranferImgToBinary(BufferedImage image) throws Exception {
		
		// OCR识别下，阈值为10效果较好
		int threshold = 10;
		int width = image.getWidth();
		int height = image.getHeight();
		int type = image.getType();
		int[][] grayArray = new int[width][height];
		int newPixel;
		BufferedImage outImage = new BufferedImage(width, height, type);
		for (int i = 0; i < width ; i++) {
			for (int j  = 0; j < height; j++) {
				final int color = image.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				int gray = (r * 28 + g * 151 + b * 77) >> 8;
				grayArray[i][j] = gray;
				if (gray > threshold) {
					newPixel = colorToRGB(255, 255, 255, 255);
				} else {
					newPixel = colorToRGB(255, 0, 0, 0);
				}
				outImage.setRGB(i, j, newPixel);
			}
		}
		return outImage;
	}
}
