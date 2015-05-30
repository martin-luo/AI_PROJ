package aiproj.squatter.bingfengl;

/**
 * This is a part of the project of COMP30024 Artificial Intelligence, the
 * University of Melbourne. The project is the Game of Squatter and is a group
 * work, the members of the group is list below, so is the rule of the game.
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
 * This class contains the methods used to build a board and to update the
 * board.
 * <p>
 * <b>Rules of Boardgame</b>
 * <ul>
 * <li>Only two players , one is called '<i>WHITE</i>' ,the Other one is
 * '<i>BLACK</i>'</li>
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
	public static String CAPTUREDLETTER = "C";
	public static String WHITECAPTURED = "WC";
	public static String BLACKCAPTURED = "BC";
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
	public int freeCell = 0;
	public int whiteCell = 0;
	public int blackCell = 0;
	/** The number of cell captured by white player. */
	public int whiteCaptured = 0;
	/** The number of cell captured by black player. */
	public int blackCaptured = 0;
	public int whiteConnectedPathValue = 0;
	public int blackConnectedPathValue = 0;
	public double whiteCriticalBlockValue = 0;
	public double blackCriticalBlockValue = 0;
	public double whiteNearlyCompleteCircleValue = 0;
	public double blackNearlyCompleteCircleValue = 0;
	public double blackDiag = 0;
	public double whiteDiag = 0;
	/** The board. */
	public String[][] boardBody = null;
	/** Used to track point is already in one circle path or not. */
	public int[][] trackingInCircle = null;
	/** Used to call the board update algorithms. */
	public BoardUpdateAlgorithm updateAlgorithm = null;
	public ArrayList<int[]> possibleMove = null;

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

		boardDimension = n;
		initializeboardBody(boardDimension);
		makeAllFreeBoardBody(boardDimension);

	}

	// copy a board
	@SuppressWarnings("unchecked")
	Board(Board oneBoard)
	{
		boardDimension = oneBoard.boardDimension;
		freeCell = oneBoard.freeCell;
		whiteCell = oneBoard.whiteCell;
		blackCell = oneBoard.blackCell;
		whiteCaptured = oneBoard.whiteCaptured;
		blackCaptured = oneBoard.blackCaptured;
		boardBody = copyBoard(oneBoard.boardBody);
		// System.out.println("in here awedae");
		// printboardBody(boardBody);
		// trackingInCircle = oneBoard.trackingInCircle.clone();
		updateAlgorithm = new FindCircleAndCapturedCellAlgorithm(this);
		if (oneBoard.possibleMove != null)
			//
			updateAlgorithm.doUpdateBoard();

	}

	public String[][] copyBoard(String[][] in)
	{
		String[][] temp = new String[boardDimension][boardDimension];
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				temp[i][j] = new String(in[i][j]);
				// System.out.println("temp"+temp[i][j]);
			}
		}

		return temp;
	}

	public ArrayList<int[]> getDummyFreeMove()
	{
		ArrayList<int[]> temp = new ArrayList<int[]>();
		;
		for (int row = 0; row < boardDimension; row++)
		{
			for (int col = 0; col < boardDimension; col++)
			{
				if (isFreeCell(row, col))
				{
					temp.add(new int[] { row, col });
				}
			}

		}
		possibleMove = temp;
		return temp;
	}

	public int[] getOnePossibleMove(String myPlayer, String opponentPlayer,
			int pickBranchMoveMethod)
	{
		int[] temp = null;
		if (pickBranchMoveMethod == 3)
		{
			Random rand = new Random();
			int n = rand.nextInt(100000) + 1;
			if (possibleMove != null && possibleMove.size() != 0)
			{
				// so far pick a random one .
				temp = possibleMove.remove(n % possibleMove.size());
			}

		}
		else if (pickBranchMoveMethod == 2)
		{
			if (possibleMove != null && possibleMove.size() != 0)
			{
				// so far pick a random one .
				temp = possibleMove.remove(0);
			}
		}
		else if (pickBranchMoveMethod == 1)
		{
			int pop_index = -1;
			// using method 2 which just simple get back first one of the
			// possible move .
			MinMaxAlgorithm prediction = new MinMaxAlgorithm(2);
			// expand first level get max movement.
			// forming a sub minimax with branch size of all possible move and
			// depth 1
			prediction.makeBestMove(this, 1, possibleMove.size(), myPlayer,
					opponentPlayer);
			temp = prediction.getBestMove();
			possibleMove.remove(temp);

			if (temp == null)
			{
				return temp;
			}

			for (int[] temp_move : possibleMove)
			{
				pop_index++;
				if (temp_move[0] == temp[0] && temp_move[1] == temp[1])
				{
					break;
				}

			}
			if (pop_index != -1)
			{
				possibleMove.remove(pop_index);
			}
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

	// making free board body
	public void makeAllFreeBoardBody(int boardDimension)
	{
		for (int row = 0; row < boardDimension; row++)
		{
			for (int col = 0; col < boardDimension; col++)
			{
				boardBody[row][col] = FREE;
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
	 * If the number of row is different from the dimension whose value is got
	 * from the first row of input file, notify the user then exit.
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
			System.out
					.println("X.X ---> Error:Actual xs and Board Dimension Mismatched.");
			System.exit(0);
		}

	}

	/**
	 * Check whether the number of cell in each row is the same as the
	 * dimension specified.
	 * 
	 * If the number of cell of each row is different from the dimension whose
	 * value is got from the first row of input, notify the user then exit.
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
			System.out
					.println("X.X ---> Error:Actual ys and Board Dimension Mismatched.");
			System.exit(0);
		}

	}

	/**
	 * Validates a cell on the board
	 *
	 * Check if the cell is one the the four types: Black, White, Captured,
	 * Free
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
	 * Read the input from standard input and put the satisfied value into an
	 * arraylist then add it to the boardBody.
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
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(System.in));
			String line;

			// Since the first line is the dimension of the board, read it
			// separately and assign it to the corresponding variable.
			line = bufferReader.readLine();
			try
			{
				boardDimension = Integer.parseInt(line);
			}
			catch (Exception e)
			{
				System.out
						.println("Error while reading file line by line Check your Input Format");
				System.exit(0);
			}
			// System.out.println("boardDimension------ :  " +
			// getBoardDimension());

			// Add sign into a string array by split each line with delimit ' '
			while (!(line = bufferReader.readLine()).equals(""))
			{

				line = line.replace(" ", "").replace("\t", "");
				parts = line.split("");
				// System.out.println("part[0]"+parts[0]);
				// take of -1 for submittsion
				colCount = parts.length;
				// System.out.println("colCount : " + colCount);
				// If it's the first line of the board, make the prevYCount
				// equal to yCount.
				checkxNumber(colCount);
				// System.out.println("" + line);
				// need to skip first one ,split("") first one is ""
				// i=0 for submitsion
				for (int i = 0; i < parts.length; i++)
				{
					// System.out.println("i: "+i+" sign : "+parts[i]+"<-"
					// +"level : "+tempStringArray.size());
					// If the element is valid, add it to the temporary array,
					// otherwise notify the user then exit.
					if (checkCellValidation(parts[i]))
					{
						tempStringArray.add(parts[i]);
					}
					else
					{
						System.out
								.println("X.X ---> Error:Unexpected types on position ("
										+ (i - 1)
										+ ","
										+ rowCount
										+ "). sign : " + parts[i] + "<-");
						System.out
								.println("Please make sure the input contains only these four types: ");
						System.out.println("\"" + BLACK + "\" \"" + WHITE
								+ "\" \"" + CAPTURED + "\" \"" + FREE + "\"");

						System.exit(0);
					}
				}
				rowCount++;
			}

			bufferReader.close();
		}
		catch (Exception e)
		{

			System.out
					.println("Error while reading file line by line Check your Input Format");
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
				// System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i +
				// ")");
				if (boardBody[i][j].equals(Board.WHITECAPTURED)
						|| boardBody[i][j].equals(Board.BLACKCAPTURED))
				{
					System.out.print(Board.CAPTURED + " ");
				}
				else
				{
					System.out.print(boardBody[i][j] + " ");
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
				// System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i +
				// ")");
				if (boardBody[i][j].equals(Board.WHITECAPTURED)
						|| boardBody[i][j].equals(Board.BLACKCAPTURED))
				{
					System.out.print(Board.CAPTURED + " ");
				}
				else
				{
					System.out.print(boardBody[i][j] + " ");
				}

			}
			System.out.println();
		}
		System.out.println();
	}

	public void printboardBody(PrintStream output, String boardBody[][])
	{
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				// System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i +
				// ")");
				if (boardBody[i][j].equals(Board.WHITECAPTURED)
						|| boardBody[i][j].equals(Board.BLACKCAPTURED))
				{
					output.print(Board.CAPTURED + " ");
				}
				else
				{
					output.print(boardBody[i][j] + " ");
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
		// System.out.println(whiteCaptured);
		// System.out.println(blackCaptured);
	}

	public ArrayList<int[]> getCellSurronding(int x, int y)
	{
		int tempX = -1;
		int tempY = -1;
		int[][] directionList = new int[][] { { -1, -1 }, { 0, -1 },
				{ 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 } };
		ArrayList<int[]> tempSurrounding = new ArrayList<int[]>();
		for (int i = 0; i < directionList.length; i++)
		{
			tempX = directionList[i][0] + x;
			tempY = directionList[i][1] + y;
			if (tempX < 0 || tempY < 0 || tempX >= boardDimension
					|| tempY >= boardDimension)
			{
				continue;
			}
			tempSurrounding.add(new int[] { tempX, tempY });
		}
		return tempSurrounding;
	}

	// see if there is a cell blocking(opposite player) a nearly complete
	// circle
	public boolean oneCriticalblock(String pathOwner, int startX, int startY,
			int midX, int midY, int endX, int endY)
	{
		// System.out.println("patthOwner : "+pathOwner+" startX : "+startX+" startY "+startY+" end X:"+endX+" end Y: "+endY+" mid x : "+midX+" mid y: "+midY);
		if (startX == endX && startY == endY)
		{
			return false;
		}
		String opponenetPlayer = null;
		// if path onwer is black then oppoenent is white ..
		opponenetPlayer = (pathOwner.equals(Board.WHITE) ? Board.BLACK
				: Board.WHITE);
		// boolean critical=false;
		int[][] directionList = new int[][] { { -1, -1 }, { 0, -1 },
				{ 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 } };
		ArrayList<int[]> startPointSurrounding = getCellSurronding(startX,
				startY);
		ArrayList<int[]> endPointSurrounding = getCellSurronding(endX, endY);
		int[] temp_one = null;
		int[] temp_two = null;

		for (int i = 0; i < startPointSurrounding.size(); i++)
		{
			temp_one = startPointSurrounding.get(i);
			for (int j = 0; j < endPointSurrounding.size(); j++)
			{
				temp_two = endPointSurrounding.get(j);
				// if have same surrounding and this surrounding cell is
				// ocupied by opoonenet means block .
				// if the end cell is within start's surroungding then abort
				if (temp_one[0] == temp_two[0] && temp_one[1] == temp_two[1])
				{
					// board body is [row][col] ===> [y][x]
					if (boardBody[temp_one[1]][temp_one[0]]
							.equals(opponenetPlayer))
					{
						// block cell is include the mid point of circle means
						// not critical block
						if (cellInforeseeBoard(midX, midY, temp_one[0],
								temp_one[1], 1, 0))
						{
							return false;
						}
						// critical=true;
						return true;
					}
				}
			}
		}
		return false;
	}

	// this method is used to get all cells arouding the input cell based on
	// its x and y ...
	// size == boundray level outer .
	public ArrayList<int[]> produceSurroundingCell(int centerX, int centerY,
			int size)
	{
		// System.out.println("center X "+centerX+"center Y"+centerY);
		ArrayList<int[]> tempForeseeBoard = new ArrayList<int[]>();
		// x==>[0] and y==>[1]
		int[] tempMostTopLeftCell = new int[] { centerX - size, centerY - size };
		int tempX = tempMostTopLeftCell[0];
		int tempY = tempMostTopLeftCell[1];
		// iterating to get valid most left cell
		for (int i = (-size); i <= size; i++)
		{
			tempY = centerY + i;
			for (int j = (-size); j <= size; j++)
			{
				tempX = centerX + j;

				// System.out.println("x ==> "+tempX+" y==>"+tempY);
				// skip any invalid cells
				if (tempX < 0 || tempY < 0 || tempX >= boardDimension
						|| tempY >= boardDimension)
				{
					continue;
				}
				tempForeseeBoard.add(new int[] { tempX, tempY });
			}
		}

		return tempForeseeBoard;
	}

	// expand size == fron center level of outer row and col
	// expand all board around the center cell based on the level of expansion
	// and see the wantedCellX Y in the expand area or not
	// in order to determine how far a circle is nearly complete or not .
	public boolean cellInforeseeBoard(int startX, int startY, int endX,
			int endY, int expandSize, int cellNumbers)
	{
		ArrayList<int[]> foreseeBoard = produceSurroundingCell(endX, endY,
				expandSize);
		for (int[] temp : foreseeBoard)
		{
			if (temp[0] == startX && temp[1] == startY)
			{
				// path start cell is in the area of last poped cell.
				return true;
			}
		}
		return false;
	}

	// measure how complete is a non closed path
	// (100/distance) * cellInPath ===> closer distance and many cells in the
	// path means this nearly complete circle is important
	public double measureNearlyCompletenCirlce(int startX, int startY,
			int midX, int midY, int endX, int endY,
			ArrayList<CellNode> cellNodes)
	{
		double score = 0;
		int expandValue = 0;
		// estimated non completed circle's center
		int[] centerPoint = centerPoint(startX, startY, midX, midY, endX, endY);
		double distance = Math.sqrt(Math.pow((startX - endX), 2)
				+ Math.pow((startY - endY), 2));
		expandValue = getExpandValue(centerPoint[0], centerPoint[1], cellNodes);
		score = (cellNodes.size() * expandValue) / (distance);
		// System.out.println("start x y ==>"+startX+","+startY+" end x y ==>"+endX+","+endY);
		// System.out.println("circle value centerPoin ==>x : "+centerPoint[0]+",y: "+centerPoint[1]+" expand value => "+expandValue+"dis => "+distance+
		// "number of cells => "+cellNodes.size()+"  score => "+score);
		if (score == Double.POSITIVE_INFINITY)
		{
			score = 0;
		}
		return score;
	}

	// find triangle's middle point
	public int[] centerPoint(int startX, int startY, int midX, int midY,
			int endX, int endY)
	{
		int[] tempCenterPoint = null;
		// find start to end point mid point
		tempCenterPoint = new int[] { (endX + startX) / 2, (endY + startY) / 2 };
		// use above mid point to find mid point between midx AND midy in the
		// path.
		tempCenterPoint = new int[] { (tempCenterPoint[0] + midX) / 2,
				(tempCenterPoint[1] + midY) / 2 };
		return tempCenterPoint;

	}

	// test expand value ,how many expansion can include all the path nodes
	// with the center point
	public int getExpandValue(int centerX, int centerY,
			ArrayList<CellNode> cellNodes)
	{
		int[] tempCell = null;
		int countMeetCellInExpandArea = 0;
		ArrayList<int[]> expandArea = null;
		int expandValue = 0;
		// boolean allInExpandArea=false;
		for (expandValue = 1;; expandValue++)
		{
			// &&expandValue<boardDimension
			countMeetCellInExpandArea = 0;
			expandArea = produceSurroundingCell(centerX, centerY, expandValue);
			// System.out.println("stuck in here 1 size ==>"+expandArea.size()+" expand value => "+expandValue);
			for (int j = 0; j < expandArea.size(); j++)
			{
				// System.out.println("stuck in here 2");
				tempCell = expandArea.get(j);
				for (CellNode oneCellNode : cellNodes)
				{

					if (oneCellNode.currentNodeX == tempCell[0]
							&& oneCellNode.currentNodeY == tempCell[1])
					{
						// System.out.println("stuck in here 3 + expandValue"+expandValue+" cell x =>"+oneCellNode.currentNodeX+"cell y ==> "+oneCellNode.currentNodeY);
						// System.out.println("stuck in here 3 + expandValue"+expandValue+" expanded x =>"+tempCell[0]+"expanded y ==> "+tempCell[1]);
						// System.out.println("number of cells ==== "+cellNodes.size());
						countMeetCellInExpandArea++;
					}
				}
			}
			if (countMeetCellInExpandArea >= cellNodes.size())
			{
				break;
			}
		}
		return expandValue;
	}

	public double diagonalValue(int x, int y, String player)
	{
		int[][] directions = new int[][] { { -1, -1 }, { 1, -1 }, { -1, 1 },
				{ 1, 1 } };
		int tempX = -1;
		int tempY = -1;
		double diagValue = 0;
		for (int i = 0; i < directions.length; i++)
		{
			tempX = x + directions[i][0];
			tempY = y + directions[i][1];
			if (tempX < 0 || tempY < 0 || tempX >= boardDimension
					|| tempY >= boardDimension)
			{
				continue;
			}
			if (boardBody[tempY][tempX].equals(player))
			{
				diagValue++;
			}
		}
		return diagValue;
	}
}
