import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main 
{

	public static void main(String[] args) 
	{
		System.out.println("hello world 12312");
	    Board newBoard = new Board();
	    
	    //int[] x=new int[]{3,2,2,3,1,0};
	    //int[] y=new int[]{3,2,0,4,99,0};
	    //AidUtility.printPositionArray(row,col);
	    //AidUtility.swapPoint(row, col, 0, 1);
	    //AidUtility.printPositionArray(x,y);
	    //System.out.println("After sort");
	    //AidUtility.sortByYandX(x, y);
	    //AidUtility.printPositionArray(x,y);
	
		//AidUtility.printPointArray(AidUtility.mergeIntArray(new int[]{1,2,3},new int[]{3,3,3}));
		BoardDataCircleStructure test1 = new BoardDataCircleStructure();
//		
		test1.cycleOwner=Board.WHITE;
		System.out.println("testing sign "+test1.cycleOwner.equals(Board.WHITE));
		test1.xPointArray =new int[]{1,2,3,4,5,1,5,1,5,1,2,3,4,5};
		test1.yPointArray =new int[]{1,1,1,1,1,2,2,3,3,4,4,4,4,4};
		AidUtility.sortByYandX(test1.xPointArray, test1.yPointArray);
		AidUtility.printPositionArray(test1.xPointArray,test1.yPointArray);
//		
		test1.constructLevel();
		System.out.println("cycle level : " + test1.circleLevel.length);
		System.out.println("Counted Captured Cell: "+newBoard.countCapturedCell(test1));
//		
		//test1.printCycleLevel();
		//testing capture function
		//System.out.println(newBoard.countCapturedCell(test1));
		//testting CellNode
		//current x ,current y, prev x, prev y, 
		CellNode testCellNode= new CellNode(1,1,1,0,Board.WHITE);
		
	}

}
