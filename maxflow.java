import java.util.*;
import java.io.*;
public class maxflow {
	static int[][] sparseTable;
	static int[] prefix;
	static int[] firstOccurence;
	static int[] nodeNum;
	static int[] nodeVis;
	static int[] timeToNode;
	static int[] parent;
	static int[] prefixArr;
	static boolean[] vis;
	static adjlist[] arr;
	static int counter;
	static int timeCounter;
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("maxflow.in"));
		// BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maxflow.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int runs=i();
		int queries=i();
		arr=new adjlist[runs];
		for (int i = 0; i < arr.length; i++) {
			arr[i]=new adjlist();
		}
		for (int i = 0; i < runs-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one=i()-1;
			int two=i()-1;
			arr[one].add(two);
			arr[two].add(one);
		}
		firstOccurence=new int[runs];
		nodeNum=new int[runs*2+1];
		nodeVis=new int[runs];
		vis=new boolean[runs];
		timeToNode=new int[runs];
		parent=new int[runs];
		parent[0]=-1;
		Arrays.fill(firstOccurence,-1);
		Arrays.fill(nodeNum,-1);
		Arrays.fill(nodeVis,-1);
		vis[0]=true;
		dfs(0);

		int[] arr=new int[nodeNum.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i]=nodeNum[i];
		}
		prefix=new int[arr.length+1];
		int curNum=0;
		while((1<<curNum)<arr.length)
		{
			prefix[1<<curNum]++;
			curNum++;
		}
		for (int i = 1; i < prefix.length; i++) {
			prefix[i]+=prefix[i-1];
		}
		sparseTable=new int[prefix[arr.length]][arr.length];
		for (int i = 0; i < prefix[arr.length]; i++) {
			for (int j = 0; j < arr.length; j++) {
				sparseTable[i][j]=-1;
			}
		}
		for (int i = 0; i < prefix[arr.length]; i++) {
			for (int j = 0; j < arr.length; j++) {
				if(i==0)
					sparseTable[i][j]=arr[j];
				else if(j+(1<<i-1)<arr.length)
					sparseTable[i][j]=Math.min(sparseTable[i-1][j],sparseTable[i-1][j+(1<<i-1)]);
			}
		}
		prefixArr=new int[runs];
		for (int i = 0; i < queries; i++) {
			st = new StringTokenizer(in.readLine());
			int one=i()-1;
			int two=i()-1;
			int posOne=firstOccurence[one];
			int posTwo=firstOccurence[two];
			int length=Math.abs(posOne-posTwo);
			int node=-1;
			if(posOne==posTwo)
				node=timeToNode[sparseTable[0][posOne]];
			else if(posOne<posTwo)
			{
				node=Math.min(sparseTable[prefix[length]-1][posOne],sparseTable[prefix[length]-1][posTwo-(1<<prefix[length]-1)+1]);
				node=timeToNode[node];
			}
			else
			{
				node=Math.min(sparseTable[prefix[length]-1][posTwo],sparseTable[prefix[length]-1][posOne-(1<<prefix[length]-1)+1]);
				node=timeToNode[node];
			}
			prefixArr[one]++;
			prefixArr[two]++;
			prefixArr[node]--;
			if(parent[node]!=-1)
				prefixArr[parent[node]]--;
		}
		vis=new boolean[runs];
		calc(0);
		int max=0;
		for (int i = 0; i < prefixArr.length; i++) {
			max=Math.max(max,prefixArr[i]);
		}
		out.println(max);
		in.close(); out.close();
	}

	public static int calc(int pos)
	{
		vis[pos]=true;
		int res=prefixArr[pos];
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			res+=calc(arr[pos].list.get(i));
		}
		return prefixArr[pos]=res;
	}

	public static void dfs(int pos)
	{
		vis[pos]=true;
		if(firstOccurence[pos]==-1)
			firstOccurence[pos]=timeCounter;
		if(nodeVis[pos]==-1)
		{
			timeToNode[counter]=pos;
			nodeVis[pos]=counter;
			counter++;
		}
		nodeNum[timeCounter]=nodeVis[pos];
		timeCounter++;
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			parent[arr[pos].list.get(i)]=pos;
			dfs(arr[pos].list.get(i));
			nodeNum[timeCounter]=nodeVis[pos];
			timeCounter++;
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}
class adjlist
{
	ArrayList<Integer> list;
	public adjlist()
	{
		list=new ArrayList<Integer>();
	}
	public void add(int a)
	{
		list.add(a);
	}
}