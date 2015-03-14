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
	String[][] boardBody=null;
	Board()
	{
		doParseInput();
	}

	public void initializeBoardBody(int boardDimension)
	{
		boardBody=new String[boardDimension][boardDimension];
		
	}
	
	public String getBoardCell(int row,int col)
	{
		return boardBody[row][col];
	}
	
	public void setBoardCell(int row,int col,String sign)
	{
		boardBody[row][col]=sign;
	}
	
	public void checkRowNumber(int rowCount)
	{
		if (rowCount==boardDimension)
		{
			return;
		}
		
		else
		{
			System.out.println ("X.X ---> Error:Actual Rows and Board Dimension Mismatched.");
			System.exit(0);
		}
		
	}
	
	public void doParseInput()
	{
		int col=0,row=0,rowCount=0;
		initializeBoardBody(boardDimension);
		ArrayList<String> tempStringArray=new ArrayList<String>();
		String[] parts=null;
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
	            	rowCount++;
	            }
	          }

	          bufferReader.close();
	     }
		catch(Exception e)
		{
	          System.out.println("Error while reading file line by line:" + e.getMessage());                      
	    }
		
		checkRowNumber(rowCount);
		
		
		
		boardDimension=Integer.parseInt(tempStringArray.remove(0));
		initializeBoardBody(boardDimension);
		
		for(String tempString : tempStringArray)
		{
			boardBody[row][col++]=tempString;
			if (col>=boardDimension)
			{
				row++;
				col=0;
			}
		}
		printBoardBody(boardBody);
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
}
