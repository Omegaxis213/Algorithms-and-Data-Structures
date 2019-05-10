import java.util.*;
import java.io.*;
public class FastFourierTransform {//Multiplies 2 polynomials in O(NlgN). May seem useless, but is actually useful in discovering sine waves and can smooth out waves
	public static void main(String[] args) throws Exception {
		int size=64;
		int log2=32-Integer.numberOfLeadingZeros(size-1);
		// int newSize=1<<(log2+1);
		int newSize=64;
		complex[] arr=new complex[newSize];
		// for (int i = 0; i < size; i++) {//put in polynomial here
		// 	arr[i]=new complex(1,0);
		// }
		for (int i = 0; i < size; i++) {
			// arr[i]=new complex(Math.cos(2*Math.PI*10*(1/320.0*i))*Math.cos(2*Math.PI*10*(1/320.0*i)),0);
			arr[i]=new complex(Math.cos(4*Math.PI*2/size*i)+Math.cos(8*Math.PI*2/size*i),0);
		}
		for (int i = size; i < arr.length; i++) {//pad with zeros
			arr[i]=new complex(0,0);
		}
		System.out.println("ARR:");
		for (int i = 0; i < arr.length; i++) {
			System.out.printf("%.6fi%+.6f%n",arr[i].imagPart,arr[i].intPart);
		}
		System.out.println();
		complex[] num=new complex[newSize];
		for (int i = 0; i < num.length; i++) {//calculate complex nums
			num[i]=new complex(Math.cos(Math.PI*2/newSize*i),Math.sin(Math.PI*2/newSize*i));
		}
		// System.out.println("NUM:");
		// for (int i = 0; i < num.length; i++) {
		// 	System.out.printf("%.6fi%+.6f%n",num[i].imagPart,num[i].intPart);
		// }
		// System.out.println();
		complex[] res=FFT(arr,num,0,1);
		System.out.println("FIRST FFT:");
		for (int i = 0; i < res.length; i++) {
			System.out.printf("%.6fi%+.6f%n",res[i].imagPart,res[i].intPart);
		}
		System.out.println();

		// System.out.println("MAGNITUDE: ");
		// for (int i = 0; i < res.length; i++) {
		// 	System.out.printf("%.6f%n",2*Math.sqrt(res[i].imagPart*res[i].imagPart+res[i].intPart*res[i].intPart));
		// }
		// System.out.println();
		// complex[] test=IFFT(res,num,0,1);
		// System.out.println("INVERSE FIRST FFT:");
		// for (int i = 0; i < test.length; i++) {
		// 	System.out.printf("%.6fi%+.6f%n",test[i].imagPart,test[i].intPart);
		// }
		// System.out.println();

		// System.out.println("DOT PRODUCT:");
		// for (int i = 0; i < res.length; i++) {
		// 	double intPart=res[i].intPart*res[i].intPart-res[i].imagPart*res[i].imagPart;
		// 	double imagPart=res[i].intPart*res[i].imagPart+res[i].intPart*res[i].imagPart;
		// 	res[i].update(intPart,imagPart);
		// 	System.out.printf("%.6fi%+.6f%n",res[i].imagPart,res[i].intPart);
		// }
		// System.out.println();
		// System.out.println("RESULT:");
		// complex[] ans=IFFT(res,num,0,1);
		// for (int i = 0; i < ans.length; i++) {//result of multiplication
		// 	System.out.printf("%.6fi%+.6f%n",ans[i].imagPart,ans[i].intPart);
		// }
	}
	static complex[] FFT(complex[] arr,complex[] num,int pos,int skip)
	{
		if(pos+skip>=arr.length) return new complex[]{arr[pos]};
		complex[] evens=FFT(arr,num,pos,skip*2);
		complex[] odds=FFT(arr,num,pos+skip,skip*2);
		complex[] res=new complex[odds.length+evens.length];
		for (int i = 0; i < res.length; i++) {
			int curPos=(i*2)%res.length/2;
			double intPart=evens[curPos].intPart+num[arr.length/res.length*i].intPart*odds[curPos].intPart-num[arr.length/res.length*i].imagPart*odds[curPos].imagPart;
			double imagPart=evens[curPos].imagPart+num[arr.length/res.length*i].intPart*odds[curPos].imagPart+num[arr.length/res.length*i].imagPart*odds[curPos].intPart;
			res[i]=new complex(intPart,imagPart);
		}
		return res;
	}
	static complex[] IFFT(complex[] arr,complex[] num,int pos,int skip)
	{
		for (int i = 0; i < num.length; i++) {
			num[i].imagPart=-num[i].imagPart;
		}
		complex[] temp=FFT(arr,num,pos,skip);
		for (int i = 0; i < temp.length; i++) {
			temp[i].intPart/=temp.length;
			temp[i].imagPart/=temp.length;
		}
		for (int i = 0; i < num.length; i++) {
			num[i].imagPart=-num[i].imagPart;
		}
		return temp;
	}
	static class complex
	{
		double intPart,imagPart;
		public complex(double a,double b)
		{
			intPart=a;
			imagPart=b;
		}
		public void update(double a,double b)
		{
			intPart=a;
			imagPart=b;
		}
	}
}