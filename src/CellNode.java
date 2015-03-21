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

	
	
	CellNode(int currentNodeX,int currentNodeY,int prevNodeX,int prevNodeY)
	{
		this.currentNodeX=currentNodeX;
		this.currentNodeY=currentNodeY;
		this.prevNodeX=prevNodeX;
		this.prevNodeY=prevNodeY;
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
	
	public void fillValidAdjcentCell(String whoseCircle)
	{
		// 8 directions adj cell from current node .
		int numberOfDirections = 8;
		int tempX;
		int tempY;
		ArrayList<Integer> tempValidAdjacentX=null;
		ArrayList<Integer> tempValidAdjacentY=null;
		//clock wise ,(x,y)
		//int[][] directionLists=new int[8][2]{[-1,-1],[0,-1],[1,-1],[1,0],[1,1],[0,-1],[-1,1],[-1,0]};
		int[][] directionList=new int[][]{{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,-1},{-1,1},{-1,0}};
		for (int i=0;i<numberOfDirections;i++)
		{
			tempX=directionList[i][0];
			tempY=directionList[i][1];
			if(currentNodeX+tempX<0||currentNodeY+tempY<0||currentNodeX+tempX>=Board.BOARDDIMENSION||currentNodeY+tempY>=Board.BOARDDIMENSION)
			{
				continue;
			}
			
			if(Board.BOARDBORDY[tempY][tempX].equals(whoseCircle))
			{
				// with respect to current node .. all its surrounding which is not exceed board limites will be added start from top left
				// in a clock wise direction to tempValidAdjacent
				//and it should be same colour of starting one of 'circle'. 
				tempValidAdjacentX.add(currentNodeX+tempX);
				tempValidAdjacentY.add(currentNodeY+tempY);
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
	
	public int mostClockwisedCellIndex()
	{
		//not valid adjacent cell
		int validIndex=-1;
		int tempX;
		int tempY;
		boolean flag=false;
		//only prev left in array ...or adj has not valid .
		if (validAdjCellX.size()<=1)
		{
			return validIndex;
		}
		
		validIndex=prevIndexInValidAdj()+1;
		//adj valid is only the preveous visited node
		
		//prev at the end of array ...so we want it just to first one.
		if(validIndex==validAdjCellX.size())
		{
			validIndex=0;
			return validIndex;
		}
		return validIndex;
	}
	
}
