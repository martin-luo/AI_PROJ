import java.io.PrintStream;

import aiproj.squatter.*;

public class Bingfengl implements Player, Piece
{

		/* This funstion is called by the referee to initialise the player.
		 *  Return 0 for successful initialization and -1 for failed one.
		 */
		
		public int init(int n, int p)
		{
			return 0;
		}
		
		/* Function called by referee to request a move by the player.
		 *  Return object of class Move
		 */

		public Move makeMove()
		{
			return new Move();
		}
		
		/* Function called by referee to inform the player about the opponent's move
		 *  Return -1 if the move is illegal otherwise return 0
		 */
		
		public int opponentMove(Move m)
		{
			return 0;
		}

		/* This function when called by referee should return the winner
		 *	Return -1, 0, 1, 2, 3 for INVALID, EMPTY, WHITE, BLACK, DEAD respectively
		 */
		
		public int getWinner()
		{
			return 0;
		}
		
		/* Function called by referee to get the board configuration in String format
		 * 
		 */
		public void printBoard(PrintStream output)
		{
			
		}
	}
}
