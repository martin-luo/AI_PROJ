package RemoteSystemsTempFiles;

public class Algorithm
{
	public static String WHITE = "W";
	public static String BLACK = "B";
	public static String CAPTURED = "-";
	public static String FREE = "+";
	
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
				if (board[row][col].equals(CAPTURED))
				{
					tempCapturedNumber++;
					metCaptured = true;
				}
				if (metCaptured)
				{
					if (board[row][col].equals(WHITE))
					{
						whiteCaptureNumber += tempCapturedNumber;
					}
					else if (board[row][col].equals(BLACK))
					{
						blackCaptureNumber += tempCapturedNumber;
					}
				}
			}
		}
	}
}
