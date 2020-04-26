import java.util.*;
import java.io.*;
public class inversePowerMethod
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader f=new BufferedReader(new FileReader("temp.in"));
		StringTokenizer st;
		int size=Integer.parseInt(f.readLine());
		double[] eVec=new double[size];
		st=new StringTokenizer(f.readLine());
		for (int i=0;i<size;i++) {
			eVec[i]=Integer.parseInt(st.nextToken());
		}
		double[][] arr=new double[size][size];
		double estimate=Double.parseDouble(f.readLine());
		for (int i=0;i<size;i++) {
			st=new StringTokenizer(f.readLine());
			for (int j=0;j<size;j++) {
				arr[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		for (int i=0;i<size;i++) {
			arr[i][i]-=estimate;
		}
		double[][] origArr=new double[size][size];
		for (int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				origArr[i][j]=arr[i][j];
			}
		}
		double[][] upper=new double[size][size];
		double[][] lower=new double[size][size];
		for (int i=0;i<size;i++) {
			for (int a=0;a<size;a++) {
				for (int b=0;b<size;b++) {
					upper[a][b]=arr[a][b];
				}
			}
			lower[i][i]=1;
			for (int j=i+1;j<size;j++) {
				lower[j][i]=arr[j][i]/arr[i][i];
				for (int k=0;k<size;k++) {
					upper[j][k]-=arr[i][k]*arr[j][i]/arr[i][i];
				}
			}
			for (int a=0;a<size;a++) {
				for (int b=0;b<size;b++) {
					arr[a][b]=upper[a][b];
				}
			}
		}
		double[][] res=new double[size][size];
		for (int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				for (int k=0;k<size;k++) {
					res[i][j]+=lower[i][k]*arr[k][j];
				}
			}
		}
		System.out.println(Arrays.deepToString(res));
		int runs=10;
		double estEigen=0;
		for (int i=0;i<runs;i++) {
			double[] vecOne=new double[size];
			for (int a=0;a<size;a++) {
				double tempVal=0;
				for (int b=0;b<a;b++) {
					tempVal+=vecOne[b]*lower[a][b];
				}
				vecOne[a]=(eVec[a]-tempVal)/lower[a][a];
			}
			double[] vecTwo=new double[size];
			for (int a=size-1;a>=0;a--) {
				double tempVal=0;
				for (int b=size-1;b>a;b--) {
					tempVal+=vecTwo[b]*upper[a][b];
				}
				vecTwo[a]=(vecOne[a]-tempVal)/upper[a][a];
			}
			// System.out.println(Arrays.toString(vecTwo));
			double max=0;
			int pos=-1;
			for (int a=0;a<size;a++) {
				if(Math.abs(vecTwo[a])>max)
				{
					max=Math.abs(vecTwo[a]);
					pos=a;
				}
			}
			estEigen=estimate+1/vecTwo[pos];
			double norm=vecTwo[pos];
			for (int a=0;a<size;a++) {
				vecTwo[a]/=norm;
			}
			eVec=vecTwo;
			System.out.println("norm: "+norm+" estEigen: "+estEigen+" eigenVec: "+Arrays.toString(vecTwo));
		}
	}
}