/**
 * All rights Reserved, Designed By 云安宝
 * @Title TestTess4j.java
 * @Package com.ming.tess4jtest
 * @Description TODO(用一句话描述该文件做什么) 
 * @author 深圳云安宝科技
 * @date 2017年7月17日 下午2:58:08
 * @version V3.0
 * @Copyright 2017 www.yunanbao.com.cn Inc. All rights reserved.
 *
 * 注意：本内容仅限于深圳云安宝科技有限公司内部传阅，禁止外泄以及用于其他商业目的 
 */
package com.ming.tess4jtest;
import java.io.File;

import org.junit.*;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
public class TestTess4j {
    public void test(){
    	File imageFile = new File("H:\\binary1.png");
    	Tesseract instance = new Tesseract();
    	instance.setLanguage("chi_sim");
    	try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
    }
}
