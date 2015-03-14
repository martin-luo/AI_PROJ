//used to store cycle data
public class BoardDataCycleStructure 
{
	public String cycleOwner="";
	int capturedCellNumber=0;
	//used to stored points which formed the cycle
	public int[] rowPointArray=null;
	public int[] colPointArray=null;
	//usede to store points which captured within this cycle
	public int[] capturedRowPointArray=null;
	public int[] capturedColPointArray=null;
	
	BoardDataCycleStructure(String cycleOwner,int []rowPointArray,int []colPointArray)
	{
		this.cycleOwner = cycleOwner;
		
	}
}
