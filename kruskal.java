import java.util.*;
import java.io.*;
public class kruskal {
	static int[] parentNode;
	static int[] nodeRank;
	static int[] mst;
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
		parentNode=new int[maxNode+1];
		nodeRank=new int[maxNode+1];
		Arrays.fill(parentNode,-1);
		edge[] res=new edge[maxNode];
		int counter=0;
		for (int i = 0; i < runs; i++) {
			if(join(arr[i].nodeOne,arr[i].nodeTwo))
			{
				res[counter]=arr[i];
				counter++;
			}
		}
		for (int i = 0; i < res.length; i++) {
			System.out.println("nodeOne: "+res[i].nodeOne+" nodeTwo: "+res[i].nodeTwo+" edgeWeight: "+res[i].weight);
		}
		adjlist[] adj=new adjlist[maxNode+1];
		for (int i = 0; i < maxNode+1; i++) {
			adj[i]=new adjlist();
		}
		for (int i = 0; i < res.length; i++) {
			adj[res[i].nodeOne].add(res[i].nodeTwo,res[i].weight);
			adj[res[i].nodeTwo].add(res[i].nodeOne,res[i].weight);
		}

		mst=new int[maxNode+1];
		mst[0]=-1;
		dfs(0,adj,new boolean[maxNode+1]);
		System.out.println(Arrays.toString(mst));

		in.close(); out.close();
	}

	static void dfs(int pos,adjlist[] arr,boolean[] vis)
	{
		vis[pos]=true;
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			mst[arr[pos].list.get(i)]=arr[pos].weight.get(i);
			dfs(arr[pos].list.get(i),arr,vis);
		}
	}

	static int findParent(int pos)
	{
		if(parentNode[pos]==-1) return pos;
		return parentNode[pos]=findParent(parentNode[pos]);
	}

	static boolean join(int a,int b)
	{
		int one=findParent(a);
		int two=findParent(b);
		if(one==two) return false;
		if(nodeRank[one]>=nodeRank[two])
		{
			if(nodeRank[one]==nodeRank[two]) nodeRank[one]++;
			parentNode[two]=one;
		}
		else
			parentNode[one]=two;
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

	static class adjlist
	{
		ArrayList<Integer> list;
		ArrayList<Integer> weight;
		public adjlist()
		{
			list=new ArrayList<>();
			weight=new ArrayList<>();
		}
		public void add(int a,int b)
		{
			list.add(a);
			weight.add(b);
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}