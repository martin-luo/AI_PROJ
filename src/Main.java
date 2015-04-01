import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
public class Main
{
	
	public static void main(String[] args)
	{
		Board newBoard = new Board();
		// newBoard.setFinderAlgorithm(new SimpleBoardCountingAlgorithm(newBoard));
		newBoard.setFinderAlgorithm(new FindCircleAndCapturedCellAlgorithm(newBoard));
		newBoard.updateBoard();
		newBoard.doOutput();
	}
	
}
