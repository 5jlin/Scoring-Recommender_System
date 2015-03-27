import java.io.*;
import java.util.*;
public class Recommend {
	
	private static int video = 0 ;
	private static int user = 0 ;
	static String inputFile = "d:\\transa2.txt";
	static String outputFile = "d:\\top3.txt";

	public static void main(String[] args) {
		deletefile();
		readfile();
		tran();
		likely();
		write();
	}
	
	public static void readfile(){
		try{
		Scanner scanner = new Scanner(new FileInputStream(inputFile));
		int k=0;
		while (scanner.hasNextLine()){
			String aline=scanner.nextLine();
			video=(aline.length() + 1) / 2;
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
	}
	
	
	public static int[][] tran(){ //.txt to int
		String[][]transa2=new String[user][video];
		int[][]transa=new int[user][video];
	//	transa2 = readfile();
	//	System.out.println("ABC"+user+" "+video);
		  try{
	    		Scanner scanner2 = new Scanner(new FileInputStream(inputFile));
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
		transa = tran();
		double[]average=new double[user];
		double total = 0.0;
		int count2 = 0;
		
		for(int i=0;i<user;i++){
			for(int j=0;j<video;j++){
			if(transa[i][j] > 0) {
				total = total + transa[i][j];
				count2++;
			}
			average [i] = total / count2 ;		
			}
			total = 0.0;
			count2 = 0;
		}
		

		return average;
	}
	
	public static double[][] likely(){ //算相似度
		double[][]like=new double[user][user];
		double []average = new double[user];
		int[][]transa=new int[user][video];
		average = avg();
		transa = tran();
		double []temX = new double[video];
		double []temY = new double[video];
		double []temXY = new double[video];
	//	double a = 0;
		System.out.println("AAA");
		for(int i=0;i<user;i++){
			System.out.print(average[i] + " ");
		}
		System.out.println();
		for(int k=0;k<video;k++){
			temX[k] = 0;
			temY[k] = 0;
			temXY[k] = 0;
		}
		for(int i=0;i<user;i++){
			for(int j=0;j<user;j++){
				if(j > i){
					for(int k=0;k<video;k++){
						if(transa[i][k] > 0 && transa[j][k] > 0){
							temX[k] = transa[i][k] - average[i];
					//		System.out.println(temX[k]);
							temY[k] = transa[j][k] - average[j];
					//		System.out.println(temY[k]);
							temXY[k] = temX[k] * temY[k];
					//		System.out.println(temXY[k]);			
						}				
					}
				
					if(Math.sqrt(add(temX)) ==0 || Math.sqrt(add(temY)) == 0)
						like[i][j] = 0;
					else
						like[i][j] = add(temXY) /( Math.sqrt(add(temX)*add(temX)) * Math.sqrt(add(temY)*add(temY)) ) ;
				}
				
				for(int k=0;k<video;k++){
					temX[k] = 0;
					temY[k] = 0;
					temXY[k] = 0;
				}
			}
		}
		
		for(int i=0;i<user;i++){
			for(int j=0;j<user;j++){
				if(i > j){
					like[i][j] = like[j][i];
				}
				else if(i == j) like[i][j] = -1000;
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
	
	public static void write(){
		double[][]like=new double[user][user];
		double[][]likesort=new double[user][user] ;
		double[]forsort=new double[user];
		int a = 0,b = 0,c = 0;
		
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
					if(likesort[i][user-1] == like[i][j] && j != i){
						a = j;
					}
				}
				for(int j=0;j<user;j++){
					if(likesort[i][user-2] == like[i][j] && j != a && j != i){
						b = j;
					}
				}
				for(int j=0;j<user;j++){
					if(likesort[i][user-3] == like[i][j] && j != a && j !=b && j != i){
						c = j;
					}
				}
			
			writer.print((a+1)+","+(b+1)+","+(c+1));
			writer.println();
			}
			
			writer.flush();
			writer.close();
			a = 0;
			b = 0;
			c = 0;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				}
		
	}

}
