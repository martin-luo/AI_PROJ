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

	@SuppressWarnings("unchecked")
	public int countCapturedCell(BoardDataCycleStructure cycleOne)
	{
		//assumed they are all sorted by x and y
		int numberOflevel=cycleOne.cycleLevel.length;
		//CeilingDataStructure ceiling = new CeilingDataStructure();
		ArrayList<Integer> ceilingX = new ArrayList<Integer>();
		ArrayList<Integer> ceilingY = new ArrayList<Integer>();
		ArrayList<Integer> tempCeilingX = new ArrayList<Integer>();
		ArrayList<Integer> tempCeilingY = new ArrayList<Integer>();
		int minX=0;
		int minY=0;
		int maxX=0;
		int maxY=0;
		int capturedNumber=0;
		//first level.
		AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[0][0],ceilingX);
		AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[0][1],ceilingY);
		
		//start from second level
		for(int i=1;i<numberOflevel;i++)
		{
			//assuming sorted properly
			AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[i][0],tempCeilingX);
			AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[i][1],tempCeilingY);
			//same level if it is sorted by y and x , lowest x and y will be on left most 
			//and vise versa
			//temp ceiling is for current level , we use previse level ceiling to count captured
			minX=tempCeilingX.get(0);
			maxX=tempCeilingX.get(tempCeilingX.size()-1);
			minY=tempCeilingY.get(0);
			maxY=tempCeilingY.get(tempCeilingY.size()-1);
			//moving horizontally at this level
			for (int moveX=minX,moveY =minY;moveX<maxX&&moveY<maxY;moveX++,moveY++)
			{
				if (checkCellCaptured(moveX,boardBody[moveX][moveY],cycleOne.cycleOwner,ceilingX))
				{
					tempCeilingX.add(moveX);
					tempCeilingY.add(moveY);
					capturedNumber++;
				}
			}
			ceilingX=(ArrayList<Integer>) tempCeilingX.clone();
			ceilingY=(ArrayList<Integer>) tempCeilingY.clone();
			tempCeilingX.clear();
			tempCeilingY.clear(); 
		}
		return capturedNumber;
		
		
	}
	
	//if x matched previous ceiling's x then everything is good
	//every level from cycle x should be unique ...
	public boolean checkCellCaptured(int moveX,String signOnCell,String playerSign,ArrayList<Integer>ceilingX)
	{
		//if not equal to free, or it is equal to owner's sign false
		if ((!signOnCell.equals(FREE))||(playerSign.equals(signOnCell)))
		{
			return false;
		}
		//if current cell level within circle has same x with previous ceiling X them BINGO!
		for(int oneCeilingX:ceilingX)
		{
			if(moveX==oneCeilingX)
			{
				return true;
			}
		}
		return true;
	}
	
	//remember to write 8 directions
	//use a hash map to declear which cycle is for which player ?
	

	
}
