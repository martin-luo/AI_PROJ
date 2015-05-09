import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import aiproj.squatter.Move;
import aiproj.squatter.Player;

/**
 * <b>Class Declaration</b>
 * <p>
 * This class is used to find the circle of the board and to count the cell and judge whether it's the final state of the game.
 * <p>
 * <b>Rules of Boardgame</b>
 * <ul>
 * <li>Only two players , one is called '<i>WHITE</i>' ,the Other one is '<i>BLACK</i>'</li>
 * <li>Board has a size of N*N, which N is greater than 5 (i.e. N > 5)</li>
 * <li>Top left corner is (0,0), Bottom right coner is (N-1,N-1)</li>
 * <li>Edges does not count as part of captured territory</li>
 * <ul>
 * <li>Only free cells and opponent's cells count as captured cell</li>
 * <li>Pieces can't be placed in</li>
 * </ul>
 * <li>Board is read from stdin (i.e. java Main < input)</li> </ul>
 * <p>
 * 
 * @author Bingfeng Liu (bingfengl)
 * @author An Luo (aluo1)
 * @version 2.0
 * @since 2015-03-30
 */
public class Main
{
	
	public static void main(String[] args)
	{
		/* searching algorithm testing*/
		Board newBoard = new Board();
		newBoard.printboardBody(newBoard.boardBody);
		newBoard.setFinderAlgorithm(new FindCircleAndCapturedCellAlgorithm(newBoard));
		newBoard.updateBoard();
		newBoard.doOutput();
		//following is simple alg for doing output ...
		/*
		newBoard.setFinderAlgorithm(new SimpleBoardCountingAlgorithm(newBoard));
		newBoard.updateBoard();
		newBoard.doOutput();
		*/
		/*
		Move lastMove=new Move();
		Bingfengl bing=new Bingfengl();
		Bingfengl pikachu=new Bingfengl();
		bing.init(6, 1);
		pikachu.init(6, 2);
		
		BoardGame game = new BoardGame(new Bingfengl(bing));
		IterativeDeepeningAlphaBetaSearch decision = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,1.0, 100);
		int [] temp=null;
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 
			String input;
			String[] inputArray=null;
			
			
			
			
			while((input=br.readLine())!=null)
			{
				inputArray=input.split(" ");
				System.out.println("===You are Palyer = "+bing.playerPiece+" Your Current Move is Row = "+inputArray[0]+" Col = "+inputArray[1]+"===");
				//bing.setCurrentRowCol(inputArray[0], inputArray[1]);
				bing.currentMoveRow=Integer.parseInt(inputArray[0]);
				bing.currentMoveCol=Integer.parseInt(inputArray[1]);
				lastMove=bing.makeMove();
				pikachu.opponentMove(lastMove);
				bing.oneBoard.updateBoard();
				bing.printBoard(System.out);
				System.out.println("Current Board State "+" Black Captured = "+bing.oneBoard.getBlackCaptured()+" White Captured = "+bing.oneBoard.getWhiteCaptured()+" Free Cell = "+bing.oneBoard.getFreeCell());
				
				if((input=br.readLine())!=null)
				{
					//temp=(int[])decision.makeDecision(new Bingfengl(pikachu));
					inputArray=input.split(" ");
					System.out.println("===You are Palyer = "+pikachu.playerPiece+" Your Current Move is Row = "+inputArray[0]+" Col = "+inputArray[1]+"===");
					//pikachu.currentMoveRow=temp[0];
					//pikachu.currentMoveCol=temp[1];
					pikachu.currentMoveRow=Integer.parseInt(inputArray[0]);
					pikachu.currentMoveCol=Integer.parseInt(inputArray[1]);
					lastMove=pikachu.makeMove();
					bing.opponentMove(lastMove);
					pikachu.oneBoard.updateBoard();
					pikachu.printBoard(System.out);
					System.out.println("Current Board State "+" Black Captured = "+pikachu.oneBoard.getBlackCaptured()+" White Captured = "+pikachu.oneBoard.getWhiteCaptured()+" Free Cell = "+pikachu.oneBoard.getFreeCell());
				}
			}
			
	 
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}	*/
	}
	
}
