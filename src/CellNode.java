import java.util.ArrayList;

public class CellNode 
{
	int prevNodeX;
	int prevNodeY;
	int currentNodeX;
	int currentNodeY;
	int nextNodeX;
	int nextNodeY;
	String whoseCell=null;
	
	ArrayList<Integer> validAdjCellX=null;
	ArrayList<Integer> validAdjCellY=null;
	
	CellNode(int currentNodeX,int currentNodeY,int prevNodeX,int prevNodeY,String whoseCircle,int[][] trackingBoard)
	{
		this.currentNodeX=currentNodeX;
		this.currentNodeY=currentNodeY;
		this.prevNodeX=prevNodeX;
		this.prevNodeY=prevNodeY;
		validAdjCellX=new ArrayList<Integer>();
		validAdjCellY=new ArrayList<Integer>();
		fillValidAdjcentCell(whoseCircle,trackingBoard);
//		System.out.println("x y adj array before choosing clockwise cell");
//		AidUtility.printPositionArrayList(validAdjCellX, validAdjCellY);
//		System.out.println("next clockwise cell");
//		int[] tempClockwiseXY=mostClockwisedCellIndex();
//		System.out.print("next x: "+tempClockwiseXY[0]+" next y: "+tempClockwiseXY[1]);
//		System.out.println("");
//		System.out.println("x y adj array after choosing clockwise cell");
//		AidUtility.printPositionArrayList(validAdjCellX, validAdjCellY);
		
	}
	
	public int getNextNodeX() 
	{
		return nextNodeX;
	}

	public void setNextNodeX(int nextNodeX) 
	{
		this.nextNodeX = nextNodeX;
	}

	public int getNextNodeY() 
	{
		return nextNodeY;
	}

	public void setNextNodeY(int nextNodeY)
	{
		this.nextNodeY = nextNodeY;
	}
	
	public int[] getValidNextCellClockwise()
	{
		//0---> x, 1--->y
		int[] validCell=new int[2];
		
		return validCell;
	}
	
	public void fillValidAdjcentCell(String whoseCircle,int [][] trackingBoard)
	{
		// 8 directions adj cell from current node .
		int numberOfDirections = 8;
		int tempDirectionX;
		int tempDirectionY;
		int tempX;
		int tempY;
		//ArrayList<Integer> tempValidAdjacentX=null;
		//ArrayList<Integer> tempValidAdjacentY=null;
		//clock wise ,(x,y)
		//int[][] directionLists=new int[8][2]{[-1,-1],[0,-1],[1,-1],[1,0],[1,1],[0,-1],[-1,1],[-1,0]};
		int[][] directionList=new int[][]{{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,-1},{-1,1},{-1,0}};
		for (int i=0;i<numberOfDirections;i++)
		{
			tempDirectionX=directionList[i][0];
			tempDirectionY=directionList[i][1];
			tempX=currentNodeX+tempDirectionX;
			tempY=currentNodeY+tempDirectionY;
			System.out.println("direction tempx : "+tempX+" direction tempY: "+tempY);
			if(tempX<0||tempY<0||tempX>=Board.BOARDDIMENSION||tempY>=Board.BOARDDIMENSION)
			{
				continue;
			}
			//visited adj nodes will be eliminated 
			if(Board.BOARDBORDY[tempY][tempX].equals(whoseCircle)&&trackingBoard[tempY][tempX]!=1)
			{
				// with respect to current node .. all its surrounding which is not exceed board limites will be added start from top left
				// in a clock wise direction to tempValidAdjacent
				//and it should be same colour of starting one of 'circle'. 
				validAdjCellX.add(tempX);
				validAdjCellY.add(tempY);
			}
		}
	}
	//return position of prev cells in the current adj array 
	
	public int prevIndexInValidAdj()
	{
		int index=-1;
		for(int i=0;i<validAdjCellX.size();i++)
		{
			if(prevNodeX==validAdjCellX.get(i) && prevNodeY==validAdjCellY.get(i))
			{
				index=i;
				return index;
			}
		}
		return index;
	}
	
	public int[] mostClockwisedCellIndex()
	{
		//not valid adjacent cell
		int validIndex=-1;
		int tempX;
		int tempY;
		boolean flag=false;
		int[] validXY=new int[2];
		//only prev left in array ...or adj has not valid .
		if (validAdjCellX.size()<=1)
		{
			return null;
		}
		
		validIndex=prevIndexInValidAdj()+1;
		System.out.println("validindex : "+validIndex+"valid x point "+validAdjCellX.get(validIndex)+" valid y point: "+validAdjCellY.get(validIndex));
		//adj valid is only the preveous visited node
		
		//prev at the end of array ...so we want it just to first one.
		if(validIndex==validAdjCellX.size())
		{
			validIndex=0;
			//valid clockwize x --->0 ,y--->1
			validXY[0]=validAdjCellX.get(validIndex);
			validXY[1]=validAdjCellY.get(validIndex);
			//removed it from valid adj array
			validAdjCellX.remove(validIndex);
			validAdjCellY.remove(validIndex);
			return validXY;
		}
		validXY[0]=validAdjCellX.get(validIndex);
		validXY[1]=validAdjCellY.get(validIndex);
		validAdjCellX.remove(validIndex);
		validAdjCellY.remove(validIndex);
		return validXY;
	}
	
}
