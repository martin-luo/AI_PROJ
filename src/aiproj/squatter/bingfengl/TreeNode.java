package aiproj.squatter.bingfengl;

import java.util.ArrayList;
import java.util.Random;

/**
 * <b>Class Declaration</b>
 * <p>
 * This class is used to find the circle of the board and to count the cell and
 * judge whether it's the final state of the game.
 * <p>
 * <b>Rules of Boardgame</b>
 * <ul>
 * <li>Only two players , one is called '<i>WHITE</i>' ,the Other one is
 * '<i>BLACK</i>'</li>
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

public class TreeNode
{
	public static int MINNODE = -1;
	public static int MAXNODE = 1;
	public TreeNode parent;
	// 0 ==> min , 1 ==> max
	public int nodeProperty;
	public ArrayList<TreeNode> children;
	public Board board;
	public double utilityValue;
	public double parentUtilityVlaue;
	public String myPlayer;
	public String opponentPlayer;
	public int depth;
	public boolean visited;

	// for debug
	public static int index = 0;

	int[] nodeMove;

	// this constructor is only for root node
	TreeNode(Board board, String myPlayer, String opponentPlayer)
	{
		this.parent = null;
		this.nodeProperty = MAXNODE;
		this.children = new ArrayList<TreeNode>();
		this.board = new Board(board);
		this.utilityValue = -1;
		this.myPlayer = myPlayer;
		this.opponentPlayer = opponentPlayer;
		// nodeMove=board.getOnePossibleMove();
		this.depth = 0;
		this.visited = false;
		this.parentUtilityVlaue = -1;
	}

	// terminate when possiblemove is less than 2

	TreeNode(TreeNode parent, int[] nodeMove)
	{
		this.parent = parent;
		// next level should be different to parent , if parent is max level
		// then child should be min level.
		this.nodeProperty = (parent.nodeProperty == MINNODE ? MAXNODE
				: MINNODE);
		this.children = new ArrayList<TreeNode>();
		this.board = new Board(parent.board);
		this.utilityValue = -1;
		this.myPlayer = new String(parent.myPlayer);
		this.opponentPlayer = new String(parent.opponentPlayer);
		board.getDummyFreeMove();
		this.nodeMove = nodeMove.clone();
		// nodeMove[0] == row , nodeMove[1] == col
		// System.out.println("myplaery = "+this.myPlayer+"oppo : "+opponentPlayer);
		if (nodeProperty == MINNODE)
			this.board.setBoardCell(nodeMove[0], nodeMove[1], myPlayer);
		else
			this.board.setBoardCell(nodeMove[0], nodeMove[1], opponentPlayer);

		this.depth = parent.depth + 1;
		this.visited = false;
		this.parentUtilityVlaue = -1;
		this.board.updateAlgorithm.doUpdateBoard();
		// this.board.printboardBody();

	}

	// eval should be perform on opponennt node , and it is the value that i am
	// going to win.
	public static void evaluation(TreeNode currentTranverseNode)
	{
		int[] testUtilityValues = new int[] { 4, 2, 1, 3, 6, 7, 8, 1, 100 };
		currentTranverseNode.utilityValue = testUtilityValues[index++];
		// Random rand = new Random();
		// int n = rand.nextInt(100) + 1;
		// if(nodeProperty==MINNODE)
		// {
		// utilityValue = 0;
		// }
		// return my chance to win
		// if(myPlayer.equals(Board.WHITE))
		// {
		// utilityValue = board.whiteCell*2.9+100*board.whiteCaptured+n;
		// }
		// else
		// {
		// utilityValue = board.blackCell*2.9+100*board.blackCaptured+n;
		// }
	}
	//method to get the utility value of current state
	public void evaluation()
	{
		// Random rand = new Random();
		// int n = rand.nextInt(100) + 1;
		// if(nodeProperty==MINNODE)
		// {
		// utilityValue = 0;
		// }
		// return my chance to win
		// System.out.println("in evaluation function");
		if (myPlayer.equals(Board.WHITE))
		{
			utilityValue = board.whiteConnectedPathValue * 0.025
					+ board.whiteCriticalBlockValue * 10000
					+ board.whiteNearlyCompleteCircleValue
					+ board.whiteCaptured * 1000000 + board.whiteDiag * 1000
					- board.blackCaptured * 1000;
			// System.out.println("in evaluation function white === whiteConnectedPathValue:"+board.whiteConnectedPathValue+"==>whiteCriticalBlockValue : "+board.whiteCriticalBlockValue+"===>whiteNearlyCompleteCircleValue "+board.whiteNearlyCompleteCircleValue+"===>whiteCaptured"+board.whiteCaptured+" white diag : "+board.whiteDiag);
			// System.out.println("utility : "+utilityValue);
		}
		else
		{
			// board.updateBoard();
			utilityValue = board.blackConnectedPathValue * 0.025
					+ board.blackCriticalBlockValue * 10000
					+ board.blackNearlyCompleteCircleValue
					+ board.blackCaptured * 1000000 + board.blackDiag * 1000
					- board.whiteCaptured * 1000;
			// System.out.println("in evaluation function black === blackConnectedPathValue:"+board.blackConnectedPathValue+"==>blackCriticalBlockValue : "+board.blackCriticalBlockValue+"===>blackNearlyCompleteCircleValue "+board.blackNearlyCompleteCircleValue+"===>blackCaptured"+board.blackCaptured+" black diag : "+board.blackDiag);
			// System.out.println("utility : "+utilityValue);
		}
		// board.printboardBody();

	}
	// public static evaluation (String myPlayer)

}
