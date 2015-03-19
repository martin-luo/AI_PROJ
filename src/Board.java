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
	//remember boardBodu[y][x]
	String[][] boardBody=null;
	BoardDataCircleStructure collectionsOfCircle[] = null;
	
	
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
		fillBoardBody(tempStringArray);
		
		printBoardBody(boardBody);
	}
	//remembe bo
	//transform 1D array of 'signs' to 2d array
	public void fillBoardBody(ArrayList<String> tempStringArray)
	{
		int x=0,y=0;
		for(String tempString : tempStringArray)
		{
			boardBody[y][x++]=tempString;
			if (x>=boardDimension)
			{
				y++;
				x=0;
			}
		}
	}
	
	public void printBoardBody(String boardBody[][])
	{
		for (int i=0 ;i<boardDimension;i++)
		{
			for(int j=0;j<boardDimension;j++)
			{
				System.out.print(boardBody[i][j]+"(x:"+j+" y:"+i+")");
			}
			System.out.println();
		}
	}
	
	public boolean isFreeCell(int y,int x)
	{
		return boardBody[y][x].equals(FREE);
	}
	
	public boolean isCapturedCell(int y,int x)
	{
		return boardBody[y][x].equals(CAPTURED);
	}
	
	public boolean isBlackCell(int y,int x)
	{
		return boardBody[y][x].equals(BLACK);
	}
	
	public boolean isWhiteCell(int y,int x)
	{
		return boardBody[y][x].equals(WHITE);
	}
	
	public int[][] countTopDown(BoardDataCircleStructure circleOne)
	{
		int[][] levelValidation = initializeLevelValidation();
		int numberOfCircleLevel = circleOne.circleLevel.length;
		//assume x and y array sorted properly by y and x
		//circleLevel[level][0] ----> x array , circleLevel[level][1] ----> y array
		//skip first level by just put first level path info in
		int tempXlevelArray[]=(int [])circleOne.circleLevel[0][0];
		//this temp Y level array will contain same value ....
		int tempYlevelArray[]=(int [])circleOne.circleLevel[0][1];
		
		fillLevelIntoValidation(levelValidation,tempYlevelArray[0],tempXlevelArray);
		
		for(int i=1;i<numberOfCircleLevel-1;i++)
		{
			tempXlevelArray=(int [])circleOne.circleLevel[i][0];
			tempYlevelArray=(int [])circleOne.circleLevel[i][1];
			fillLevelIntoValidationWithCondition(levelValidation,tempYlevelArray[0],tempXlevelArray);
			
		}
		//put last level in 
		tempXlevelArray=(int [])circleOne.circleLevel[circleOne.circleLevel.length-1][0];
		tempYlevelArray=(int [])circleOne.circleLevel[circleOne.circleLevel.length-1][1];
		fillLevelIntoValidation(levelValidation,tempYlevelArray[0],tempXlevelArray);
		
		//skip last level by just put last level path info in
		
		return levelValidation;
		
	}
	//following only for reading in board ... so we only determine 'captured' cells .
	
	public void fillLevelIntoValidation(int[][] levelValidation,int levelIndex,int[] xPointArray,int conditionNumber)
	{
		if (conditionNumber==0)
		{
			for(int i=0;i<xPointArray.length;i++)
			{
				levelValidation[levelIndex][xPointArray[i]]=1;
			}
		}
		
		if (conditionNumber==1)
		{
			for(int i=0;i<xPointArray.length;i++)
			{
				if(levelValidation[levelIndex-1][xPointArray[i]]==1&&boardBody[levelIndex][xPointArray[i]]==CAPTURED)
				{
					levelValidation[levelIndex][xPointArray[i]]=1;
				}
			}
		}
	}
	
	public int[][] initializeLevelValidation()
	{
		int levelValidation[][]=new int[boardDimension][boardDimension];
		for (int i=0;i<boardDimension;i++)
		{
			for(int j=0;j<boardDimension;j++)
			{
				levelValidation[i][j]=0;
			}
		}
		return levelValidation;
	}
	
	
	
	//no matter how big is the board the max surrounding is 8 which is 8 directions 
	//haha worst case is 2N^2
	//index 0 = x = x, 1= y = y
	//cycles are sorted by x first then by y , so first point is the most top left of cycle
//
//	@SuppressWarnings("unchecked")
//	public int countCapturedCell(BoardDataCycleStructure cycleOne)
//	{
//		//assumed they are all sorted by x and y
//		int numberOflevel=cycleOne.cycleLevel.length;
//		//CeilingDataStructure ceiling = new CeilingDataStructure();
//		ArrayList<Integer> ceilingX = new ArrayList<Integer>();
//		int[] ceilingXtempIntArray=null;
//		int[] ceilingYtempIntArray=null;
//		ArrayList<Integer> ceilingY = new ArrayList<Integer>();
//		ArrayList<Integer> tempCeilingX = new ArrayList<Integer>();
//		ArrayList<Integer> tempCeilingY = new ArrayList<Integer>();
//		int minX=0;
//		int maxX=0;
//		int minY=0;
//		int moveY=0;
//		int capturedNumber=0;
//		int signalNum=-1;
//		//first level.
//		AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[0][0],ceilingX);
//		AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[0][1],ceilingY);
//		//test
////		System.out.println("ceiling :");
////		AidUtility.printPositionArrayList(ceilingX,ceilingY);
////		System.out.println("ceiling end");
//		
//		//start from second level
//		for(int i=1;i<numberOflevel;i++)
//		{
//			System.out.println("hello i am here");
//			//assuming sorted properly
//			AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[i][0],tempCeilingX);
//			AidUtility.insertIntListToArrayList((int[])cycleOne.cycleLevel[i][1],tempCeilingY);
//			//same level if it is sorted by y and x , lowest x and y will be on left most 
//			//and vise versa
//			//temp ceiling is for current level , we use previse level ceiling to count captured
//			minX=tempCeilingX.get(0);
//			maxX=tempCeilingX.get(tempCeilingX.size()-1);
//			//all y are same in this level can use any of them
//			moveY=tempCeilingY.get(0);
//			//moving horizontally at this level
//			for (int moveX=minX;moveX<=maxX;moveX++)
//			{
//				//System.out.println("check moveX: "+moveX+"check MoveY"+moveY+" signal"+boardBody[moveY][moveX]);
//				signalNum=checkCellCaptured(moveX,moveY,boardBody[moveY][moveX],cycleOne.cycleOwner,ceilingX,tempCeilingX,tempCeilingY);
//				if (signalNum>0)
//				{
//					tempCeilingX.add(moveX);
//					tempCeilingY.add(moveY);
//					if(signalNum==1)
//						capturedNumber++;
//				}
//				//System.out.println("above signal num :" +signalNum);
//			}
//			ceilingXtempIntArray=AidUtility.parseIntArrayList(tempCeilingX);
//			ceilingYtempIntArray=AidUtility.parseIntArrayList(tempCeilingY);
//			
//			AidUtility.sortByYandX(ceilingXtempIntArray, ceilingYtempIntArray);
//			
//			
//			
//			ceilingX=AidUtility.parseIntListToArrayList(ceilingXtempIntArray);
//			ceilingY=AidUtility.parseIntListToArrayList(ceilingYtempIntArray);
//			signalNum=-1;
//			tempCeilingX.clear();
//			tempCeilingY.clear(); 
//		}
//		return capturedNumber;
//		
//		
//	}
//	
//	//if x matched previous ceiling's x then everything is good
//	//every level from cycle x should be unique ...
//	public int checkCellCaptured(int moveX,int moveY,String signOnCell,String playerSign,ArrayList<Integer>ceilingX,ArrayList<Integer>tempCeilingX,ArrayList<Integer>tempCeilingY)
//	{
//		//System.out.println("here singOncell: "+signOnCell+" playerSign:"+playerSign+" moveX:"+moveX);
//		//AidUtility.printPointArrayList(ceilingX);
//		//System.out.println("sign ? : "+signOnCell.equals(playerSign));
//		//if not equal to captured, or it is equal to owner's sign false
//		// -1 = captured 
//		//basically we want all cell under  last ceiling
//		// 1 means cell under ceiling and can be count as captured
//		// 2 mean
//		// -1 means nothing to add
//		//if free means no who captured
//		if (signOnCell.equals(FREE))
//		{
//			System.out.println("abort in here");
//			return -1;
//		}
//		
//		for(int i =0 ; i<tempCeilingX.size();i++)
//		{
//			//already in temp ceiling.
//			if(moveX==tempCeilingX.get(i)&&moveY==tempCeilingY.get(i))
//			{
//				return -2;
//			}
//		}
//		
//		//if current cell level within circle has same x with previous ceiling X them BINGO!
//		// only FREE or W or B come here
//		for(int oneCeilingX:ceilingX)
//		{
//			// under ceiling and it is free so tell captured up one and add it to new ceiling
//			if(moveX==oneCeilingX&&signOnCell.equals(CAPTURED))
//			{
//				return 1;
//			}
//			
//			if(moveX==oneCeilingX&&(!signOnCell.equals(playerSign)))
//			{
//				return 1;
//			}
//			
//			// under ceiling but captured does not up one, add it to new ceiling
//			if(moveX==oneCeilingX&&signOnCell.equals(playerSign))
//			{
//				return 2;
//			}
//			
//		}
//		System.out.println("true in checkCellCaptured");
//		return -3;
//	}
	
	//remember to write 8 directions
	//use a hash map to declear which cycle is for which player ?
	

	
}
