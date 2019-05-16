import java.util.*;
import java.io.*;
public class suffixArray {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("suffixArray.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("suffixArray.out")));
		
		st = new StringTokenizer(in.readLine());
		String input=s().toUpperCase();
		suffix[] suffixes=new suffix[input.length()];
		for (int i = 0; i < input.length(); i++) {
			suffixes[i]=new suffix(i,input.charAt(i)-36,i+1==input.length()?-1:input.charAt(i+1)-36);
		}
		countingSortMain(suffixes);
		int[] order=new int[suffixes.length];
		for (int i = 2; i < suffixes.length; i*=2) {
			int rank=0;
			int prevRank=suffixes[0].rank[0];
			suffixes[0].rank[0]=order[suffixes[0].index]=0;
			for (int j = 1; j < suffixes.length; j++) {
				if(!(suffixes[j].rank[0]==prevRank&&suffixes[j].rank[1]==suffixes[j-1].rank[1]))
					rank++;
				prevRank=suffixes[j].rank[0];
				suffixes[j].rank[0]=rank;
				order[suffixes[j].index]=j;
			}
			for (int j = 0; j < suffixes.length; j++) {
				suffixes[j].rank[1]=suffixes[j].index+i<suffixes.length?suffixes[order[suffixes[j].index+i]].rank[0]:-1;
			}
			countingSortMain(suffixes);
		}
		int[] invPos=new int[suffixes.length];
		for (int i = 0; i < invPos.length; i++) {
			invPos[suffixes[i].index]=i;
		}
		int[] lcp=new int[suffixes.length];
		int length=0;
		for (int i = 0; i < suffixes.length; i++) {
			if(invPos[i]==suffixes.length-1)
			{
				length=0;
				continue;
			}
			int nextPos=suffixes[invPos[i]+1].index;
			while(length+i<input.length()&&length+nextPos<input.length()&&input.charAt(length+i)==input.charAt(length+nextPos))
				length++;
			lcp[invPos[i]]=length;
			if(length>0)
				length--;
		}
		for (int i = 0; i < suffixes.length; i++) {
			System.out.printf("%-2d %2d %s%n",suffixes[i].index,lcp[i],input.substring(suffixes[i].index));
		}
		// System.out.println(Arrays.toString(lcp));
		in.close(); out.close();
	}

	public static void countingSortMain(suffix[] arr)
	{
		countingSort(arr,1);
		countingSort(arr,0);
	}
	public static void countingSort(suffix[] arr,int type)
	{
		int max=0;
		for (int i = 0; i < arr.length; i++) {
			max=Math.max(max,arr[i].rank[type]+1);
		}
		int[] prefix=new int[max+1];
		for (int i = 0; i < arr.length; i++) {
			prefix[arr[i].rank[type]+1]++;
		}
		for (int i = 1; i < prefix.length; i++) {
			prefix[i]+=prefix[i-1];
		}
		int[] order=new int[arr.length];
		for (int i = arr.length-1; i >= 0; i--) {
			order[prefix[arr[i].rank[type]+1]-1]=i;
			prefix[arr[i].rank[type]+1]--;
		}
		int[] tempArr1=new int[arr.length];
		int[] tempArr2=new int[arr.length];
		int[] index=new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			tempArr1[i]=arr[order[i]].rank[0];
			tempArr2[i]=arr[order[i]].rank[1];
			index[i]=arr[order[i]].index;
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i].rank[0]=tempArr1[i];
			arr[i].rank[1]=tempArr2[i];
			arr[i].index=index[i];
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}
class suffix
{
	int index;
	int[] rank;
	public suffix(int a,int b,int c)
	{
		index=a;
		rank=new int[2];
		rank[0]=b;
		rank[1]=c;
	}
}