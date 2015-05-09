import java.util.List;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;


public class BoardGame implements Game<Bingfengl, int[], String> 
{
	Bingfengl oneBingfengl=null;
	public static int currentPlayerPiece=-1;
	
	BoardGame()
	{
		
	}
	
	BoardGame(Bingfengl oneBingfengl)
	{
		this.oneBingfengl=oneBingfengl;
		this.currentPlayerPiece=oneBingfengl.playerPiece;
	}
	
	public Bingfengl getInitialState()
	{
		return oneBingfengl;
	}

	public String[] getPlayers()
	{
		return new String[] {oneBingfengl.oneBoard.WHITE,oneBingfengl.oneBoard.BLACK};
	}

	public String getPlayer(Bingfengl state)
	{
		return state.getPlayer();
	}

	public List<int[]> getActions(Bingfengl state)
	{
		//this is a dummy one .
		//this only reurn how many movements are possible .. Integer means nothing ,they all == 1
		//it is used to trick ami alg.
		List<int[]> tempActions=state.oneBoard.getDummyFreeMove();
		return tempActions;
	}

	public Bingfengl getResult(Bingfengl state, int[] action)
	{
		Bingfengl oneBingfengl=new Bingfengl(state);
		int[] onePossibleMove = oneBingfengl.oneBoard.getOnePossibleMove();
		if(onePossibleMove != null)
		{
			oneBingfengl.setMoveOnBoardBody(onePossibleMove[0],onePossibleMove[1],currentPlayerPiece);
			currentPlayerPiece = (currentPlayerPiece == Piece.WHITE ? Piece.BLACK : Piece.WHITE);
		}
		return oneBingfengl;
	}

	public boolean isTerminal(Bingfengl state)
	{
		if (state.oneBoard.freeCell==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public double getUtility(Bingfengl state, String player)
	{
		if(player.equals(Board.WHITE))
		{
			return state.oneBoard.whiteCell;
		}
		else
		{
			return state.oneBoard.blackCell;
		}
	}
	
	public void setBingfengl(Bingfengl oneBingfengl)
	{
		this.oneBingfengl=oneBingfengl;
	}
}
