import java.util.*;
import java.io.*;
public class segmentedSieve {
	public static void main(String[] args) throws Exception {
		Set<Integer> primes=new HashSet<Integer>();
		int size=10000;
		int limit=(int)Math.sqrt(size)+1;
		boolean[] mark=new boolean[limit+1];
		for (int i = 2; i*i <= limit; i++) {
			if(!mark[i])
				for (int j = i*2; j <= limit; j+=i) {
					mark[j]=true;
				}
		}
		for (int i = 2; i < mark.length; i++) {
			if(!mark[i]) primes.add(i);
		}
		Set<Integer> res=new HashSet<Integer>();
		res.addAll(primes);
		int low=limit;
		int high=limit*2;
		while(low<size)
		{
			if(high>size) high=size;
			mark=new boolean[high-low+1];
			for (int a : primes) {
				int start=low/a*a;
				if(start<low) start+=a;
				start-=low;
				for (int i = start; i < mark.length; i+=a) {
					mark[i]=true;
				}
			}
			for (int i = 0; i < mark.length; i++) {
				if(!mark[i]) res.add(i+low);
			}
			low+=limit;
			high+=limit;
		}
		System.out.println(res.size());
	}
}