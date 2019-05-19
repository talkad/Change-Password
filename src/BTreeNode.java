
public class BTreeNode {
	static int t;  //variable to determine order of tree
	private int count; // number of keys in node
	private String key[];  // array of key values
	private BTreeNode child[]; //array of references
	private boolean leaf; //is node a leaf or not

	// this will be default constructor for new node 
	public BTreeNode()	{}
	
	// initial value constructor for new node             
	// will be called from BTree.java                     
	public BTreeNode(int t)
	{
		this.t = t;  //assign size
		setKey(new String[2*t - 1]);  // array of proper size
		setChild(new BTreeNode[2*t]); // array of refs proper size
		setLeaf(true); // everynode is leaf at first;
		setCount(0); //until we add keys later.
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

	public String toString() {
	       String result = "";
	       for(int i=0;i<this.count;i++) {
    		   if(this.child[i]!=null)
	    		   result = result + this.child[i].toString();
    		   result = result + this.getValue(i)+",";
	    	   if(this.child[i+1]!=null)
	    		   result = result + this.child[i+1].toString();
	       }
	       
	       return result;		
	}
}