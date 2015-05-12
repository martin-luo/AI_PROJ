public class MinMaxAlgorithm
{
	int [] bestMove;
	public int[] getBestMove(Board board, int depth,int branch,String myPlayer,String opponentPlayer)
	{
		//when going down add parent's utility to child and 
		bestMove =null;
		TreeNode root = new TreeNode(board,myPlayer,opponentPlayer);
		TreeNode currentTranverseNode=null;
		System.out.println("=====root movement seting up =====");
		setupBranch(root,branch);
		System.out.println("=====root movement seting up end=====");
		currentTranverseNode=root.children.remove(0);
		currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
		outerLoop: while(true)
		{
			
			//if does not reach depth  ,going down and setup branch
			if(currentTranverseNode.depth!=depth)
			{
				setupBranch(currentTranverseNode,branch);
				//get first child
				currentTranverseNode=currentTranverseNode.children.remove(0);
				//when going down one node pass down the parent's utility all the way down.
				currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
				continue;
			}
			//meet node to stop and jump back
			else
			{
				currentTranverseNode.evaluation();
				setParentUtility(currentTranverseNode);
				currentTranverseNode.board.printboardBody();
				while(currentTranverseNode.parent.children.size()==0||isPrunningTrue(currentTranverseNode))
				{
					
					//means in first min node .
					if(currentTranverseNode.parent.parent==null&&currentTranverseNode.parent.children.size()==0)
					{
						currentTranverseNode.parent.board.printboardBody();
						break outerLoop;
					}
					else
					{
						//when jumpping back one will make decision about whether if to change the parent's utility value or not
						setParentUtility(currentTranverseNode);
						//go up node
						currentTranverseNode=currentTranverseNode.parent;
						currentTranverseNode.board.printboardBody();
					}
				}
				currentTranverseNode=currentTranverseNode.parent;
				currentTranverseNode.board.printboardBody();
				setParentUtility(currentTranverseNode);
				currentTranverseNode=currentTranverseNode.children.remove(0);
				currentTranverseNode.utilityValue=currentTranverseNode.parent.utilityValue;
				continue;
			}
		}
			
		return bestMove;
	}
	
	
	public boolean isPrunningTrue(TreeNode currentTranverseNode)
	{
		
		if(currentTranverseNode.parent==null||currentTranverseNode.parent.parent==null||currentTranverseNode.parent.parent.utilityValue==-1||currentTranverseNode.utilityValue==-1)
		{
			return false;
		}
		
		if(currentTranverseNode.nodeProperty==TreeNode.MAXNODE)
		{
			//current max node's utility less than parent's (min) utility and also biggger than grandparent(max)
			if(currentTranverseNode.utilityValue>currentTranverseNode.parent.parent.utilityValue)
			{
				return false;
			}
			//if does not we just jump back one node and try to jump back more with the while loop 
			else
			{
				System.out.println("prunning tree******");
				return true;
			}
		}
		
		else
		{
			//current is min, and it is bigger than its parent(max), and smallert than its grandpa (min level), if so we dont jump back two node(or prunning the tree).
			if(currentTranverseNode.utilityValue<currentTranverseNode.parent.parent.utilityValue)
			{
				return false;
			}
			//if does not we just jump back one node and try to jump back more with the while loop 
			else
			{
				System.out.println("prunning tree******");
				return true;
			}
		}
		
	}
	
	public void setParentUtility(TreeNode currentTranverseNode)
	{
		//update last node of this branch's utility ,and assign it to its parent
		//currentTranverseNode.evaluation();
		//if current node is a max node ,means its utility should smaller than its parent ,then parent will take the utility
		//jump to root
		if(currentTranverseNode.parent==null)
		{
			return;
		}
		
		if(currentTranverseNode.nodeProperty==TreeNode.MAXNODE)
		{
			System.out.println("======in set max node====== Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth);
			//if current max node utility is smaller than its parent(min) node , then parent utility change to its utility ,if parent's utility is -1 means it is not been assigned yet
			//we just give current node's utility to it.
			currentTranverseNode.parent.utilityValue=(currentTranverseNode.utilityValue<currentTranverseNode.parent.utilityValue||currentTranverseNode.parent.utilityValue==-1?currentTranverseNode.utilityValue:currentTranverseNode.parent.utilityValue);
			System.out.println("======in set max node parent ====== Utility = "+currentTranverseNode.parent.utilityValue+"current node depth = "+currentTranverseNode.parent.depth);
		}
		else
		{
			System.out.println("======in set min node======");
			//current is min node and its parent is sroot node (max), if utility need to be changed, then we point best move to its nodeMove.
			if(currentTranverseNode.parent.parent==null &&(currentTranverseNode.utilityValue>currentTranverseNode.parent.utilityValue||currentTranverseNode.parent.utilityValue==-1))
			{
				System.out.println("======set root utility====== Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth);
				currentTranverseNode.parent.utilityValue=currentTranverseNode.utilityValue;
				bestMove=currentTranverseNode.nodeMove.clone();
				System.out.println("================set root utility=============parent Utility = "+currentTranverseNode.parent.utilityValue+"current node depth = "+currentTranverseNode.parent.depth);
			}
			else
			{
				System.out.println("======set min utility====== Utility = "+currentTranverseNode.utilityValue+"current node depth = "+currentTranverseNode.depth);
				currentTranverseNode.parent.utilityValue=(currentTranverseNode.utilityValue>currentTranverseNode.parent.utilityValue||currentTranverseNode.parent.utilityValue==-1?currentTranverseNode.utilityValue:currentTranverseNode.parent.utilityValue);
				System.out.println("======set min utility====== Utility = parent Utility = "+currentTranverseNode.parent.utilityValue+"current node depth = "+currentTranverseNode.parent.depth);
			}
			
		}
		//find proper parent whoes children size is not 0
	}
	public void setupBranch(TreeNode currentNode,int branch)
	{
		currentNode.board.getDummyFreeMove();
		currentNode.visited=true;
		int [] tempPossibleMove=currentNode.board.getOnePossibleMove();
		for(int i=0;i<branch&&tempPossibleMove!=null;i++)
		{
			System.out.println("child movement row = "+tempPossibleMove[0]+" col = "+tempPossibleMove[1]);
			currentNode.children.add(new TreeNode (currentNode,tempPossibleMove));
			tempPossibleMove=currentNode.board.getOnePossibleMove();
		}
	}
	 
}
