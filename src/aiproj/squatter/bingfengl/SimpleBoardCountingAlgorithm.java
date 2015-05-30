package aiproj.squatter.bingfengl;

/**
 * <b>Class Declaration</b>
 * <p>
 * This class is used to find the circle of the board and to count the cell and
 * judge whether it's the final state of the game.
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
 * @author Bingfeng Liu (bingfengl)
 * @author An Luo (aluo1)
 * @version 2.0
 * @since 2015-03-30
 */

public class SimpleBoardCountingAlgorithm extends BoardUpdateAlgorithm
{
	Board board = null;
	int whiteCaptured;
	int blackCaptured;
	int freeCell;

	/**
	 * Used to initialize the SimpleBoardCountingAlgorithm obj
	 * 
	 * @param board
	 */

	SimpleBoardCountingAlgorithm(Board board)
	{
		this.board = board;
		whiteCaptured = 0;
		blackCaptured = 0;
		freeCell = 0;
	}

	/**
	 * this function will run find circle and find captured cell and update the
	 * information of board
	 */

	public void doUpdateBoard()
	{
		countCell();
	}

	/**
	 * count cell use simple algorithm
	 */
	public void countCell()
	{
		System.out.println("Simple");
		String[][] tempBoardBody = board.getBoardBody();
		whiteCaptured = 0;
		blackCaptured = 0;
		freeCell = 0;
		for (int row = 0; row < tempBoardBody.length; row++)
		{
			int tempCapturedNumber = 0;
			boolean metCaptured = false;

			for (int col = 0; col < tempBoardBody[0].length; col++)
			{
				if (tempBoardBody[row][col].equals(Board.FREE))
				{
					freeCell += 1;
					continue;
				}
				if (tempBoardBody[row][col].equals(Board.CAPTURED))
				{
					// System.out.println("The position is [" + row + "][" +
					// col
					// + "]");
					tempCapturedNumber++;
					metCaptured = true;
				}
				if (metCaptured)
				{
					if (tempBoardBody[row][col].equals(Board.WHITE))
					{
						whiteCaptured += tempCapturedNumber;
						metCaptured = false;
						tempCapturedNumber = 0;
					}
					else if (tempBoardBody[row][col].equals(Board.BLACK))
					{
						blackCaptured += tempCapturedNumber;
						metCaptured = false;
						tempCapturedNumber = 0;
					}
				}
			}
		}
		board.setBlackCaptured(blackCaptured);
		board.setWhiteCaptured(whiteCaptured);
		board.setFreeCell(freeCell);
	}
}
