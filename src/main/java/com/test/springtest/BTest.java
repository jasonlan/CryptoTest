package com.test.springtest;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;

//@BeanTest("btest")
public class BTest {

	public void dot() throws IOException {
		
		Random random = new Random();
		String fString = System.nanoTime()+""+random.nextInt();
		FileUtils.copyDirectoryToDirectory(new File("D:\\work\\apache-maven-3.5.0"), 
				new File("D:\\test\\"+fString));
	}
	
	public static void main(String[] args) {
		try {
			int i = 0;
			while(true) {
				new BTest().dot();
				System.out.println(++i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
