
package com.ming.tess4jtest;
import java.io.File;

import org.junit.*;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
public class TestTess4j {
    public void test(){
    	File imageFile = new File("H:\\binary1.png");
    	Tesseract instance = new Tesseract();
    	instance.setLanguage("normal");
    	try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
    }
}
