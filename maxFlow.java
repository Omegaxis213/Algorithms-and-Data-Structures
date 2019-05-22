import java.util.*;
import java.io.*;
public class maxFlow {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("maxFlow.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maxFlow.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int runs=i();
		int nodes=i();
		int[][] adjmat=new int[nodes][nodes];
		for (int i = 0; i < runs; i++) {
			st = new StringTokenizer(in.readLine());
			int one=i();
			int two=i();
			int three=i();
			adjmat[one][two]=three;
		}
		int ans=0;
		while(true)
		{
			int[] mst=new int[nodes];
			int[] parent=new int[nodes];
			boolean[] vis=new boolean[nodes];
			Arrays.fill(mst,1<<28);
			mst[0]=0;
			parent[0]=-1;
			while(true)
			{
				int pos=-1;
				int min=1<<28;
				for (int i = 0; i < nodes; i++) {
					if(vis[i]) continue;
					if(mst[i]<min)
					{
						min=mst[i];
						pos=i;
					}
				}
				if(pos==-1) break;
				vis[pos]=true;
				for (int i = 0; i < nodes; i++) {
					if(vis[i]||adjmat[pos][i]==0) continue;
					if(mst[i]>adjmat[pos][i])
					{
						mst[i]=adjmat[pos][i];
						parent[i]=pos;
					}
				}
			}
			if(mst[mst.length-1]==1<<28) break;
			int cur=mst.length-1;
			int min=1<<28;
			while(parent[cur]!=-1)
			{
				min=Math.min(min,mst[cur]);
				cur=parent[cur];
			}
			ans+=min;
			cur=mst.length-1;
			while(parent[cur]!=-1)
			{
				adjmat[cur][parent[cur]]+=min;
				adjmat[parent[cur]][cur]-=min;
				cur=parent[cur];
			}
		}
		System.out.println(ans);

		in.close(); out.close();
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}