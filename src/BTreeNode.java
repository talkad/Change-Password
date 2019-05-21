
public class BTreeNode {
	static int t;  //variable to determine order of tree
	private int count; // number of keys in node
	private String key[];  // array of key values
	private BTreeNode child[]; //array of references
	private boolean leaf; //is node a leaf or not
	private int height;

	// this will be default constructor for new node 
	public BTreeNode()	{}
	
	// initial value constructor for new node             
	// will be called from BTree.java                     
	public BTreeNode(int t)
	{
		this.t = t;  //assign size
		this.key=new String[2*t - 1];  // array of proper size
		this.child=new BTreeNode[2*t]; // array of refs proper size
		this.leaf=true; // everynode is leaf at first;
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
	   static int height(BTreeNode node)  
	    { 
	        if (node == null) 
	            return 0; 
	        else 
	        { 
	        	int lDepth=0;
	        	int rDepth=0;
	        	for(int i=0;i<node.count;i++) {
	        		 lDepth = height(node.child[i]); 
	        		 rDepth = height(node.child[i+1]); 
	        	}
	            /* use the larger one */
	            if (lDepth > rDepth) 
	                return (lDepth + 1); 
	             else 
	                return (rDepth + 1); 
	        	
	        } 
	    }
	static void printLevelOrder(BTreeNode root) 
	{ 
	    int h = height(root); 
	    int i; 
	    for (i=0; i<h; i++) 
	    { 
	        printGivenLevel(root, i,h); 
	    } 
	} 
	static void printGivenLevel(BTreeNode root, int level,int h) 
	{ 
	    if (root == null) 
	        return; 
	    root.height=level-h+1;
	    for(int i=0;i<root.count;i++) 
	   		printGivenLevel(root.child[i], level+1,h); 
   		printGivenLevel(root.child[root.count], level+1,h); 
	    
	} 
	
}