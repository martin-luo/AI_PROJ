import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main 
{

	public static void main(String[] args) throws Exception
	{
	    Board newBoard = new Board();
	   // newBoard.setFinderAlgorithm(new SimpleBoardCountingAlgorithm(newBoard));
	    newBoard.setFinderAlgorithm(new FindCircleAndCapturedCellAlgorithm(newBoard));
	    newBoard.updateBoard();
	    newBoard.doOutput();
	}

}
