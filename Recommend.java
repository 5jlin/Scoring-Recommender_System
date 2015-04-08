import java.io.*;
import java.util.*;
public class Recommend {
	
	private static int video = 0 ;
	private static int user = 0 ;
	static String inputFile = "d:\\transa.txt";
	static String outputFile = "d:\\top3.txt";
	static String retransaFile = "d:\\retransa.txt";

	public static void main(String[] args) {
		deletefile();
		deletefile2();
		readfile();
		tran(inputFile);
		changefile();
		changesize();
		write();
	}
	
	public static void changefile(){
		int[][]transa=new int[user][video];
		transa = tran(inputFile);		
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(retransaFile, true));
			for(int j=0;j<video;j++){
				for(int k=0;k<user;k++){
					writer.print(transa[k][j]);
					if(k!=user-1) writer.print(" ");
				}
				writer.println();
			}
			
			writer.flush();
			writer.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}	
	}
	
	public static void changesize(){ //user<->video
		int temp = 0;
		temp = user;
		user = video;
		video = temp;
	}
	
	public static void readfile(){
		try{
		Scanner scanner = new Scanner(new FileInputStream(inputFile));
		int k=0;
		while (scanner.hasNextLine()){
			String aline=scanner.nextLine();
			video=(aline.length() + 1) / 2;
		//	video=aline.length();
			user++;
		
		}
		}
		catch(Exception e)
        {
            System.out.println(e);
        }
		String[][]transa=new String[user][video];
		System.out.println(user+" "+video);
		try{
			Scanner scanner = new Scanner(new FileInputStream(inputFile));
			int k=0;
			while (scanner.hasNextLine()){
				String aline=scanner.nextLine();
				for(int j=0;j<2*video;j+=2){
					String str = aline.substring(j,j+1);
					transa[k][j/2]=str;	
				}
				k++;
			}
			}
			catch(Exception e)
	        {
	            System.out.println(e);
	        }
		
		for(int j=0;j<user;j++){
			for(int k=0;k<video;k++){
				System.out.print(transa[j][k]);
			}
			System.out.println();
		}
	//	return transa;
	}
	
	
	public static int[][] tran(String file){ //.txt to int
		String[][]transa2=new String[user][video];
		int[][]transa=new int[user][video];
		  try{
	    		Scanner scanner2 = new Scanner(new FileInputStream(file));
	    		int count=0;
	                    while (scanner2.hasNextLine()){
							String aline=scanner2.nextLine();
	                   		for(int j=0;j<video*2;j+=2){
								String cut = aline.substring(j,j+1);
								transa2[count][j/2]=cut;
	    			
								}
							count++;	
						}
	                    }
	        catch(Exception e)
	            {
	                System.out.println(e);
	            }
		
		for(int i=0;i<user;i++){
			for(int j=0;j<video;j++){
				int a=Integer.parseInt(transa2[i][j]);
				transa[i][j]=a;
			}
		}
		
		return transa ;

	}
	
	public static double add(double[] array){
		double sum = 0;
		for (int i = 0; i < array.length; ++i){
			sum = sum + array[i];
		}
		return sum;
	}
	

	public static double[] avg(){ //couculate average
		int[][]transa=new int[user][video];
		transa = tran(retransaFile);
		double[]average=new double[user];
		double total = 0.0;
		int count2 = 0;
		
		for(int i=0;i<user;i++){
			for(int j=0;j<video;j++){
			if(transa[i][j] > 0) {
				total = total + transa[i][j];
				count2++;
			}
			 if(count2 > 0)
                 average[i] = total / count2 ;
         else
                 average[i] = 0;
	
			}
			total = 0.0;
			count2 = 0;
		}
		
	/*	for(int j=0;j<user;j++){
			System.out.print(average[j] + " ");
		}*/
		return average;
	}
	
	public static double[][] likely(){ //算相似度
		double[][]like=new double[user][user];
		double []average = new double[user];
		int[][]transa=new int[user][video];
		average = avg();
		transa = tran(retransaFile);
		double []temX = new double[video];
		double []temY = new double[video];
		double []temXY = new double[video];
	//	double a = 0;
		double sum1 = 0;
		double sum2 = 0;
	//	double sum3 = 0;
		System.out.println("AAA");
		for(int i=0;i<user;i++){
			System.out.print(average[i] + " ");
		}
		System.out.println();
		
		for(int i=0;i<user;i++){
			for(int j=0;j<user;j++){
				if(j > i){
					sum1 = 0;
					sum2 = 0;
				//	sum3 = 0;
					for(int k=0;k<video;k++){
						temX[k] = 0;
						temY[k] = 0;
						temXY[k] = 0;
					}
					for(int k=0;k<video;k++){
						if(transa[i][k] > 0 && transa[j][k] > 0){
							temX[k] = transa[i][k] - average[i];
							temY[k] = transa[j][k] - average[j];
		
						}
					}
					for(int k=0;k<video;k++){
						temXY[k] = temX[k] * temY[k];
				//		sum3 = sum3 + temX[k] * temY[k];
					}
				
						for(int k = 0;k<video;k++){
							sum1 = sum1 + temX[k]*temX[k];
						}
						for(int k = 0;k<video;k++){
							sum2 = sum2 + temY[k]*temY[k];
						}
				//		System.out.println("XY:"+i+" "+j+" "+sum1+" "+sum2);
						if(sum1 == 0 || sum2 == 0)		like[i][j] = 0;
						else
						//	like[i][j] = sum3 / (Math.sqrt(sum1)*Math.sqrt(sum2));
							like[i][j] = add(temXY) / (Math.sqrt(sum1)*Math.sqrt(sum2));

				}
				
			}
		}
		
		for(int i=0;i<user;i++){
			for(int j=0;j<user;j++){
				if(i > j){
					like[i][j] = like[j][i];
				}
			}
		}
	
		for(int i=0;i<user;i++){
			for(int j=0;j<user;j++){
				System.out.print(like[i][j] + "	 ");
			}
			System.out.println();
		}
//		System.out.println("WWWWWWW");
		return like;
	}
	

	
	public static void Sort(double[] array)
    {
        int j;
        double n;
        for (int i = 1; i < array.length; ++i)
        {
            n = array[i];
            for (j = i - 1; j >= 0 && array[j] > n; --j)
                array[j + 1] = array[j];
            array[j + 1] = n;
        }
    } 

	private static void deletefile(){
		try{
		File file = new File(outputFile);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
		System.out.println("Delete operation is failed.");
		}
		}catch(Exception e){
		e.printStackTrace();
		}
		}
	
	private static void deletefile2(){
		try{
		File file = new File(retransaFile);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
		System.out.println("Delete operation is failed.");
		}
		}catch(Exception e){
		e.printStackTrace();
		}
		}
	
	public static void write(){
		double[][]like=new double[user][user];
		double[][]likesort=new double[user][user] ;
		double[]forsort=new double[user];
		int a = -1,b = -1,c = -1;
		
		like = likely();

		for(int i=0;i<user;i++){
			for(int j=0;j<user;j++){
				forsort[j] = like[i][j];	
			}
			Sort(forsort);
			for(int j=0;j<user;j++){
				likesort[i][j] = forsort[j];
			}
	//		System.out.println();
		}
		System.out.println();
			for(int i=0;i<user;i++){
				for(int j=0;j<user;j++){
					System.out.print(likesort[i][j] + " ");
				}
				System.out.println();
			}
			
			try {
				PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile,true));
				
			for(int i=0;i<user;i++){
				writer.print((i+1)+":");
				for(int j=0;j<user;j++){
					if(likesort[i][user-1] == like[i][j] && j != i && likesort[i][user-1] > 0){
						a = j;
					}
				}
				for(int j=0;j<user;j++){
					if(likesort[i][user-2] == like[i][j] && j != a && j != i && likesort[i][user-2] > 0){
						b = j;
					}
				}
				for(int j=0;j<user;j++){
					if(likesort[i][user-3] == like[i][j] && j != a && j !=b && j != i && likesort[i][user-3] > 0){
						c = j;
					}
				}
					
				if(a>0) writer.print((a+1)+",");
				if(b>0) writer.print((b+1)+",");
				if(c>0) writer.print((c+1)+",");
				//writer.print((a+1)+","+(b+1)+","+(c+1));
				a = -1;
				b = -1;
				c = -1;

			writer.println();
			}
			
			writer.flush();
			writer.close();
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				}
		
	}
	

}
