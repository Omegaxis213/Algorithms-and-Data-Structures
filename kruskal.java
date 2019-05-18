import java.util.*;
import java.io.*;
public class kruskal {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("kruskal.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kruskal.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int runs=i();
		edge[] arr=new edge[runs];
		int maxNode=-1;
		for (int i = 0; i < runs; i++) {
			st = new StringTokenizer(in.readLine());
			int one=i();
			int two=i();
			arr[i]=new edge(one,two,i());
			maxNode=Math.max(maxNode,Math.max(one,two));
		}
		Arrays.sort(arr);
		node[] nodes=new node[maxNode+1];
		for (int i = 0; i < maxNode+1; i++) {
			nodes[i]=new node();
		}
		edge[] res=new edge[maxNode];
		int counter=0;
		for (int i = 0; i < runs; i++) {
			if(join(nodes[arr[i].nodeOne],nodes[arr[i].nodeTwo]))
			{
				res[counter]=arr[i];
				counter++;
			}
		}
		for (int i = 0; i < res.length; i++) {
			System.out.println("nodeOne: "+res[i].nodeOne+" nodeTwo: "+res[i].nodeTwo+" edgeWeight: "+res[i].weight);
		}

		in.close(); out.close();
	}

	static node findParent(node a)
	{
		if(a.parent==null) return a;
		return a.parent=findParent(a.parent);
	}

	static boolean join(node a,node b)
	{
		node one=findParent(a);
		node two=findParent(b);
		if(one==two) return false;
		if(one.rank>=two.rank)
		{
			if(one.rank==two.rank) one.rank++;
			two.parent=one;
		}
		else
			one.parent=two;
		return true;
	}

	static class node
	{
		node parent;
		int rank;
	}

	static class edge implements Comparable<edge>
	{
		int nodeOne,nodeTwo;
		int weight;
		public edge(int a,int b,int c)
		{
			nodeOne=a;
			nodeTwo=b;
			weight=c;
		}
		public int compareTo(edge a)
		{
			return weight-a.weight;
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}