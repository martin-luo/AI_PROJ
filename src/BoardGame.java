import java.util.List;


public class BingfenglGame implements Game<Bingfengl, BoardAction, String> 
{
	Bingfengl oneBingfengl=null;
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

	public List<BoardAction> getActions(Bingfengl state)
	{
		
	}

	public Bingfengl getResult(Bingfengl state, BoardAction action)
	{
		
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
			
		}
		else
		{
			
		}
	}
	
	public void setBingfengl(Bingfengl oneBingfengl)
	{
		this.oneBingfengl=oneBingfengl;
	}
}
