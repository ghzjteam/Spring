package classwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class A3_1 {
	static List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
	static Scanner input=new Scanner(System.in);
	public static void main(String[] args) {
		boolean flag=true;
		System.out.println("加载数据中...");
		readData();
		System.out.println("加载成功!");
		while(flag) {
			System.out.println("请输入代码执行操作:");
			System.out.println("1-显示数据");
			System.out.println("2-添加数据");
			System.out.println("3-修改数据");
			System.out.println("4-查找数据");
			System.out.println("5-删除数据");
			System.out.println("6-保存数据");
			int cas=input.nextInt();
			switch (cas) {
			case 1:
				printInfo(data);
				break;
				
			case 2:
				add();
				break;
				
			case 3:
				update();
				break;
				
			case 4:
				search();
				break;
				
			case 5:
				delete();
				break;
				
			case 6:
				writeData();
				flag=false;
				break;

			default:
				break;
			}
		}
	}
	
	public static void readData() {
		
		try {
			FileInputStream fis=new FileInputStream(new File("score.data"));
			int n;
			byte[] numByte=new byte[8];
			byte[] nameByte=new byte[20];
			byte[] scoreByte=new byte[1];
			while(true) {
				Map<String, Object> map=new HashMap<String, Object>();
				n=fis.read(numByte);
				n=fis.read(nameByte);
				n=fis.read(scoreByte);
				if(n==-1)
					break;
				Long num=byteToLoingint(numByte);
				String name=new String(nameByte);
				map.put("num", num);
				map.put("name", name);
				map.put("score",(int)scoreByte[0]);
				data.add(map);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void printInfo(List<Map<String, Object>> data) {
		for(Map<String, Object> m:data) {
			System.out.println("学号:"+m.get("num")+",姓名:"+m.get("name")+",分数:"+m.get("score"));
			
		}
	}
	
	public static void writeData() {
		try {
			FileOutputStream fos=new FileOutputStream(new File("score.data"));
			/*
			 * for(long i=16204101;i<=16204131;i++) { byte[] numByte=longintToByte(i);
			 * byte[] nameByte=strToByte(GetChineseName.getName()); byte
			 * scoreByte=(byte)(int)(Math.random()*100);
			 * 
			 * fos.write(numByte,0,8); fos.write(nameByte,0,20); fos.write(scoreByte);
			 * fos.flush(); }
			 */
			
			for (Map<String, Object> m : data) {
				byte[] numByte = longintToByte((long) m.get("num"));
				byte[] nameByte = strToByte((String) m.get("name"));
				byte scoreByte = (byte) (int) m.get("score");

				fos.write(numByte, 0, 8);
				fos.write(nameByte, 0, 20);
				fos.write(scoreByte);
				fos.flush();
			}

			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("写入成功!");
	}
	public static byte[] longintToByte(long num) {
		byte[] numByte=new byte[8];
		for(int i=0;i<8;i++) {
			numByte[i]=(byte)(num>>8*(7-i)&0xFF);
		}
		return numByte;
	}
	
	public static long byteToLoingint(byte[] longbyte) {
		long num=0;
		for(int i=0;i<8;i++) {
			num+=((long)longbyte[i]&0xFF)<<8*(7-i);
		}
		return num;
	}
	
	public static byte[] strToByte(String str) {
		byte[] nameByte=new byte[20];
		byte[] chinese=str.getBytes();
		for(int j=20-chinese.length-1;j>=0;j--) {
			nameByte[j]=0x0;
		}
		for(int j=20-chinese.length;j<20;j++)
			nameByte[j]=chinese[j-(20-chinese.length)];
		return nameByte;
	}
	
	public static void add() {
		long num;
		String name;
		byte score;
		
		System.out.println("请输入学号：");
		num=input.nextLong();
		System.out.println("请输入姓名：");
		name=input.next();
		System.out.println("请输入分数：");
		score=(byte)input.nextInt();
		Map<String, Object> map=new HashMap<>();
		map.put("num",num);
		map.put("name", new String(strToByte(name)));
		map.put("score", (int)score);
		data.add(map);
		System.out.println("添加成功!");
	}
	
	public static void delete() {
		long num;
		System.out.println("请输入要删除的学号:");
		num=input.nextLong();
		for(int i=0;i<data.size();i++) {
			if((long)data.get(i).get("num")==num) {
				data.remove(i);
				break;
			}
		}
		System.out.println("删除成功!");
	}
	
	public static void update() {
		long num;
		int score;
		System.out.println("请输入要修改的学生学号:");
		num=input.nextLong();
		for(int i=0;i<data.size();i++) {
			if((long)data.get(i).get("num")==num) {
				System.out.println("此学生成绩为:"+data.get(i).get("score"));
				System.out.println("请输入要修改的成绩:");
				data.get(i).put("score", (int)input.nextInt());
				break;
			}
		}
		System.out.println("修改成功!");
	}
	
	public static void search() {
		long num;
		System.out.println("请输入要查找的学生学号:");
		num=input.nextLong();
		for(int i=0;i<data.size();i++) {
			if((long)data.get(i).get("num")==num) {
				Map<String, Object> m=data.get(i);
				System.out.println("学号:"+m.get("num")+",姓名:"+m.get("name")+",分数:"+m.get("score"));
				break;
			}
		}
		System.out.println("查找成功!");
	}
}
