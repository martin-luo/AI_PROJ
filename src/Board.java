import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Board
{
	/** The word used for white player. */
	public static String WHITE = "W";
	/** The word used for black player. */
	public static String BLACK = "B";
	/** The word used for captured cell. */
	public static String CAPTURED = "-";
	/** The word used for free cell. */
	public static String FREE = "+";
	/** The dimension of the board. */
	public int boardDimension = 0;
	
	/** The number of free cell on the board. */
	int freeCell = 0;
	/** The number of cell captured by white player. */
	int whiteCaptured = 0;
	/** The number of cell captured by black player. */
	int blackCaptured = 0;
	
	/** The board. */
	public String[][] boardBody = null;
	/** Used to track point is already in one circle path or not. */
	public int[][] trackingInCircle = null;
	/** Used to store the data of circle. */
	/** Used to call the board update algorithms. */
	public BoardUpdateAlgorithm updateAlgorithm = null;
	
	/**
	 * Return the dimension of board.
	 * 
	 * @return the dimension of the board.
	 */
	public int getBoardDimension()
	{
		return boardDimension;
	}
	
	/**
	 * Set the variable boardDimension to the specified value.
	 * 
	 * @param boardDimension
	 *            the value boardDimension will be set to.
	 */
	public void setBoardDimension(int boardDimension)
	{
		this.boardDimension = boardDimension;
	}
	
	/**
	 * Return the board body.
	 * 
	 * @return the board body.
	 */
	public String[][] getBoardBody()
	{
		return boardBody;
	}
	
	/**
	 * Return the number of free cell on the board.
	 * 
	 * @return the number of free cell on the board.
	 */
	public int getFreeCell()
	{
		return freeCell;
	}
	
	/**
	 * Set the variable freeCell to the specified value.
	 * 
	 * @param freeCell
	 *            the value freeCell will be set to.
	 */
	public void setFreeCell(int freeCell)
	{
		this.freeCell = freeCell;
	}
	
	/**
	 * Return the number of cell captured by white player.
	 * 
	 * @return the number of cell captured by white player.
	 */
	public int getWhiteCaptured()
	{
		return whiteCaptured;
	}
	
	/**
	 * Set the variable whiteCaptured to the specified value.
	 * 
	 * @param whiteCaptured
	 *            the value whiteCaptured will be set to.
	 */
	public void setWhiteCaptured(int whiteCaptured)
	{
		this.whiteCaptured = whiteCaptured;
	}
	
	/**
	 * Return the number of cell captured by black player.
	 * 
	 * @return the number of cell captured by black player.
	 */
	public int getBlackCaptured()
	{
		return blackCaptured;
	}
	
	/**
	 * Set the variable blackCaptured to the specified value.
	 * 
	 * @param blackCaptured
	 *            the value blackCaptured will be set to.
	 */
	public void setBlackCaptured(int blackCaptured)
	{
		this.blackCaptured = blackCaptured;
	}
	
	/**
	 * Used to initialize the board.
	 */
	Board()
	{
		doParseInput();
	}
	
	public void setFinderAlgorithm(BoardUpdateAlgorithm updateAlgorithm)
	{
		this.updateAlgorithm = updateAlgorithm;
	}
	
	public void updateBoard()
	{
		updateAlgorithm.doUpdateBoard();
	}
	
	/**
	 * Initialize the variable boardBody to the specified value.
	 * 
	 * @param boardDimension
	 *            the value boardBody will be set to.
	 */
	public void initializeboardBody(int boardDimension)
	{
		boardBody = new String[boardDimension][boardDimension];
		
	}
	
	/**
	 * Get the cell at the specified position.
	 * 
	 * @param row
	 *            the row the required cell is in.
	 * @param col
	 *            the column the required cell is in.
	 * 
	 * @return the cell at the specified position.
	 */
	public String getBoardCell(int row, int col)
	{
		return boardBody[row][col];
	}
	
	/**
	 * Set the specified cell on the board to the specified value.
	 * 
	 * @param row
	 *            the row the target cell is in.
	 * @param col
	 *            the column the target cell is in.
	 * @param sign
	 *            the value the target cell will be set to.
	 * 
	 */
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
			System.out.println("X.X ---> Error:Actual ys and Board Dimension Mismatched.");
			System.exit(0);
		}
		
	}
	
	/**
	 * Validates a cell on the board
	 *
	 * Check if the cell is one the the four types: Black, White, Captured, Free
	 *
	 * @param cell
	 *            the element to be validated.
	 * @return true if the cell is valid, otherwise false
	 */
	public boolean checkCellValidation(String cell)
	{
		if (cell.equals(BLACK))
		{
			return true;
		}
		else if (cell.equals(WHITE))
		{
			return true;
		}
		else if (cell.equals(CAPTURED))
		{
			return true;
		}
		else if (cell.equals(FREE))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void doParseInput()
	{

		int rowCount = 0;
		int colCount = 0;
		
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
			// System.out.println("boardDimension------ :  " + getBoardDimension());
			
			// Add sign into a string array by split each line with delimit ' '
			while (!(line = bufferReader.readLine()).equals(""))
			{
				

				line=line.replace(" ","").replace("\t","");
				parts = line.split("");
				colCount=parts.length-1;
				// If it's the first line of the board, make the prevYCount
				// equal to yCount.
				checkxNumber(colCount);
				//System.out.println("" + line);
				//need to skip first one ,split("") first one is ""
				for (int i = 1; i < parts.length; i++)
				{
					//System.out.println("i: "+i+" sign : "+parts[i]+"<-" +"level : "+tempStringArray.size());
					// If the element is valid, add it to the temporary array,
					// otherwise notify the user then exit.
					if (checkCellValidation(parts[i]))
					{
						tempStringArray.add(parts[i]);
					}
					else
					{
						System.out.println("X.X ---> Error:Unexpected types on position (" + (i-1) + "," + rowCount + "). sign : " + parts[i]+"<-");
						System.out.println("Please make sure the input contains only these four types: ");
						System.out.println("\"" + BLACK + "\" \"" + WHITE + "\" \"" + CAPTURED + "\" \"" + FREE + "\"");
						
						System.exit(0);
					}
				}
				rowCount++;
			}
			
			bufferReader.close();
		}
		catch (Exception e)
		{
			
			System.out.println("Error while reading file line by line Check your Input Format" );
			System.exit(0);
		}
		
		// System.out.println("Dimension" + boardDimension + " xCount " +
		// xCount);
		// If dimension not match, exit.
		
		// If the size of rows does not match dimension, exit.
		checkyNumber(rowCount);
		initializeboardBody(boardDimension);
		fillboardBody(tempStringArray);
		// printboardBody(boardBody);
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
		if (freeCell != 0)
		{
			System.out.println("None");
		}
		else if (whiteCaptured > blackCaptured)
		{
			System.out.println("White");
		}
		else if (blackCaptured > whiteCaptured)
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
