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
		if(a.rank>=b.rank)
		{
			if(a.rank==b.rank) a.rank++;
			b.parent=a;
		}
		else
			a.parent=b;
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