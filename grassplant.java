import java.util.*;
import java.io.*;
public class grassplant {
	static boolean[] vis;
	static adjlist[] arr;
	static int[] subTree;
	static chain[] chains;
	static int counter;
	static int[] nodeToChain;
	static int[] parentChain;
	static int counter1;
	static int[] order;
	static int[] orderToNode;
	static ArrayList<Integer> node;
	static int[] parentNode;
	static int[] chainPos;
	static int[] firstOccur;
	static int fin;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("grassplant.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("grassplant.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int runs=i();
		int queries=i();
		arr=new adjlist[runs];
		subTree=new int[runs];
		for (int i = 0; i < runs; i++) {
			arr[i]=new adjlist();
		}
		for (int i = 0; i < runs-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one=i()-1;
			int two=i()-1;
			arr[one].add(two);
			arr[two].add(one);
		}

		vis=new boolean[runs];
		dfs(0);
		vis=new boolean[runs];
		parentNode=new int[runs];
		Arrays.fill(parentNode,-1);
		updateParent(0);
		chains=new chain[runs];//not sure why this seems like O(N) memory in worst case. For a full binary tree, it uses n/2 spaces
		vis=new boolean[runs];
		nodeToChain=new int[runs];
		parentChain=new int[runs];
		chainPos=new int[runs];
		Arrays.fill(nodeToChain,-1);
		Arrays.fill(parentChain,-1);
		HLD(0,0);
		
		segtree chainSegtree=new segtree(runs);
		
		vis=new boolean[runs];
		node=new ArrayList<Integer>();
		order=new int[runs];
		orderToNode=new int[runs];
		firstOccur=new int[runs];
		Arrays.fill(order,-1);
		Arrays.fill(orderToNode,-1);
		Arrays.fill(firstOccur,-1);
		eulerianTour(0);
		segtree a=new segtree(node.size());
		for (int i = 0; i < node.size(); i++) {
			a.update(1,0,node.size()-1,i,node.get(i));
		}

		for (int i = 0; i < queries; i++)
		{
			st = new StringTokenizer(in.readLine());
			String type=s();
			int one=i()-1;
			int two=i()-1;
			int lcaNode=orderToNode[a.queryMin(1,0,node.size()-1,Math.min(firstOccur[one],firstOccur[two]),Math.max(firstOccur[one],firstOccur[two]))];
			int chainOne=nodeToChain[one];
			int chainTwo=nodeToChain[two];
			if(type.equals("P"))
			{
				while(chainOne!=nodeToChain[lcaNode])
				{
					chainSegtree.updateInterval(1,0,runs-1,chainPos[chainOne],chainPos[chainOne]+chains[chainOne].map.get(one),1);
					one=parentNode[chains[chainOne].headOfChain];
					chainOne=parentChain[chainOne];
				}
				while(chainTwo!=nodeToChain[lcaNode])
				{
					chainSegtree.updateInterval(1,0,runs-1,chainPos[chainTwo],chainPos[chainTwo]+chains[chainTwo].map.get(two),1);
					two=parentNode[chains[chainTwo].headOfChain];
					chainTwo=parentChain[chainTwo];
				}
				chainSegtree.updateInterval(1,0,runs-1,chainPos[chainOne]+(chains[chainOne].map.get(lcaNode)==null?0:chains[chainOne].map.get(lcaNode)+1),chainPos[chainOne]+Math.max(chains[chainOne].map.get(one)==null?0:chains[chainOne].map.get(one),chains[chainOne].map.get(two)==null?0:chains[chainOne].map.get(two)),1);
			}
			else
			{
				int res=0;
				while(chainOne!=nodeToChain[lcaNode])
				{
					res+=chainSegtree.querySum(1,0,runs-1,chainPos[chainOne],chainPos[chainOne]+chains[chainOne].map.get(one));
					one=parentNode[chains[chainOne].headOfChain];
					chainOne=parentChain[chainOne];
				}
				while(chainTwo!=nodeToChain[lcaNode])
				{
					res+=chainSegtree.querySum(1,0,runs-1,chainPos[chainTwo],chainPos[chainTwo]+chains[chainTwo].map.get(two));
					two=parentNode[chains[chainTwo].headOfChain];
					chainTwo=parentChain[chainTwo];
				}
				if(one!=two)
					res+=chainSegtree.querySum(1,0,runs-1,chainPos[chainOne]+(chains[chainOne].map.get(lcaNode)==null?0:chains[chainOne].map.get(lcaNode)+1),chainPos[chainOne]+Math.max(chains[chainOne].map.get(one)==null?0:chains[chainOne].map.get(one),chains[chainOne].map.get(two)==null?0:chains[chainOne].map.get(two)));
				out.println(res);
			}
		}

		in.close(); out.close();
	}

	public static void updateParent(int pos)
	{
		vis[pos]=true;
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			parentNode[arr[pos].list.get(i)]=pos;
			updateParent(arr[pos].list.get(i));
		}
	}

	public static void eulerianTour(int pos)
	{
		vis[pos]=true;
		if(order[pos]==-1)
		{
			order[pos]=counter1;
			orderToNode[counter1]=pos;
			firstOccur[pos]=node.size();
			counter1++;
		}
		node.add(order[pos]);
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			eulerianTour(arr[pos].list.get(i));
			node.add(order[pos]);
		}
	}

	public static void HLD(int pos,int curChain)
	{
		vis[pos]=true;
		if(chains[curChain]==null)
		{
			nodeToChain[pos]=curChain;
			chains[curChain]=new chain(pos);
			chainPos[curChain]+=curChain==0?0:chainPos[curChain-1]+chains[curChain-1].list.size();
		}
		int max=0;
		int nextPos=-1;
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			if(subTree[arr[pos].list.get(i)]>max)
			{
				max=subTree[arr[pos].list.get(i)];
				nextPos=arr[pos].list.get(i);
			}
		}
		if(nextPos==-1) return;
		nodeToChain[nextPos]=curChain;
		chains[curChain].add(nextPos);
		HLD(nextPos,curChain);
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]||arr[pos].list.get(i)==nextPos) continue;
			counter++;
			parentChain[counter]=curChain;
			HLD(arr[pos].list.get(i),counter);
		}
	}

	public static int dfs(int pos)
	{
		fin++;
		vis[pos]=true;
		int res=1;
		for (int i = 0; i < arr[pos].list.size(); i++) {
			if(vis[arr[pos].list.get(i)]) continue;
			res+=dfs(arr[pos].list.get(i));
		}
		return subTree[pos]=res;
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
class chain
{
	HashMap<Integer,Integer> map;
	ArrayList<Integer> list;
	int headOfChain;
	segtree a;
	public chain(int pos)
	{
		map=new HashMap<Integer,Integer>();
		list=new ArrayList<Integer>();
		if(pos!=0)
		{
			list.add(pos);
			map.put(pos,0);
			headOfChain=pos;
		}
	}
	public void add(int a)
	{
		if(headOfChain==0) headOfChain=a;
		map.put(a,list.size());
		list.add(a);
	}
}
class segtree
{
	long[] seg;
	int[] min;
	long[] add;
	public segtree(int size)
	{
		seg=new long[size<<2];
		min=new int[size<<2];
		add=new long[size<<2];
	}
	public void updateInterval(int node,int left,int right,int l,int r,int val)
	{
		if(left>r||right<l) return;
		if(left>=l&&right<=r)
		{
			seg[node]+=val;
			add[node]+=val;
			return;
		}
		if(add[node]!=0)
		{
			seg[node*2]+=add[node];
			seg[node*2+1]+=add[node];
			add[node*2]+=add[node];
			add[node*2+1]+=add[node];
			add[node]=0;
		}
		int mid=(left+right)/2;
		updateInterval(node*2,left,mid,l,r,val);
		updateInterval(node*2+1,mid+1,right,l,r,val);
	}
	public void update(int node,int left,int right,int pos,long val)
	{
		if(left>pos||right<pos) return;
		if(left==right&&left==pos)
		{
			seg[node]=val;
			min[node]=(int)val;
			return;
		}
		int mid=(left+right)/2;
		update(node*2,left,mid,pos,val);
		update(node*2+1,mid+1,right,pos,val);
		seg[node]=seg[node*2]+seg[node*2+1];
		min[node]=Math.min(min[node*2],min[node*2+1]);
	}
	public long querySum(int node,int left,int right,int l,int r)
	{
		if(left>r||right<l) return 0;
		if(left>=l&&right<=r) return seg[node];
		if(add[node]!=0)
		{
			seg[node*2]+=add[node];
			seg[node*2+1]+=add[node];
			add[node*2]+=add[node];
			add[node*2+1]+=add[node];
			add[node]=0;
		}
		int mid=(left+right)/2;
		return querySum(node*2,left,mid,l,r)+querySum(node*2+1,mid+1,right,l,r);
	}
	public int queryMin(int node,int left,int right,int l,int r)
	{
		if(left>r||right<l) return 1<<28;
		if(left>=l&&right<=r) return min[node];
		int mid=(left+right)/2;
		return Math.min(queryMin(node*2,left,mid,l,r),queryMin(node*2+1,mid+1,right,l,r));
	}
}