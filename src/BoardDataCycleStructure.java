import java.util.ArrayList;

//used to store cycle data
public class BoardDataCycleStructure 
{
	public String cycleOwner="";
	int capturedCellNumber=0;
	int ciyclePointsNumber=0;
	//used to stored points which formed the cycle
	//they are all sorted
	public int[] xPointArray=null;
	public int[] yPointArray=null;
	//usede to store points which captured within this cycle
	public int[] capturedxPointArray=null;
	public int[] capturedyPointArray=null;
	public ArrayList<Integer[]> cycleLevel =null; 
	
	BoardDataCycleStructure(String cycleOwner,int []xPointArray,int []yPointArray)
	{
		this.cycleOwner = cycleOwner;
		this.xPointArray = xPointArray;
		this.yPointArray= yPointArray;
	}
}
