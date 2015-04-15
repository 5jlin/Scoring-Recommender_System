#Scoring and Recommender_System

Author: Alexpon, 5jlin


Architecture:

Scoring part: all jsp files in the folder named jsp
Recommender part: java files
Connection part: a shell script file


Scoring part:

Using five stars pics to represent scoring1-5

The number of lighting star is the score you will give. If you click your mouse, the 	score you give will be push to the database and a file, trans.txt. Then call the shell 	script, exec.sh, simultaneously. So we can get the newest recommend video. 		Delete and upload video is similar.

Recommender part:

Apriori-like.java is the algorithm modified by Apriori

Some of them was based on @author Nathan Magnus, under the supervision of 		Howard Hamilton Copyright: University of Regina, Nathan Magnus and Su Yibin, 	June 2009.

It's item based

Input file : transa.txt

Output file : top3.txt will tell you the top 3 related item of each item

Recommend.java is the algorithm which can recommend nonboolean data
it based on Correlation coefficient ,also item based

Input file : transa.txt

Output file : top3.txt will tell you the top 3 related item of each item
