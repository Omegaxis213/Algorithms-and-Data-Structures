import java.util.*;
class RedBlackTree
{
	static final int DEBUG=0;//0==no printing, 1==printing, 2==printing+number of collisions
	static int check;
	static int count1;
	public static void main(String args[])
	{
		RBT a=new RBT();
		boolean flag=true;
		Set<Integer> set=new TreeSet<Integer>();
		for (int i = 0; i < 100000; i++) {
			int num=(int)(Math.random()*10000000);
			a.insert(num);
			set.add(num);
		}
		System.out.println("num nodes: "+a.countNodes(a.root));
		System.out.println("depth: "+a.depth(a.root,0));
		System.out.println("correctness- "+(a.checkCorrectness(a.root)==-1000?"Incorrect":"Correct: "+a.checkCorrectness(a.root)));
		a.printInOrder(a.root);
		Integer[] arr=set.toArray(new Integer[0]);
		for (int i = 0; i < arr.length; i++) {
			if(!a.search(a.root,arr[i])) System.out.println("ERROR");
		}
		int count=0;
		int prevNum=a.countNodes(a.root);
		for (int i = 0; i < arr.length; i++) {
			if(Math.random()>.1)
			{
				count++;
				a.delete(arr[i]);
			}
		}
		check=0;
		System.out.println();
		System.out.println("num nodes: "+a.countNodes(a.root));
		System.out.println("num nodes deleted: "+count+", num of collisions: "+count1);
		System.out.println("Total nodes: "+(a.countNodes(a.root)+count+count1));
		System.out.println("depth: "+a.depth(a.root,0));
		System.out.println("correctness- "+(a.checkCorrectness(a.root)==-1000?"Incorrect":"Correct: "+a.checkCorrectness(a.root)));
		a.printInOrder(a.root);
	}
	static class Node
	{
		Node left,right,parent;
		int key;
		boolean isBlack;
		boolean isDoubleBlack;
		public Node(int a)
		{
			key=a;
		}
		public Node(int a,boolean b)
		{
			key=a;
			isBlack=b;
		}
	}
	static class RBT
	{
		Node root;
		public RBT()
		{
		}
		boolean search(Node node,int key)
		{
			if(node==null) return false;
			if(key<node.key) return search(node.left,key);
			if(key>node.key) return search(node.right,key);
			return key==node.key;
		}
		Node BSTInsert(Node node,Node insert)
		{
			if(root==null)
			{
				setBlack(insert);
				return insert;
			}
			if(node==null) return insert;
			if(insert.key<node.key)
			{
				node.left=BSTInsert(node.left,insert);
				node.left.parent=node;
			}
			else if(insert.key>node.key)
			{
				node.right=BSTInsert(node.right,insert);
				node.right.parent=node;
			}
			else
			{
				count1++;
				if(DEBUG>=2) System.out.println("COLLISION");
				return node;
			}
			return node;
		}
		void fix(Node insert)
		{
			Node parent=null;
			Node grandParent=null;
			while(insert!=root&&!nodeIsBlack(insert)&&!nodeIsBlack(insert.parent))
			{
				parent=insert.parent;
				grandParent=parent.parent;
				if(grandParent==null)
				{
					printInOrderColor(root);
					System.out.println("ERROR: "+insert.parent.isBlack+" "+root+" "+insert.parent);
					System.out.println();
				}
				if(parent==grandParent.left)//Uncle is on the right
				{
					Node uncle=grandParent.right;
					if(!nodeIsBlack(uncle))//Uncle is red
					{
						setBlack(parent);
						setBlack(uncle);
						setRed(grandParent);
						insert=grandParent;
					}
					else
					{
						if(parent.left==insert)//Left Left
							swapColors(parent,grandParent);
						if(parent.right==insert)//Left Right
						{
							leftRotate(parent);
							swapColors(insert,grandParent);
						}
						rightRotate(grandParent);
						insert=parent;
					}
				}
				else//Uncle is on the left
				{
					Node uncle=grandParent.left;
					if(!nodeIsBlack(uncle))//Uncle is red
					{
						setBlack(parent);
						setBlack(uncle);
						setRed(grandParent);
						insert=grandParent;
					}
					else
					{
						if(parent.right==insert)//Right Right
							swapColors(parent,grandParent);
						if(parent.left==insert)//Right Left
						{
							rightRotate(parent);
							swapColors(insert,grandParent);
						}
						leftRotate(grandParent);
						insert=parent;
					}
				}
			}
			setBlack(root);
		}
		void delete(int key)
		{
			Node cur=root;
			while(cur.key!=key)//find the node
			{
				if(key<cur.key) cur=cur.left;
				if(key>cur.key) cur=cur.right;
			}

			Node temp;
			if(cur.left==cur.right&&cur.right==null)//find the replacement node
				temp=cur;
			else if(cur.right==null)
				temp=maxNode(cur.left);
			else
				temp=minNode(cur.right);

			boolean flag=cur.right!=null;
			cur.key=temp.key;
			cur=temp;
			temp.isDoubleBlack=temp.isBlack;
			while(cur.isDoubleBlack&&cur!=root)//repeat until the current node isn't double black anymore
			{
				Node parent=cur.parent;
				Node sibling=parent.left==cur?parent.right:parent.left;
				cur.isDoubleBlack=false;
				if(!nodeIsBlack(sibling))//if the sibling is red
				{
					setBlack(sibling);
					setRed(parent);
					if(parent.left==sibling)//Left Case
						rightRotate(parent);
					else//Right Case
						leftRotate(parent);
				}
				if(!blackSibling(cur)) break;//fix the double black and break if there isn't a double black anymore
				cur=cur.parent;
			}

			//delete the node
			if(temp.parent.left==temp) temp.parent.left=flag?temp.right:temp.left;
			else temp.parent.right=flag?temp.right:temp.left;

			if(flag&&temp.right!=null) temp.right.parent=temp.parent;
			if(!flag&&temp.left!=null) temp.left.parent=temp.parent;

			//root can never be double black
			root.isDoubleBlack=false;
		}
		boolean blackSibling(Node a)//true means continue, false means break
		{
			Node parent=a.parent;
			Node sibling=parent.left==a?parent.right:parent.left;
			if(nodeIsBlack(sibling.left)&&nodeIsBlack(sibling.right))//sibling's children are both black
			{
				if(sibling!=null) setRed(sibling);
				if(nodeIsBlack(parent)) parent.isDoubleBlack=true;
				else setBlack(parent);
				return true;
			}
			else//at least one of the sibling's child is red
			{
				if(sibling==parent.left)
				{
					if(!nodeIsBlack(sibling.left))//Left Left Case
					{
						setBlack(sibling.left);
						swapColors(sibling,parent);
					}
					else//Left Right Case
					{
						if(nodeIsBlack(parent)) setBlack(sibling.right);
						else setBlack(parent);
						leftRotate(sibling);
					}
					rightRotate(parent);
				}
				else
				{
					if(!nodeIsBlack(sibling.right))//Right Right Case
					{
						setBlack(sibling.right);
						swapColors(sibling,parent);
					}
					else//Right Left Case
					{
						if(nodeIsBlack(parent)) setBlack(sibling.left);
						else setBlack(parent);
						rightRotate(sibling);
					}
					leftRotate(parent);
				}
			}
			return false;
		}
		Node minNode(Node a)
		{
			Node cur=a;
			while(cur.left!=null) cur=cur.left;
			return cur;
		}
		Node maxNode(Node a)
		{
			Node cur=a;
			while(cur.right!=null) cur=cur.right;
			return cur;
		}
		void insert(int key)
		{
			Node insert=new Node(key);
			root=BSTInsert(root,insert);
			if(insert.parent==null&&insert!=root) return;//case for checking collision
			fix(insert);
		}
		void leftRotate(Node x)
		{
			Node y=x.right;
			Node rightLeft=y.left;
			y.left=x;
			x.right=rightLeft;
			y.parent=x.parent;
			x.parent=y;
			if(rightLeft!=null) rightLeft.parent=x;
			if(y.parent==null) root=y;
			else if(y.parent.right==x) y.parent.right=y;
			else y.parent.left=y;
		}
		void rightRotate(Node y)
		{
			Node x=y.left;
			Node leftRight=x.right;
			x.right=y;
			y.left=leftRight;
			x.parent=y.parent;
			y.parent=x;
			if(leftRight!=null) leftRight.parent=y;
			if(x.parent==null) root=x;
			else if(x.parent.right==y) x.parent.right=x;
			else x.parent.left=x;
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
			if(DEBUG>=1) System.out.print(a.key+" ");
			printInOrder(a.right);
		}
		int checkCorrectness(Node a)
		{
			if(a==null) return 0;
			int left=checkCorrectness(a.left)+(nodeIsBlack(a)?1:0);
			int right=checkCorrectness(a.right)+(nodeIsBlack(a)?1:0);
			if(left<0||right<0||left!=right||(!nodeIsBlack(a)&&!nodeIsBlack(a.left))||(!nodeIsBlack(a)&&!nodeIsBlack(a.right))) return -1000;
			return left;
		}
		void printInOrderColor(Node a)
		{
			if(a==null) return;
			System.out.println("Left");
			printInOrderColor(a.left);
			System.out.println("Up");
			System.out.println(a.isBlack+" "+a.key);
			System.out.println("Right");
			printInOrderColor(a.right);
			System.out.println("Up");
		}
		boolean nodeIsBlack(Node a)
		{
			return a==null?true:a.isBlack;
		}
		int countNodes(Node a)
		{
			if(a==null) return 0;
			return countNodes(a.left)+countNodes(a.right)+1;
		}
		void setBlack(Node a)
		{
			a.isBlack=true;
		}
		void setRed(Node a)
		{
			a.isBlack=false;
		}
		void swapColors(Node a,Node b)
		{
			boolean temp=a.isBlack;
			a.isBlack=b.isBlack;
			b.isBlack=temp;
		}
	}
}