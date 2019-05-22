import java.util.*;
import java.io.*;
public class dijkstraAdjmat {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("dijkstraAdjmat.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dijkstraAdjmat.out")));
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
			adjmat[two][one]=three;
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
			for (int i = 0; i < nodes; i++) {
				if(adjmat[pos][i]==0) continue;
				dis[i]=Math.min(dis[i],dis[pos]+adjmat[pos][i]);
			}
		}
		System.out.println(Arrays.toString(dis));

		in.close(); out.close();
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}