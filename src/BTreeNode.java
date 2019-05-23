
public class BTreeNode {
	static int t;  //variable to determine order of tree
	private int count; // number of keys in node
	private String key[];  // array of key values
	private BTreeNode child[]; //array of references
	private boolean leaf; //is node a leaf or not
	private int height;
	
	public BTreeNode(int t)
	{
		this.t = t;  //assign size
		this.key=new String[2*t - 1];  // array of proper size
		this.child=new BTreeNode[2*t]; // array of refs proper size
		this.leaf=true; // every node is leaf at first;
		this.count=0;//until we add keys later.
		this.height=0;
	}
	// this is method to return key value at index position|
	public String getValue(int index)
	{
		return getKey()[index];
	}
	// this is method to get ith child of node            |
	public BTreeNode getChild(int index)
	{
		return child[index];
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String key[]) {
		this.key = key;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public BTreeNode[] getChild() {
		return child;
	}

	public void setChild(BTreeNode child[]) {
		this.child = child;
	}
	public String toString(int count) {
	       String result = "";
	       for(int i=0;i<this.count;i++) {
    		   if(this.child[i]!=null) 
	    		   result = result + this.child[i].toString(height);
    		   result = result + this.getValue(i)+"_"+this.height+", ";
	       }
    	   if(this.child[this.count]!=null)
    		   result = result + this.child[this.count].toString(height);
	       return result;		
	}
	public void UpdateTreeDepth(BTreeNode root, int level) {
		if (root == null) 
	        return; 
	    root.height=level;
	    for(int i=0;i<root.count;i++) 
	    	UpdateTreeDepth(root.child[i], level+1); 
	    UpdateTreeDepth(root.child[root.count], level+1);
	}
	
}