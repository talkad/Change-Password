import java.io.BufferedReader;
import java.io.FileReader;

public class BTree  {
	static int order;
	private BTreeNode root;
	public BTree(String tVal)
	{
		this.order =Integer.parseInt(tVal);

		root = new BTreeNode(order);

	}
	public BTreeNode search(BTreeNode root, String key)
	{
		int i = 0;
		while(i < root.getCount() && key.compareTo(root.getKey()[i])>0)
			i++;
		if(i <= root.getCount() && key.equals(root.getKey()[i]))	
			return root;
		if(root.isLeaf())
  			return null ;
		else
			return search(root.getChild(i),key);
	}
	//this will be the method to insert in general, it will call insert non full if needed.                                  
	public void insert(String key)
	{
		BTreeNode r = this.root;
		if(r.getCount() == 2*order - 1)
		{
			BTreeNode s = new BTreeNode(order);
			this.root = s;
			s.setLeaf(false);
			s.setCount(0); 
			s.getChild()[0] = r;
			split(s,0,r);
			nonfullInsert(s, key);
		}
		else
			nonfullInsert(r,key);
	}
//  this will be the split method.  It will split node we  |
//  want to insert into if it is full.                     |
	public void split(BTreeNode x, int i, BTreeNode y)
	{
		BTreeNode z = new BTreeNode(order);
		z.setLeaf(y.isLeaf());
		z.setCount(order - 1);//this is updated size
		for(int j = 0; j < order - 1; j++)
			z.getKey()[j] = y.getKey()[j+order]; //copy end of y into front of z
		if(!y.isLeaf())//if not leaf we have to reassign child nodes.
		{
			for(int k = 0; k < order; k++)
				z.getChild()[k] = y.getChild()[k+order]; //reassing child of y			
		}
		y.setCount(order - 1); //new size of y
		for(int j = x.getCount() ; j> i ; j--)//if we push key into x we have to rearrange child nodes
			x.getChild()[j+1] = x.getChild()[j]; //shift children of x
		x.getChild()[i+1] = z; //reassign i+1 child of x
		for(int j = x.getCount(); j> i; j--)
			x.getKey()[j] = x.getKey()[j-1]; // shift keys
		x.getKey()[i] = y.getKey()[order-1];//finally push value up into root.
		y.getKey()[order-1 ] = null; //erase value where we pushed from
		for(int j = 0; j < order - 1; j++)
			y.getKey()[j + order] = null; //'delete' old values
		x.setCount(x.getCount() + 1);  //increase count of keys in x
	}

	// this will be insert method when node is not full.
	public void nonfullInsert(BTreeNode x, String key)
	{
		int i = x.getCount();
		if(x.isLeaf())
		{
			while(i >= 1 && key.compareTo(x.getKey()[i-1])<0)
			{
				x.getKey()[i] = x.getKey()[i-1];
				i--;
			}
			x.getKey()[i] = key;
			x.setCount(x.getCount() + 1);
		}
		else
		{
			int j = 0;
			//updateCount(x);
			while(j < x.getCount()  && key.compareTo(x.getKey()[j])>=0)		
				j++;
			if(x.getChild()[j].getCount() == order*2 - 1)
			{
				split(x,j,x.getChild()[j]);
				if(key.compareTo(x.getKey()[j])>0)
					j++;
			}
			nonfullInsert(x.getChild()[j],key);
		}
	}
	public void createFullTree(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			while ((next = reader.readLine()) != null) { 
				this.insert(next);
			}			
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}
	private void updateCount(BTreeNode node) {
		int count=0;
		if(node!=null) {
		for(int i=0;i<node.getCount();i++) {
			if(node.getKey()[i]!=null ) 
				count++;
		}
		node.setCount(count);
		for(int i=0;i<node.getCount();i++) {
			updateCount(node.getChild(i));
		}
		if(node.getChild()[count]!=null)
		updateCount(node.getChild(count));
		}
		else
			return;

	}
	public BTreeNode getRoot() {
		return this.root;
	}
	public String toString() {
		this.root.UpdateTreeDepth(this.root, 0);
		String s=this.root.toString(0);
		return s.substring(0, s.length()-1);
	}
	
}
