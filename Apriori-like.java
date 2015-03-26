/****
Apriori-like algorithm
based on @author Nathan Magnus, under the supervision of Howard Hamilton

input file: transa.txt
output file: top3.txt
***/

import java.io.*;
import java.util.*;

public class Apriori {

    public static void main(String[] args) {
        AprioriCalculation ap = new AprioriCalculation();

        ap.aprioriProcess();
    }
}

class AprioriCalculation
{
    Vector<String> candidates=new Vector<String>(); //the current candidates
    String transaFile="transa.txt"; //transaction file
    String outputselectFile="output.txt";
    String resultFile="result.txt";
    String top3result="top3.txt";    

    int numItems; //number of items per transaction
    int numTransactions; //number of transactions
    double minSup; //minimum support for a frequent itemset
    int user=0;
    int video=0;
    int maxvideo=0;
    String oneVal[]; //array of value per column that will be treated as a '1'
    String itemSep = " "; //the separator value for items in the database


    public void aprioriProcess()
    {

        int itemsetNumber=0; //the current itemset being looked at
        int num=1;
		deletefile();   
		deletefile2();
        readsize();
        //get config
        while(num<=maxvideo){
        itemsetNumber=0;
		getConfig(num);
		//    readsize();

        System.out.println("Apriori algorithm has started.\n");

        //while not complete
        try{
        	PrintWriter writer = new PrintWriter(new FileOutputStream(resultFile,true));
			writer.print(num+" ");
        do
        {
            //increase the itemset that is being looked at
            itemsetNumber++;

	    try {
           		PrintWriter writer3 = new PrintWriter(new FileOutputStream(top3result,true));
           		if(itemsetNumber==1 ){
           			writer3.print(num+":");         			
           		}
           		writer3.flush();
           		writer3.close();
           		} catch (FileNotFoundException e) {
           		e.printStackTrace();
           		}

            //generate the candidates
            generateCandidates(itemsetNumber);
           // writer.print(num+" ");
            //determine and display frequent itemsets
            calculateFrequentItemsets(itemsetNumber,num);
            if(candidates.size()!=0)
            {
                System.out.println("Frequent " + itemsetNumber + "-itemsets");               
                if(itemsetNumber==1 ){
                	for(int i=0;i<candidates.size();i++){
                		if(Integer.parseInt(candidates.get(i))!=num)
                		
                		writer.print(candidates.get(i)+",");
                	//	   System.out.println(candidates);
                	}
                }                
                System.out.println(candidates);
    //            num++;
            }
        //if there are <=1 frequent items, then its the end. This prevents reading through the database again. When there is only one frequent itemset.
        }while(candidates.size()>1);
		writer.println();
        writer.flush();
        writer.close();
        	}
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        }
	try {
       		PrintWriter writer3 = new PrintWriter(new FileOutputStream(top3result,true));
       		writer3.println();
       		writer3.flush();
       		writer3.close();
       		} catch (FileNotFoundException e) {
       		e.printStackTrace();
       		}

        num++;
		}

    }
    
    private void readsize(){
//		int video=0;
		try{
		Scanner scanner = new Scanner(new FileInputStream(transaFile));
		while (scanner.hasNextLine()){
			String aline=scanner.nextLine();
			video=aline.length()/2 ;
	//		user++;
		}
		}
		catch(Exception e)
        {
            System.out.println(e);
        }
		maxvideo=video;

	}
    
    private void deletefile(){
	try{
	File file = new File(resultFile);
	if(file.delete()){
		System.out.println(file.getName() + " is deleted!");
	}else{
	System.out.println("Delete operation is failed.");
	}
	}catch(Exception e){
	e.printStackTrace();
	}
	}
	
	private void deletefile2(){
        try{
        File file = new File(top3result);
        if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
        }else{
        System.out.println("Delete operation is failed.");
        }
        }catch(Exception e){
        e.printStackTrace();
        }
        }


    

   
    private void getConfig(int n)
    {
        FileWriter fw;
        BufferedWriter file_out;


        int video=0,user=0;
    	try{
    		Scanner scanner = new Scanner(new FileInputStream(transaFile));
    		while (scanner.hasNextLine()){
    			String aline=scanner.nextLine();
    			video=(aline.length())/2 ;
    			user++;
    		}
    		}
    		catch(Exception e)
            {
                System.out.println(e);
            }
    	
    	
    	
    	System.out.println("USER "+user+" VIDEO "+video);
    	String[][]transa2=new String[user][video];
    	try{
    		Scanner scanner2 = new Scanner(new FileInputStream(transaFile));
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
    	int a=0;
    	int[][]transa=new int[user][video];
    	for(int i=0;i<user;i++){
    		for(int j=0;j<video;j++){
    			a=Integer.parseInt(transa2[i][j]);
    			transa[i][j]=a;
    			}
    		}
    	
    	
    	try{
    	PrintWriter writer = new PrintWriter(new FileOutputStream(outputselectFile));

    	for(int i=0;i<user;i++){
    		if(transa[i][n-1]!=0){
    			for(int j=0;j<video;j++){
    		writer.print(transa[i][j]+" ");
    		}
    		writer.println();
    		}
    	}
    	writer.flush();
        writer.close();}
    	catch(Exception e) {
    	e.printStackTrace();
    	}
    	
    	user=0;
    	video=0;
    	try{
    		Scanner scanner2 = new Scanner(new FileInputStream(outputselectFile));
    		while (scanner2.hasNextLine()){
    			String aline=scanner2.nextLine();
    			video=(aline.length())/2 ;
    			user++;
    		}
    		}
    		catch(Exception e)
            {
                System.out.println(e);
            }

        try
        {

             numItems=video;

             numTransactions=user;
             minSup=3;
             minSup/=100.0;

            oneVal = new String[numItems];

                for(int i=0; i<oneVal.length; i++)
                    oneVal[i]="1";

            PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile, true));
            writer.println(numTransactions);
            writer.println(numItems);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }


    private void generateCandidates(int n)
    {
        Vector<String> tempCandidates = new Vector<String>(); //temporary candidate string vector
        String str1, str2; //strings that will be used for comparisons
        StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared

        //if its the first set, candidates are just the numbers
        if(n==1)
        {
            for(int i=1; i<=numItems; i++)
            {
                tempCandidates.add(Integer.toString(i));
            }
        }
        else if(n==2) //second itemset is just all combinations of itemset 1
        {
            //add each itemset from the previous frequent itemsets together
            for(int i=0; i<candidates.size(); i++)
            {
                st1 = new StringTokenizer(candidates.get(i));
                str1 = st1.nextToken();
                for(int j=i+1; j<candidates.size(); j++)
                {
                    st2 = new StringTokenizer(candidates.elementAt(j));
                    str2 = st2.nextToken();
                    tempCandidates.add(str1 + " " + str2);
                }
            }
        }
        else
        {
            //for each itemset
            for(int i=0; i<candidates.size(); i++)
            {
                //compare to the next itemset
                for(int j=i+1; j<candidates.size(); j++)
                {
                    //create the strigns
                    str1 = new String();
                    str2 = new String();
                    //create the tokenizers
                    st1 = new StringTokenizer(candidates.get(i));
                    st2 = new StringTokenizer(candidates.get(j));

                    //make a string of the first n-2 tokens of the strings
                    for(int s=0; s<n-2; s++)
                    {
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }

                    //if they have the same n-2 tokens, add them together
                    if(str2.compareToIgnoreCase(str1)==0)
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                }
            }
        }
        //clear the old candidates
        candidates.clear();
        //set the new ones
        candidates = new Vector<String>(tempCandidates);
        tempCandidates.clear();
    }


    private void calculateFrequentItemsets(int n,int num)
    {
        Vector<String> frequentCandidates = new Vector<String>(); //the frequent candidates for the current itemset
        FileInputStream file_in; //file input stream
        BufferedReader data_in; //data input stream

        StringTokenizer st, stFile; //tokenizer for candidate and transaction
        boolean match; //whether the transaction has all the items in an itemset
        boolean trans[] = new boolean[numItems]; //array to hold a transaction so that can be checked
        int count[] = new int[candidates.size()]; //the number of successful matches

        double anothercounter = 0;
		double[]second = new double[3];
		try
        {
                //output file
       //         fw= new FileWriter(outputFile, true);
      //          file_out = new BufferedWriter(fw);
		double[][]first=new double[2][maxvideo];
         	double[]forsort=new double[maxvideo];
        	PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile, true));
        	
                //load the transaction file
                file_in = new FileInputStream(outputselectFile);
                
                data_in = new BufferedReader(new InputStreamReader(file_in));
                
                //for each transaction
                for(int i=0; i<numTransactions; i++)
                {
                    //System.out.println("Got here " + i + " times"); //useful to debug files that you are unsure of the number of line
                    stFile = new StringTokenizer(data_in.readLine(), itemSep); //read a line from the file to the tokenizer
                    //put the contents of that line into the transaction array
                    for(int j=0; j<numItems; j++)
                    {
                       trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
                    //	trans[j]=Integer.parseInt(stFile.nextToken());
		    }

                    //check each candidate
                    for(int c=0; c<candidates.size(); c++)
                    {
                        match = false; //reset match to false
                        //tokenize the candidate so that we know what items need to be present for a match
                        anothercounter = 0;
			st = new StringTokenizer(candidates.get(c));
                        //check each item in the itemset to see if it is present in the transaction
                        while(st.hasMoreTokens())
                        {
                            match = (trans[Integer.valueOf(st.nextToken())-1]);
                           // anothercounter = (trans[Integer.valueOf(st.nextToken())-1]);
			    if(!match) //if it is not present in the transaction stop checking
                                break;
		//	    if(anothercounter==0)
                  //          	break;
                        }
                        if(match) //if at this point it is a match, increase the count
                            count[c]++;
		//	if(anothercounter>0)
                 //   	   count[c]=count[c]+anothercounter/5;
                    }

                }
                for(int i=0; i<candidates.size(); i++)
                {
                   
                    if((count[i]/(double)numTransactions)>=minSup)
                    {
                        frequentCandidates.add(candidates.get(i));
                        //put the frequent itemset into the output file
                        writer.println(candidates.get(i) + "," + count[i]/(double)numTransactions + "\n");
                        
			if(n==1){
                        first[0][i]=Double.parseDouble(candidates.get(i));
                        first[1][i]=count[i]/(double)numTransactions;
                        }	
                        
                        if(candidates.size()==2 /*&& Integer.valueOf(candidates.get(0)).intValue()!=num*/)
                        {
                        	System.out.println("final~~");
                        	System.out.println(candidates.get(i));
                        }
                    }
                }

			for(int j=0;j<maxvideo;j++){
                	if(first[0][j]==num)
                		first[1][j]=0;
                }
                
                for(int j=0;j<maxvideo;j++){
                	forsort[j] = first[1][j];
                }
                Sort(forsort);

                int a = 0,b = 0;
                	for(int j=0;j<maxvideo;j++){
                		if(first[1][j]==forsort[maxvideo-1] && first[0][j]!=num){
                			second[0]=first[0][j];
                			a=j;
                			break;
                		}
                	}
                	for(int j=0;j<maxvideo;j++){
                		if(first[1][j]==forsort[maxvideo-2] && j!=a &&first[0][j]!=num){
                			second[1]=first[0][j];
                			b=j;
                			break;
                		}
                	}
                	for(int j=0;j<maxvideo;j++){
                		if(first[1][j]==forsort[maxvideo-3] &&j!=a && j!=b && first[0][j]!=num){
                			second[2]=first[0][j];
                			break;
                		}
                	}
		
                writer.println("-\n");
                writer.flush();
                writer.close();
        }
        //if error at all in this process, catch it and print the error messate
        catch(IOException e)
        {
            System.out.println(e);
        }

	try {

   		PrintWriter writer3 = new PrintWriter(new FileOutputStream(top3result,true));
  
   		if(second[0]>0){
   			for(int i=0;i<3;i++){
   				int d = (int)second[i];
				if(d>0)
   				writer3.print( d+",");
   			}
   		}

   		writer3.flush();
   		writer3.close();
   		} catch (FileNotFoundException e) {
   		e.printStackTrace();
   		}

        //clear old candidates
        candidates.clear();
        //new candidates are the old frequent candidates
        candidates = new Vector<String>(frequentCandidates);
        frequentCandidates.clear();
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

}
