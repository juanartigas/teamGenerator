package teams;
import java.awt.FileDialog;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
public class start {
	
	static int teamNum = 0;
	static int studentNum = 0;
	static student[] myStudent = new student[100];
	final static int passCode = 1000;
	static String[] qArr = new String[10];
	static String[] qArrY = new String[10];
	static int[] matches = new int[100];
	static int matchNum = 0;
	
	static FileWriter profilesF;//stores profile data per id
	static FileWriter matchesF;//stores id's that have matched ("liked") each other
	static FileWriter matchDataF;//stores generated match coefficient of all students by pairs
	static FileWriter matchLikesF;
	
	static Scanner myKeys = new Scanner(System.in);

	public static void main(String[] args) throws IOException 
	{
		FileWriter studentF = new FileWriter("studentF");
		
		profilesF = new FileWriter("profilesF");
		matchesF = new FileWriter("matchesF");
		matchLikesF = new FileWriter("matchLikesF");
		matchDataF = new FileWriter("matchDataF");
		
		System.out.println("Type any key to start..");
		//below, on site "inp" variable replaced by txt boxes
		while(myKeys.hasNext())
		{
			String z = myKeys.next();//test, not needed; just to access loop
			
			//professor's field
			
				System.out.println();
				
				System.out.println("Are you a student or professor? ");
				String in = myKeys.next();
				
				if(in.equalsIgnoreCase("professor"))
				{
					System.out.println("Professor field below:");
					
					System.out.println("Enter number of students: ");
					studentNum = myKeys.nextInt();
						
					System.out.println("Enter number of teams: ");
					teamNum = myKeys.nextInt();
						
					//name and create students
					for(int i = studentNum; i > 0; i--)
					{
						System.out.println("Enter student's #"+i+" name:");
						String n = myKeys.next();
						myStudent[i] = new student(n);
					}

					//generate student numbers
					int ints[] = new Random().ints(100, 999).distinct().limit(studentNum).toArray();
					for(int j = studentNum; j > 0; j--)
					{
						myStudent[j].myNumber(ints[j-1]);
					}
						
					//save to file
					for(int i = studentNum; i > 0; i--)
					{
							studentF.append(myStudent[i].getName());
							studentF.append(" "+myStudent[i].getNumber());
							studentF.append("\n");
					}
					studentF.flush();
					System.out.println("Saved to studentF");
					//print
					getStudents();
						
				}//end professor
				else if(in.equalsIgnoreCase("print"))
				{
					getTeams();
					System.out.println("Teams generated!");
				}
				else if(in.equalsIgnoreCase("student"))
				{
					//student's field
					
					
					String myName = null;//reset, set null until correct id assigns name
					
					
					System.out.println();
					System.out.println("Student field below:");
					System.out.println("Enter your student id number: ");
					int idNum = 0;
					if(myKeys.hasNextInt())
					{
						idNum = myKeys.nextInt();
					}
					
					myName = getName(idNum);
					
					if(myName != null)
					{
						System.out.println("Name: "+myName);
						
						System.out.println("Would you like to complete profile?: (yes/no)");
						System.out.println("You can also test for matches (test) or for likes (like)");
						System.out.println("You can also print file with match data -");
						System.out.println("after using likes and profile questions (print)");
						
								
						String next = myKeys.next();
						if(next.equalsIgnoreCase("yes"))
						{
							questions(idNum);
						}
						
						if(next.equalsIgnoreCase("no"))
						{
							System.out.println("Your current profile is: ");
							//show profile data 
							String[] profileArr = new String[9];
							profileArr = qFileOutX(idNum);
							for(int i=0; i < 9; i++)
							{
								if(profileArr[i] != null)
								{
									System.out.print(profileArr[i]+" ");
								}
							}
						}
						if(next.equalsIgnoreCase("test"))
						{
							System.out.println("Entering test for match-c method..");
							System.out.println("Enter first ID");
							int ida = myKeys.nextInt();
							System.out.println("You entered "+ida);
							System.out.println("Enter second ID");
							int idb = myKeys.nextInt();
							System.out.println("You entered "+idb);
							
							testMatches(ida, idb);
						}
						if(next.equalsIgnoreCase("like"))
						{
							System.out.println("You've entered the likes test seccion");
							System.out.println("Below just a sample of students to pick from");
							getStudents();
							System.out.println("Enter just 3 matches for testing");
							int likeIn;
							if(myKeys.hasNextInt())
							{
								likeIn = myKeys.nextInt();
								addLikes(idNum, likeIn);
							}
							if(myKeys.hasNextInt())
							{
								likeIn = myKeys.nextInt();
								addLikes(idNum, likeIn);
							}
							if(myKeys.hasNextInt())
							{
								likeIn = myKeys.nextInt();
								addLikes(idNum, likeIn);
							}
							System.out.println("Done");
						}
						//if(next.equalsIgnoreCase("print"))
						//{
							//printMatchData();
						//}
					}else {System.out.println("Name not found");}
					
				}//end student field
				
		}//end hasNext
			
		
	}
	//stores individual likes from students towards other students
	public static void addLikes(int myId, int myLike) throws IOException
	{
		matchLikesF.append(" "+myId+" ");
		matchLikesF.append(myLike+" ");
		matchLikesF.append("\n");
		
		matchLikesF.flush();
	}
	
	public static void getStudents()
	{
		File myFile = new File("studentF");//gets file
	    Scanner fileReader = null;
	    //Try and catch method when incorrect file is picked  
	    try
	    {
	         fileReader = new Scanner(myFile);
	    }
	    //file's exception
	    catch (FileNotFoundException fnfe)//when file not selected
	    {
	         System.out.println("Could not find a file");//no file
	    }
	    
	    while(fileReader.hasNext())
	    {
	    	String sname = fileReader.next();
	    	int sid = fileReader.nextInt();
	    	System.out.println("Name: "+sname+" ID: "+sid);
	    }
	    
	}
	
	public static String getName(int num)//returns student name from id
	{
		int studentNum = num;
		String found = null;
		
	    File myFile = new File("studentF");//gets file
	    Scanner fileReader = null;
	    //Try and catch method when incorrect file is picked  
	    try
	    {
	         fileReader = new Scanner(myFile);
	    }
	    //file's exception
	    catch (FileNotFoundException fnfe)//when file not selected
	    {
	         System.out.println("Could not find a file");//no file
	    }
	    
	    while(fileReader.hasNext())
	    {
	    	
	    	String foundName = fileReader.next();
	    	int foundNum = fileReader.nextInt();
	    	
	    	if(fileReader.hasNextInt())
	    	{
	    		int matchNum = fileReader.nextInt();
	    		if(matchNum > 0)
	    		{
	    			for(int i = matchNum; i > 0; i--)
	    			{
	    				int nextMatch = fileReader.nextInt();
	    				nextMatch = matches[i];
	    			}
	    		}
	    	}
	    	
	    	if(foundNum == studentNum)
	    	{
	    		found = foundName;
	    	}
	    }
		
		return found;
	}
	
	//returns true if both inserted student id's have "liked" (matched) each other
	public static boolean getLikes(int matching, int matched)
	{
		boolean studentMatches = false;
			
		    File myFile = new File("matchLikesF");//gets file
		    Scanner fileReader = null;
		    //Try and catch method when incorrect file is picked  
		    try
		    {
		         fileReader = new Scanner(myFile);
		    }
		    //file's exception
		    catch (FileNotFoundException fnfe)//when file not selected
		    {
		         System.out.println("Could not find a file");//no file
		    }
		    
		    while(fileReader.hasNext())
		    {
		    	int foundMatching = fileReader.nextInt();
		    	int foundMatched = fileReader.nextInt();
		    	if(foundMatching == matching && foundMatched == matched)
		    	{
		    		studentMatches = true;
		    	}
		    }
		    
		    return studentMatches;
	}
	
	public static String[] qFileOutX(int id)
	{
    	String sName;
    	int sNum, sID = id;
    	
	    File myFile = new File("profilesF");//gets file
	    Scanner fileReader = null;
	    //Try and catch method when incorrect file is picked  
	    try
	    {
	         fileReader = new Scanner(myFile);
	    }
	    //file's exception
	    catch (FileNotFoundException fnfe)//when file not selected
	    {
	         System.out.println("Could not find a file");//no file
	    }

	    
	    while(fileReader.hasNext())
	    {
	    	
	    	String q1 = fileReader.next();
	    	String q2 = fileReader.next();
	    	String q3 = fileReader.next();
	    	String q4 = fileReader.next();
	    	String q5 = fileReader.next();
	    	String q6 = fileReader.next();
	    	String q7 = fileReader.next();
	    	String q8 = fileReader.next();
	    	String q9 = fileReader.next();
	    	String q10 = fileReader.next();
	    	
	    	sNum = fileReader.nextInt();
	    	
	    	if(sNum == sID)
	    	{
	    		
	    		qArr[0] = q1;
	    		qArr[1] = q2;
	    		qArr[2] = q3;
	    		qArr[3] = q4;
	    		qArr[4] = q5;
	    		qArr[5] = q6;
	    		qArr[6] = q7;
	    		qArr[7] = q8;
	    		qArr[8] = q9;
	    		qArr[9] = q10;
	    	}
	    	
	    }
		return qArr;
	}
	
	public static String[] qFileOutY(int id)
	{
    	String sName;
    	int sNum, sID = id;
    	
	    File myFile = new File("profilesF");//gets file
	    Scanner fileReader = null;
	    //Try and catch method when incorrect file is picked  
	    try
	    {
	         fileReader = new Scanner(myFile);
	    }
	    //file's exception
	    catch (FileNotFoundException fnfe)//when file not selected
	    {
	         System.out.println("Could not find a file");//no file
	    }

	    
	    while(fileReader.hasNext())
	    {
	    	
	    	String q1 = fileReader.next();
	    	String q2 = fileReader.next();
	    	String q3 = fileReader.next();
	    	String q4 = fileReader.next();
	    	String q5 = fileReader.next();
	    	String q6 = fileReader.next();
	    	String q7 = fileReader.next();
	    	String q8 = fileReader.next();
	    	String q9 = fileReader.next();
	    	String q10 = fileReader.next();
	    	
	    	sNum = fileReader.nextInt();
	    	
	    	if(sNum == sID)
	    	{
	    		
	    		qArrY[0] = q1;
	    		qArrY[1] = q2;
	    		qArrY[2] = q3;
	    		qArrY[3] = q4;
	    		qArrY[4] = q5;
	    		qArrY[5] = q6;
	    		qArrY[6] = q7;
	    		qArrY[7] = q8;
	    		qArrY[8] = q9;
	    		qArrY[9] = q10;
	    	}
	    	
	    }
		return qArrY;
	}
	
	//method below stores a student's answers to file 
	public static void qFileIn(int sID, String q1,String q2,String q3,String q4,String q5, String q6, String q7, String q8, String q9, String qAnswer) throws IOException
	{
    		
    		profilesF.append(q1);
    		profilesF.append(" "+q2);
    		profilesF.append(" "+q3);
    		profilesF.append(" "+q4);
    		profilesF.append(" "+q5);
    		profilesF.append(" "+q6);
    		profilesF.append(" "+q7);
    		profilesF.append(" "+q8);
    		profilesF.append(" "+q9);
    		profilesF.append(" "+qAnswer);
    		
    		profilesF.append(" "+sID);
    		
    		profilesF.append("\n");//next line
    		
    		//save to file
    		profilesF.flush();
    		
	}
	
	public static void questions(int id) throws IOException
	{
		String q1,q2,q3,q4,q5,q6,q7,q8,q9,qAbout;
		//mix
		System.out.println("Where is home?");
		System.out.println("a) Born and raised in Missori");
		System.out.println("b) Not far, I'm still from the Midwest");
		System.out.println("c) Elsewhere in the U.S");
		System.out.println("d) I'm from Latin America");
		System.out.println("e) I'm from Europe");
		System.out.println("f) I'm from Asia");
		System.out.println("g) I'm from Africa");
		System.out.println("h) I'm from the Middle East");
		System.out.println("i) Elsewhere");
		q1 = myKeys.next();
		//match
		System.out.println("When you were a child, what was one of your biggest fears?");  
		System.out.println("a) Darkness");
		System.out.println("b) Ghosts");
		System.out.println("c) Being alone");
		System.out.println("d) Loosing your parent/s");
		q2 = myKeys.next();
		//match
		System.out.println("What do you do when you're stressed?");
		System.out.println("a) Plan vigorously");
		System.out.println("b) Make time to talk about it");
		System.out.println("c) Take a walk, hike or workout");
		System.out.println("d) Ignore it, wait it out, and/or think on sothing else");
		q3 = myKeys.next();
		//mix
		System.out.println("What major would you like to pursue?");
		System.out.println("a) I'd like a major from the school of business");
		System.out.println("b) I'd like a major from the school of science");
		System.out.println("c) I'd like a major from the school of arts");
		System.out.println("d) I'd like a major from the school of nursing");
		System.out.println("e) I'd like a major from the school of media");
		System.out.println("f) I'd like a major from the school of humanities");
		System.out.println("g) Not sure or something else");
		q4 = myKeys.next();
		
		System.out.println("Do you play any sport for school?");
		System.out.println("a) I do not");
		System.out.println("b) I play soccer");
		System.out.println("c) I play basquetball");
		System.out.println("d) I play tennis");
		System.out.println("e) I play football");
		System.out.println("f) I play field hockey");
		System.out.println("g) I play something else");
		q5 = myKeys.next();
		
		q6="z";
		q7="z";
		q8="z";
		q9="z";
		
		System.out.println("Finally, type something about yourslef you'd like oters to see:");
		qAbout = "z";
		System.out.println("not working yet");
		
		qFileIn(id,q1,q2,q3,q4,q5,q6,q7,q8,q9,qAbout);
		
		System.out.println("test qAbout: ");
		System.out.println(qAbout);
	}
	
	
	//returns a 2D array, where each dimension is an id and their value is their match coefficient
	private static int[][] matchCoefficient()
	{
		String q1x=null,q2x=null,q3x=null,q4x=null,q5x=null,q6x=null,q7x=null,q8x=null,q9x=null;
		String[] profileArrX = new String[10];
		String q1y = null,q2y=null,q3y=null,q4y=null,q5y=null,q6y=null,q7y=null,q8y=null,q9y=null;   
		String[] profileArrY = new String[10];
		int idX = 0, idY = 0;
		int studentCount = 0;
		int[] idArr = new int[1000];
		int[][] matchData = new int[1000][1000];//stores id/its match/match value
		
	    File myFile = new File("studentF");//gets file
	    Scanner fileReader = null;
	    
	    //Try and catch method when incorrect file is picked  
	    try
	    {
	         fileReader = new Scanner(myFile);
	    }
	    //file's exception
	    catch (FileNotFoundException fnfe)//when file not selected
	    {
	         System.out.println("Could not find a file");//no file
	    }
		
	    while(fileReader.hasNext())
	    {
	    	String name = fileReader.next();
	    	int id = fileReader.nextInt();
	    	idArr[studentCount++] = id;//creates student array, storing all id's
	    }
	    
	    //studentNum and studentCount should be the same, one assigned at beginning the other in this method
	    for(int x = studentCount; x >= 0; x--)//"x-loop"
	    {
	    	
	    	for(int y = studentCount - 1; y >= 0; y--)//"y-loop"
	    	{
	    	
	    		idX = idArr[x];//same id for every y loop
	    		idY = idArr[y];//different id every x and y loop run
	    			
	    		profileArrX = qFileOutX(idX);//stores student 'x' profile array
	    			
	    		profileArrY = qFileOutY(idY);//stores student 'y' profile array
	    		if(idX != idY && idX != 0 && idY != 0)
	    		{
	    		
	    			q1x = profileArrX[0];
	    			q2x = profileArrX[1];
	    			q3x = profileArrX[2];
	    			q4x = profileArrX[3];
	    			q5x = profileArrX[4];
	    			q6x = profileArrX[5];
	    			q7x = profileArrX[6];
	    			q8x = profileArrX[7];
	    			q9x = profileArrX[8];

	    			q1y = profileArrY[0];
	    			q2y = profileArrY[1];
	    			q3y = profileArrY[2];
	    			q4y = profileArrY[3];
	    			q5y = profileArrY[4];
	    			q6y = profileArrY[5];
	    			q7y = profileArrY[6];
	    			q8y = profileArrY[7];
	    			q9y = profileArrY[8];
			
			
	    		int m = 0;//integer holding match potential
				
				if(q1x != null && q1y != null)
				{
					if(q1x.equalsIgnoreCase(q1y))
					{
						m++;
					}
				}
				if(q2x != null && q2y != null)
				{
					if(q1x.equalsIgnoreCase(q2y))
					{
						m++;
					}
				}
				if(q3x != null && q3y != null)
				{
					if(q3x.equalsIgnoreCase(q3y))
					{
						m++;
					}
				}
				if(q4x != null && q4y != null)
				{
					if(q4x.equalsIgnoreCase(q4y))
					{
						m++;
					}
				}
				if(q5x != null && q5y != null)
				{
					if(q5x.equalsIgnoreCase(q5y))
					{
						m++;
					}
				}
				if(q6x != null && q6y != null)
				{
					if(q6x.equalsIgnoreCase(q6y))
					{
						m++;
					}
				}
				if(q7x != null && q7y != null)
				{
					if(q7x.equalsIgnoreCase(q7y))
					{
						m++;
					}
				}
				if(q8x != null && q8y != null)
				{
					if(q8x.equalsIgnoreCase(q8y))
					{
						m++;
					}
				}
				if(q9x != null && q9y != null)
				{
					if(q9x.equalsIgnoreCase(q9y))
					{
						m++;
					}
				}
				
						//if selected students have liked each other add 3 extra points 
						if(getLikes(idX, idY) == true)
						{
							m += 3;
						}
						
						matchData[idX][idY] = m;
				
			    
	    	  }//end if idX != idY method
	    	
	    	
	    	}//end second nested loop (y)
		
	    }//end first nested loop (x)
		
	    return matchData;
	    
	}//end of match method
	
	//test hand-picked student id's matches within 2d array
	private static void testMatches(int idA, int idB)
	{
		int idC;
		matchCoefficient();
		int[][] myMatchT = matchCoefficient();
		idC = myMatchT[idA][idB] + myMatchT[idB][idA];
		System.out.println("My match coefficient is: "+idC);
	}
	private static String makeTeams()
	{
		String team = null;
		
		
		
		return team;
	}
	
	
	//method not used, returns number of "types" within a file
	public static void getTeams() throws IOException//returns number of inserted type
	{
		int[][] matchC = matchCoefficient();//call and store 2d array of all matches
		int t = teamNum;
		
		for(int compare = 25; compare > 0; compare--)
		{
			//nested loop extracts data from 2d array 
			for(int ix = 100; ix < 1000; ix++)
	    	{
	    		for(int iy = 101; iy < 1000; iy++)
	    		{
	    			if(matchC[ix][iy] == compare)
					{	
	    				int ic = matchC[ix][iy];
	    				
	    				if(matchC[iy][ix] > 0)//when students like each other										
	    				{
	    					ic += matchC[iy][ix];
	    					matchC[iy][ix] = 0;//reset field
	    				}
	    				
	    					if(t == 0)//team number generator
	    					{
	    						t = teamNum;
	    					}
	    					
	    					matchesF.append("Student: "+ix);
	    					matchesF.append("  :  with student: "+iy);
	    					matchesF.append("  match coefficient is: "+ic);
	    					matchesF.append("  ===> Team  # "+t);
	    					matchesF.append("\n");
	    					
	    					t--;
					}
	    		}
	    	}
	    
		}
	    
	    
	    matchesF.flush();
	    
	}//end of method
	
	
	
	
	
	
	
}
