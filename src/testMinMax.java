
public class testMinMax {

	public static void main(String[] args) 
	{
		int [] temp=null;
		Bingfengl bing=new Bingfengl();
		bing.init(6, 1);
		BoardGame game = new BoardGame(bing);
		bing.printBoard(System.out);
		IterativeDeepeningAlphaBetaSearch decision = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,1.0, 100);
		temp=(int[])decision.makeDecision(bing);
		for(int a: temp)
		{
			System.out.print(a+" ");
		}
		System.out.println();
	}

}
