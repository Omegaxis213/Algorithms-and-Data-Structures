import java.util.*;
import java.io.*;
public class lazySegmentTree {
	public static void main(String[] args) throws Exception {

		int[] temp=new int[10000];
		segTree a=new segTree(10000);

		for (int i = 0; i < 1000; i++) {
			int one=(int)(Math.random()*temp.length);
			int two=(int)(Math.random()*temp.length);
			int startPos=Math.min(one,two);
			int endPos=Math.max(one,two);
			int val=(int)(Math.random()*1000);
			for (int j = startPos; j <= endPos; j++) {
				temp[j]+=val;
			}
			a.update(0,0,temp.length-1,startPos,endPos,val);
			for (int j = 0; j < 100; j++) {
				one=(int)(Math.random()*temp.length);
				two=(int)(Math.random()*temp.length);
				startPos=Math.min(one,two);
				endPos=Math.max(one,two);
				int sum=0;
				for (int k = startPos; k <= endPos; k++) {
					sum+=temp[k];
				}
				if(a.query(0,0,temp.length-1,startPos,endPos)!=sum) System.out.println("ERROR");
			}
		}

	}

	static class segTree
	{
		long[] seg;
		long[] lazy;
		public segTree(int size)
		{
			seg=new long[size<<2];
			lazy=new long[size<<2];
		}
		public void update(int node,int left,int right,int l,int r,long val)
		{
			if(left>r||right<l)
				return;
			if(left>=l&&right<=r)
			{
				seg[node]+=val*(right-left+1);
				lazy[node]+=val;
				return;
			}
			if(lazy[node]!=0)
			{
				seg[node*2+1]+=lazy[node]*((left+right)/2-left+1);
				seg[node*2+2]+=lazy[node]*(right-((left+right)/2+1)+1);
				lazy[node*2+1]+=lazy[node];
				lazy[node*2+2]+=lazy[node];
				lazy[node]=0;
			}
			update(node*2+1,left,(left+right)/2,l,r,val);
			update(node*2+2,(left+right)/2+1,right,l,r,val);
			seg[node]=seg[node*2+1]+seg[node*2+2];
		}
		public long query(int node,int left,int right,int l,int r)
		{
			if(left>r||right<l)
				return 0;
			if(left>=l&&right<=r)
				return seg[node];
			if(lazy[node]!=0)
			{
				seg[node*2+1]+=lazy[node]*((left+right)/2-left+1);
				seg[node*2+2]+=lazy[node]*(right-((left+right)/2+1)+1);
				lazy[node*2+1]+=lazy[node];
				lazy[node*2+2]+=lazy[node];
				lazy[node]=0;
			}
			return query(node*2+1,left,(left+right)/2,l,r)+query(node*2+2,(left+right)/2+1,right,l,r);
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}