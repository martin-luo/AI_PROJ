
public class AidUtility
{
	static public int getMaxPoint(int[] pointArray)
	{
		int max=0;
		
		if(pointArray.length==0)
		{
			System.out.println ("X.X --->Error: getMaxRow received an empty array .");
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
	
	static public void sortByRowthenCol(int[] rowPointArray,int[] colPointArray)
	{
		int tempRowPoint;
		int tempColPoint;
		
		if(rowPointArray.length!=colPointArray.length)
		{
			System.out.println("X.X ---> Error: sortByRowthenCol got invalid array");
			System.exit(0);
		}
		
	}
	
	static public void swapPoint(int[] rowPointArray,int[] colPointArray,int fromIndex,int toIndex)
	{
		int tempRowPoint = rowPointArray[fromIndex];
		int tempColPoint = colPointArray[fromIndex];
		rowPointArray[fromIndex]=rowPointArray[toIndex];
		colPointArray[fromIndex]=colPointArray[toIndex];
		rowPointArray[toIndex]=tempRowPoint;
		colPointArray[toIndex]=tempColPoint;
	}
	
	static public void printPointArray(int[] pointArray)
	{
		for(int i:pointArray)
		{
			System.out.println(i);
		}
	}
	
	static public void printPositionArray(int[] rowPointArray, int[] colPointArray)
	{
		if(rowPointArray.length!=colPointArray.length)
		{
			System.out.println("X.X ---> Error: printPositionArray got invalid array");
			System.exit(0);
		}
		
		for(int i=0;i<rowPointArray.length;i++)
		{
			System.out.println("row:"+rowPointArray[i] + "  col:" +colPointArray[i]);
		}
		
	}

}