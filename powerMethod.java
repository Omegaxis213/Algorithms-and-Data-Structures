import java.util.*;
import java.io.*;
public class powerMethod
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader f=new BufferedReader(new FileReader("temp.in"));
		StringTokenizer st;
		int size=Integer.parseInt(f.readLine());
		double[][] mat=new double[size][size];
		double[] eVec=new double[size];
		st=new StringTokenizer(f.readLine());
		for (int i=0;i<size;i++) {
			eVec[i]=Integer.parseInt(st.nextToken());
		}
		f.readLine();
		for (int i=0;i<size;i++) {
			st=new StringTokenizer(f.readLine());
			for (int j=0;j<size;j++) {
				mat[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		int num=20;
		for (int i=0;i<num;i++) {
			double[] newEVec=new double[size];
			double max=0;
			int pos=-1;
			for (int a=0;a<size;a++) {
				for (int b=0;b<size;b++) {
					newEVec[a]+=mat[a][b]*eVec[b];
				}
				if(Math.abs(newEVec[a])>max)
				{
					max=Math.abs(newEVec[a]);
					pos=a;
				}
			}
			// System.out.println(Arrays.toString(newEVec));
			double norm=newEVec[pos];
			for (int a=0;a<size;a++) {
				newEVec[a]/=norm;
			}
			eVec=newEVec;
			System.out.print("iteration - "+(i+1)+" arr: "+Arrays.toString(newEVec)+" norm: "+norm+"\n");
		}
	}
}