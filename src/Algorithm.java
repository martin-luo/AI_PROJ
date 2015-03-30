public class Algorithm
{	
	private int blackCaptureNumber = 0;
	private int whiteCaptureNumber = 0;
	
	public int getBlackCaptureNumber()
	{
		return blackCaptureNumber;
	}
	
	public int getWhitekCaptureNumber()
	{
		return whiteCaptureNumber;
	}
	
	public void countCaptureCellNumber(String[][] board)
	{
		for (int row = 0; row < board.length; row++)
		{
			int tempCapturedNumber = 0;
			boolean metCaptured = false;
			
			for (int col = 0; col < board[0].length; col++)
			{
				if (board[row][col].equals(Board.CAPTURED))
				{
					// System.out.println("The position is [" + row + "][" + col
					// + "]");
					tempCapturedNumber++;
					metCaptured = true;
				}
				if (metCaptured)
				{
					if (board[row][col].equals(Board.WHITE))
					{
						whiteCaptureNumber += tempCapturedNumber;
						metCaptured = false;
						tempCapturedNumber = 0;
					}
					else if (board[row][col].equals(Board.BLACK))
					{
						blackCaptureNumber += tempCapturedNumber;
						metCaptured = false;
						tempCapturedNumber = 0;
					}
				}
			}
		}
	}
}
