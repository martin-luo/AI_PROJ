public class MinMaxAlgorithm
{
	public int[] bestMove;
	public int benchmark = 0;

	/**
	 * This function return the bestMove founded.
	 */
	public int[] getBestMove()
	{
		return bestMove;
	}

	public void makeBestMove(Board board, int depth, int branch, String myPlayer, String opponentPlayer)
	{
		// when going down add parent's utility to child and
		bestMove = null;
		TreeNode root = new TreeNode(board, myPlayer, opponentPlayer);
		TreeNode currentTranverseNode = null;
		System.out.println("=====root movement seting up =====");
		setupBranch(root, branch);
		System.out.println("=====root movement seting up end=====");
		currentTranverseNode = root.children.remove(0);
		currentTranverseNode.utilityValue = currentTranverseNode.parent.utilityValue;
		outerLoop: while (true)
		{
			benchmark++;
			// if does not reach depth ,going down and setup branch
			// for visited node and need to go down without evaluation until hit
			// the las one .

			if (currentTranverseNode.visited = true && currentTranverseNode.children.size() != 0)
			{
				currentTranverseNode = currentTranverseNode.children.remove(0);
				// currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
			}
			else if (currentTranverseNode.depth != depth && setupBranch(currentTranverseNode, branch))
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
				currentTranverseNode.board.printboardBody();
				// go up after eval func.
				currentTranverseNode = currentTranverseNode.parent;

				if (currentTranverseNode == null)
				{
					break;
				}

				while (currentTranverseNode.children.size() == 0 || isPrunningTrue(currentTranverseNode))
				{
					benchmark++;
					// means in first min node .
					if (currentTranverseNode.parent == null)
					{
						// setParentUtility(currentTranverseNode);
						currentTranverseNode.board.printboardBody();
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
						currentTranverseNode.board.printboardBody();
					}
				}
				// currentTranverseNode=currentTranverseNode.parent;
				currentTranverseNode.board.printboardBody();
				// setParentUtility(currentTranverseNode);
				currentTranverseNode = currentTranverseNode.children.remove(0);
				// currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
				continue;
			}
		}
		System.out.println("benchmark = " + benchmark);
	}

	public boolean isPrunningTrue(TreeNode currentTranverseNode)
	{
		// max node ,check its parent -1

		if (currentTranverseNode.parent == null)
		{
			return false;
		}

		if (currentTranverseNode.nodeProperty == TreeNode.MAXNODE)
		{
			if (currentTranverseNode.parent.utilityValue != -1 && currentTranverseNode.utilityValue > currentTranverseNode.parent.utilityValue)
			{
				return true;
			}
		}
		// min node check its parent if parent utility is not -1
		else
		{
			// if parent has utility , and if this is min node , if it is not
			// bigger than parent(max) then we can prune the rest of tree
			if (currentTranverseNode.parent.utilityValue != -1 && currentTranverseNode.utilityValue < currentTranverseNode.parent.utilityValue)
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
			System.out.println("======in set max node====== Utility = " + currentTranverseNode.utilityValue + "current node depth = " + currentTranverseNode.depth + " node move ment row= " + currentTranverseNode.nodeMove[0] + "node move ment col " + currentTranverseNode.nodeMove[1]);
			// if current max node utility is smaller than its parent(min) node
			// , then parent utility change to its utility ,if parent's utility
			// is -1 means it is not been assigned yet
			// we just give current node's utility to it.
			currentTranverseNode.parent.utilityValue = (currentTranverseNode.utilityValue < currentTranverseNode.parent.utilityValue || currentTranverseNode.parent.utilityValue == -1 ? currentTranverseNode.utilityValue : currentTranverseNode.parent.utilityValue);
			System.out.println("======in set max node parent ====== Utility = " + currentTranverseNode.parent.utilityValue + "current node depth = " + currentTranverseNode.parent.depth + " node move ment row= " + currentTranverseNode.parent.nodeMove[0] + "node move ment col " + currentTranverseNode.parent.nodeMove[1]);
		}
		else
		{
			System.out.println("======in set min node======");
			// current is min node and its parent is sroot node (max), if
			// utility need to be changed, then we point best move to its
			// nodeMove.
			if (currentTranverseNode.parent.parent == null)
			{
				System.out.println("parent is root current node utility = " + currentTranverseNode.utilityValue + " root utility = " + currentTranverseNode.parent.utilityValue);
				if (currentTranverseNode.utilityValue > currentTranverseNode.parent.utilityValue || currentTranverseNode.parent.utilityValue == -1)
				{
					System.out.println("======set root utility====== Utility = " + currentTranverseNode.utilityValue + "current node depth = " + currentTranverseNode.depth + " node move ment row= " + currentTranverseNode.nodeMove[0] + "node move ment col " + currentTranverseNode.nodeMove[1]);
					currentTranverseNode.parent.utilityValue = currentTranverseNode.utilityValue;
					bestMove = currentTranverseNode.nodeMove.clone();
					System.out.println("================set root utility=============parent Utility = " + currentTranverseNode.parent.utilityValue + "current node depth = " + currentTranverseNode.parent.depth);
				}
			}
			else
			{
				System.out.println("======set min utility====== Utility = " + currentTranverseNode.utilityValue + "current node depth = " + currentTranverseNode.depth + " node move ment row= " + currentTranverseNode.nodeMove[0] + "node move ment col" + currentTranverseNode.nodeMove[1]);
				currentTranverseNode.parent.utilityValue = (currentTranverseNode.utilityValue > currentTranverseNode.parent.utilityValue || currentTranverseNode.parent.utilityValue == -1 ? currentTranverseNode.utilityValue : currentTranverseNode.parent.utilityValue);
				System.out.println("======set min utility====== Utility = parent Utility = " + currentTranverseNode.utilityValue + "current node depth = " + currentTranverseNode.depth + " node move ment row= " + currentTranverseNode.parent.nodeMove[0] + "node move ment col " + currentTranverseNode.parent.nodeMove[1]);
			}

		}
		// find proper parent whoes children size is not 0
	}

	public boolean setupBranch(TreeNode currentNode, int branch)
	{
		currentNode.board.getDummyFreeMove();
		currentNode.visited = true;
		boolean hasMovement = false;
		int[] tempPossibleMove = currentNode.board.getOnePossibleMove();
		for (int i = 0; i < branch && tempPossibleMove != null; i++)
		{
			System.out.println("child movement row = " + tempPossibleMove[0] + " col = " + tempPossibleMove[1]);
			currentNode.children.add(new TreeNode(currentNode, tempPossibleMove));
			tempPossibleMove = currentNode.board.getOnePossibleMove();
			hasMovement = true;
		}
		return hasMovement;
	}

}
