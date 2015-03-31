public class SimpleBoardCountingAlgorithm extends BoardUpdateAlgorithm
{	
	Board board=null;
	int whiteCaptured;
	int blackCaptured;
	int freeCell;
	
	SimpleBoardCountingAlgorithm(Board board)
	{
		this.board=board;
		whiteCaptured=0;
		blackCaptured=0;
		freeCell=0;
	}
	
	public void doUpdateBoard()
	{
		countCell();
	}
	
	public void countCell()
	{
		System.out.println("Simple");
		String[][] tempBoardBody=board.getBoardBody();
		whiteCaptured=0;
		blackCaptured=0;
		freeCell=0;
		for (int row = 0; row < tempBoardBody.length; row++)
		{
			int tempCapturedNumber = 0;
			boolean metCaptured = false;
			
			for (int col = 0; col < tempBoardBody[0].length; col++)
			{
				if(tempBoardBody[row][col].equals(Board.FREE))
				{
					freeCell+=1;
					continue;
				}
				if (tempBoardBody[row][col].equals(Board.CAPTURED))
				{
					// System.out.println("The position is [" + row + "][" + col
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
