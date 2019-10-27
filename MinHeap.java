import java.util.*;
import java.io.*;
public class MinHeap {
	public static void main(String[] args) throws Exception {
		// Heap a=new Heap(10);
		// a.add(4);
		// a.add(100);
		// a.add(45);
		// a.add(2);
		// a.add(10);
		// a.add(0);
		// System.out.println(Arrays.toString(a.arr));
		// PriorityQueue<Integer> pq=new PriorityQueue<Integer>();
		// pq.add(4);
		// pq.add(100);
		// pq.add(45);
		// pq.add(2);
		// pq.add(10);
		// pq.add(0);
		// System.out.println(pq);
		// System.out.println(pq.remove()+" "+a.remove());
		// System.out.println(pq+" "+Arrays.toString(a.arr));
		// System.out.println(pq.remove()+" "+a.remove());
		// System.out.println(pq+" "+Arrays.toString(a.arr));
		// System.out.println(pq.remove()+" "+a.remove());
		// System.out.println(pq+" "+Arrays.toString(a.arr));
		// System.out.println(pq.remove()+" "+a.remove());
		// System.out.println(pq+" "+Arrays.toString(a.arr));
		// System.out.println(pq.remove()+" "+a.remove());
		// System.out.println(pq+" "+Arrays.toString(a.arr));
		// System.out.println(pq.remove()+" "+a.remove());
		// System.out.println(pq+" "+Arrays.toString(a.arr));
		Heap a=new Heap(1005);
		PriorityQueue<Integer> pq=new PriorityQueue<Integer>();
		for (int i = 0; i < 1000; i++) {
			int num=(int)(Math.random()*10000);
			a.add(num);
			pq.add(num);
		}
		for (int i = 0; i < 1000; i++) {
			int one=a.remove();
			int two=pq.remove();
			System.out.println(one+" "+two+" "+(one==two));
			// System.out.println(Arrays.toString(a.arr));
		}
	}
	static class Heap
	{
		int[] arr;
		int count;
		public Heap(int size)
		{
			arr=new int[size+1];
			count=1;
		}
		public void add(int a)
		{
			int curNum=count;
			arr[curNum]=a;
			while(curNum!=1)
			{
				if(arr[curNum/2]>arr[curNum])
				{
					arr[curNum/2]^=arr[curNum];
					arr[curNum]^=arr[curNum/2];
					arr[curNum/2]^=arr[curNum];
				}
				else
					break;
				curNum/=2;
			}
			count++;
		}
		public int remove()
		{
			count--;
			int res=arr[1];
			arr[1]^=arr[count];
			arr[count]^=arr[1];
			arr[1]^=arr[count];
			arr[count]=0;
			int curNum=1;
			if(count<=3)
			{
				if(count==3)
				{
					if(arr[1]>arr[2])
					{
						arr[1]^=arr[2];
						arr[2]^=arr[1];
						arr[1]^=arr[2];
					}
				}
				return res;
			}
			while(curNum*2+1<count)
			{
				if(arr[curNum*2]<arr[curNum*2+1])
				{
					if(arr[curNum]>arr[curNum*2])
					{
						arr[curNum]^=arr[curNum*2];
						arr[curNum*2]^=arr[curNum];
						arr[curNum]^=arr[curNum*2];
						curNum=curNum*2;
					}
					else
						break;
				}
				else
				{
					if(arr[curNum]>arr[curNum*2+1])
					{
						arr[curNum]^=arr[curNum*2+1];
						arr[curNum*2+1]^=arr[curNum];
						arr[curNum]^=arr[curNum*2+1];
						curNum=curNum*2+1;
					}
					else
						break;
				}
			}
			return res;
		}
	}
}