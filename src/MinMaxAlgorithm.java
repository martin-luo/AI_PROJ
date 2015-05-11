
public class MinMaxAlgorithm
{
	int [] bestMove;
	public int[] getBestMove(Board board, int depth,int branch,String myPlayer,String opponentPlayer)
	{
		bestMove =null;
		TreeNode root = new TreeNode(board,myPlayer,opponentPlayer);
		TreeNode tranverseNode=null;
		tranverseNode=root.children.remove(0);
		outerLoop: while(true)
		{
			
			//if does not reach depth  ,going down and setup branch
			if(tranverseNode.depth!=depth)
			{
				setupBranch(tranverseNode,branch);
				tranverseNode=tranverseNode.children.remove(0);
				continue;
			}
			//meet node to stop
			else
			{
				setParentUtility(tranverseNode);
				while(tranverseNode.parent.children.size()==0)
				{
					
					//means in first min node .
					if(tranverseNode.parent.parent==null&&tranverseNode.parent.children.size()==0)
					{
						break outerLoop;
					}
					else
					{
						setParentUtility(tranverseNode);
						tranverseNode=tranverseNode.parent;
					}
				}
				tranverseNode=tranverseNode.parent;
				continue;
			}
		}
			
		return bestMove;
	}
	public void setParentUtility(TreeNode tranverseNode)
	{
		//update last node of this branch's utility ,and assign it to its parent
		tranverseNode.evaluation();
		//if current node is a max node ,means its utility should smaller than its parent ,then parent will take the utility
		if(tranverseNode.nodeProperty==TreeNode.MAXNODE)
		{
			//if current max node utility is smaller than its parent(min) node , then parent utility change to its utility ,if parent's utility is -1 means it is not been assigned yet
			//we just give current node's utility to it.
			tranverseNode.parent.utilityValue=(tranverseNode.utilityValue<tranverseNode.parent.utilityValue||tranverseNode.parent.utilityValue==-1?tranverseNode.utilityValue:tranverseNode.parent.utilityValue);
		}
		else
		{
			//current is min node and its parent is root node (max), if utility need to be changed, then we point best move to its nodeMove.
			if(tranverseNode.parent.parent==null &&(tranverseNode.utilityValue>tranverseNode.parent.utilityValue||tranverseNode.parent.utilityValue==-1))
			{
				tranverseNode.parent.utilityValue=tranverseNode.utilityValue;
				bestMove=tranverseNode.nodeMove.clone();
			}
			else
			{
				tranverseNode.parent.utilityValue=(tranverseNode.utilityValue>tranverseNode.parent.utilityValue||tranverseNode.parent.utilityValue==-1?tranverseNode.utilityValue:tranverseNode.parent.utilityValue);
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
			currentNode.children.add(new TreeNode (currentNode,currentNode.board.getOnePossibleMove()));
			tempPossibleMove=currentNode.board.getOnePossibleMove();
		}
	}
	 
}
