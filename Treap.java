import java.util.*;
import java.io.*;
public class Treap {
	static final int DEBUG=0;//0==no printing, 1==printing
	static int count1;
	public static void main(String[] args) throws Exception {
		Tree a=new Tree();
		Set<Integer> set=new TreeSet<Integer>();
		for (int i = 0; i < 100000; i++) {
			int num=(int)(Math.random()*10000000);
			a.root=a.insert(a.root,new Node(num,(int)(Math.random()*10000000)));
			set.add(num);
		}
		System.out.println("num nodes: "+a.countNodes(a.root));
		System.out.println("depth: "+a.depth(a.root,0));
		System.out.println("weight correctness- "+(a.checkWeights(a.root)==1<<29?"Incorrect":"Correct: "+a.checkWeights(a.root)));
		System.out.println();
		Integer[] arr=set.toArray(new Integer[0]);
		int count=0;
		for (int i = 0; i < arr.length; i++) {
			if(Math.random()>.001)
			{
				count++;
				a.root=a.delete(a.root,arr[i]);
			}
		}
		System.out.println("num nodes: "+a.countNodes(a.root));
		System.out.println("num nodes deleted: "+count+", num of collisions: "+count1);
		System.out.println("Total nodes: "+(a.countNodes(a.root)+count+count1));
		System.out.println("depth: "+a.depth(a.root,0));
		System.out.println("weight correctness- "+(a.checkWeights(a.root)==1<<29?"Incorrect":"Correct: "+a.checkWeights(a.root)));
	}
	static class Node
	{
		int key,weight;
		Node left,right;
		public Node(int a,int b)
		{
			key=a;
			weight=b;
		}
	}
	static class Tree
	{
		Node root;
		public Tree()
		{
		}
		boolean search(Node node,int key)
		{
			if(node==null) return false;
			if(key<node.key) return search(node.left,key);
			else if(key>node.key) return search(node.right,key);
			return key==node.key;
		}
		Node insert(Node node,Node insert)
		{
			if(node==null) return insert;
			if(insert.key>node.key) node.right=insert(node.right,insert);
			else if(insert.key<node.key) node.left=insert(node.left,insert);
			else
			{
				if(DEBUG==1) System.out.println("COLLISION");
				count1++;
				return node;
			}
			if(node.left!=null&&node.weight<node.left.weight) return rightRotate(node);
			else if(node.right!=null&&node.weight<node.right.weight) return leftRotate(node);
			return node;
		}
		Node delete(Node node,int key)
		{
			if(node==null) return node;
			if(key<node.key) node.left=delete(node.left,key);
			else if(key>node.key) node.right=delete(node.right,key);
			else if(node.left==null) node=node.right;
			else if(node.right==null) node=node.left;
			else if(node.left.weight<node.right.weight)
			{
				node=leftRotate(node);
				node.left=delete(node.left,key);
			}
			else
			{
				node=rightRotate(node);
				node.right=delete(node.right,key);
			}
			return node;
		}
		Node leftRotate(Node x)
		{
			if(x.right==null) return x;
			Node y=x.right;
			Node rightLeft=y.left;
			y.left=x;
			x.right=rightLeft;
			return y;
		}
		Node rightRotate(Node y)
		{
			if(y.left==null) return y;
			Node x=y.left;
			Node leftRight=x.right;
			x.right=y;
			y.left=leftRight;
			return x;
		}
		int checkWeights(Node a)
		{
			if(a==null) return 0;
			int one=checkWeights(a.left);
			int two=checkWeights(a.right);
			int max=Math.max(one,two);
			if(max>a.weight)
			{
				// System.out.println(one+" "+two+" "+max+" "+a.weight);
				System.out.println("ERROR");
				return 1<<29;
			}
			return a.weight;
		}
		int depth(Node a,int count)
		{
			if(a==null) return count;
			int max=0;
			max=Math.max(max,depth(a.left,count+1));
			max=Math.max(max,depth(a.right,count+1));
			return max;
		}
		void printInOrder(Node a)
		{
			if(a==null) return;
			printInOrder(a.left);
			System.out.println(a.key+" "+a.weight);
			printInOrder(a.right);
		}
		int countNodes(Node a)
		{
			if(a==null) return 0;
			return countNodes(a.left)+countNodes(a.right)+1;
		}
	}
}