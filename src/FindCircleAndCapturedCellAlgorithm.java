/*
 * This is a part of the project of COMP30024 Artificial Intelligence, the University of Melbourne. The project is the Game of Squatter and is a group work, the members of the group is list below, so is the rule of the game.
 */
/**
 * <b>Class Declaration</b>
 * <p>
 * This class is used to find the circle of the board and to count the cell and judge whether it's the final state of the game.
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
 * @since 2015-03-30
 */


import java.util.ArrayList;


public class FindCircleAndCapturedCellAlgorithm extends BoardUpdateAlgorithm
{
	Board board = null;
	ArrayList<BoardDataCircleStructure> collectionOfCircle = null;
	public int[][] trackingInCircle = null;
	int whiteCaptured;
	int blackCaptured;
	int freeCell;
	String[][] boardBody = null;
	int boardDimension;
	
	/**
	 * Initialize find circle algorithm  obj
	 * @param board is the board of this game
	 */
	
	FindCircleAndCapturedCellAlgorithm(Board board)
	{
		this.board = board;
		this.boardBody = board.getBoardBody();
		this.boardDimension = board.getBoardDimension();
	}
	/**
	 * this function will run find circle and find captured cell and update the information of board
	 */
	public void doUpdateBoard()
	{
		BoardDataCircleStructure tempOneCircle = null;
		collectionOfCircle = new ArrayList<BoardDataCircleStructure>();
		freeCell = 0;
		// keep track whether if a point is already in circle or not .
		trackingInCircle = initialize2Darray(0);
		whiteCaptured = 0;
		blackCaptured = 0;
		// trackingVistiedBoard=initialize2Darray(0);
		// iterating row
		for (int y = 0; y < boardDimension; y++)
		{
			// iterating cols
			for (int x = 0; x < boardDimension; x++)
			{
				// if encounter a cell which is belong to white or black , and it is not currently in the part of any circle .. we use it
				// as start point to find cirlce
				if ((boardBody[y][x].equals(Board.WHITE) || boardBody[y][x].equals(Board.BLACK)) && trackingInCircle[y][x] == 0)
				{
					tempOneCircle = new BoardDataCircleStructure(boardBody[y][x]);
					// initial board data circle , parss in x ,y and black or white
					//finding circle for current cell
					doFindCircle(tempOneCircle, x, y, boardBody[y][x]);
					//if circle found per form count capture alg
					if (tempOneCircle.positionCells.size() > 0)
					{
						tempOneCircle.transformCellNodeToIntArray();
						tempOneCircle.constructLevel();
						if (tempOneCircle.circleOwner.equals(Board.WHITE))
						{
							// System.out.println("White count capture");
							whiteCaptured += countCapturedCell(tempOneCircle);
						}
						
						else if (tempOneCircle.circleOwner.equals(Board.BLACK))
						{
							// System.out.println("Black count capture");
							blackCaptured += countCapturedCell(tempOneCircle);
						}
						collectionOfCircle.add(tempOneCircle);
						// AidUtility.printBoardDataCircleStructureCellNode(tempOneCircle);
						//System.out.println("------tracking in circle");
						//AidUtility.print2DintArray(trackingInCircle, boardDimension);
						
					}
				}
				else if (boardBody[y][x].equals(Board.FREE))
				{
					freeCell += 1;
				}
			}
			board.setBlackCaptured(blackCaptured);
			board.setWhiteCaptured(whiteCaptured);
			board.setFreeCell(freeCell);
			
			
		}
		
	}
	
	/**
	 * this function will find circle from starting row and col value,
	 * @param oneCircle used to store cells which forming the circle
	 * @param startX starting col point of finding circle
	 * @param startY starting row point of finding circle
	 * @param whoseCircle state which circle we are finding is belonged to 
	 * 
	 */
	
	public void doFindCircle(BoardDataCircleStructure oneCircle, int startX, int startY, String whoseCircle)
	{
		// ArrayList<CellNode> arrayListOfCellNodes=new ArrayList<CellNode>();
		// CellNode tempValidCell = null;
		// -1 ---> original , 0 --> not visited, 1 ---> visited
		// int [][]trackingVistiedBoard=initialize2Darray(0);
		// initial value is all -1 which means it is not in queue
		// int[][] trackingIndex=initialize2Darray(-1);
		int[] tempClockwiseXY = null;
		// ArrayList<Integer> circleRangeFrom=new ArrayList<Integer>();
		// ArrayList<Integer> circleRangeTo=new ArrayList<Integer>();
		int currentTempX;
		int currentTempY;
		int prevTempX;
		int prevTempY;
		trackingInCircle[startY][startX] = 1;
		// trackingVistiedBoard[startY][startX]=-1;
		// trackingIndex[startY][startX]=0;
		// -1,-1 means starting cell does not have prev x and prev y
		oneCircle.positionCells.add(new CellNode(startX, startY, -1, -1, whoseCircle, boardDimension, boardBody));
		while (oneCircle.positionCells.size() != 0)
		{
			tempClockwiseXY = oneCircle.positionCells.get(oneCircle.positionCells.size() - 1).mostClockwisedCellIndex();
			// means no next cell can be found
			if (tempClockwiseXY == null)
			{
				// set index to -1 means it is not in the queue.
				trackingInCircle[oneCircle.positionCells.get(oneCircle.positionCells.size() - 1).currentNodeY][oneCircle.positionCells.get(oneCircle.positionCells.size() - 1).currentNodeX] = 0;
				oneCircle.positionCells.remove(oneCircle.positionCells.size() - 1);
				continue;
			}
			currentTempX = tempClockwiseXY[0];
			currentTempY = tempClockwiseXY[1];
			// meet a next point which is already in circle and it is not starting point
			if (trackingInCircle[currentTempY][currentTempX] == 1 && currentTempX != startX && currentTempY != startY)
			{
				continue;
			}
			if (currentTempX == startX && currentTempY == startY)
			{
				break;
			}
			// find a circle before returning to original
			// make new next node visited m
			// trackingVistiedBoard[currentTempY][currentTempX]=1;
			trackingInCircle[currentTempY][currentTempX] = 1;
			// track current cellnodes index in array
			// trackingIndex[currentTempY][currentTempX]=oneCircle.positionCells.size();
			prevTempX = oneCircle.positionCells.get(oneCircle.positionCells.size() - 1).currentNodeX;
			prevTempY = oneCircle.positionCells.get(oneCircle.positionCells.size() - 1).currentNodeY;
			oneCircle.positionCells.add(new CellNode(currentTempX, currentTempY, prevTempX, prevTempY, whoseCircle, boardDimension, boardBody));
			// returned valid next cell won't be the current cell's prev cell
			// so if returned something that is already in the path ....
			// we can confirm that there will be another circle .
		}
	}
	
	// //////////////////////////Count Capture Algorithm//////////////////////////////////
	
	/**
	 * this function count number of captured cell of this circle based on validation array
	 * @return countCapturedNumber(circleOne, mergeCountBottomUpAndTopDown(countTopDown(circleOne), countBottomUp(circleOne))) ---> merged and count
	 */
	
	public int countCapturedCell(BoardDataCircleStructure circleOne)
	{
		return countCapturedNumber(circleOne, mergeCountBottomUpAndTopDown(countTopDown(circleOne), countBottomUp(circleOne)));
	}
	
	/**
	 * this function count number of captured cell of this circle based on validation array
	 * @return count captured cells of this circle's onwer 
	 */
	
	public int countCapturedNumber(BoardDataCircleStructure circleOne, int[][] mergedLevelValidation)
	{
		int count = 0;
		int level = 0;
		// iterate number of levels,skip first and last level
		int[] tempX = null;
		int[] tempY = null;
		for (int i = 1; i < circleOne.circleLevel.length - 1; i++)
		{
			tempX = (int[]) circleOne.circleLevel[i][0];
			tempY = (int[]) circleOne.circleLevel[i][1];
			// from left to right but skip boarder
			level = tempY[0];
			for (int j = tempX[0] + 1; j < tempX[tempX.length - 1]; j++)
			{ 
				// if cell is captured and it is marked '-' means captured count
				if (boardBody[level][j].equals(Board.CAPTURED) && mergedLevelValidation[level][j] == 1)
				{
					count++;
					//mark cells which is in circle ... so we can perform less finding circle + finding capture alg
					trackingInCircle[level][j] = 1;
				}
				// if cell is captured self cell
				if (mergedLevelValidation[level][j] == 2)
				{
					trackingInCircle[level][j] = 1;
				}
			}
		}
		
		return count;
		
	}
	
	/**
	 * this function perform capture algorithm in topDown manner
	 * @return levelValidation 
	 */
	
	public int[][] countTopDown(BoardDataCircleStructure circleOne)
	{
		int[][] levelValidation = initializeLevelValidation();
		int numberOfCircleLevel = circleOne.circleLevel.length;
		// assume x and y array sorted properly by y and x
		// circleLevel[level][0] ----> x array , circleLevel[level][1] ----> y array
		// skip first level by just put first level path info in
		int tempXlevelArray[] = (int[]) circleOne.circleLevel[0][0];
		// this temp Y level array will contain same value ....
		int tempYlevelArray[] = (int[]) circleOne.circleLevel[0][1];
		
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 0, circleOne.circleOwner);
		
		for (int i = 1; i < numberOfCircleLevel - 1; i++)
		{
			tempXlevelArray = (int[]) circleOne.circleLevel[i][0];
			tempYlevelArray = (int[]) circleOne.circleLevel[i][1];
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 0, circleOne.circleOwner);
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 1, circleOne.circleOwner);
			
		}
		// put last level in
		tempXlevelArray = (int[]) circleOne.circleLevel[circleOne.circleLevel.length - 1][0];
		tempYlevelArray = (int[]) circleOne.circleLevel[circleOne.circleLevel.length - 1][1];
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 0, circleOne.circleOwner);
		// skip last level by just put last level path info in
		return levelValidation;
	}
	
	/**
	 * this function perform capture algorithm in BottomUp manner
	 * @return levelValidation 
	 */
	
	public int[][] countBottomUp(BoardDataCircleStructure circleOne)
	{
		// 1 means free, 0 means not valid , 2 means self cell,in future we mark opponent as 3 and change it to '-' after merge
		int[][] levelValidation = initializeLevelValidation();
		int numberOfCircleLevel = circleOne.circleLevel.length;
		// assume x and y array sorted properly by y and x
		// skip last level by just put first level path info in
		int tempXlevelArray[] = (int[]) circleOne.circleLevel[numberOfCircleLevel - 1][0];
		// this temp Y level array will contain same value ....
		int tempYlevelArray[] = (int[]) circleOne.circleLevel[numberOfCircleLevel - 1][1];
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 0, circleOne.circleOwner);
		for (int i = numberOfCircleLevel - 2; i > 0; i--)
		{
			tempXlevelArray = (int[]) circleOne.circleLevel[i][0];
			tempYlevelArray = (int[]) circleOne.circleLevel[i][1];
			// put in boundary points of this level first
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 0, circleOne.circleOwner);
			// tempYlevelArray[0] == current level's y value
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 2, circleOne.circleOwner);
		}
		// put last level in
		tempXlevelArray = (int[]) circleOne.circleLevel[0][0];
		tempYlevelArray = (int[]) circleOne.circleLevel[0][1];
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0], tempXlevelArray, 0, circleOne.circleOwner);
		// skip last level by just put last level path info in
		return levelValidation;
	}
	
	/**
	 * this function is used to merge two validation array from topDown and bottomUp
	 * @return resultCountValidation is merged result validation array 
	 */
	
	public int[][] mergeCountBottomUpAndTopDown(int[][] countTopDown, int[][] countBottomUp)
	{
		int[][] resultCountValidation = initializeLevelValidation();
		
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				// one not valid == all not valid .
				if (countTopDown[i][j] == 0 && countBottomUp[i][j] == 0)
				{
					continue;
				}
				// self cell captured
				if (countTopDown[i][j] == 2 && countBottomUp[i][j] == 2)
				{
					resultCountValidation[i][j] = 2;
					continue;
				}
				// valid captured cell
				resultCountValidation[i][j] = 1;
			}
		}
		return resultCountValidation;
	}
	
	/**
	 * this function is used to fill validation array with conditions
	 *
	 */
	
	// following only for reading in board ... so we only determine 'captured' cells .
	public void fillLevelIntoValidation(int[][] levelValidation, int levelIndex, int[] xPointArray, int conditionNumber, String whoseCircle)
	{
		if (conditionNumber == 0)
		{
			for (int i = 0; i < xPointArray.length; i++)
			{
				levelValidation[levelIndex][xPointArray[i]] = 1;
			}
		}
		// top down decision to add cell into ceiling with comparing to previous level
		if (conditionNumber == 1)
		{
			// only go between this level's max and min x-boundary .
			// System.out.println("topdown in here");
			for (int i = xPointArray[0] + 1; i < xPointArray[xPointArray.length - 1]; i++)
			{
				// skip this level's boundary x and if above x is ==1 and this board position marked captured ==> good
				if (levelValidation[levelIndex][i] != 1 && levelValidation[levelIndex - 1][i] != 0 && boardBody[levelIndex][i].equals(Board.CAPTURED))
				{
					levelValidation[levelIndex][i] = 1;
				}
				// meet self cell inside circle under ceiling ..still include it to ceiling but mark it as 2
				if (levelValidation[levelIndex][i] != 1 && levelValidation[levelIndex - 1][i] != 0 && boardBody[levelIndex][i].equals(whoseCircle))
				{
					levelValidation[levelIndex][i] = 2;
				}
				// System.out.println (" sign : "+boardBody[levelIndex][i]+" check : "+boardBody[levelIndex][i].equals(Board.CAPTURED));
			}
			// System.out.println();
		}
		// bottom up decision to add cell into ceiling with comparing to previous level
		if (conditionNumber == 2)
		{ // only go between this level's max and min x-boundary .
			// System.out.println("bottomup in here");
			for (int i = xPointArray[0] + 1; i < xPointArray[xPointArray.length - 1]; i++)
			{
				// skip this level's boundary x and if above x is ==1 and this board position marked captured ==> good
				if (levelValidation[levelIndex][i] != 1 && levelValidation[levelIndex + 1][i] != 0 && boardBody[levelIndex][i].equals(Board.CAPTURED))
				{
					levelValidation[levelIndex][i] = 1;
				}
				if (levelValidation[levelIndex][i] != 1 && levelValidation[levelIndex + 1][i] != 0 && boardBody[levelIndex][i].equals(whoseCircle))
				{
					levelValidation[levelIndex][i] = 2;
				}
			}
		}
	}
	
	/**
	 * it is used to initialize 2d array for validation array with zero
	 * @return levelValidation
	 */
	
	public int[][] initializeLevelValidation()
	{
		int levelValidation[][] = new int[boardDimension][boardDimension];
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				levelValidation[i][j] = 0;
			}
		}
		return levelValidation;
	}
	
	/**
	 * it is used to initialize 2d array with a value
	 * @return levelValidation
	 */
	
	public int[][] initialize2Darray(int initialValue)
	{
		int levelValidation[][] = new int[boardDimension][boardDimension];
		for (int i = 0; i < boardDimension; i++)
		{
			for (int j = 0; j < boardDimension; j++)
			{
				levelValidation[i][j] = initialValue;
			}
		}
		return levelValidation;
	}
}
