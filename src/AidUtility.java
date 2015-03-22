import java.util.ArrayList;


public class AidUtility
{
	static public int getMaxPoint(int[] pointArray)
	{
		int max=0;
		
		if(pointArray.length==0)
		{
			System.out.println ("X.X --->Error: getMaxx received an empty array .");
		}
		
		max= pointArray[0];
		
		for (int i : pointArray)
		{
			if (i>max)
			{	
				max=i;
			}
		}
		return max;
	}
	
	static public void sortByYandX(int[] xPointArray,int[] yPointArray)
	{
		int tempxPoint;
		int tempyPoint;
		int length=xPointArray.length;
		if(xPointArray.length!=yPointArray.length)
		{
			System.out.println("X.X ---> Error: sortByxtheny got invalid array");
			System.exit(0);
		}
		
		for(int i=0;i<length;i++)
		{
			tempxPoint=xPointArray[i];
			tempyPoint=yPointArray[i];
			for(int j=i-1,tempI=i;j>=0&&j<length&&tempI>=0;j--)
			{
				if(tempyPoint > yPointArray[j])
				{
					continue;
				}
				else if(tempyPoint < yPointArray[j])
				{
					swapPoint(xPointArray,yPointArray,tempI--,j);
				}
				else //x is equal compare y
				{
					// want most upper left ... 
					if (tempxPoint < xPointArray[j])
					{
						swapPoint(xPointArray,yPointArray,tempI--,j);
					}
				}
				//System.out.println("----");
				//printPositionArray(xPointArray,yPointArray);
			}
		}
		
		
	}
	
	static public void swapPoint(int[] xPointArray,int[] yPointArray,int fromIndex,int toIndex)
	{
		int tempxPoint = xPointArray[fromIndex];
		int tempyPoint = yPointArray[fromIndex];
		xPointArray[fromIndex]=xPointArray[toIndex];
		yPointArray[fromIndex]=yPointArray[toIndex];
		xPointArray[toIndex]=tempxPoint;
		yPointArray[toIndex]=tempyPoint;
	}
	
	static public void printPointArray(int[] pointArray)
	{
		for(int i:pointArray)
		{
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	static public void printPositionArray(int[] xPointArray, int[] yPointArray)
	{
		if(xPointArray.length!=yPointArray.length)
		{
			System.out.println("X.X ---> Error: printPositionArray got invalid array");
			System.exit(0);
		}
		
		for(int i=0;i<xPointArray.length;i++)
		{
			System.out.println("x:"+xPointArray[i] + "  y:" +yPointArray[i]);
		}
		
	}
	
	static public void printPointArrayList(ArrayList<Integer> pointArrayList)
	{
		for(int i : pointArrayList)
		{
			System.out.print(i+" ");
		}
		System.out.println();
		
		
	}
	
	static public void printPositionArrayList(ArrayList<Integer> xPointArrayList , ArrayList<Integer> yPointArrayList)
	{
		if(xPointArrayList.size()!=yPointArrayList.size())
		{
			System.out.println("X.X ---> Error: printPositionArray got invalid array");
			System.exit(0);
		}
		
		for(int i=0;i<xPointArrayList.size();i++)
		{
			System.out.println("x:"+xPointArrayList.get(i) + "  y:" +yPointArrayList.get(i));
		}
	}
	
	static public void print2DintArray(int[][] temp,int breakLine)
	{
		for (int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[i].length;j++)
			{
				System.out.print(""+temp[i][j]+" ");
				if(j==breakLine-1)
				{
					System.out.println("");
				}
			}
		}
		System.out.println("");
		
	}
	
	static public int[] parseIntArrayList(ArrayList<Integer> intArrayList)
	{
		int[] tempList=new int[intArrayList.size()];
		for (int i=0;i<intArrayList.size();i++)
		{
			tempList[i]=intArrayList.get(i);
		}
		return tempList;
	}
	
	static public boolean checkPointInPositionArray(int xPoint,int yPoint,int[] xPointArray,int[] yPointArray)
	{
		for(int i=0;i<xPointArray.length;i++)
		{
			if(xPoint==xPointArray[i]&&yPoint==yPointArray[i])
			{
				return true;
			}
		}
		return false;
	}
	
	static public boolean checkPointInPointArray(int point,int[] pointArray)
	{
		for(int i:pointArray)
		{
			if(i==point)
				return true;
		}
		return false;
	}
	//return unique index in that array back 
	static public int[] getUniquePointInArray(int[] point)
	{
		int[] uniquePointArray=null;
		int tempPoint;
		int repetition;
		ArrayList<Integer> tempArrayList=new ArrayList<Integer>();
		tempArrayList.add(point[0]);
		for(int i=0;i<point.length;i++)
		{
			tempPoint=point[i];
			repetition=0;
			for (int j : tempArrayList)
			{
				if (tempPoint==j)
				{
					//System.out.println("inside "+tempPoint);
					repetition=1;
					break;
				}
			}
			if(repetition==0)
			{
				tempArrayList.add(tempPoint);
			}
			
		}
		
		uniquePointArray= convertArrayListToIntArray(tempArrayList);
		//System.out.println(tempArrayList.size());
		return uniquePointArray;
	}
	
	static public int[] convertArrayListToIntArray(ArrayList<Integer> arrayList)
	{
		int[] result=new int[arrayList.size()];
		
		for (int i =0;i<arrayList.size();i++)
		{
			result[i]=arrayList.get(i);
		}
		
		return result;
		
	}
	
	static public ArrayList<Integer> parseIntListToArrayList(int[] intList)
	{
		ArrayList<Integer> result= new ArrayList<Integer>();
		for (int i:intList)
		{
			result.add(i);
		}
		
		return result;
		
	}
	
	static public int[] mergeIntArray(int[] arrayA,int[] arrayB)
	{
		int[] mergedIntArray = new int[arrayA.length+arrayB.length];
		
		int indexOfMergedIntArray=0;
		
		for(int i=0;i<arrayA.length;i++)
		{
			mergedIntArray[indexOfMergedIntArray++]=arrayA[i];
		}
		
		for(int i=0;i<arrayB.length;i++)
		{
			mergedIntArray[indexOfMergedIntArray++]=arrayB[i];
		}
		
		return mergedIntArray;
	}
	
	static public void insertIntListToArrayList(int[] from,ArrayList<Integer> to)
	{
		for(int i : from)
		{
			to.add(i);
		}
	}
	
	static public BoardDataCircleStructure slicingCellNodeCollectionToBoardDataCircleStructure(int from,int to, String circleOwner,ArrayList<CellNode> arrayListOfCellNodes)
	{
		BoardDataCircleStructure oneCircle = null;
		int tempX[]=new int[to-from];
		int tempY[]=new int[to-from];
		int index=0;
		
		for(int i=from;i<to;i++)
		{
			tempX[index]=arrayListOfCellNodes.get(i).currentNodeX;
			tempY[index]=arrayListOfCellNodes.get(i).currentNodeY;
			
			index++;
		}
		//removed circle path/CellNodes from the original path collection .
		
		oneCircle= new BoardDataCircleStructure(circleOwner,tempX,tempY);
		return oneCircle;
	}
	static public void arrayListOfCellNodesRemove(int from,int to,ArrayList<CellNode> arrayListOfCellNodes)
	{
		ArrayList<CellNode> removeTemp=new ArrayList<CellNode>();
		for (int i=from;i<to;i++)
		{
			removeTemp.add(arrayListOfCellNodes.get(i));
		}
		arrayListOfCellNodes.removeAll(removeTemp);
	}
}
