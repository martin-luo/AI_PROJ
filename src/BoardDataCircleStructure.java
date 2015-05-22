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

//used to store cycle data
public class BoardDataCircleStructure
{
	// black or white
	public String circleOwner = "";
	int capturedCellNumber = 0;
	int circlePointsNumber = 0;
	// used to stored points which formed the cycle
	// they are all sorted
	public int[] xPointArray = null;
	public int[] yPointArray = null;
	public ArrayList<CellNode> positionCells = new ArrayList<CellNode>();
	// usede to store points which captured within this cycle
	public int[] capturedxPointArray = null;
	public int[] capturedyPointArray = null;
	// cast to int[]
	public Object[][] circleLevel = null;

	/**
	 * Initialize BoardDataCircleStructure obj
	 */

	BoardDataCircleStructure()
	{

	}

	/**
	 * Initialize BoardDataCircleStructure obj
	 * 
	 * @param circleOnwer
	 *            it specifies which onwer is current circle
	 */
	BoardDataCircleStructure(String circleOwner)
	{
		this.circleOwner = circleOwner;
	}

	/**
	 * Initialize BoardDataCircleStructure obj
	 * 
	 * @param xPointArray
	 *            it set the col values for this circle
	 * @param yPointArray
	 *            it set the row valus for this circle
	 */

	BoardDataCircleStructure(String circleOwner, int[] xPointArray, int[] yPointArray)
	{
		this.circleOwner = circleOwner;
		this.xPointArray = xPointArray;
		this.yPointArray = yPointArray;
	}

	/**
	 * Initialize BoardDataCircleStructure obj
	 * 
	 * @param xPointArray
	 *            it set the col values for this circle
	 * @param yPointArray
	 *            it set the row valus for this circle
	 */

	BoardDataCircleStructure(String cycleOwner, ArrayList<Integer> xPointArrayList, ArrayList<Integer> yPointArrayList)
	{
		this.circleOwner = cycleOwner;
		this.xPointArray = xPointArray;
		this.yPointArray = yPointArray;
	}

	/**
	 * this function construct level of this circle
	 */

	public void constructLevel()
	{
		AidUtility.sortByYandX(xPointArray, yPointArray);
		// System.out.println("debugging array");
		// AidUtility.printPositionArray(xPointArray, yPointArray);
		int[] tempLevelPointY = AidUtility.getUniquePointInArray(yPointArray);
		ArrayList<Integer> tempX = new ArrayList<Integer>();
		ArrayList<Integer> tempY = new ArrayList<Integer>();
		// System.out.println("tempLevel:");
		// AidUtility.printPointArray(tempLevelPointY);
		circleLevel = new Object[tempLevelPointY.length][2];
		for (int i = 0; i < tempLevelPointY.length; i++)
		{
			for (int j = 0; j < yPointArray.length; j++)
			{
				// put col value of same y into tempX and tempY
				if (tempLevelPointY[i] == yPointArray[j])
				{
					tempX.add(xPointArray[j]);
					tempY.add(yPointArray[j]);
				}
			}
			// 0 = level x array, 1= y array, need to be casted
			// add same level points into circlelevel
			circleLevel[i][0] = AidUtility.parseIntArrayList(tempX);
			circleLevel[i][1] = AidUtility.parseIntArrayList(tempY);
			tempX.clear();
			tempY.clear();
		}
		// System.out.println("circle array");
		// printCircleLevel();
	}

	/**
	 * this function print out level of this circle
	 */
	public void printCircleLevel()
	{
		for (int i = 0; i < circleLevel.length; i++)
		{
			AidUtility.printPositionArray((int[]) circleLevel[i][0], (int[]) circleLevel[i][1]);
			// System.out.println("----");
		}
	}

	/**
	 * this function transform CellNode data to normal array
	 */
	public void transformCellNodeToIntArray()
	{
		CellNode tempCellNode = null;
		xPointArray = new int[positionCells.size()];
		yPointArray = new int[positionCells.size()];
		// System.out.println("transforming");
		for (int i = 0; i < positionCells.size(); i++)
		{
			tempCellNode = positionCells.get(i);
			xPointArray[i] = tempCellNode.currentNodeX;
			yPointArray[i] = tempCellNode.currentNodeY;
		}
	}

}
