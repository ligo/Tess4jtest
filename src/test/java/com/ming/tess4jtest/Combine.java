/**
 * All rights Reserved, Designed By 云安宝
 * @Title Combine.java
 * @Package com.ming.tess4jtest
 * @Description TODO(用一句话描述该文件做什么) 
 * @author 深圳云安宝科技
 * @date 2017年7月17日 下午3:45:00
 * @version V3.0
 * @Copyright 2017 www.yunanbao.com.cn Inc. All rights reserved.
 *
 * 注意：本内容仅限于深圳云安宝科技有限公司内部传阅，禁止外泄以及用于其他商业目的 
 */
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
