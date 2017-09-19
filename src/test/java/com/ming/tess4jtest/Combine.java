
package com.ming.tess4jtest;

import java.io.IOException;

public class Combine {

	public static void main(String[] args) {
		TestGray tg = new TestGray();
		try {
			tg.grayImage();
			tg.binaryImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestTess4j tess4j = new TestTess4j();
			tess4j.test();
	}

}
