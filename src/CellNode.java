import java.util.ArrayList;

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

public class CellNode 
{
	int prevNodeX;
	int prevNodeY;
	int currentNodeX;
	int currentNodeY;
	int nextNodeX;
	int nextNodeY;
	int boardDimension;
	String whoseCell=null;
	String[][] boardBody=null;
	
	ArrayList<Integer> validAdjCellX=null;
	ArrayList<Integer> validAdjCellY=null;
	
	/**
	 * Used to initialize the CellNode
	 * @param currentNodeX this node's X
	 * @param currentNodeY this node's y
	 * @param prevNodeX its prev x
	 * @param prevNodeY its prev y
	 * @param whoseCircle onwer of this circle
	 * @param boardDimension 
	 * @param boardBody
	 */
	
	CellNode(int currentNodeX,int currentNodeY,int prevNodeX,int prevNodeY,String whoseCircle,int boardDimension,String[][] boardBody)
	{
		this.boardDimension=boardDimension;
		this.boardBody=boardBody;
		this.currentNodeX=currentNodeX;
		this.currentNodeY=currentNodeY;
		this.prevNodeX=prevNodeX;
		this.prevNodeY=prevNodeY;
		validAdjCellX=new ArrayList<Integer>();
		validAdjCellY=new ArrayList<Integer>();
		fillValidAdjcentCell(whoseCircle);
		
	}
	
	/**
	 * this function is used to return nextNodeX 
	 * 
	 * @return nextNodeX
	 */
	
	public int getNextNodeX() 
	{
		return nextNodeX;
	}
	
	/**
	 * this function is used to set nextNodeX 
	 * 
	 * @param nextNodeX
	 */

	public void setNextNodeX(int nextNodeX) 
	{
		this.nextNodeX = nextNodeX;
	}

	/**
	 * this function is used to return nextNodeY
	 * 
	 * @return nextNodeY
	 */
	
	public int getNextNodeY() 
	{
		return nextNodeY;
	}

		
	/**
	 * this function is used to set nextNodeY
	 * 
	 * @param nextNodeX
	 */
	public void setNextNodeY(int nextNodeY)
	{
		this.nextNodeY = nextNodeY;
	}
	
	/**
	 * this function is used to return set of validCell x and y
	 * 
	 * @return validCell
	 */
	
	public int[] getValidNextCellClockwise()
	{
		//0---> x, 1--->y
		int[] validCell=new int[2];
		
		return validCell;
	}
	
	/**
	 * this function is used to generate current cell's 8 adjacent cell 
	 * 
	 * @param whoseCircle is used to determine who is the owner of this circle 
	 */
	
	public void fillValidAdjcentCell(String whoseCircle)
	{
		// 8 directions adj cell from current node .
		//int numberOfDirections = 8;
		int tempDirectionX;
		int tempDirectionY;
		int tempX;
		int tempY;
		//clock wise ,(x,y)
		//8 directions 
		int[][] directionList=new int[][]{{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0}};
		//iterate through 8 directions
		for (int i=0;i<directionList.length;i++)
		{
			tempDirectionX=directionList[i][0];
			tempDirectionY=directionList[i][1];
			tempX=currentNodeX+tempDirectionX;
			tempY=currentNodeY+tempDirectionY;
			if(tempX<0||tempY<0||tempX>=boardDimension||tempY>=boardDimension)
			{
				continue;
			}
			//8 direction which is same color as the starting cell
			//System.out.println("tempX = "+tempX+"temp Y = "+tempY);
			//System.out.println(""+"current x = "+currentNodeX+"current y = "+currentNodeY+""+(boardBody[tempY][tempX].equals(whoseCircle))+" sign "+boardBody[tempY][tempX]);
			if(boardBody[tempY][tempX].equals(whoseCircle))
			{
				// with respect to current node .. all its surrounding which is not exceed board limites will be added start from top left
				// in a clock wise direction to tempValidAdjacent
				//and it should be same colour of starting one of 'circle'. 
				validAdjCellX.add(tempX);
				validAdjCellY.add(tempY);
				//System.out.println("current x = "+currentNodeX+"current y = "+currentNodeY+"tempX = "+tempX+"temp Y = "+tempY+" Sign= "+boardBody[tempX][tempY]);
			}
			
		}
	}
	//return position of prev cells in the current adj array 
	
	/**
	 * clockwise is based on this cell's previous cell ,so this function get index of prev cell in 8 directions cell array
	 * 
	 * @return index of prev cell in 8 directions array 
	 */
	
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
	
	/**
	 * get the valid most clockwise adjacent cell available form current cell
	 * 
	 * @return vadlidXY its first index is valid adjacent cell's x , second index is y
	 */
	
	public int[] mostClockwisedCellIndex()
	{
		//not valid adjacent cell
		int validIndex=-1;
		boolean flag=false;
		int[] validXY=new int[2];
		//only prev left in array ...or adj has not valid .
		if (validAdjCellX.size()<=1)
		{
			return null;
		}
		
		validIndex=prevIndexInValidAdj()+1;
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
