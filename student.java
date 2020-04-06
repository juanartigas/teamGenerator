package teams;

public class student {
	
	private String myName;
	private int myNumber, matchNum;
	private int[] myMatches = new int[1000];
	
	public student(String name) 
	{
		this.myName = name;
	}
	
	public int getMatches(int i)
	{
		return myMatches[i];
	}
	public int myMatches()
	{
		return matchNum;
	}
	public void match(int matchedID)
	{
		myMatches[matchNum++] = matchedID;
	}
	public void myNumber(int num)
	{
		this.myNumber = num;
	}
	public int getNumber()
	{
		return myNumber;
	}
	public String getName()
	{
		return myName;
	}
	
	

}
