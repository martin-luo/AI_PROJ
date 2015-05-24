import java.util.ArrayList;


public class TestArea {

	public static void main(String[] args) {
		int a[]={1,2};
		int[] temp=null;
		ArrayList<int[]> b=new ArrayList<int[]>();
		b.add(new int[]{1,2});
		temp=b.get(0);
		b.remove(temp);
		for(int[] temp_temp:b)
		{
			System.out.println("in here "+temp_temp[0]+" "+temp_temp[1]);
		}
	}

}
