import java.util.*;
import java.io.*;
public class dijkstraPQ {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("dijkstraPQ.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dijkstraPQ.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int runs=i();
		int nodes=i();
		adjlist[] arr=new adjlist[nodes];
		for (int i = 0; i < arr.length; i++) {
			arr[i]=new adjlist();
		}
		for (int i = 0; i < runs; i++) {
			st = new StringTokenizer(in.readLine());
			int one=i();
			int two=i();
			int three=i();
			arr[one].add(two,three);
			arr[two].add(one,three);
		}
		int[] dis=new int[nodes];
		boolean[] vis=new boolean[nodes];
		Arrays.fill(dis,1<<28);
		dis[0]=0;
		PriorityQueue<node> pq=new PriorityQueue<>();
		pq.add(new node(0,0));
		while(!pq.isEmpty())
		{
			node temp=pq.poll();
			if(vis[temp.pos]) continue;
			vis[temp.pos]=true;
			for (int i = 0; i < arr[temp.pos].list.size(); i++) {
				if(vis[arr[temp.pos].list.get(i)]) continue;
				if(dis[arr[temp.pos].list.get(i)]>dis[temp.pos]+arr[temp.pos].val.get(i))
				{
					dis[arr[temp.pos].list.get(i)]=dis[temp.pos]+arr[temp.pos].val.get(i);
					pq.add(new node(arr[temp.pos].list.get(i),dis[arr[temp.pos].list.get(i)]));
				}
			}
		}
		System.out.println(Arrays.toString(dis));

		in.close(); out.close();
	}

	static class adjlist
	{
		ArrayList<Integer> list;
		ArrayList<Integer> val;
		public adjlist()
		{
			list=new ArrayList<>();
			val=new ArrayList<>();
		}
		public void add(int a,int b)
		{
			list.add(a);
			val.add(b);
		}
	}

	static class node implements Comparable<node>
	{
		int pos,weight;
		public node(int a,int b)
		{
			pos=a;
			weight=b;
		}
		public int compareTo(node a)
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
