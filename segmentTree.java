import java.util.*;
import java.io.*;
public class segmentTree {
	public static void main(String[] args) throws Exception {

		int[] temp=new int[10000];
		segTree a=new segTree(10000);

		for (int i = 0; i < 1000; i++) {
			int pos=(int)(Math.random()*temp.length);
			int val=(int)(Math.random()*1000);
			temp[pos]+=val;
			a.update(0,0,temp.length-1,pos,val);
			for (int j = 0; j < temp.length; j++) {
				if(a.query(0,0,temp.length-1,j,j)!=temp[j]) System.out.println("ERROR");
			}
		}
	}

	static class segTree
	{
		int[] arr;
		public segTree(int size)
		{
			arr=new int[size<<2];
		}
		public int query(int node,int left,int right,int l,int r)
		{
			if(left>r||right<l) return 0;
			if(left>=l&&right<=r) return arr[node];
			int mid=(left+right)/2;
			return query(node*2+1,left,mid,l,r)+query(node*2+2,mid+1,right,l,r);
		}
		public void update(int node,int left,int right,int pos,int val)
		{
			if(left>pos||right<pos) return;
			if(left==right&&left==pos)
			{
				arr[node]+=val;
				return;
			}
			int mid=(left+right)/2;
			update(node*2+1,left,mid,pos,val);
			update(node*2+2,mid+1,right,pos,val);
			arr[node]=arr[node*2]+arr[node*2+1];
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}