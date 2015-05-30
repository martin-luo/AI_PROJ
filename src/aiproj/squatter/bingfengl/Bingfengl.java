package aiproj.squatter.bingfengl;

import java.io.PrintStream;

import aiproj.squatter.*;

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

public class Bingfengl implements Player, Piece
{
	public boolean debug = false;
	public Board oneBoard = null;
	public int playerPiece;
	public int currentMoveRow;
	public int currentMoveCol;
	public Move opponentCurrentMove = null;
	public boolean opponentIllegalMoveFlag = false;
	public MinMaxAlgorithm prediction = null;

	/*
	 * This funstion is called by the referee to initialise the player. Return
	 * 0 for successful initialization and -1 for failed one.
	 */
	public Bingfengl()
	{
	}

	public void setCurrentRowCol(int row, int col)
	{
		currentMoveRow = row;
		currentMoveCol = col;
	}

	public Bingfengl(Bingfengl oneBingfengl)
	{
		oneBoard = new Board(oneBingfengl.oneBoard);
		playerPiece = oneBingfengl.playerPiece;
		currentMoveRow = oneBingfengl.currentMoveRow;
		currentMoveCol = oneBingfengl.currentMoveCol;
		// opponentCurrentMove=new Move();
		// opponentCurrentMove.Col=oneBingfengl.opponentCurrentMove.Col;
		// opponentCurrentMove.Row=oneBingfengl.opponentCurrentMove.Row;
		// opponentCurrentMove.P=oneBingfengl.opponentCurrentMove.P;
		opponentIllegalMoveFlag = oneBingfengl.opponentIllegalMoveFlag;
	}

	public int init(int n, int p)
	{

		// p is invalid
		// diemension bigger than 5 ?
		if ((p != WHITE && p != BLACK) || n <= 5)
		{
			return INVALID;
		}
		// applied alg to get move
		prediction = new MinMaxAlgorithm(1);
		oneBoard = new Board(n);
		oneBoard.setFinderAlgorithm(new FindCircleAndCapturedCellAlgorithm(
				oneBoard));
		playerPiece = p;
		return 0;
	}

	/*
	 * Function called by referee to request a move by the player. Return
	 * object of class Move
	 */

	public Move makeMove()
	{
		int tempMove[] = null;
		Move newMove = new Move();
		if (oneBoard.boardDimension > 6)
		{
			prediction.makeBestMove(this.oneBoard, 2, 1, getPlayer(),
					getOpponentP());
		}
		else
		{
			// System.out.println("in here ");
			prediction.makeBestMove(this.oneBoard, 2, 5, getPlayer(),
					getOpponentP());
		}

		tempMove = prediction.getBestMove();
		// mistake make haha in here remember first index is row , second index
		// is col
		if (tempMove == null)
		{
			return null;
		}
		currentMoveRow = tempMove[0];
		currentMoveCol = tempMove[1];
		newMove.P = playerPiece;
		newMove.Row = currentMoveRow;
		newMove.Col = currentMoveCol;
		if (!checkLegalMove(newMove))
		{
			// opponentIllegalMoveFlag=true;
			return null;
		}

		setMoveOnBoardBody(currentMoveRow, currentMoveCol, playerPiece);
		oneBoard.updateBoard();
		return newMove;
	}

	public Move testMakeMove()
	{
		int tempMove[] = null;
		Move newMove = new Move();
		newMove.P = playerPiece;
		newMove.Row = currentMoveRow;
		newMove.Col = currentMoveCol;
		if (!checkLegalMove(newMove))
		{
			// opponentIllegalMoveFlag=true;
			return null;
		}

		setMoveOnBoardBody(currentMoveRow, currentMoveCol, playerPiece);
		oneBoard.updateBoard();
		return newMove;
	}

	/*
	 * Function called by referee to inform the player about the opponent's
	 * move Return -1 if the move is illegal otherwise return 0
	 */

	public String getPlayer()
	{
		if (playerPiece == WHITE)
		{
			return Board.WHITE;
		}
		else
		{
			return Board.BLACK;
		}
	}

	public int opponentMove(Move m)
	{
		opponentCurrentMove = m;

		if (m == null || !checkLegalMove(opponentCurrentMove))
		{
			opponentIllegalMoveFlag = true;
			return INVALID;
		}

		setMoveOnBoardBody(opponentCurrentMove.Row, opponentCurrentMove.Col,
				opponentCurrentMove.P);
		oneBoard.updateBoard();
		return 0;
	}

	public String getOpponentP()
	{
		if (playerPiece == Piece.WHITE)
		{
			return Board.BLACK;
		}
		else
		{
			return Board.WHITE;
		}
	}

	public void setMoveOnBoardBody(int row, int col, int p)
	{
		if (p == WHITE)
		{
			oneBoard.setBoardCell(row, col, Board.WHITE);
		}
		else
		{
			oneBoard.setBoardCell(row, col, Board.BLACK);
		}
	}

	/*
	 * This function when called by referee should return the winner Return -1,
	 * 0, 1, 2, 3 for INVALID, EMPTY, WHITE, BLACK, DEAD respectively
	 */

	public int getWinner()
	{
		oneBoard.updateBoard();
		// System.out.println("myplayer = "+playerPiece+" free : "+oneBoard.freeCell+" white capture :  "+oneBoard.whiteCaptured+" black capture : "+oneBoard.blackCaptured);
		if (oneBoard.freeCell > 0)
		{
			return EMPTY;
		}
		else if (oneBoard.whiteCaptured > oneBoard.blackCaptured)
		{
			return WHITE;
		}
		else if (oneBoard.blackCaptured > oneBoard.whiteCaptured)
		{
			return BLACK;
		}
		else
		{
			return DEAD;
		}
		// System.out.println(oneBoard.whiteCaptured);
		// System.out.println(oneBoard.blackCaptured);
	}

	/*
	 * Function called by referee to get the board configuration in String
	 * format
	 */
	public void printBoard(PrintStream output)
	{
		oneBoard.printboardBody(output, oneBoard.getBoardBody());
	}

	public boolean checkLegalMove(Move oneMove)
	{
		// System.out.println("in check");
		if (oneMove.Row < 0
				|| oneMove.Col < 0
				|| oneMove.Row >= oneBoard.getBoardDimension()
				|| oneMove.Col >= oneBoard.getBoardDimension()
				|| !oneBoard.getBoardBody()[oneMove.Row][oneMove.Col]
						.equals(Board.FREE))
		{
			return false;
		}
		return true;
	}
}
