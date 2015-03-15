import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Board 
{
	public static String WHITE="W";
	public static String BLACK="B";
	public static String CAPTURED="-";
	public static String FREE="+";
	String inputFileName;
	int boardDimension=0;
	int freeCell=0;
	int whiteCaptured=0;
	int blackCaptured=0;
	String[][] boardBody=null;
	Board()
	{
		doParseInput();
	}

	public void initializeBoardBody(int boardDimension)
	{
		boardBody=new String[boardDimension][boardDimension];
		
	}
	
	public String getBoardCell(int x,int y)
	{
		return boardBody[x][y];
	}
	
	public void setBoardCell(int x,int y,String sign)
	{
		boardBody[x][y]=sign;
	}
	
	public void checkxNumber(int xCount)
	{
		if (xCount==boardDimension)
		{
			return;
		}
		
		else
		{
			System.out.println ("X.X ---> Error:Actual xs and Board Dimension Mismatched.");
			System.exit(0);
		}
		
	}
	
	
	public void doParseInput()
	{
		//first line is dimension
		int xCount=-1;
		
		ArrayList<String> tempStringArray=new ArrayList<String>();
		String[] parts=null;
		//transform everything into a 1d array .
		try
		{
	         // FileReader inputFile = new FileReader("file name");
	          BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
	          String line;
	          //add sign into a string array by split each line with delimit ' '
	          while ((line = bufferReader.readLine()) != null)
	          {
	            parts=line.split(" ");
	            for(int i=0 ;i<parts.length;i++)
	            {
	            	tempStringArray.add(parts[i]);
	            }
	            xCount++;
	          }

	          bufferReader.close();
	     }
		catch(Exception e)
		{
	          System.out.println("Error while reading file line by line:" + e.getMessage());                      
	    }
		
		//set Dimension to first line of input and remove it from arraylist
		boardDimension=Integer.parseInt(tempStringArray.remove(0));
		//if dimension not match exit
		System.out.println("Dimension" + boardDimension +" xCount "+xCount);
		checkxNumber(xCount);
		initializeBoardBody(boardDimension);
		fillboardBody(tempStringArray);
		
		
		

		printBoardBody(boardBody);
	}
	
	//transform 1D array of 'signs' to 2d array
	public void fillboardBody(ArrayList<String> tempStringArray)
	{
		int x=0,y=0;
		for(String tempString : tempStringArray)
		{
			boardBody[x][y++]=tempString;
			if (y>=boardDimension)
			{
				x++;
				y=0;
			}
		}
	}
	
	public void printBoardBody(String boardBody[][])
	{
		for (int i=0 ;i<boardDimension;i++)
		{
			for(int j=0;j<boardDimension;j++)
			{
				System.out.print(boardBody[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public boolean isFreeCell(int x,int y)
	{
		return boardBody[y][y].equals(FREE);
	}
	//no matter how big is the board the max surrounding is 8 which is 8 directions 
	//haha worst case is 2N^2
	//index 0 = x = x, 1= y = y
	//cycles are sorted by x first then by y , so first point is the most top left of cycle

	public void countCapturedCell(BoardDataCycleStructure cycleOne)
	{
		//assumed they are all sorted by x and y
		int numberxs=cycleOne.xPointArray[cycleOne.xPointArray.length] -cycleOne.xPointArray[0];
		//CeilingDataStructure ceiling = new CeilingDataStructure();
		while(numberxs>0)
		{
			
			numberxs--;
		}
		
		
	}
	
	//use a hash map to declear which cycle is for which player ?
	

	
}
