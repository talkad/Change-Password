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
		//we always want to start searching the 0th index of node
		while(i < root.getCount() && key.compareTo(root.getKey()[i])>0)//keep incrementing in node while key > current value.
			i++;
		if(i <= root.getCount() && key.equals(root.getKey()[i]))//obviously if key is in node we went to return node.	
			return root;
		if(root.isLeaf())//since we've already checked root
    			    //if it is leaf we don't have anything else to check
  			return null ;
		else//else if it is not leave recurse down through ith child
			return search(root.getChild(i),key);
	}
	//this will be the method to insert in general, it will call insert non full if needed.                                  
	public void insert(BTree t, String key)
	{
		BTreeNode r = t.root;//this method finds the node to be inserted as it goes through this starting at root node.
		if(r.getCount() == 2*order - 1)//if is full
		{
			BTreeNode s = new BTreeNode(order);//new node
			t.root = s;
			s.setLeaf(false);
			s.setCount(0); 
			s.getChild()[0] = r;
			split(s,0,r);//split root
			nonfullInsert(s, key); //call insert method
		}
		else
			nonfullInsert(r,key);//if its not full just insert it
	}
//  this will be the split method.  It will split node we  |
//  want to insert into if it is full.                     |

	public void split(BTreeNode x, int i, BTreeNode y)
	{
		BTreeNode z = new BTreeNode(order);//gotta have extra node if we are to split.
		z.setLeaf(y.isLeaf());//set boolean to same as y
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
			x.getKey()[j + 1] = x.getKey()[j]; // shift keys
		x.getKey()[i] = y.getKey()[order-1];//finally push value up into root.
		y.getKey()[order-1 ] = ""; //erase value where we pushed from
		for(int j = 0; j < order - 1; j++)
			y.getKey()[j + order] = ""; //'delete' old values
		x.setCount(x.getCount() + 1);  //increase count of keys in x
	}

	// this will be insert method when node is not full.
	public void nonfullInsert(BTreeNode x, String key)
	{
		int i = x.getCount(); //i is number of keys in node x
		if(x.isLeaf())
		{
			while(i >= 1 && key.compareTo(x.getKey()[i-1])<0)//here find spot to put key.
			{
				x.getKey()[i] = x.getKey()[i-1];//shift values to make room
				i--;//decrement
			}
			x.getKey()[i] = key;//finally assign value to node
			x.setCount(x.getCount() + 1); //increment count of keys in this node now.
		}
		else
		{
			int j = 0;
			while(j < x.getCount()  && key.compareTo(x.getKey()[j])>0)//find spot to recurse on correct child  		
				j++;
			if(x.getChild()[j].getCount() == order*2 - 1)
			{
				split(x,j,x.getChild()[j]);//call split on node x's ith child
				if(key.compareTo(x.getKey()[j])>0)
					j++;
			}
			nonfullInsert(x.getChild()[j],key);//recurse
		}
	}
	public void createFullTree(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			while ((next = reader.readLine()) != null) { 
				this.insert(this, next);
			}			
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}
	public BTreeNode getRoot() {
		return this.root;
	}
	
}
