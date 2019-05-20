import java.util.*;
import java.io.*;
public class matrixExponentiation {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("matrixExponentiation.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("matrixExponentiation.out")));
		String line;
		
		st = new StringTokenizer(in.readLine());
		int num=i()-1;
		long[][][] res=new long[32-Integer.numberOfLeadingZeros(num)][2][2];
		long[][] mat={{1,1},{1,0}};//matrix that advances the function by one. This matrix is specifically used for the nth fibonacci number.
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					res[i][j][k]=mat[j][k];
				}
			}
			long[][] tempArr=new long[2][2];
			for (int a = 0; a < 2; a++) {
				for (int b = 0; b < 2; b++) {
					for (int c = 0; c < 2; c++) {
						tempArr[a][b]+=mat[a][c]*mat[c][b];
					}
				}
			}
			mat=tempArr;
		}
		long[][] resMat={{1,0},{0,1}};//identity matrix
		long ans=0;
		for (int i = 0; i < res.length; i++) {
			if((num&1<<i)!=0)
			{
				long[][] tempMat=new long[2][2];
				for (int a = 0; a < 2; a++) {
					for (int b = 0; b < 2; b++) {
						for (int c = 0; c < 2; c++) {
							tempMat[a][b]+=resMat[a][c]*res[i][c][b];
						}
					}
				}
				resMat=tempMat;
			}
		}
		System.out.println(num<0?0:resMat[0][0]);

		in.close(); out.close();
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}