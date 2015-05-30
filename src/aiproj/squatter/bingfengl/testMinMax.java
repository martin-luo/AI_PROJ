package aiproj.squatter.bingfengl;

import aiproj.squatter.Piece;

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

public class testMinMax
{

	public static void main(String[] args)
	{
		// int [] temp=null;
		// Bingfengl bing=new Bingfengl();
		// bing.init(6, 1);
		// BoardGame game = new BoardGame(bing);
		// bing.printBoard(System.out);
		// IterativeDeepeningAlphaBetaSearch decision =
		// IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,1.0, 100);
		// temp=(int[])decision.makeDecision(bing);
		// for(int a: temp)
		// {
		// System.out.print(a+" ");
		// }
		// System.out.println();
		int[] bestMove = null;
		Bingfengl oneBingfengl = new Bingfengl();
		oneBingfengl.init(6, Piece.WHITE);
		MinMaxAlgorithm prediction = new MinMaxAlgorithm(1);

		prediction.makeBestMove(oneBingfengl.oneBoard, 1, 3, Board.WHITE,
				Board.BLACK);
		if (prediction.getBestMove() == null)
		{
			bestMove = new int[] { -1, -1 };
		}
		else
		{
			bestMove = prediction.getBestMove();
		}

		System.out.println("best move row == " + bestMove[0]
				+ "best move col == " + bestMove[1]);

	}

}
