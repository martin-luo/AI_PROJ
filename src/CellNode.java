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
	
	public void fillValidAdjcentCell()
	{
		// 8 directions adj cell from current node .
		int numberOfDirections = 8;
		int tempX=currentNodeX;
		int tempY=currentNodeY;
		ArrayList<Integer> tempValidAdjacentX=null;
		ArrayList<Integer> tempValidAdjacentY=null;
		//clock wise 
		//int[][] directionLists=new int[8][2]{[-1,-1],[0,-1],[1,-1],[1,0],[1,1],[0,-1],[-1,1],[-1,0]};
		int[][] directionLists=new int[][]{{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,-1},{-1,1},{-1,0}};
		for (int i=0;i<numberOfDirections;i++)
		{
			
		}
	}
	
}
