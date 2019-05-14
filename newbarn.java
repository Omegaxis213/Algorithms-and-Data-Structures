import java.util.*;
import java.io.*;
public class newbarn {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("newbarn.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("newbarn.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int runs=i();
		int[] prefix=new int[runs+1];
		int curNum=0;
		while((1<<curNum)<runs)
		{
			prefix[1<<curNum]++;
			curNum++;
		}
		for (int i = 1; i < prefix.length; i++) {
			prefix[i]+=prefix[i-1];
		}
		int[][] jump=new int[runs][prefix[runs]];
		for (int i = 0; i < runs; i++) {
			for (int j = 0; j < prefix[runs]; j++) {
				jump[i][j]=-1;
			}
		}
		int diamOne=0;
		int diamTwo=0;
		int curDiam=0;
		int counter=0;
		int[] level=new int[runs];
		Arrays.fill(level,-1);
		level[0]=0;
		for (int i = 0; i < runs; i++) {
			st = new StringTokenizer(in.readLine());
			String type=s();
			int barn=i();
			if(barn==-1)
				level[counter]=0;
			if(barn!=-1)
				barn--;
			if(type.equals("B"))
			{
				if(barn!=-1)
					level[counter]=level[barn]+1;
				jump[counter][0]=barn;
				for (int j = 1; j < prefix[runs]; j++) {//make the parent links
					if(jump[counter][j-1]==-1) break;
					jump[counter][j]=jump[jump[counter][j-1]][j-1];//a jump of length 1<<j from counter is equal to a jump from counter to counter+1<<(j-1) and then from counter+1<<(j-1) to counter+1<<j
				}
				int one=counter;
				int two=diamOne;
				int jumpCounter=prefix[runs]-1;
				while(level[one]>level[two])//if node one is below node two, keep jumping node one
				{
					if(level[one]-(1<<jumpCounter)<level[two])
					{
						jumpCounter--;
						continue;
					}
					one=jump[one][jumpCounter];
					jumpCounter--;
				}
				jumpCounter=prefix[runs]-1;
				while(level[two]>level[one])//if node two is below node one, keep jumping node two
				{
					if(level[two]-(1<<jumpCounter)<level[one])
					{
						jumpCounter--;
						continue;
					}
					two=jump[two][jumpCounter];
					jumpCounter--;
				}
				// System.out.println(one+" "+two+" "+level[one]+" "+level[two]);
				if(one==two)//found the LCA
				{
					int dis=level[counter]-level[one]+level[diamOne]-level[one];
					if(dis>curDiam)
					{
						curDiam=dis;
						diamTwo=counter;
					}
				}
				else//didn't find the LCA
				{
					jumpCounter=prefix[runs]-1;
					while(jumpCounter>=0)
					{
						if(jump[one][jumpCounter]!=jump[two][jumpCounter])
						{
							one=jump[one][jumpCounter];
							two=jump[two][jumpCounter];
						}
						jumpCounter--;
					}
					one=jump[one][0];
					two=jump[two][0];
					System.out.println(counter+" "+one+" "+diamOne+" "+one);
					int dis=level[counter]-level[one]+level[diamOne]-level[one];
					if(dis>curDiam)
					{
						curDiam=dis;
						diamTwo=counter;
					}
				}

				one=counter;
				two=diamTwo;
				jumpCounter=prefix[runs]-1;
				while(level[one]>level[two])//if node one is below node two, keep jumping node one
				{
					if(level[one]-(1<<jumpCounter)<level[two])
					{
						jumpCounter--;
						continue;
					}
					one=jump[one][jumpCounter];
					jumpCounter--;
				}
				jumpCounter=prefix[runs]-1;
				while(level[two]>level[one])//if node two is below node one, keep jumping node two
				{
					if(level[two]-(1<<jumpCounter)<level[one])
					{
						jumpCounter--;
						continue;
					}
					two=jump[two][jumpCounter];
					jumpCounter--;
				}
				if(one==two)//found the LCA
				{
					int dis=level[counter]-level[one]+level[diamTwo]-level[one];
					if(dis>curDiam)
					{
						curDiam=dis;
						diamOne=counter;
					}
				}
				else//didn't find the LCA
				{
					jumpCounter=prefix[runs]-1;
					while(jumpCounter>=0)
					{
						if(jump[one][jumpCounter]!=jump[two][jumpCounter])
						{
							one=jump[one][jumpCounter];
							two=jump[two][jumpCounter];
						}
						jumpCounter--;
					}
					one=jump[one][0];
					two=jump[two][0];
					int dis=level[counter]-level[one]+level[diamTwo]-level[one];
					if(dis>curDiam)
					{
						curDiam=dis;
						diamOne=counter;
					}
				}
				counter++;
				// System.out.println("diamter: "+diamOne+" "+diamTwo+" "+curDiam);
			}
			else
			{
				int ans=0;
				int one=barn;
				int two=diamOne;
				int jumpCounter=prefix[runs]-1;
				while(level[one]>level[two])//if node one is below node two, keep jumping node one
				{
					if(level[one]-(1<<jumpCounter)<level[two])
					{
						jumpCounter--;
						continue;
					}
					one=jump[one][jumpCounter];
					jumpCounter--;
				}
				jumpCounter=prefix[runs]-1;
				while(level[two]>level[one])//if node two is below node one, keep jumping node two
				{
					if(level[two]-(1<<jumpCounter)<level[one])
					{
						jumpCounter--;
						continue;
					}
					two=jump[two][jumpCounter];
					jumpCounter--;
				}
				if(one==two)//found the LCA
				{
					int dis=level[barn]-level[one]+level[diamOne]-level[one];
					ans=Math.max(ans,dis);
				}
				else//didn't find the LCA
				{
					jumpCounter=prefix[runs]-1;
					while(jumpCounter>=0)
					{
						if(jump[one][jumpCounter]!=jump[two][jumpCounter])
						{
							one=jump[one][jumpCounter];
							two=jump[two][jumpCounter];
						}
						jumpCounter--;
					}
					one=jump[one][0];
					two=jump[two][0];
					int dis=level[barn]-level[one]+level[diamOne]-level[one];
					ans=Math.max(ans,dis);
				}

				one=barn;
				two=diamTwo;
				jumpCounter=prefix[runs]-1;
				while(level[one]>level[two])//if node one is below node two, keep jumping node one
				{
					if(level[one]-(1<<jumpCounter)<level[two])
					{
						jumpCounter--;
						continue;
					}
					one=jump[one][jumpCounter];
					jumpCounter--;
				}
				jumpCounter=prefix[runs]-1;
				while(level[two]>level[one])//if node two is below node one, keep jumping node two
				{
					if(level[two]-(1<<jumpCounter)<level[one])
					{
						jumpCounter--;
						continue;
					}
					two=jump[two][jumpCounter];
					jumpCounter--;
				}
				if(one==two)//found the LCA
				{
					int dis=level[barn]-level[one]+level[diamTwo]-level[one];
					ans=Math.max(ans,dis);
				}
				else//didn't find the LCA
				{
					jumpCounter=prefix[runs]-1;
					while(jumpCounter>=0)
					{
						if(jump[one][jumpCounter]!=jump[two][jumpCounter])
						{
							one=jump[one][jumpCounter];
							two=jump[two][jumpCounter];
						}
						jumpCounter--;
					}
					one=jump[one][0];
					two=jump[two][0];
					int dis=level[barn]-level[one]+level[diamTwo]-level[one];
					ans=Math.max(ans,dis);
				}
				out.println(ans);
			}
		}
		in.close(); out.close();
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}