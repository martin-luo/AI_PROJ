import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Board
{
	public static String WHITE = "W";
	public static String BLACK = "B";
	public static String CAPTURED = "-";
	public static String FREE = "+";
	//String inputFileName;
	public int boardDimension = 0;

	int freeCell = 0;
	int whiteCaptured = 0;
	int blackCaptured = 0;
	// used to keep tracking of point which is in circle 0 === not in circle ,
	// 1== in circle
	//public int[][] positionInCircle = null;
	// remember boardBodu[y][x]
	public String[][] boardBody = null;
	// tracking point is already in one circle path or not.
	public int[][] trackingInCircle = null;
	// public int [][]trackingVistiedBoard=null;
	ArrayList<BoardDataCircleStructure> collectionOfCircle = null;
	public BoardUpdateAlgorithm updateAlgorithm = null;
	
	
	public int getBoardDimension() 
	{
		return boardDimension;
	}

	public void setBoardDimension(int boardDimension) 
	{
		this.boardDimension = boardDimension;
	}
	public String[][] getBoardBody()
	{
		return boardBody;
	}
	
	public int getFreeCell() 
	{
		return freeCell;
	}

	public void setFreeCell(int freeCell) 
	{
		this.freeCell = freeCell;
	}

	public int getWhiteCaptured() 
	{
		return whiteCaptured;
	}

	public void setWhiteCaptured(int whiteCaptured) 
	{
		this.whiteCaptured = whiteCaptured;
	}

	public int getBlackCaptured()
	{
		return blackCaptured;
	}

	public void setBlackCaptured(int blackCaptured) 
	{
		this.blackCaptured = blackCaptured;
	}

	
	Board()
	{
		//positionInCircle = initialize2Darray(0);
		doParseInput();
		//updateAlgorithm=new SimpleBoardCountingAlgorithm(this);
		updateAlgorithm=new FindCircleAndCapturedCellAlgorithm(this);
		updateAlgorithm.doUpdateBoard();
		doOutput();
	}
	
	public void initializeboardBody(int boardDimension)
	{
		boardBody = new String[boardDimension][boardDimension];
		
	}
	
	public String getBoardCell(int row, int col)
	{
		return boardBody[row][col];
	}
	
	public void setBoardCell(int row, int col, String sign)
	{
		boardBody[row][col] = sign;
	}
	
	public void checkxNumber(int xCount)
	{
		if (xCount == boardDimension)
		{
			return;
		}
		
		else
		{
			System.out.println("X.X ---> Error:Actual xs and Board Dimension Mismatched.");
			System.exit(0);
		}
		
	}
	
	public void checkyNumber(int yCount)
	{
		if (yCount == boardDimension)
		{
			return;
		}
		
		else
		{
			System.out
					.println("X.X ---> Error:Actual ys and Board Dimension Mismatched.");
			System.exit(0);
		}
		
	}
	
	public void doParseInput()
	{
		// The size of table row.
		int xCount = 0;
		// The size of table column.
		int yCount = 0;
		// Used check whether each line contains the same number of cell.
		int prevYCount = 0;
		
		ArrayList<String> tempStringArray = new ArrayList<String>();
		String[] parts = null; // Transform everything into a 1d array .
		try
		{
			// FileReader inputFile = new FileReader("file name");
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
			String line;
			
			// Since the first line is the dimension of the board, read it
			// separately and assign it to the corresponding variable.
			line = bufferReader.readLine();
			boardDimension = Integer.parseInt(line);
			System.out.println("boardDimension------ :  "+getBoardDimension());
			// Add sign into a string array by split each line with delimit ' '
			while ((line = bufferReader.readLine()) != null)
			{
				// If it's not the first line of the board, make the prevYCount
				// equal to yCount.
				if (xCount != 0)
				{
					prevYCount = yCount;
				}
				parts = line.split(" ");
				yCount = parts.length;
				
				// If it's the first line of the board, make the prevYCount
				// equal to yCount.
				if (xCount == 0)
				{
					prevYCount = yCount;
				}
				System.out.println("" + line);
				for (int i = 0; i < parts.length; i++)
				{
					tempStringArray.add(parts[i]);
				}
				xCount++;
				if (prevYCount != yCount)
				{
					System.out.println("X.X ---> Error:Different size for each row.");
					System.exit(0);
				}
			}
			
			bufferReader.close();
		}
		catch (Exception e)
		{
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
		
		//System.out.println("Dimension" + boardDimension + " xCount " + xCount);
		// If dimension not match, exit.
		checkxNumber(xCount);
		// If the size of each row does not match dimension, exit.
		checkyNumber(yCount);
		initializeboardBody(boardDimension);
		fillboardBody(tempStringArray);
		//printboardBody(boardBody);
	}
	
	// remembe bo
	// transform 1D array of 'signs' to 2d array
	public void fillboardBody(ArrayList<String> tempStringArray)
	{
		int x = 0, y = 0;
		for (String tempString : tempStringArray)
		{
			boardBody[y][x++] = tempString;
			if (x >= boardDimension)
			{
				y++;
				x = 0;
			}
		}
	}
	
	public void printboardBody(String boardBody[][])
	{
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i + ")");
			}
			System.out.println();
		}
	}
	
	public boolean isFreeCell(int y, int x)
	{
		return boardBody[y][x].equals(FREE);
	}
	
	public boolean isCapturedCell(int y, int x)
	{
		return boardBody[y][x].equals(CAPTURED);
	}
	
	public boolean isBlackCell(int y, int x)
	{
		return boardBody[y][x].equals(BLACK);
	}
	
	public boolean isWhiteCell(int y, int x)
	{
		return boardBody[y][x].equals(WHITE);
	}
	
	// //////////////////////////find circle
	// Algorithm/////////////////////////////////////

	public void doOutput()
	{
		if(freeCell!=0)
		{
			System.out.println("None");
		}
		else if(whiteCaptured>blackCaptured)
		{
			System.out.println("White");
		}
		else if(blackCaptured>whiteCaptured)
		{
			System.out.println("Black");
		}
		else
		{
			System.out.println("Draw");
		}
		System.out.println(whiteCaptured);
		System.out.println(blackCaptured);
	}
}
