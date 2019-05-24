import java.util.*;
import java.io.*;
import java.awt.geom.*;
public class lineSegmentIntersection {
	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 1000; i++) {
			double oneX=Math.random()*1000;
			double oneY=Math.random()*1000;
			double twoX=Math.random()*1000;
			double twoY=Math.random()*1000;
			double threeX=Math.random()*1000;
			double threeY=Math.random()*1000;
			double fourX=Math.random()*1000;
			double fourY=Math.random()*1000;
			boolean flag=ccw(oneX,oneY,twoX,twoY,threeX,threeY)*ccw(oneX,oneY,twoX,twoY,fourX,fourY)<=0&&ccw(threeX,threeY,fourX,fourY,oneX,oneY)*ccw(threeX,threeY,fourX,fourY,twoX,twoY)<=0;
			Line2D lineOne=new Line2D.Double(oneX,oneY,twoX,twoY);
			Line2D lineTwo=new Line2D.Double(threeX,threeY,fourX,fourY);
			boolean flag1=lineOne.intersectsLine(lineTwo);
			if(flag!=flag1) System.out.println("ERROR");
		}

	}

	static double ccw(double oneX,double oneY,double twoX,double twoY,double threeX,double threeY)
	{
		return (twoX-oneX)*(threeY-oneY)-(threeX-oneX)*(twoY-oneY);
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}