import java.util.*;
import java.io.*;
public class ahoCorasick {
	static int counter;
	public static void main(String[] args) throws Exception {
		// BufferedReader in = new BufferedReader(new FileReader("test.in"));
		// PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("test.out")));
		// String line;
		
		//program outputs all the times a string is found as a substring of another string
		trie a=new trie();
		// a.update("start",0);//these are the strings that are checked for the other string
		// a.update("sting",0);
		// a.update("tin",0);
		// a.update("in",0);
		// a.update("i",0);
		a.update("ddta",0);
		a.update("atb",0);
		a.update("ti",0);
		a.createSuffixLinks(null,a);

		// dfs("",a);
		a.createOutputLinks();
		// search(a);

		trie curNode=a;
		// String check="stingtintintinstartin";//string that is being checked
		String check="ddti";
		for (int i = 0; i < check.length(); i++) {
			while(curNode!=a&&!curNode.map.containsKey(check.charAt(i)))
				curNode=curNode.suffixLink;
			if(curNode.map.containsKey(check.charAt(i)))
			{
				curNode=curNode.list.get(curNode.map.get(check.charAt(i)));
				if(curNode.output!=null)
					System.out.println(curNode.output);
				trie temp=curNode.outputNode;
				while(temp!=null)
				{
					System.out.println(temp.output);
					temp=temp.outputNode;
				}
			}
		}
		// in.close(); out.close();
	}

	public static void search(trie cur)
	{
		System.out.println(cur.val+" "+(cur.suffixLink==null?null:cur.suffixLink.val)+" "+(cur.outputNode==null?null:cur.outputNode.val));
		for (char a : cur.map.keySet()) {
			search(cur.list.get(cur.map.get(a)));
		}
	}

	public static void dfs(String temp,trie cur)
	{
		cur.val=counter;
		counter++;
		System.out.println(temp+" "+cur.output+" "+cur.val);
		for (char a : cur.map.keySet()) {
			dfs(temp+a,cur.list.get(cur.map.get(a)));
		}
	}

	static StringTokenizer st;
	static int i() {return Integer.parseInt(st.nextToken());}
	static String s() {return st.nextToken();}
	static double d() {return Double.parseDouble(st.nextToken());}
	static long l() {return Long.parseLong(st.nextToken());}
}
class trie
{
	HashMap<Character,Integer> map;
	ArrayList<trie> list;
	trie suffixLink;
	trie outputNode;
	boolean vis;
	String output;
	int val;
	public trie()
	{
		map=new HashMap<Character,Integer>();
		list=new ArrayList<trie>();
	}
	public void update(String in,int pos)
	{
		if(in.length()==pos)
		{
			output=in;
			return;
		}
		if(map.containsKey(in.charAt(pos)))
			list.get(map.get(in.charAt(pos))).update(in,pos+1);
		else
		{
			map.put(in.charAt(pos),map.size());
			list.add(new trie());
			list.get(list.size()-1).update(in,pos+1);
		}
	}
	public void createSuffixLinks(trie curNode,trie rootNode)
	{
		for (char a : map.keySet()) {
			if(curNode==null)
			{
				list.get(map.get(a)).createSuffixLinks(rootNode,rootNode);
				list.get(map.get(a)).suffixLink=rootNode;
				continue;
			}
			if(curNode.map.containsKey(a))
			{
				list.get(map.get(a)).createSuffixLinks(curNode.list.get(curNode.map.get(a)),rootNode);
				list.get(map.get(a)).suffixLink=curNode.list.get(curNode.map.get(a));
			}
			else
			{
				trie nextNode=rootNode;
				if(nextNode.map.containsKey(a))
					nextNode=nextNode.list.get(nextNode.map.get(a));
				list.get(map.get(a)).createSuffixLinks(nextNode,rootNode);
				list.get(map.get(a)).suffixLink=nextNode;
			}
		}
	}
	public void createOutputLinks()
	{
		outputNode=linkNodes(suffixLink);
		for (char a : map.keySet()) {
			list.get(map.get(a)).createOutputLinks();
		}
	}
	public trie linkNodes(trie curNode)
	{
		if(curNode==null) return null;
		if(curNode.vis) return curNode.outputNode;
		if(curNode.output!=null) return curNode;
		curNode.vis=true;
		return curNode.outputNode=linkNodes(curNode.suffixLink);
	}
}