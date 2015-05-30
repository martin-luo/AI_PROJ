package aiproj.squatter.bingfengl;

/**
 * This is a part of the project of COMP30024 Artificial Intelligence, the
 * University of Melbourne. The project is the Game of Squatter and is a group
 * work, the members of the group is list below, so is the rule of the game.
 */

/**
 * <b>Class Declaration</b>
 * <p>
 * This class is a abstract class contains only one class called
 * BoardUpdateAlgorithm, it's used to update the status of cells on the board.
 * <p>
 * <b>Rules of Boardgame</b>
 * <ul>
 * <li>Only two players , one is called '<i>WHITE</i>' ,the Other one is
 * '<i>BLACK</i>'</li>
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

public abstract class BoardUpdateAlgorithm
{
	/**
	 * This is an abstract method, it's used to update the status of cells on
	 * the board.
	 */
	public abstract void doUpdateBoard();
}
