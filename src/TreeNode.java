import java.util.ArrayList;


public class TreeNode 
{
	public static int MINNODE=-1;
	public static int MAXNODE=1;
	public TreeNode parent;
	//0 ==> min , 1 ==> max
	public int nodeProperty;
	public ArrayList<TreeNode> children;
	public Board board;
	public double utilityValue;
	public double parentUtilityVlaue;
	public String myPlayer;
	public String opponentPlayer;
	public int depth;
	public boolean visited;
	
	int[] nodeMove;
	//this constructor is only for root node
	TreeNode(Board board,String myPlayer,String opponentPlayer)
	{
		this.parent=null;
		this.nodeProperty=MAXNODE;
		this.children =new ArrayList<TreeNode>();
		this.board = new Board(board);
		this.utilityValue=-1;
		this.myPlayer=myPlayer;
		this.opponentPlayer=opponentPlayer;
		//nodeMove=board.getOnePossibleMove();
		this.depth=0;
		this.visited=false;
		this.parentUtilityVlaue=-1;
	}
	//terminate when possiblemove is less than 2 
	
	TreeNode(TreeNode parent,int[] nodeMove)
	{
		this.parent=parent;
		//next level should be different to parent , if parent is max level then child should be min level.
		this.nodeProperty = (parent.nodeProperty == MINNODE ? MAXNODE : MINNODE);
		this.children =new ArrayList<TreeNode>();
		this.board = new Board(parent.board);
		this.utilityValue=-1;
		this.myPlayer = parent.myPlayer;
		this.opponentPlayer=parent.opponentPlayer;
		board.getDummyFreeMove();
		this.nodeMove=nodeMove.clone();
		//nodeMove[0] == row , nodeMove[1] == col
		if(nodeProperty==MINNODE)
			this.board.setBoardCell(nodeMove[0], nodeMove[1],myPlayer);
		else
			this.board.setBoardCell(nodeMove[0],nodeMove[1],opponentPlayer);
		
		this.depth=parent.depth+1;
		this.visited=false;
		this.parentUtilityVlaue=-1;
		this.board.updateAlgorithm.doUpdateBoard();
		//this.board.printboardBody();
		
	}
	//eval should be perform on opponennt node , and it is the value that i am going to win.
	public void evaluation()
	{
		
		if(nodeProperty==MINNODE)
		{
			utilityValue = 0;
		}
		//return my chance to win
		if(myPlayer.equals(Board.WHITE))
		{
			utilityValue = board.whiteCell*2.9+100*board.whiteCaptured;
		}
		else
		{
			utilityValue = board.blackCell*2.9+100*board.blackCaptured;
		}

	}
	
}
