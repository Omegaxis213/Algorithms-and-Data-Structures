import java.util.*;
import java.io.*;
public class vanEmdeBoasTree {//correct so far, but still need to convert the array into hashmaps to save on memory space and runtime
	static int count;
	static int count1;
	static int count2;
	static int newAdditions;
	public static void main(String[] args) throws Exception {
		long time=System.nanoTime();
		vEBTree a=new vEBTree(1L<<50);
		System.out.println(System.nanoTime()-time);
		time=System.nanoTime();
		TreeSet<Long> set=new TreeSet<Long>();
		for (int i = 0; i < 200000; i++) {
			long num=(long)(Math.random()*(1L<<50));
			// int num=i;
			if(set.contains(num)) continue;
			set.add(num);
			a.insert(a,num);
			// System.out.println();
		}
		System.out.println("new additions: "+newAdditions);
		System.out.println("count: "+count);
		System.out.println("size: "+set.size());
		System.out.println("average operations: "+(double)count/set.size());
		System.out.println(System.nanoTime()-time);

		// Queue<vEBTree> queue=new LinkedList<vEBTree>();
		// queue.add(a);
		// int counter=0;
		// while(!queue.isEmpty())
		// {
		// 	vEBTree temp=queue.poll();
		// 	System.out.println("cluster- size: "+temp.totSize+" min: "+temp.min+" max: "+temp.max+" num: "+temp.num+" ID: "+temp+" parent: "+temp.parent);
		// 	System.out.println("summary- size: "+temp.summary.totSize+" min: "+temp.summary.min+" max: "+temp.summary.max+" num: "+temp.summary.num+" ID: "+temp.summary+" parent: "+temp.summary.parent);
		// 	for (int i = 0; i < temp.cluster.length; i++) {
		// 		temp.cluster[i].parent=temp;
		// 		queue.add(temp.cluster[i]);
		// 	}
		// }
		// dfs(a);
		// System.out.println(set);
		time=System.nanoTime();
		for (long val : set) {
			if(!a.search(a,val)) System.out.println(a.search(a,val));
		}
		for (long val : set) {
			// System.out.println(val);
			if(set.higher(val)==null) continue;
			if(set.higher(val)!=a.successor(a,val)) System.out.println(set.higher(val)+" "+a.successor(a,val));
		}
		System.out.println("new additions: "+newAdditions);
		System.out.println("count1: "+count1);
		System.out.println("average operations: "+(double)count1/set.size());
		Long[] arr=set.toArray(new Long[0]);
		boolean[] del=new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if(Math.random()>.01)
			{
				a.delete(a,arr[i]);
				del[i]=true;
				// set.remove(arr[i]);
			}
		}
		System.out.println("new additions: "+newAdditions);
		System.out.println("count2: "+count2);
		System.out.println("average operations: "+(double)count2/set.size());
		for (int i = 0; i < arr.length; i++) {
			// if(del[i]) continue;
			if(!(del[i]^a.search(a,arr[i]))) System.out.println(a.search(a,arr[i]));
		}
		// for (int i = 0; i < arr.length-1; i++) {
		// 	if(del[i]) continue;
		// 	if(arr[i+1]!=a.successor(a,arr[i])) System.out.println(arr[i+1]+" "+a.successor(a,arr[i]));
		// }
		// for (long val : set) {
		// 	// System.out.println(val);
		// 	if(set.higher(val)==null) continue;
		// 	if(set.higher(val)!=a.successor(a,val)) System.out.println(set.higher(val)+" "+a.successor(a,val));
		// }
		System.out.println(System.nanoTime()-time);
	}
	static void dfs(vEBTree a)
	{
		if(a==null) return;
		System.out.println("size: "+a.totSize+" min: "+a.min+" max: "+a.max+" num: "+a.num+" ID: "+a);
		// if(a.summary!=null) System.out.println("summary min: "+a.summary.min+" max: "+a.summary.max);
		// if(a.cluster[0]==null) count++;
		for (long temp : a.cluster.keySet()) {
			dfs(a.cluster.get(temp));
		}
	}
	static class vEBTree
	{
		HashMap<Long,vEBTree> cluster;
		// vEBTree[] cluster;
		vEBTree summary;
		long totSize;
		long nextSize;
		long min,max;
		int num;
		int numOfBits;
		long highBits,lowBits;
		vEBTree parent;
		public vEBTree(long size)
		{
			min=max=-1;
			totSize=size;
			nextSize=(totSize==2?1:(long)Math.ceil(Math.sqrt(totSize)));
			// System.out.println(Long.toBinaryString(totSize)+" "+Long.toBinaryString(nextSize)+" "+Long.toBinaryString(high(totSize))+" "+(64-Long.numberOfLeadingZeros(totSize)));
			numOfBits=64-Long.numberOfLeadingZeros(totSize);
			// System.out.println(Long.toBinaryString((1L<<numOfBits)-1));
			// System.out.println(Long.toBinaryString((1L<<numOfBits)-((1L<<numOfBits/2))));
			// System.out.println(Long.toBinaryString((1L<<numOfBits/2)-1));
			highBits=(1L<<numOfBits)-(1L<<numOfBits/2);
			lowBits=(1L<<numOfBits/2)-1;
			// createCluster();
		}
		public vEBTree(long size,boolean flag)
		{
			min=max=-1;
			totSize=size;
		}
		void createCluster()
		{
			cluster=new HashMap<>();
		}
		void createSummary()
		{
			if(nextSize==1)
				summary=new vEBTree(nextSize,true);
			else
				summary=new vEBTree(nextSize);
		}
		void insert(vEBTree a,long pos)
		{
			// System.out.println("first: "+pos+" curSize: "+a.totSize+" num: "+a.num+" id: "+a);
			if(a.min==-1)
			{
				a.min=a.max=pos;
				return;
			}
			count++;
			if(pos<a.min)
			{
				long temp=a.min;
				a.min=pos;
				pos=temp;
			}
			if(pos>a.max) a.max=pos;
			if(a.totSize==1)
				a.min=a.max=pos;
			else
			{
				long i=a.high(pos);
				if(a.cluster==null) a.createCluster();
				if(!a.cluster.containsKey(i))
				{
					newAdditions++;
					a.cluster.put(i,new vEBTree(a.nextSize));
				}
				if(a.cluster.get(i).min==-1)
				{
					if(a.summary==null) a.createSummary();
					insert(a.summary,i);
				}
				insert(a.cluster.get(i),a.low(pos));
			}
		}
		long successor(vEBTree a,long pos)
		{
			if(pos<a.min) return a.min;
			if(pos>=a.max) return -1;
			count1++;
			long i=a.high(pos);
			long j=-1;
			if(a.cluster==null) a.createCluster();
			if(!a.cluster.containsKey(i))
			{
				newAdditions++;
				a.cluster.put(i,new vEBTree(a.nextSize));
			}
			if(a.low(pos)<a.cluster.get(i).max)
			{
				j=successor(a.cluster.get(i),a.low(pos));
			}
			else
			{
				if(a.summary==null) a.createSummary();
				i=successor(a.summary,i);
				if(i>=0) j=a.cluster.get(i).min;
			}
			return i<0||j<0?-2:a.index(i,j);
		}
		void delete(vEBTree a,long pos)
		{
			if(pos==a.min)
			{
				long i=a.summary==null?-1:a.summary.min;
				if(i==-1)
				{
					a.min=a.max=-1;
					return;
				}
				pos=a.min=a.index(i,a.cluster.get(i).min);
			}
			count2++;
			long posi=a.high(pos);
			if(a.cluster==null) a.createCluster();
			if(!a.cluster.containsKey(posi))
			{
				newAdditions++;
				a.cluster.put(posi,new vEBTree(a.nextSize));
			}
			delete(a.cluster.get(posi),a.low(pos));
			if(a.cluster.get(posi).min==-1) delete(a.summary,posi);
			if(pos==a.max)
			{
				if(a.summary==null) a.createSummary();
				if(a.summary.max==-1)
					a.max=a.min;
				else
				{
					long i=a.summary.max;
					a.max=a.index(i,a.cluster.get(i).max);
				}
			}
		}
		boolean search(vEBTree a,long pos)
		{
			if(pos<a.min||pos>a.max) return false;
			if(pos==a.min||pos==a.max) return true;
			long i=a.high(pos);
			long j=a.low(pos);
			if(a.cluster==null||a.cluster.get(i)==null) return false;
			return search(a.cluster.get(i),j);
		}
		long high(long a)
		{
			return a/nextSize;
		}
		long low(long a)
		{
			return a%nextSize;
		}
		long index(long a,long b)
		{
			return nextSize*a+b;
		}
	}
}