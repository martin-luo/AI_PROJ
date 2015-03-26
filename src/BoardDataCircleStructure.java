import java.util.ArrayList;

//used to store cycle data
public class BoardDataCircleStructure 
{
	//black or white
	public String circleOwner="";
	int capturedCellNumber=0;
	int circlePointsNumber=0;
	//used to stored points which formed the cycle
	//they are all sorted
	public int[] xPointArray=null;
	public int[] yPointArray=null;
	public ArrayList<CellNode> positionCells=new ArrayList<CellNode>();
	//usede to store points which captured within this cycle
	public int[] capturedxPointArray=null;
	public int[] capturedyPointArray=null;
	//cast to int[]
	public Object[][] circleLevel =null; 
	
	BoardDataCircleStructure()
	{
		
	}
	
	
	BoardDataCircleStructure(String circleOwner)
	{
		this.circleOwner=circleOwner;
	}

	
	BoardDataCircleStructure(String circleOwner,int []xPointArray,int []yPointArray)
	{
		this.circleOwner = circleOwner;
		this.xPointArray = xPointArray;
		this.yPointArray= yPointArray;
	}
	
	BoardDataCircleStructure(String cycleOwner,ArrayList<Integer>xPointArrayList,ArrayList<Integer> yPointArrayList)
	{
		this.circleOwner = cycleOwner;
		this.xPointArray = xPointArray;
		this.yPointArray= yPointArray;
	}
	
	public void constructLevel()
	{
		AidUtility.sortByYandX(xPointArray, yPointArray);
		System.out.println("debugging array");
		AidUtility.printPositionArray(xPointArray, yPointArray);
		int[] tempLevelPointY= AidUtility.getUniquePointInArray(yPointArray);
		ArrayList<Integer> tempX = new ArrayList<Integer>();
		ArrayList<Integer> tempY = new ArrayList<Integer>();
		//System.out.println("tempLevel:");
		//AidUtility.printPointArray(tempLevelPointY);
		circleLevel = new Object[tempLevelPointY.length][2];
		for (int i =0;i<tempLevelPointY.length;i++)
		{
			for(int j = 0 ; j<yPointArray.length;j++)
			{
				if(tempLevelPointY[i]==yPointArray[j])
				{
					tempX.add(xPointArray[j]);
					tempY.add(yPointArray[j]);
				}
			}
			//0 = level x array, 1= y array,  need to be casted 
			circleLevel[i][0]=AidUtility.parseIntArrayList(tempX);
			circleLevel[i][1]=AidUtility.parseIntArrayList(tempY);
			tempX.clear();
			tempY.clear();
		}
		System.out.println("circle array");
		printCircleLevel();
	}
	
	
	public void printCircleLevel()
	{
		for (int i =0;i<circleLevel.length;i++)
		{
			AidUtility.printPositionArray((int[])circleLevel[i][0],(int[])circleLevel[i][1]);
			//System.out.println("----");
		}
	}
	
	
	public void transformCellNodeToIntArray()
	{
		CellNode tempCellNode = null;
		xPointArray=new int[positionCells.size()];
		yPointArray=new int[positionCells.size()];
		System.out.println("transforming");
		for(int i=0;i<positionCells.size();i++)
		{
			tempCellNode=positionCells.get(i);
			xPointArray[i]=tempCellNode.currentNodeX;
			yPointArray[i]=tempCellNode.currentNodeY;
		}
	}
	
	
}
