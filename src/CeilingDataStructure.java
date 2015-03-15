public class CeilingDataStructure 
{
	int ceilingCurrentSize;
	int ceilingIndex;
	int ceilingOldIndex;
	int[] ceilingRowArray;
	int[] ceilingColArray;
	
	CeilingDataStructure()
	{
		
	}
	
	CeilingDataStructure(int ceilingIndex,int[] ceilingRowArray,int[] ceilingColArray,int[] rowPointArray)
	{
		this.ceilingIndex=ceilingIndex;
		this.ceilingRowArray=ceilingRowArray;
		this.ceilingColArray=ceilingColArray;
		this.ceilingIndex=getCeilingIndex(rowPointArray,0);
	}
	//this will return a index which represent all same level point.
	//assume rowPointArray is sorted
	public int getCeilingIndex(int[] rowPointArray,int checkFrom)
	{
		this.ceilingCurrentSize=0;
		int tempRowPoint=rowPointArray[checkFrom];
		this.ceilingIndex=checkFrom;
		this.ceilingOldIndex=checkFrom;
		while(rowPointArray[this.ceilingIndex]==tempRowPoint && this.ceilingIndex<rowPointArray.length)
		{
			ceilingIndex++;
			this.ceilingCurrentSize++;
		}
		//index will be one after ceiling row
		
		return ceilingIndex;
		
	}
	
	
	
	
}
