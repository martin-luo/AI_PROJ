import java.io.PrintStream;

import aiproj.squatter.*;

public class Bingfengl implements Player, Piece
{
		public boolean debug=false;
		public Board oneBoard=null;
		public int playerPiece;
		public int currentMoveRow;
		public int currentMoveCol;
		public Move opponentCurrentMove=null;
		public boolean opponentIllegalMoveFlag=false;
		/* This funstion is called by the referee to initialise the player.
		 *  Return 0 for successful initialization and -1 for failed one.
		 */
		
		public int init(int n, int p)
		{
			
			//p is invalid
			//diemension bigger than  5 ?
			if((p!=WHITE && p!=BLACK) || n<=5)
			{
				return INVALID;
			}
			oneBoard=new Board(n);
			oneBoard.setFinderAlgorithm(new FindCircleAndCapturedCellAlgorithm(oneBoard));
			playerPiece=p;
			return 0;
		}
		
		/* Function called by referee to request a move by the player.
		 *  Return object of class Move
		 */

		public Move makeMove()
		{
			Move newMove = new Move();
			newMove.P=playerPiece;
			newMove.Row=currentMoveRow;
			newMove.Col=currentMoveCol;
			
			if (!checkLegalMove(newMove))
			{
				opponentIllegalMoveFlag=true;
				return null;
			}
			
			setMoveOnBoardBody(currentMoveRow, currentMoveCol,playerPiece);
			
			return newMove ;
		}
		
		/* Function called by referee to inform the player about the opponent's move
		 *  Return -1 if the move is illegal otherwise return 0
		 */
		
		public int opponentMove(Move m)
		{
			opponentCurrentMove = m;
			
			if (m == null||!checkLegalMove(opponentCurrentMove) )
			{
				opponentIllegalMoveFlag=true;
				return INVALID;
			}
			
			setMoveOnBoardBody(opponentCurrentMove.Row, opponentCurrentMove.Col,opponentCurrentMove.P);
			
			return 0;
		}
		
		public void setMoveOnBoardBody(int row,int col,int p)
		{
			if(p==WHITE)
			{
				oneBoard.setBoardCell(row, col, Board.WHITE);
			}
			else
			{
				oneBoard.setBoardCell(row, col, Board.BLACK);
			}
		}

		/* This function when called by referee should return the winner
		 *	Return -1, 0, 1, 2, 3 for INVALID, EMPTY, WHITE, BLACK, DEAD respectively
		 */
		
		public int getWinner()
		{
			if(opponentIllegalMoveFlag)
			{
				
			}
			else if (oneBoard.freeCell != 0)
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
			System.out.println(oneBoard.whiteCaptured);
			System.out.println(oneBoard.blackCaptured);
			return 0;
		}
		
		/* Function called by referee to get the board configuration in String format
		 * 
		 */
		public void printBoard(PrintStream output)
		{
			oneBoard.printboardBody(output,oneBoard.getBoardBody());
		}
		
		public boolean checkLegalMove(Move oneMove)
		{
			System.out.println("in check");
			if(oneMove.Row<0||oneMove.Col<0||oneMove.Row>=oneBoard.getBoardDimension()||oneMove.Col>=oneBoard.getBoardDimension()||!oneBoard.getBoardBody()[oneMove.Row][oneMove.Col].equals(Board.FREE))
			{
				return false;
			}
			return true;
		}
}
