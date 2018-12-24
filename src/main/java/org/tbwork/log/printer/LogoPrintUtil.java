package org.tbwork.log.printer;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.tbwork.anole.loader.util.StringUtil;

class LogoPrintUtil {
	
    private static void load() throws FileNotFoundException{ 
       List<Integer> chars = new ArrayList<Integer>();
  	   File file = new File("D:/Workspace/tbworks/logo-printer.w/log-printer/src/main/resources/logo.txt");
  	   Scanner scanner = new Scanner(file); 
  	   Integer lineWidth = 0;
  	   while(scanner.hasNextLine()){
  		   String line = scanner.nextLine();
  		   lineWidth = line.length();
  		   for(int i = 0 ; i< line.length(); i++){
  			   char a = line.charAt(i);
  			   chars.add((int)a);
  		   }
  	   }
  	   compress(chars, lineWidth);  
	   System.out.println("DONE");
    }
    
    private static void compress(List<Integer> chars, int length){
    	File file = new File("D:/Workspace/tbworks/logo-printer.w/log-printer/src/main/resources/saas.cps");
    	FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(length+"\n");
			int count = 0;
			int cur = 0;
	    	for(int i : chars){
	    		if( cur != i ){
	    			if( count > 0)
	    				fw.write(cur+","+count+"\n");
	    			cur = i;
	    			count = 1;
	    		}
	    		else 
	    			count ++;
	    	}
	    	if(count > 0) 
	    		fw.write(cur+","+count);
	    	fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    
     
    public static void decompress(String line1, String line2, String line3){
	    InputStream in = LogoPrintUtil.class.getResourceAsStream("/saas.cps");  
    	Scanner scanner = null;
    	List<Integer> chars = new ArrayList<Integer>();
		try {
			scanner = new Scanner(in); 
			Integer width = Integer.valueOf(scanner.nextLine());
			while(scanner.hasNextLine()){
				String lineStr = scanner.nextLine();
				String [] charAndRepeatCount = lineStr.split(",");
				Integer targetChar =  Integer.valueOf(charAndRepeatCount[0]);
				int repeatCount = Integer.valueOf(charAndRepeatCount[1]);
				for(int i = 0; i < repeatCount ; i++){
					chars.add(targetChar);
				}
			}
			scanner.close();
			setFrameChar(chars, '+');
			addCustomContet(chars, '-', line1, '=');
			addCustomContet(chars, '#', line2, ' ');
			addCustomContet(chars, '?', line3, ' '); 
			print(chars, width);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
  
    
    private static void print(List<Integer> chars, int length){
    	for(int i = 0 ; i< chars.size(); i++){
    		System.out.print(String.valueOf((char)chars.get(i).byteValue()));
    		if((i+1) % length ==0){
    			System.out.println("");
    		}
    	}
    }
    
    
    private static void addCustomContet(List<Integer> chars, char placeHolderChar, String customString, char blankChar){
    	StringBuilder sb = new StringBuilder();  
    	int start = -1;
    	for(int i =0; i < chars.size(); i++){
    		if(chars.get(i)==placeHolderChar){
    			if(start == -1){
    				start = i;
    			}
    			sb.append(placeHolderChar); 
    		}
    	}
    	String customPlaceholder = sb.toString();
    	int customSize = customPlaceholder.length();
    	if(customString.length() > customSize){
    		customString = customString.substring(0, customSize);
    	}
    	int blankSize = customSize - customString.length();
    	int foreBlankSize = blankSize/2;
    	int tailBlankSize = blankSize - foreBlankSize;
    	customString = StringUtil.getRepeatCharString(blankChar, foreBlankSize) + customString + StringUtil.getRepeatCharString(blankChar, tailBlankSize);
    	for(int i = start; i < start + customSize;  i ++){
    		chars.set(i, (int) customString.charAt(i-start)); 
    	}
    } 
    
    private static void setFrameChar(List<Integer> chars, char targetChar){
    	if(targetChar == '-' || targetChar == '#' || targetChar == '?'){
    	   System.out.println("Invalid frame char. It can not be '-', '#' or '?'"); 
    	}
    	for(int i=0; i<chars.size() ; i++){
    		if(chars.get(i)=='.'){
    			chars.set(i, (int)targetChar);
    		}
    	}
    }
    
    public static void main(String[] args) throws FileNotFoundException { 
    	load();
    	decompress("==", "https://github.com/tbwork/anole-loader","Version: 1.2.4"); 
	}
	
}
