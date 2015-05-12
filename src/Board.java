/**
 * This is a part of the project of COMP30024 Artificial Intelligence, the University of Melbourne. The project is the Game of Squatter and is a group work, the members of the group is list below, so is the rule of the game.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * <b>Class Declaration</b>
 * <p>
 * This class contains the methods used to build a board and to update the board.
 * <p>
 * <b>Rules of Boardgame</b>
 * <ul>
 * <li>Only two players , one is called '<i>WHITE</i>' ,the Other one is '<i>BLACK</i>'</li>
 * <li>Board has a size of N*N, which N is greater than 5 (i.e. N > 5)</li>
 * <li>Top left corner is (0,0), Bottom right coner is (N-1,N-1)</li>
 * <li>Edges does not count as part of captured territory</li>
 * <ul>
 * <li>Only free cells and opponent's cells count as captured cell</li>
 * <li>Pieces can't be placed in</li>
 * </ul>
 * <li>Board is read from stdin (i.e. java Main < input)</li> </ul>
 * <p>
 * 
 * @author Bingfeng Liu (bingfengl)
 * @author An Luo (aluo1)
 * @version 2.0
 * @since 2015-03-14
 */
public class Board
{
	public static String CAPTUREDLETTER="C";
	public static String WHITECAPTURED="WC";
	public static String BLACKCAPTURED="BC";
	/** The word used for white player. */
	public static String WHITE = "W";
	/** The word used for black player. */
	public static String BLACK = "B";
	/** The word used for captured cell. */
	public static String CAPTURED = "-";
	/** The word used for free cell. */
	public static String FREE = "+";
	/** The dimension of the board. */
	private int boardDimension = 0;
	
	/** The number of free cell on the board. */
	public int freeCell = 0;
	public int whiteCell=0;
	public int blackCell=0;
	/** The number of cell captured by white player. */
	public int whiteCaptured = 0;
	/** The number of cell captured by black player. */
	public int blackCaptured = 0;
	
	/** The board. */
	public String[][] boardBody = null;
	/** Used to track point is already in one circle path or not. */
	public int[][] trackingInCircle = null;
	/** Used to call the board update algorithms. */
	public BoardUpdateAlgorithm updateAlgorithm = null;
	public ArrayList<int[]> possibleMove=null;
	
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
	
	Board(int n)
	{
		
		
		boardDimension=n;
		initializeboardBody(boardDimension);
		makeAllFreeBoardBody(boardDimension);
		
		
	}
	//copy a board
	Board(Board oneBoard)
	{
		boardDimension = oneBoard.boardDimension;
		freeCell = oneBoard.freeCell;
		whiteCell= oneBoard.whiteCell;
		blackCell= oneBoard.blackCell;
		whiteCaptured = oneBoard.whiteCaptured;
		blackCaptured = oneBoard.blackCaptured;
		boardBody =  copyBoard(oneBoard.boardBody);
		//System.out.println("in here awedae");
		//printboardBody(boardBody);
		//trackingInCircle = oneBoard.trackingInCircle.clone();
		updateAlgorithm = new FindCircleAndCapturedCellAlgorithm(this);
		if(oneBoard.possibleMove!=null)
		possibleMove = (ArrayList<int[]>) oneBoard.possibleMove.clone();
		updateAlgorithm.doUpdateBoard();
		
	}
	
	public String[][] copyBoard(String[][] in)
	{
		String[][] temp=new String[boardDimension][boardDimension];
		for(int i=0;i<boardDimension;i++)
		{
			for(int j=0;j<boardDimension;j++)
			{
				temp[i][j]=new String(in[i][j]);
				//System.out.println("temp"+temp[i][j]);
			}
		}
		
		return temp;
	}
	
	public ArrayList<int[]> getDummyFreeMove()
	{
		ArrayList<int[]> temp=new ArrayList<int[]>(); ;
		for(int row=0;row<boardDimension;row++)
		{
			for(int col=0;col<boardDimension;col++)
			{
				if(isFreeCell(row,col))
				{
					temp.add(new int[]{row,col});
				}
			}
		
		}
		possibleMove = temp;
		return temp;
	}
	
	public int[] getOnePossibleMove()
	{
		Random rand = new Random();
		int  n = rand.nextInt(100000) + 1;
		int[] temp=null;
		if (possibleMove!=null && possibleMove.size()!=0)
		{
			//so far pick a random one .
			temp=possibleMove.remove(n%possibleMove.size());
		}
		return temp;
	}

	
	
	
	/**
	 * Choose the wanted board update algorithm.
	 * 
	 * @param updateAlgorithm
	 *            the algorithm the board game will use.
	 */
	public void setFinderAlgorithm(BoardUpdateAlgorithm updateAlgorithm)
	{
		this.updateAlgorithm = updateAlgorithm;
	}
	
	/**
	 * Use the algorithm to update the state of board.
	 */
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
	
	//making free board body
	public void makeAllFreeBoardBody(int boardDimension)
	{
		for(int row=0;row<boardDimension;row++)
		{
			for(int col=0;col<boardDimension;col++)
			{
				boardBody[row][col]=FREE;
			}
		}
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
	
	/**
	 * Check whether the number of row is the same as the dimension specified.
	 * 
	 * If the number of row is different from the dimension whose value is got from the first row of input file, notify the user then exit.
	 * 
	 * @param xCount
	 *            the number of row
	 */
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
	
	/**
	 * Check whether the number of cell in each row is the same as the dimension specified.
	 * 
	 * If the number of cell of each row is different from the dimension whose value is got from the first row of input, notify the user then exit.
	 * 
	 * @param yCount
	 *            the number of cell of row.
	 */
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
	
	/**
	 * Read the input from standard input and put the satisfied value into an arraylist then add it to the boardBody.
	 */
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
			try
			{
				boardDimension = Integer.parseInt(line);
			}
			catch(Exception e)
			{
				System.out.println("Error while reading file line by line Check your Input Format");
				System.exit(0);
			}
			//System.out.println("boardDimension------ :  " + getBoardDimension());
			
			// Add sign into a string array by split each line with delimit ' '
			while (!(line = bufferReader.readLine()).equals(""))
			{
				
				line = line.replace(" ", "").replace("\t", "");
				parts = line.split("");
				//System.out.println("part[0]"+parts[0]);
				//take of -1 for submittsion
				colCount = parts.length;
				//System.out.println("colCount : " + colCount);
				// If it's the first line of the board, make the prevYCount
				// equal to yCount.
				checkxNumber(colCount);
				//System.out.println("" + line);
				// need to skip first one ,split("") first one is ""
				//i=0 for submitsion 
				for (int i = 0; i < parts.length; i++)
				{
					// System.out.println("i: "+i+" sign : "+parts[i]+"<-" +"level : "+tempStringArray.size());
					// If the element is valid, add it to the temporary array,
					// otherwise notify the user then exit.
					if (checkCellValidation(parts[i]))
					{
						tempStringArray.add(parts[i]);
					}
					else
					{
						System.out.println("X.X ---> Error:Unexpected types on position (" + (i - 1) + "," + rowCount + "). sign : " + parts[i] + "<-");
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
			
			System.out.println("Error while reading file line by line Check your Input Format");
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
	
	/**
	 * Transform 1D array of 'signs' to 2d array boardBody
	 * 
	 * @param tempStringArray
	 *            the arraylist contains all the satisfied value of the board.
	 */
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
	
	/**
	 * Print out the board.
	 * 
	 * @param boardBody
	 *            the current state of the board.
	 */
	public void printboardBody(String boardBody[][])
	{
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				//System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i + ")");
				if(boardBody[i][j].equals(Board.WHITECAPTURED)||boardBody[i][j].equals(Board.BLACKCAPTURED))
				{
					System.out.print(Board.CAPTURED+" ");
				}
				else
				{
					System.out.print(boardBody[i][j]+" ");
				}
				
			}
			System.out.println();
		}
	}
	
	public void printboardBody()
	{
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				//System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i + ")");
				if(boardBody[i][j].equals(Board.WHITECAPTURED)||boardBody[i][j].equals(Board.BLACKCAPTURED))
				{
					System.out.print(Board.CAPTURED+" ");
				}
				else
				{
					System.out.print(boardBody[i][j]+" ");
				}
				
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printboardBody(PrintStream output,String boardBody[][])
	{
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				//System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i + ")");
				if(boardBody[i][j].equals(Board.WHITECAPTURED)||boardBody[i][j].equals(Board.BLACKCAPTURED))
				{
					output.print(Board.CAPTURED+" ");
				}
				else
				{
					output.print(boardBody[i][j]+" ");
				}
			}
			output.println();
		}
	}
	
	/**
	 * Check whether the cell at the specified position is a free cell.
	 * 
	 * @param y
	 *            the row the cell is in.
	 * @param x
	 *            the column the cell is in.
	 * @return true if the cell is free, otherwise false.
	 */
	public boolean isFreeCell(int y, int x)
	{
		return boardBody[y][x].equals(FREE);
	}
	
	/**
	 * Check whether the cell at the specified position is a captured cell.
	 * 
	 * @param y
	 *            the row the cell is in.
	 * @param x
	 *            the column the cell is in.
	 * @return true if the cell is captured, otherwise false.
	 */
	public boolean isCapturedCell(int y, int x)
	{
		return boardBody[y][x].equals(CAPTURED);
	}
	
	/**
	 * Check whether the cell belongs to the black player.
	 * 
	 * @param y
	 *            the row the cell is in.
	 * @param x
	 *            the column the cell is in.
	 * @return true if the cell belongs to black player, otherwise false.
	 */
	public boolean isBlackCell(int y, int x)
	{
		return boardBody[y][x].equals(BLACK);
	}
	
	/**
	 * Check whether the cell belongs to the whiteplayer.
	 * 
	 * @param y
	 *            the row the cell is in.
	 * @param x
	 *            the column the cell is in.
	 * @return true if the cell belongs to white player, otherwise false.
	 */
	public boolean isWhiteCell(int y, int x)
	{
		return boardBody[y][x].equals(WHITE);
	}
	
	
	/**
	 * Print out the result of the board.
	 */
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
