package aiproj.squatter.bingfengl;

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

public class MinMaxAlgorithm
{
	public int[] bestMove;
	public int benchmark = 0;
	public String myPlayer = null;
	public String opponenetPlayer = null;
	// this following two values will be passed into getOnePossibleMove method.
	// first one will get call second one to expamd all possible move and get
	// best one
	public int pickBestBranchMoves = 1;
	// this will simple expand all tree
	public int expandAllAndGetOne = 2;
	public int pickBestBranchMoveMethod = 2;

	public MinMaxAlgorithm()
	{
	}

	public MinMaxAlgorithm(int pickBestBranchMoveMethod)
	{
		this.pickBestBranchMoveMethod = pickBestBranchMoveMethod;
	}

	public int[] getBestMove()
	{
		return bestMove;
	}
	
	//this function is used to choose best move from current state
	public void makeBestMove(Board board, int depth, int branch,
			String myPlayer, String opponentPlayer)
	{
		this.myPlayer = new String(myPlayer);
		this.opponenetPlayer = new String(opponentPlayer);
		// when going down add parent's utility to child and
		bestMove = null;
		TreeNode root = new TreeNode(board, myPlayer, opponentPlayer);
		TreeNode currentTranverseNode = null;
		// System.out.println("=====root movement seting up =====");
		if (!setupBranch(root, branch))
		{
			return;
		}
		// System.out.println("=====root movement seting up end=====");
		currentTranverseNode = root.children.remove(0);
		currentTranverseNode.utilityValue = currentTranverseNode.parent.utilityValue;
		outerLoop: while (true)
		{
			benchmark++;
			// if does not reach depth ,going down and setup branch
			// for visited node and need to go down without evaluation until
			// hit the las one .

			// if(currentTranverseNode.visited==true &&
			// currentTranverseNode.children.size()!=0)
			// jump back to visited node just go down ...if come to here means
			// there must be a children in this node.
			if (currentTranverseNode.visited == true)
			{
				currentTranverseNode = currentTranverseNode.children.remove(0);
				// currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
			}
			else if (currentTranverseNode.depth != depth
					&& setupBranch(currentTranverseNode, branch))
			{

				// get first child
				// if(currentTranverseNode.children.size()==0)
				// {
				// continue;
				// }
				currentTranverseNode = currentTranverseNode.children.remove(0);
				// when going down one node pass down the parent's utility all
				// the way down.
				// currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
				continue;
			}
			// meet node to stop and jump back
			else
			{
				currentTranverseNode.evaluation();
				// for test
				// TreeNode.evaluation(currentTranverseNode);
				setParentUtility(currentTranverseNode);
				// currentTranverseNode.board.printboardBody();
				// go up after eval func.
				currentTranverseNode = currentTranverseNode.parent;

				if (currentTranverseNode == null)
				{
					break;
				}

				while (currentTranverseNode.children.size() == 0
						|| isPrunningTrue(currentTranverseNode))
				{
					benchmark++;
					// means in first min node .
					if (currentTranverseNode.parent == null)
					{
						// setParentUtility(currentTranverseNode);
						// currentTranverseNode.board.printboardBody();
						break outerLoop;
					}
					else
					{
						// when jumpping back one will make decision about
						// whether if to change the parent's utility value or
						// not
						setParentUtility(currentTranverseNode);
						// go up node
						currentTranverseNode = currentTranverseNode.parent;
						// currentTranverseNode.board.printboardBody();
					}
				}
				// currentTranverseNode=currentTranverseNode.parent;
				// currentTranverseNode.board.printboardBody();
				// setParentUtility(currentTranverseNode);
				currentTranverseNode = currentTranverseNode.children.remove(0);
				// currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
				continue;
			}
		}
		// System.out.println("benchmark = "+benchmark);
	}
	//this function is used to determine whether if we shoud ignore current node's children or not
	public boolean isPrunningTrue(TreeNode currentTranverseNode)
	{
		// max node ,check its parent -1

		if (currentTranverseNode.parent == null)
		{
			return false;
		}

		if (currentTranverseNode.nodeProperty == TreeNode.MAXNODE)
		{
			if (currentTranverseNode.parent.utilityValue != -1
					&& currentTranverseNode.utilityValue > currentTranverseNode.parent.utilityValue)
			{
				return true;
			}
		}
		// min node check its parent if parent utility is not -1
		else
		{
			// if parent has utility , and if this is min node , if it is not
			// bigger than parent(max) then we can prune the rest of tree
			if (currentTranverseNode.parent.utilityValue != -1
					&& currentTranverseNode.utilityValue < currentTranverseNode.parent.utilityValue)
			{
				return true;
			}
		}
		return false;
	}

	// public boolean isPrunningTrue(TreeNode currentTranverseNode)
	// {
	//
	// if(currentTranverseNode.parent==null||currentTranverseNode.parent.parent==null||currentTranverseNode.parent.parent.utilityValue==-1||currentTranverseNode.utilityValue==-1)
	// {
	// return false;
	// }
	//
	// if(currentTranverseNode.nodeProperty==TreeNode.MAXNODE)
	// {
	// //current max node's utility less than parent's (min) utility and also
	// biggger than grandparent(max)
	// if(currentTranverseNode.utilityValue>currentTranverseNode.parent.parent.utilityValue)
	// {
	// return false;
	// }
	// //if does not we just jump back one node and try to jump back more with
	// the while loop
	// else
	// {
	// System.out.println("prunning tree******");
	// return true;
	// }
	// }
	//
	// else
	// {
	// //current is min, and it is bigger than its parent(max), and smallert
	// than its grandpa (min level), if so we dont jump back two node(or
	// prunning the tree).
	// if(currentTranverseNode.utilityValue<currentTranverseNode.parent.parent.utilityValue)
	// {
	// return false;
	// }
	// //if does not we just jump back one node and try to jump back more with
	// the while loop
	// else
	// {
	// System.out.println("prunning tree******");
	// return true;
	// }
	// }
	//
	// }
	
	//when jump from child node to parent, the utility value need to update correctly with this function
	public void setParentUtility(TreeNode currentTranverseNode)
	{
		// update last node of this branch's utility ,and assign it to its
		// parent
		// currentTranverseNode.evaluation();
		// if current node is a max node ,means its utility should smaller than
		// its parent ,then parent will take the utility
		// jump to root
		if (currentTranverseNode.parent == null)
		{
			return;
		}

		if (currentTranverseNode.nodeProperty == TreeNode.MAXNODE)
		{
			// System.out.println("======in set max node====== Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth+" node move ment row= "+currentTranverseNode.nodeMove[0]+"node move ment col "+currentTranverseNode.nodeMove[1]);
			// if current max node utility is smaller than its parent(min) node
			// , then parent utility change to its utility ,if parent's utility
			// is -1 means it is not been assigned yet
			// we just give current node's utility to it.
			currentTranverseNode.parent.utilityValue = (currentTranverseNode.utilityValue < currentTranverseNode.parent.utilityValue
					|| currentTranverseNode.parent.utilityValue == -1 ? currentTranverseNode.utilityValue
					: currentTranverseNode.parent.utilityValue);
			// System.out.println("======in set max node parent ====== Utility = "+currentTranverseNode.parent.utilityValue+"current node depth = "+currentTranverseNode.parent.depth+" node move ment row= "+currentTranverseNode.parent.nodeMove[0]+"node move ment col "+currentTranverseNode.parent.nodeMove[1]);
		}
		else
		{
			// System.out.println("======in set min node======");
			// current is min node and its parent is sroot node (max), if
			// utility need to be changed, then we point best move to its
			// nodeMove.
			if (currentTranverseNode.parent.parent == null)
			{
				// System.out.println("parent is root current node utility = "+currentTranverseNode.utilityValue+" root utility = "+currentTranverseNode.parent.utilityValue);
				if (currentTranverseNode.utilityValue > currentTranverseNode.parent.utilityValue
						|| currentTranverseNode.parent.utilityValue == -1)
				{
					// System.out.println("======set root utility====== Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth+" node move ment row= "+currentTranverseNode.nodeMove[0]+"node move ment col "+currentTranverseNode.nodeMove[1]);
					currentTranverseNode.parent.utilityValue = currentTranverseNode.utilityValue;
					bestMove = currentTranverseNode.nodeMove.clone();
					// System.out.println("================set root utility=============parent Utility = "+currentTranverseNode.parent.utilityValue+"current node depth = "+currentTranverseNode.parent.depth);
				}
			}
			else
			{
				// System.out.println("======set min utility====== Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth+" node move ment row= "+currentTranverseNode.nodeMove[0]+"node move ment col"+currentTranverseNode.nodeMove[1]);
				currentTranverseNode.parent.utilityValue = (currentTranverseNode.utilityValue > currentTranverseNode.parent.utilityValue
						|| currentTranverseNode.parent.utilityValue == -1 ? currentTranverseNode.utilityValue
						: currentTranverseNode.parent.utilityValue);
				// System.out.println("======set min utility====== Utility = parent Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth+" node move ment row= "+currentTranverseNode.parent.nodeMove[0]+"node move ment col "+currentTranverseNode.parent.nodeMove[1]);
			}

		}
		// find proper parent whoes children size is not 0
	}
	
	//fill children for this current parent.
	public boolean setupBranch(TreeNode currentNode, int branch)
	{
		currentNode.board.getDummyFreeMove();
		currentNode.visited = true;
		boolean hasMovement = false;
		int[] tempPossibleMove = null;
		// int []
		// tempPossibleMove=currentNode.board.getOnePossibleMove(myPlayer,opponenetPlayer,pickBestBranchMoveMethod);
		// System.out.println("child movement row = "+tempPossibleMove[0]+" col = "+tempPossibleMove[1]);
		// need to take away one because already get one move above
		// System.out.println("before add my player : "+myPlayer
		// +" oppo : "+opponenetPlayer);
		for (int i = 0; i < branch; i++)
		{

			if (currentNode.nodeProperty == TreeNode.MAXNODE)
			{
				tempPossibleMove = currentNode.board.getOnePossibleMove(
						myPlayer, opponenetPlayer, pickBestBranchMoveMethod);
			}
			else
			{
				tempPossibleMove = currentNode.board.getOnePossibleMove(
						opponenetPlayer, myPlayer, pickBestBranchMoveMethod);
			}
			if (tempPossibleMove == null)
			{
				break;
			}
			/*
			 * if(killerHeuristic(currentNode,tempPossibleMove))
			 * {
			 * System.out.println("killer working!=== x: "+tempPossibleMove[0]+
			 * " === y : "+tempPossibleMove[1]);
			 * continue;
			 * }
			 */
			// System.out.println("child movement row = "+tempPossibleMove[0]+" col = "+tempPossibleMove[1]);
			// System.out.println("before add my player : "+myPlayer
			// +" oppo : "+opponenetPlayer);
			currentNode.children.add(new TreeNode(currentNode,
					tempPossibleMove));
			// System.out.println("===finish setting");
			hasMovement = true;

		}
		// System.out.println("child movement row = "+tempPossibleMove[0]+" col = "+tempPossibleMove[1]);
		// System.out.println("in set up branch");
		return hasMovement;
	}

	// if a cell's four possion has other cell then just ignore it ,prevent it
	// to be added to min max tree
	// find clockwise if contiguous more than 2 then abort .
	public boolean killerHeuristic(TreeNode oneNode, int[] cell)
	{
		// x,y clock wise from most left
		System.out.println("killer x : " + cell[0] + " killer y: " + cell[1]);
		int[][] direction = new int[][] { { -1, -1 }, { 0, -1 }, { 1, -1 },
				{ 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 } };
		int tempX = -1;
		int tempY = -1;
		// 8 cell around go around 8 time each time start with a different cell
		// among 8 cells
		int count = direction.length;
		int j = 0;
		// if its has 4 surroundings then just return true i dont want this
		// move
		int countFourSurrounding = 1;
		boolean contiguousFlag = false;
		for (int i = 0; i < direction.length; i++)
		{
			count = direction.length;
			countFourSurrounding = 1;
			contiguousFlag = false;
			j = i;
			while (count > 0)
			{

				if (j >= direction.length)
				{
					j = 0;
				}
				tempX = cell[0] + direction[j][0];
				tempY = cell[1] + direction[j][1];
				if (tempX < 0 || tempY < 0
						|| tempX >= oneNode.board.boardDimension
						|| tempY >= oneNode.board.boardDimension)
				{
					contiguousFlag = false;
					j++;
					count--;
					continue;
				}
				// if not free means something on one of the four direction

				if (!oneNode.board.boardBody[tempY][tempX].equals(Board.FREE))
				{
					// go around a cell in clock wise ,if find contiguous cell
					// around it increase the number
					if (contiguousFlag)
					{
						countFourSurrounding++;
					}
					contiguousFlag = true;
				}
				else
				{
					contiguousFlag = false;
				}
				if (countFourSurrounding >= 3)
				{
					return true;
				}
				j++;
				count--;
			}
		}
		System.out.println("count ==> " + countFourSurrounding);
		// 3 surrounding means nothing.

		return false;
	}

}
