package com.qiandu.dev.util;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetARandomPosition {
	static String filePath="classpath:济南市地址与经纬度对应表.txt";
	public static String getARandomPosition() {
		try {
			String encoding = "utf8";
//			File file = new File(filePath);
			File file = ResourceUtils.getFile(filePath);
			String Temp[]=new String[2];
			String temp[]=new String[1000];
			int n=0;
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt =new String();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					Temp = lineTxt.split(" ");
					temp[n]=Temp[1];
					n++;
				}
				read.close();
				
				int randomInt=(int)(Math.random()*n);
				return temp[randomInt];
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;

	}

	public static List<String> getPositionFromFile(String fileName) throws  Exception{
		List<String> posList = new ArrayList<>();
		String filePath="classpath:"+fileName;
		File file = ResourceUtils.getFile(filePath);
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt =new String();
		while ((lineTxt = bufferedReader.readLine()) != null) {
			posList.add(lineTxt);
		}
		return posList;
	}

	public static List<String> getPositionFromFile(InputStream inputStream) throws  Exception{
		List<String> posList = new ArrayList<>();
		InputStreamReader read = new InputStreamReader(inputStream, "utf8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt =new String();
		while ((lineTxt = bufferedReader.readLine()) != null) {
			posList.add(lineTxt);
		}
		return posList;
	}

	public static void main(String[] args) throws Exception {
		GetARandomPosition get = new GetARandomPosition();
		List<String> list=get.getPositionFromFile("pois_jinan.txt");
	}

}
