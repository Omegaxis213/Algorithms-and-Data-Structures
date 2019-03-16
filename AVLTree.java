import java.util.*;
class AVLTree
{
	static final int DEBUG=0;//0==no printing, 1==printing
	static int check;
	static int count1;
	public static void main(String args[])
	{
		AVL a=new AVL();
		Set<Integer> set=new TreeSet<Integer>();
		for (int i = 0; i < 100000; i++) {
			int num=(int)(Math.random()*10000000);
			a.root=a.insert(a.root,num);
			set.add(num);
		}
		System.out.println("num nodes: "+a.countNodes(a.root));
		a.checkHeight(a.root);
		System.out.println("depth: "+a.depth(a.root,0));
		a.printInOrder(a.root);
		Integer[] arr=set.toArray(new Integer[0]);
		int count=0;
		int prevNum=a.countNodes(a.root);
		for (int i = 0; i < arr.length; i++) {
			if(Math.random()>.1)
			{
				count++;
				a.root=a.delete(a.root,arr[i]);
			}
		}
		System.out.println("num nodes: "+a.countNodes(a.root));
		System.out.println("num nodes deleted: "+count+", num of collisions: "+count1);
		System.out.println("Total nodes: "+(a.countNodes(a.root)+count+count1));
		check=0;
		a.checkHeight(a.root);
		System.out.println("depth: "+a.depth(a.root,0));
		a.printInOrder(a.root);
	}
	static class Node
	{
		Node left,right;
		int height,key;
		public Node(int a)
		{
			key=a;
			height=1;
		}
	}
	static class AVL
	{
		Node root;
		public AVL()
		{
		}
		Node insert(Node node,int key)
		{
			if(node==null) return new Node(key);
			if(key<node.key) node.left=insert(node.left,key);
			else if(key>node.key) node.right=insert(node.right,key);
			else
			{
				if(DEBUG==1) System.out.println("COLLISION");
				count1++;
				return node;
			}

			node.height=Math.max(height(node.left),height(node.right))+1;
			int balance=getBalance(node);

			if(balance>1&&key<node.left.key)//Left Left
				return rightRotate(node);
			if(balance>1&&key>node.left.key)//Left Right
			{
				node.left=leftRotate(node.left);
				return rightRotate(node);
			}
			if(balance<-1&&key>node.right.key)//Right Right
				return leftRotate(node);
			if(balance<-1&&key<node.right.key)//Right Left
			{
				node.right=rightRotate(node.right);
				return leftRotate(node);
			}
			return node;
		}
		Node delete(Node node,int key)
		{
			if(node==null) return node;
			if(key<node.key) node.left=delete(node.left,key);
			else if(key>node.key) node.right=delete(node.right,key);
			else
			{
				if(node.left==null||node.right==null)
				{
					if(node.left==node.right) return null;
					return node=(node.left==null?node.right:node.left);
				}
				else
				{
					Node temp=minNode(node.right);
					node.key=temp.key;
					node.right=delete(node.right,temp.key);
				}
			}
			if(node==null) return node;
			node.height=Math.max(height(node.left),height(node.right))+1;
			int balance=getBalance(node);

			if(balance>1&&getBalance(node.left)>=0)//Left Left
				return rightRotate(node);
			if(balance>1&&getBalance(node.left)<0)//Left Right
			{
				node.left=leftRotate(node.left);
				return rightRotate(node);
			}
			if(balance<-1&&getBalance(node.right)<=0)//Right Right
				return leftRotate(node);
			if(balance<-1&&getBalance(node.right)>0)//Right Left
			{
				node.right=rightRotate(node.right);
				return leftRotate(node);
			}
			return node;
		}
		Node minNode(Node a)
		{
			Node cur=a;
			while(cur.left!=null) cur=cur.left;
			return cur;
		}
		int height(Node a)
		{
			return a==null?0:a.height;
		}
		int getBalance(Node a)
		{
			return a==null?0:height(a.left)-height(a.right);
		}
		Node leftRotate(Node x)
		{
			Node y=x.right;
			Node rightLeft=y.left;
			y.left=x;
			x.right=rightLeft;
			x.height=Math.max(height(x.left),height(x.right))+1;
			y.height=Math.max(height(y.left),height(y.right))+1;
			return y;
		}
		Node rightRotate(Node y)
		{
			Node x=y.left;
			Node leftRight=x.right;
			x.right=y;
			y.left=leftRight;
			y.height=Math.max(height(y.left),height(y.right))+1;
			x.height=Math.max(height(x.left),height(x.right))+1;
			return x;
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
			if(a.key<check) System.out.println("ERROR. NOT IN CORRECT ORDER");
			check=a.key;
			if(DEBUG==1) System.out.println(a.key+" ");
			printInOrder(a.right);
		}
		void printPreOrder(Node a)
		{
			if(a==null) return;
			System.out.println("Left");
			printPreOrder(a.left);
			System.out.println("Up");
			System.out.println("Right");
			printPreOrder(a.right);
			System.out.println("Up");
		}
		boolean checkHeight(Node a)
		{
			if(a==null) return false;
			boolean flag=false;
			flag|=checkHeight(a.left);
			a.height=Math.max(height(a.left),height(a.right))+1;
			if(Math.abs(height(a.left)-height(a.right))>1)
			{
				System.out.println("ERROR: height of left: "+height(a.left)+" height of right: "+height(a.right));
				flag=true;
			}
			flag|=checkHeight(a.right);
			return flag;
		}
		int countNodes(Node a)
		{
			if(a==null) return 0;
			return countNodes(a.left)+countNodes(a.right)+1;
		}
	}
}