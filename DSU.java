import java.util.*;
import java.io.*;
public class DSU {
	public static void main(String[] args) throws Exception {

		

	}

	static node findParent(node a)
	{
		if(a.parent==null) return a;
		return a.parent=findParent(a.parent);
	}

	static void join(node a,node b)
	{
		if(findParent(a)==findParent(b)) return;
		node one=findParent(a);
		node two=findParent(b);
		if(one.rank>=two.rank)
		{
			if(one.rank==two.rank) one.rank++;
			two.parent=one;
		}
		else
			one.parent=two;
	}

	static class node
	{
		node parent;
		int rank;
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}