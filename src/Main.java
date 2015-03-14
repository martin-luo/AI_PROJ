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
	    int[] testInt= new int[]{3,2,1,99,0,12,-123,13333,0};
	    System.out.println("hello world");
	    System.out.println("haha"+newBoard.getMaxPoint(testInt));
	}

}
