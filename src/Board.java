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
	String inputFileName;
	public static int BOARDDIMENSION = 0;
	int freeCell = 0;
	int whiteCaptured = 0;
	int blackCaptured = 0;
	// used to keep tracking of point which is in circle 0 === not in circle ,
	// 1== in circle
	public int[][] positionInCircle = null;
	// remember boardBodu[y][x]
	public static String[][] BOARDBODY = null;
	// tracking point is already in one circle path or not.
	public int[][] trackingInCirclePath = null;
	// public int [][]trackingVistiedBoard=null;
	ArrayList<BoardDataCircleStructure> collectionOfCircle = null;
	
	Board()
	{
		positionInCircle = initialize2Darray(0);
		doParseInput();
	}
	
	public void initializeBoardBody(int boardDimension)
	{
		BOARDBODY = new String[boardDimension][boardDimension];
		
	}
	
	public String getBoardCell(int x, int y)
	{
		return BOARDBODY[x][y];
	}
	
	public void setBoardCell(int x, int y, String sign)
	{
		BOARDBODY[x][y] = sign;
	}
	
	public void checkxNumber(int xCount)
	{
		if (xCount == BOARDDIMENSION)
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
	
	public void checkyNumber(int yCount)
	{
		if (yCount == BOARDDIMENSION)
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
		// First line is dimension
		// int xCount = -1;
		int xCount = 0;
		int yCount = 0;
		int prevYCount = 0;
		
		ArrayList<String> tempStringArray = new ArrayList<String>();
		String[] parts = null; // Transform everything into a 1d array .
		try
		{
			// FileReader inputFile = new FileReader("file name");
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(System.in));
			String line;

			// Since the first line is the dimension of the board, read it separately
			// and assign it to the corresponding variable.
			line = bufferReader.readLine();
			BOARDDIMENSION = Integer.parseInt(line);

			// add sign into a string array by split each line with delimit ' '
			while ((line = bufferReader.readLine()) != null)
			{
				// If it's not the first line of the board, make the prevYCount
				// equal to yCount. To check whether each line contains the same
				// number of cell.
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
					System.out
							.println("X.X ---> Error:Different size for each row.");
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
		
		// Set Dimension to first line of input and remove it from ArrayList.
		// BOARDDIMENSION = Integer.parseInt(tempStringArray.remove(0));
		// yCount = tempStringArray.get(0).length();
		// for (int i = 0; i < tempStringArray.size(); i++)
		// {
		
		// }
		
		// if dimension not match exit
		System.out.println("Dimension" + BOARDDIMENSION + " xCount " + xCount);
		checkxNumber(xCount);
		checkyNumber(yCount);
		initializeBoardBody(BOARDDIMENSION);
		fillBoardBody(tempStringArray);
		
		printBoardBody(BOARDBODY);
	}
	
	// remembe bo
	// transform 1D array of 'signs' to 2d array
	public void fillBoardBody(ArrayList<String> tempStringArray)
	{
		int x = 0, y = 0;
		for (String tempString : tempStringArray)
		{
			BOARDBODY[y][x++] = tempString;
			if (x >= BOARDDIMENSION)
			{
				y++;
				x = 0;
			}
		}
	}
	
	public void printBoardBody(String boardBody[][])
	{
		for (int i = 0; i < BOARDDIMENSION; i++)
		{
			for (int j = 0; j < BOARDDIMENSION; j++)
			{
				System.out.print(boardBody[i][j] + "(x:" + j + " y:" + i + ")");
			}
			System.out.println();
		}
	}
	
	public boolean isFreeCell(int y, int x)
	{
		return BOARDBODY[y][x].equals(FREE);
	}
	
	public boolean isCapturedCell(int y, int x)
	{
		return BOARDBODY[y][x].equals(CAPTURED);
	}
	
	public boolean isBlackCell(int y, int x)
	{
		return BOARDBODY[y][x].equals(BLACK);
	}
	
	public boolean isWhiteCell(int y, int x)
	{
		return BOARDBODY[y][x].equals(WHITE);
	}
	
	// //////////////////////////find circle
	// Algorithm/////////////////////////////////////
	
	public void chekcBoard()
	{
		BoardDataCircleStructure tempOneCircle = null;
		collectionOfCircle = new ArrayList<BoardDataCircleStructure>();
		freeCell = 0;
		// keep track whether if a point is already in circle or not .
		trackingInCirclePath = initialize2Darray(0);
		// trackingVistiedBoard=initialize2Darray(0);
		// y
		for (int y = 0; y < BOARDDIMENSION; y++)
		{
			// x
			for (int x = 0; x < BOARDDIMENSION; x++)
			{
				// if encounter a cell which is belong to white or black , and
				// it is not currently in the part of any circle .. we use it
				// as start point to find cirlce
				// System.out.println("in here ");
				if ((BOARDBODY[y][x].equals(WHITE) || BOARDBODY[y][x]
						.equals(BLACK)) && trackingInCirclePath[y][x] == 0)
				{
					tempOneCircle = new BoardDataCircleStructure(
							BOARDBODY[y][x]);
					// initial board data circle , parss in x ,y and black or
					// white
					doFindCircle(tempOneCircle, x, y, BOARDBODY[y][x]);
					if (tempOneCircle.positionCells.size() > 0)
					{
						collectionOfCircle.add(tempOneCircle);
						AidUtility
								.printBoardDataCircleStructureCellNode(tempOneCircle);
					}
				}
				else if (BOARDBODY[y][x].equals(FREE))
				{
					freeCell += 1;
				}
			}
		}
		whiteCaptured = 0;
		blackCaptured = 0;
		
		for (int i = 0; i < collectionOfCircle.size(); i++)
		{
			System.out.println("----start finding capture ------");
			tempOneCircle = collectionOfCircle.get(i);
			tempOneCircle.transformCellNodeToIntArray();
			tempOneCircle.constructLevel();
			if (tempOneCircle.circleOwner.equals(WHITE))
			{
				System.out.println("White count capture");
				whiteCaptured += countCapturedCell(tempOneCircle);
			}
			
			else if (tempOneCircle.circleOwner.equals(BLACK))
			{
				System.out.println("Black count capture");
				blackCaptured += countCapturedCell(tempOneCircle);
			}
		}
		
		do_output();
		
	}
	
	public void do_check_captured(BoardDataCircleStructure oneCircle)
	{
		
	}
	
	public void do_output()
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
	
	public void doFindCircle(BoardDataCircleStructure oneCircle, int startX,
			int startY, String whoseCircle)
	{
		// ArrayList<CellNode> arrayListOfCellNodes=new ArrayList<CellNode>();
		// CellNode tempValidCell = null;
		// -1 ---> original , 0 --> not visited, 1 ---> visited
		int[][] trackingVistiedBoard = initialize2Darray(0);
		// initial value is all -1 which means it is not in queue
		// int[][] trackingIndex=initialize2Darray(-1);
		int[] tempClockwiseXY = null;
		// ArrayList<Integer> circleRangeFrom=new ArrayList<Integer>();
		// ArrayList<Integer> circleRangeTo=new ArrayList<Integer>();
		int currentTempX;
		int currentTempY;
		int prevTempX;
		int prevTempY;
		trackingInCirclePath[startY][startX] = 1;
		trackingVistiedBoard[startY][startX] = -1;
		// trackingIndex[startY][startX]=0;
		// -1,-1 means starting cell does not have prev x and prev y
		System.out.println("in find circle function");
		oneCircle.positionCells.add(new CellNode(startX, startY, -1, -1,
				whoseCircle, trackingVistiedBoard, trackingInCirclePath));
		System.out.println("Start X: " + startX + "Start Y:" + startY);
		while (oneCircle.positionCells.size() != 0)
		{
			tempClockwiseXY = oneCircle.positionCells.get(
					oneCircle.positionCells.size() - 1)
					.mostClockwisedCellIndex();
			// means no next cell can be found
			// System.out.println("after most clockwse result : "+(tempClockwiseXY==null));
			System.out.println("Most clockwise");
			if (tempClockwiseXY == null)
			{
				// set index to -1 means it is not in the queue.
				// trackingIndex[oneCircle.positionCells.get(oneCircle.positionCells.size()-1).currentNodeX][oneCircle.positionCells.get(oneCircle.positionCells.size()-1).currentNodeY]=-1;
				// positionInCircle[oneCircle.positionCells.get(oneCircle.positionCells.size()-1).currentNodeX][oneCircle.positionCells.get(oneCircle.positionCells.size()-1).currentNodeY]=-1;
				System.out.println("removing");
				trackingInCirclePath[oneCircle.positionCells
						.get(oneCircle.positionCells.size() - 1).currentNodeY][oneCircle.positionCells
						.get(oneCircle.positionCells.size() - 1).currentNodeX] = 0;
				oneCircle.positionCells
						.remove(oneCircle.positionCells.size() - 1);
				continue;
			}
			// System.out.println("temp clock wise");
			currentTempX = tempClockwiseXY[0];
			currentTempY = tempClockwiseXY[1];
			// meet a point which is already in circle and it is not starting
			// point
			if (trackingInCirclePath[currentTempY][currentTempX] == 1
					&& currentTempX != startX && currentTempY != startY)
			{
				System.out.println("next --->  x : " + currentTempX + " y: "
						+ currentTempY);
				continue;
			}
			
			System.out.println("CurrentTemp X: " + currentTempX
					+ "CurrentTemp Y:" + currentTempY);
			if (currentTempX == startX && currentTempY == startY)
			{
				System.out.println("found loop first point X"
						+ oneCircle.positionCells.get(0).currentNodeX + "Y: "
						+ oneCircle.positionCells.get(0).currentNodeY);
				
				// slicing and remove them from arrayListOfCellNodes.
				// collectionOfCircle.add(AidUtility.slicingCellNodeCollectionToBoardDataCircleStructure(0,arrayListOfCellNodes.size(),whoseCircle,arrayListOfCellNodes));
				// AidUtility.arrayListOfCellNodesRemove(0,arrayListOfCellNodes.size(),arrayListOfCellNodes);
				break;
			}
			// find a circle before returning to original
			// if(trackingBoard[currentTempY][currentTempX]==1&&trackingIndex[currentTempX][currentTempY]!=-1)
			// {
			// collectionOfCircle.add(AidUtility.slicingCellNodeCollectionToBoardDataCircleStructure(trackingIndex[currentTempY][currentTempX],arrayListOfCellNodes.size(),whoseCircle,arrayListOfCellNodes));
			// AidUtility.arrayListOfCellNodesRemove(trackingIndex[currentTempY][currentTempX]+1,arrayListOfCellNodes.size(),arrayListOfCellNodes);
			// }
			// make new next node visited m
			trackingVistiedBoard[currentTempY][currentTempX] = 1;
			trackingInCirclePath[currentTempY][currentTempX] = 1;
			// track current cellnodes index in array
			// trackingIndex[currentTempY][currentTempX]=oneCircle.positionCells.size();
			prevTempX = oneCircle.positionCells.get(oneCircle.positionCells
					.size() - 1).currentNodeX;
			prevTempY = oneCircle.positionCells.get(oneCircle.positionCells
					.size() - 1).currentNodeY;
			System.out.println("adding currentTempX " + currentTempX
					+ " current temp Y " + currentTempY + " prevTempX"
					+ prevTempX + " prevTexp Y" + prevTempY);
			oneCircle.positionCells.add(new CellNode(currentTempX,
					currentTempY, prevTempX, prevTempY, whoseCircle,
					trackingVistiedBoard, trackingInCirclePath));
			// returned valid next cell won't be the current cell's prev cell
			// so if returned something that is already in the path ....
			// we can confirm that there will be another circle .
		}
	}
	
	// //////////////////////////Count Capture
	// Algorithm//////////////////////////////////
	public int countCapturedCell(BoardDataCircleStructure circleOne)
	{
		return countCapturedNumber(
				circleOne,
				mergeCountBottomUpAndTopDown(countTopDown(circleOne),
						countBottomUp(circleOne)));
	}
	
	public int countCapturedNumber(BoardDataCircleStructure circleOne,
			int[][] mergedLevelValidation)
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
			{ // boardBody[y][x]
				if (BOARDBODY[level][j].equals(CAPTURED)
						&& mergedLevelValidation[level][j] == 1)
				{
					count++;
				}
			}
		}
		
		return count;
		
	}
	
	public int[][] countTopDown(BoardDataCircleStructure circleOne)
	{
		int[][] levelValidation = initializeLevelValidation();
		int numberOfCircleLevel = circleOne.circleLevel.length;
		// assume x and y array sorted properly by y and x
		// circleLevel[level][0] ----> x array , circleLevel[level][1] ----> y
		// array
		// skip first level by just put first level path info in
		int tempXlevelArray[] = (int[]) circleOne.circleLevel[0][0];
		// this temp Y level array will contain same value ....
		int tempYlevelArray[] = (int[]) circleOne.circleLevel[0][1];
		
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
				tempXlevelArray, 0, circleOne.circleOwner);
		
		for (int i = 1; i < numberOfCircleLevel - 1; i++)
		{
			tempXlevelArray = (int[]) circleOne.circleLevel[i][0];
			tempYlevelArray = (int[]) circleOne.circleLevel[i][1];
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
					tempXlevelArray, 0, circleOne.circleOwner);
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
					tempXlevelArray, 1, circleOne.circleOwner);
			
		}
		// put last level in
		tempXlevelArray = (int[]) circleOne.circleLevel[circleOne.circleLevel.length - 1][0];
		tempYlevelArray = (int[]) circleOne.circleLevel[circleOne.circleLevel.length - 1][1];
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
				tempXlevelArray, 0, circleOne.circleOwner);
		// skip last level by just put last level path info in
		System.out.println("TopDown Degbug: ");
		AidUtility.print2DintArray(levelValidation, 6);
		return levelValidation;
	}
	
	public int[][] countBottomUp(BoardDataCircleStructure circleOne)
	{
		// 1 means free, 0 means not valid , 2 means self cell,in future we mark
		// opponent as 3 and change it to '-' after merge
		int[][] levelValidation = initializeLevelValidation();
		int numberOfCircleLevel = circleOne.circleLevel.length;
		// assume x and y array sorted properly by y and x
		// circleLevel[level][0] ----> x array , circleLevel[level][1] ----> y
		// array
		// skip last level by just put first level path info in
		int tempXlevelArray[] = (int[]) circleOne.circleLevel[numberOfCircleLevel - 1][0];
		// this temp Y level array will contain same value ....
		int tempYlevelArray[] = (int[]) circleOne.circleLevel[numberOfCircleLevel - 1][1];
		System.out.println("out index?");
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
				tempXlevelArray, 0, circleOne.circleOwner);
		System.out.println("out index?");
		for (int i = numberOfCircleLevel - 2; i > 0; i--)
		{
			tempXlevelArray = (int[]) circleOne.circleLevel[i][0];
			tempYlevelArray = (int[]) circleOne.circleLevel[i][1];
			// put in boundary points of this level first
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
					tempXlevelArray, 0, circleOne.circleOwner);
			// tempYlevelArray[0] == current level's y value
			fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
					tempXlevelArray, 2, circleOne.circleOwner);
		}
		// put last level in
		tempXlevelArray = (int[]) circleOne.circleLevel[0][0];
		tempYlevelArray = (int[]) circleOne.circleLevel[0][1];
		fillLevelIntoValidation(levelValidation, tempYlevelArray[0],
				tempXlevelArray, 0, circleOne.circleOwner);
		// skip last level by just put last level path info in
		System.out.println("BottomUp Degbug: ");
		AidUtility.print2DintArray(levelValidation, 6);
		return levelValidation;
	}
	
	public int[][] mergeCountBottomUpAndTopDown(int[][] countTopDown,
			int[][] countBottomUp)
	{
		int[][] resultCountValidation = initializeLevelValidation();
		
		for (int i = 0; i < BOARDDIMENSION; i++)
		{
			for (int j = 0; j < BOARDDIMENSION; j++)
			{
				// one not valid == all not valid .
				if (countTopDown[i][j] == 0 || countBottomUp[i][j] == 0)
				{
					continue;
				}
				resultCountValidation[i][j] = 1;
			}
		}
		return resultCountValidation;
	}
	
	// following only for reading in board ... so we only determine 'captured'
	// cells .
	
	public void fillLevelIntoValidation(int[][] levelValidation,
			int levelIndex, int[] xPointArray, int conditionNumber,
			String whoseCircle)
	{
		if (conditionNumber == 0)
		{
			for (int i = 0; i < xPointArray.length; i++)
			{
				levelValidation[levelIndex][xPointArray[i]] = 1;
			}
		}
		// top down
		if (conditionNumber == 1)
		{ // only go between this level's max and min x-boundary .
			System.out.println("topdown in here");
			for (int i = xPointArray[0] + 1; i < xPointArray[xPointArray.length - 1]; i++)
			{
				// skip this level's boundary x and if above x is ==1 and this
				// board position marked captured ==> good
				if (levelValidation[levelIndex][i] != 1
						&& levelValidation[levelIndex - 1][i] != 0
						&& BOARDBODY[levelIndex][i].equals(CAPTURED))
				{
					levelValidation[levelIndex][i] = 1;
					
				}
				// meet self cell inside circle under ceiling ..still include it
				// to ceiling but mark it as 2
				if (levelValidation[levelIndex][i] != 1
						&& levelValidation[levelIndex - 1][i] != 0
						&& BOARDBODY[levelIndex][i].equals(whoseCircle))
				{
					levelValidation[levelIndex][i] = 2;
				}
				System.out.println(" sign : " + BOARDBODY[levelIndex][i]
						+ " check : "
						+ BOARDBODY[levelIndex][i].equals(CAPTURED));
			}
			System.out.println();
		}
		// bottom up
		if (conditionNumber == 2)
		{ // only go between this level's max and min x-boundary .
			// System.out.println("bottomup in here");
			for (int i = xPointArray[0] + 1; i < xPointArray[xPointArray.length - 1]; i++)
			{
				// skip this level's boundary x and if above x is ==1 and this
				// board position marked captured ==> good
				if (levelValidation[levelIndex][i] != 1
						&& levelValidation[levelIndex + 1][i] != 0
						&& BOARDBODY[levelIndex][i].equals(CAPTURED))
				{
					levelValidation[levelIndex][i] = 1;
				}
				if (levelValidation[levelIndex][i] != 1
						&& levelValidation[levelIndex + 1][i] != 0
						&& BOARDBODY[levelIndex][i].equals(whoseCircle))
				{
					levelValidation[levelIndex][i] = 2;
				}
				// System.out.println (" sign : "+boardBody[levelIndex][i]);
			}
			// System.out.println();
		}
	}
	
	public int[][] initializeLevelValidation()
	{
		int levelValidation[][] = new int[BOARDDIMENSION][BOARDDIMENSION];
		for (int i = 0; i < BOARDDIMENSION; i++)
		{
			for (int j = 0; j < BOARDDIMENSION; j++)
			{
				levelValidation[i][j] = 0;
			}
		}
		return levelValidation;
	}
	
	public int[][] initialize2Darray(int initialValue)
	{
		int levelValidation[][] = new int[BOARDDIMENSION][BOARDDIMENSION];
		for (int i = 0; i < BOARDDIMENSION; i++)
		{
			for (int j = 0; j < BOARDDIMENSION; j++)
			{
				levelValidation[i][j] = initialValue;
			}
		}
		return levelValidation;
	}
	
	// //////////////////////////Count Capture
	// Algorithm//////////////////////////////////
	
	// no matter how big is the board the max surrounding is 8 which is 8
	// directions
	// haha worst case is 2N^2
	// index 0 = x = x, 1= y = y
	// cycles are sorted by x first then by y , so first point is the most top
	// left of cycle
	//
	// @SuppressWarnings("unchecked")
	// public int countCapturedCell(BoardDataCycleStructure cycleOne)
	// {
	// //assumed they are all sorted by x and y
	// int numberOflevel=cycleOne.cycleLevel.length;
	// //CeilingDataStructure ceiling = new CeilingDataStructure();
	// ArrayList<Integer> ceilingX = new ArrayList<Integer>();
	// int[] ceilingXtempIntArray=null;
	// int[] ceilingYtempIntArray=null;
	// ArrayList<Integer> ceilingY = new ArrayList<Integer>();
	// ArrayList<Integer> tempCeilingX = new ArrayList<Integer>();
	// ArrayList<Integer> tempCeilingY = new ArrayList<Integer>();
	// int minX=0;
	// int maxX=0;
	// int minY=0;
	// int moveY=0;
	// int capturedNumber=0;
	// int signalNum=-1;
	// //first level.
	// AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[0][0],ceilingX);
	// AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[0][1],ceilingY);
	// //test
	// // System.out.println("ceiling :");
	// // AidUtility.printPositionArrayList(ceilingX,ceilingY);
	// // System.out.println("ceiling end");
	//
	// //start from second level
	// for(int i=1;i<numberOflevel;i++)
	// {
	// System.out.println("hello i am here");
	// //assuming sorted properly
	// AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[i][0],tempCeilingX);
	// AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[i][1],tempCeilingY);
	// //same level if it is sorted by y and x , lowest x and y will be on left
	// most
	// //and vise versa
	// //temp ceiling is for current level , we use previse level ceiling to
	// count captured
	// minX=tempCeilingX.get(0);
	// maxX=tempCeilingX.get(tempCeilingX.size()-1);
	// //all y are same in this level can use any of them
	// moveY=tempCeilingY.get(0);
	// //moving horizontally at this level
	// for (int moveX=minX;moveX<=maxX;moveX++)
	// {
	// //System.out.println("check moveX: "+moveX+"check MoveY"+moveY+" signal"+boardBody[moveY][moveX]);
	// signalNum=checkCellCaptured(moveX,moveY,boardBody[moveY][moveX],cycleOne.cycleOwner,ceilingX,tempCeilingX,tempCeilingY);
	// if (signalNum>0)
	// {
	// tempCeilingX.add(moveX);
	// tempCeilingY.add(moveY);
	// if(signalNum==1)
	// capturedNumber++;
	// }
	// //System.out.println("above signal num :" +signalNum);
	// }
	// ceilingXtempIntArray=AidUtility.parseIntArrayList(tempCeilingX);
	// ceilingYtempIntArray=AidUtility.parseIntArrayList(tempCeilingY);
	//
	// AidUtility.sortByYandX(ceilingXtempIntArray, ceilingYtempIntArray);
	//
	//
	//
	// ceilingX=AidUtility.parseIntListToArrayList(ceilingXtempIntArray);
	// ceilingY=AidUtility.parseIntListToArrayList(ceilingYtempIntArray);
	// signalNum=-1;
	// tempCeilingX.clear();
	// tempCeilingY.clear();
	// }
	// return capturedNumber;
	//
	//
	// }
	//
	// //if x matched previous ceiling's x then everything is good
	// //every level from cycle x should be unique ...
	// public int checkCellCaptured(int moveX,int moveY,String signOnCell,String
	// playerSign,ArrayList<Integer>ceilingX,ArrayList<Integer>tempCeilingX,ArrayList<Integer>tempCeilingY)
	// {
	// //System.out.println("here singOncell: "+signOnCell+" playerSign:"+playerSign+" moveX:"+moveX);
	// //AidUtility.printPointArrayList(ceilingX);
	// //System.out.println("sign ? : "+signOnCell.equals(playerSign));
	// //if not equal to captured, or it is equal to owner's sign false
	// // -1 = captured
	// //basically we want all cell under last ceiling
	// // 1 means cell under ceiling and can be count as captured
	// // 2 mean
	// // -1 means nothing to add
	// //if free means no who captured
	// if (signOnCell.equals(FREE))
	// {
	// System.out.println("abort in here");
	// return -1;
	// }
	//
	// for(int i =0 ; i<tempCeilingX.size();i++)
	// {
	// //already in temp ceiling.
	// if(moveX==tempCeilingX.get(i)&&moveY==tempCeilingY.get(i))
	// {
	// return -2;
	// }
	// }
	//
	// //if current cell level within circle has same x with previous ceiling X
	// them BINGO!
	// // only FREE or W or B come here
	// for(int oneCeilingX:ceilingX)
	// {
	// // under ceiling and it is free so tell captured up one and add it to new
	// ceiling
	// if(moveX==oneCeilingX&&signOnCell.equals(CAPTURED))
	// {
	// return 1;
	// }
	//
	// if(moveX==oneCeilingX&&(!signOnCell.equals(playerSign)))
	// {
	// return 1;
	// }
	//
	// // under ceiling but captured does not up one, add it to new ceiling
	// if(moveX==oneCeilingX&&signOnCell.equals(playerSign))
	// {
	// return 2;
	// }
	//
	// }
	// System.out.println("true in checkCellCaptured");
	// return -3;
	// }
	
	// remember to write 8 directions
	// use a hash map to declear which cycle is for which player ?
	
}
