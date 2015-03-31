/**
 * This is a part of the project of COMP30024 Artificial Intelligence, the University of Melbourne. The project is the Game of Squatter and is a group work, the members of the group is list below, so is the rule of the game.
 */

/**
 * <b>Class Declaration</b>
 * <p>
 * This class is used to store the structure of ceiling data.
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
 * @author Bingfeng Liu (bingfengl@student.unimelb.edu.au)
 * @author An Luo (aluo1@student.unimelb.edu.au)
 * @version 2.0
 * @since 2015-03-30
 */
public class CeilingDataStructure
{
	/** The current size of ceiling. */
	int ceilingCurrentSize;
	/** The index of ceiling. */
	int ceilingIndex;
	/** The old index of ceiling. */
	int ceilingOldIndex;
	/** The array store the row of ceiling. */
	int[] ceilingRowArray;
	/** The array store the column of ceiling. */
	int[] ceilingColArray;
	
	/**
	 * This constructor take no argument and build nothing.
	 */
	CeilingDataStructure()
	{
		
	}
	
	/**
	 * This class initialize the instance of this class to the given value.
	 * 
	 * @param ceilingIndex the value the variable ceilingIndex will be set to.
	 * @param ceilingRowArray the value the variable ceilingRowArray will be set to.
	 * @param ceilingColArray the value the variable ceilingColArray will be set to.
	 * @param rowPointArray 
	 */
	CeilingDataStructure(int ceilingIndex, int[] ceilingRowArray, int[] ceilingColArray, int[] rowPointArray)
	{
		this.ceilingIndex = ceilingIndex;
		this.ceilingRowArray = ceilingRowArray;
		this.ceilingColArray = ceilingColArray;
		this.ceilingIndex = getCeilingIndex(rowPointArray, 0);
	}

	/**
	 * 
	 * @param rowPointArray
	 * @param checkFrom
	 * @return a index which represent all same level point assume rowPointArray is sorted
	 */
	public int getCeilingIndex(int[] rowPointArray, int checkFrom)
	{
		this.ceilingCurrentSize = 0;
		int tempRowPoint = rowPointArray[checkFrom];
		this.ceilingIndex = checkFrom;
		this.ceilingOldIndex = checkFrom;
		while (rowPointArray[this.ceilingIndex] == tempRowPoint && this.ceilingIndex < rowPointArray.length)
		{
			ceilingIndex++;
			this.ceilingCurrentSize++;
		}
		// index will be one after ceiling row
	
		return ceilingIndex;	
	}
}
