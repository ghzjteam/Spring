package classwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class A1_1 {
	public static void main(String[] args) {
List<String> data=new ArrayList<>();
		try {
			FileReader fr = new FileReader(new File("test.txt"));
			BufferedReader br=new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null) {
				data.add(line);
			}
			br.close();
			for(String str : data) {
				if(str.indexOf("test")!=-1)
					System.out.println(str);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}