import java.util.*;
import java.io.*;

public class BIT {
	public static void main(String[] args) throws Exception {
		
		fenwickTree a=new fenwickTree(1000);
		int[] prefix=new int[1000];
		for (int i = 1; i < 1000; i++) {
			int num=(int)(Math.random()*100);
			prefix[i]=prefix[i-1]+num;
			a.add(i,num);
		}
		int one=(int)(Math.random()*1000);
		int two=(int)(Math.random()*1000);
		System.out.println(prefix[one]+" "+prefix[two]);
		System.out.println(a.query(one)+" "+a.query(two));
		out.close();
	}

	static BufferedReader in;
	static {
		try {
			in = new BufferedReader(new FileReader("bit.dat"));
		} catch (Exception e) {}
	}
	static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	static StringTokenizer st = new StringTokenizer("");
	static void check() throws Exception {
		if (!st.hasMoreTokens()) st = new StringTokenizer(in.readLine());
	}
	static int i() throws Exception {check(); return Integer.parseInt(st.nextToken());}
	static String s() throws Exception {check(); return st.nextToken();}
	static double d() throws Exception {check(); return Double.parseDouble(st.nextToken());}
	static long l() throws Exception {check(); return Long.parseLong(st.nextToken());}
}
class fenwickTree
{
	int[] arr;
	public fenwickTree(int size)
	{
		arr=new int[size];
	}
	public void add(int pos,int val)
	{
		while(pos<arr.length)
		{
			arr[pos]+=val;
			pos+=pos&-pos;
		}
	}
	public int query(int pos)
	{
		int ret=0;
		while(pos!=0)
		{
			ret+=arr[pos];
			pos-=pos&-pos;
		}
		return ret;
	}
}