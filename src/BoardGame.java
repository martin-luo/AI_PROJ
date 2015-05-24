import java.util.List;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;


public class BoardGame implements Game<Bingfengl, int[], String> 
{
	Bingfengl oneBingfengl=null;
	public static int currentPlayerPiece=-1;
	public static int movementCount=0;
	
	BoardGame()
	{
		
	}
	
	BoardGame(Bingfengl oneBingfengl)
	{
		this.oneBingfengl=oneBingfengl;
		this.currentPlayerPiece=oneBingfengl.playerPiece;
		//currentPlayerPiece = (currentPlayerPiece == Piece.WHITE ? Piece.BLACK : Piece.WHITE);
	}
	
	public Bingfengl getInitialState()
	{
		System.out.println("====In getinitialState====");
		return oneBingfengl;
	}

	public String[] getPlayers()
	{
		System.out.println("====In getPlayer====");
		return new String[] {oneBingfengl.oneBoard.WHITE,oneBingfengl.oneBoard.BLACK};
	}

	public String getPlayer(Bingfengl state)
	{
		System.out.println("====In getPlayer====");
		return state.getPlayer();
	}

	public List<int[]> getActions(Bingfengl state)
	{
		//this is a dummy one .
		//this only reurn how many movements are possible .. Integer means nothing ,they all == 1
		//it is used to trick ami alg.
		System.out.println("====In getAction====");
		List<int[]> tempActions=state.oneBoard.getDummyFreeMove();
		System.out.println("====End getAction====");
		return tempActions;
	}
	public Bingfengl getResult(Bingfengl state, int[] action)
	{
		
		System.out.println("====In getReulst====");
		Bingfengl oneBingfengl=new Bingfengl(state);
		//int[] onePossibleMove = oneBingfengl.oneBoard.getOnePossibleMove();
		/*
		if(onePossibleMove != null)
		{
			oneBingfengl.setMoveOnBoardBody(onePossibleMove[0],onePossibleMove[1],currentPlayerPiece);
			//currentPlayerPiece = (currentPlayerPiece == Piece.WHITE ? Piece.BLACK : Piece.WHITE);
		}*/
		System.out.println("====End getReulst====");
		return oneBingfengl;
	}

	public boolean isTerminal(Bingfengl state)
	{
		System.out.println("====In isTerminal====");
		//return false will stop evaluation  , go to next level 
		//movementCount++;
//		if(movementCount==10)
//		{
//			movementCount=0;
//			currentPlayerPiece = (currentPlayerPiece == Piece.WHITE ? Piece.BLACK : Piece.WHITE);
//			System.out.println("====In isTerminal movement count====");
//			return false;
//		}
		if (state.oneBoard.freeCell==0)
		{
			System.out.println("====In isTerminal false====");
			return false;
		}
		else
		{
			System.out.println("====In isTerminal true====");
			return true;
		}
	}

	public double getUtility(Bingfengl state, String player)
	{
		
		//System.out.println("get utility "+" whiteCell = "+state.oneBoard.whiteCell+" White captured "+state.oneBoard.whiteCaptured);
		System.out.println("====In utility====");
		state.oneBoard.updateBoard();
		state.oneBoard.printboardBody();
		if(currentPlayerPiece==Piece.WHITE)
		{
			System.out.println("get utility "+" whiteCell = "+state.oneBoard.whiteCell+" White captured "+state.oneBoard.whiteCaptured);
			System.out.println("====white End utility====");
			return state.oneBoard.whiteCell*2.9+100*state.oneBoard.whiteCaptured;
		}
		else
		{
			System.out.println("get utility "+" blackcell = "+state.oneBoard.blackCell+" black captured "+state.oneBoard.blackCaptured);
			System.out.println("====black End utility====");
			return state.oneBoard.blackCell*5.3+100*state.oneBoard.blackCaptured;
		}
	}
	
	public void setBingfengl(Bingfengl oneBingfengl)
	{
		this.oneBingfengl=oneBingfengl;
	}
}
