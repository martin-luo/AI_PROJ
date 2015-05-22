import aiproj.squatter.Piece;

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
		MinMaxAlgorithm prediction = new MinMaxAlgorithm();

		prediction.makeBestMove(oneBingfengl.oneBoard, 2, 3, Board.WHITE, Board.BLACK);
		if (prediction.getBestMove() == null)
		{
			bestMove = new int[] { -1, -1 };
		}
		else
		{
			bestMove = prediction.getBestMove();
		}

		System.out.println("best move row == " + bestMove[0] + "best move col == " + bestMove[1]);

	}

}
