import java.util.*;
import java.io.*;
public class dijkstraAdjlist {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("dijkstraAdjlist.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dijkstraAdjlist.out")));
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
		while(true)
		{
			int pos=-1;
			int min=1<<28;
			for (int i = 0; i < nodes; i++) {
				if(vis[i]) continue;
				if(dis[i]<min)
				{
					min=dis[i];
					pos=i;
				}
			}
			if(pos==-1) break;
			vis[pos]=true;
			for (int i = 0; i < arr[pos].list.size(); i++) {
				dis[arr[pos].list.get(i)]=Math.min(dis[arr[pos].list.get(i)],dis[pos]+arr[pos].val.get(i));
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

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}