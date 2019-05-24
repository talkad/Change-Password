
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
	public int findKey(String key) 
	{ 
	    int idx=0; 
	    while (idx<this.count && this.key[idx].compareTo(key)<0) 
	        idx++; 
	    return idx; 
	} 
	public void UpdateTreeDepth(BTreeNode root, int level) {
		if (root == null) 
	        return; 
	    root.height=level;
	    for(int i=0;i<root.count;i++) 
	    	UpdateTreeDepth(root.child[i], level+1); 
	    UpdateTreeDepth(root.child[root.count], level+1);
	}// A function to remove the key k from the sub-tree rooted with this node 
	public void delete(String key) 
	{ 
	    int idx = findKey(key); 
	  
	    // The key to be removed is present in this node 
	    if (idx < this.count && this.key[idx].equals(key)) 
	    { 
	  
	        // If the node is a leaf node - removeFromLeaf is called 
	        // Otherwise, removeFromNonLeaf function is called 
	        if (this.leaf) 
	            removeFromLeaf(idx); 
	        else
	            removeFromNonLeaf(idx); 
	    } 
	    else
	    { 
	        // If this node is a leaf node, then the key is not present in tree 
	        if (this.leaf) 
	        { 
	            System.out.println("The key "+ key +" is does not exist in the tree"); 
	            return; 
	        } 
	        // The key to be removed is present in the sub-tree rooted with this node 
	        // The flag indicates whether the key is present in the sub-tree rooted 
	        // with the last child of this node 
	        boolean flag = ( idx==this.count); 
	        // If the child where the key is supposed to exist has less that t keys, 
	        // we fill that child 
	        if (this.child[idx].count < this.t) 
	            fill(idx); 
	  
	        // If the last child has been merged, it must have merged with the previous 
	        // child and so we recurse on the (idx-1)th child. Else, we recurse on the 
	        // (idx)th child which now has atleast t keys 
	        if (flag && idx > this.count) 
	            this.child[idx-1].delete(key);
	        else
	            this.child[idx].delete(key); 
	    } 
	    return; 
	} 
	  
	// A function to remove the idx-th key from this node - which is a leaf node 
	public void removeFromLeaf (int idx) 
	{ 
	  
	    // Move all the keys after the idx-th pos one place backward 
	    for (int i=idx+1; i<this.count; ++i) 
	        this.key[i-1] = this.key[i]; 
	  
	    // Reduce the count of keys 
	    this.count--; 
	  
	    return; 
	} 
	  
	// A function to remove the idx-th key from this node - which is a non-leaf node 
	public void removeFromNonLeaf(int idx) 
	{ 
	    String key = this.key[idx]; 
	  
	    // If the child that precedes k (C[idx]) has atleast t keys, 
	    // find the predecessor 'pred' of k in the subtree rooted at 
	    // C[idx]. Replace k by pred. Recursively delete pred 
	    // in C[idx] 
	    if (this.child[idx].count >= t) 
	    { 
	        String pred = getPred(idx); 
	        this.key[idx] = pred; 
	        this.child[idx].delete(pred); 
	    } 
	    // If the child C[idx] has less that t keys, examine C[idx+1]. 
	    // If C[idx+1] has atleast t keys, find the successor 'succ' of k in 
	    // the subtree rooted at C[idx+1] 
	    // Replace k by succ 
	    // Recursively delete succ in C[idx+1] 
	    else if  (this.child[idx+1].count >= t) 
	    { 
	        String succ = getSucc(idx); 
	        this.key[idx] = succ; 
	        this.child[idx+1].delete(succ); 
	    } 
	    // If both C[idx] and C[idx+1] has less that t keys,merge k and all of C[idx+1] 
	    // into C[idx] 
	    // Now C[idx] contains 2t-1 keys 
	    // Free C[idx+1] and recursively delete k from C[idx] 
	    else
	    { 
	        merge(idx); 
	       this.child[idx].delete(key); 
	    } 
	    return; 
	} 
	  
	// A function to get predecessor of keys[idx] 
	public String getPred(int idx) 
	{ 
	    // Keep moving to the right most node until we reach a leaf 
	    BTreeNode cur=this.child[idx]; 
	    while (!cur.leaf) 
	        cur = cur.child[cur.count]; 
	    // Return the last key of the leaf 
	    return cur.key[cur.count-1]; 
	} 
	  
	public String getSucc(int idx) 
	{ 
	    // Keep moving the left most node starting from C[idx+1] until we reach a leaf 
	    BTreeNode cur =this.child[idx+1]; 
	    while (!cur.leaf) 
	        cur = cur.child[0]; 
	  
	    // Return the first key of the leaf 
	    return cur.key[0]; 
	} 
	  
	// A function to fill child C[idx] which has less than t-1 keys 
	public void fill(int idx) 
	{ 
	  
	    // If the previous child(C[idx-1]) has more than t-1 keys, borrow a key 
	    // from that child 
	    if (idx!=0 && this.child[idx-1].count>=t) 
	        borrowFromPrev(idx); 
	  
	    // If the next child(C[idx+1]) has more than t-1 keys, borrow a key 
	    // from that child 
	    else if (idx!=count && this.child[idx+1].count>=t) 
	        borrowFromNext(idx); 
	  
	    // Merge C[idx] with its sibling 
	    // If C[idx] is the last child, merge it with with its previous sibling 
	    // Otherwise merge it with its next sibling 
	    else
	    { 
	        if (idx != count) 
	            merge(idx); 
	        else
	            merge(idx-1); 
	    } 
	    return; 
	} 
	  
	// A function to borrow a key from C[idx-1] and insert it 
	// into C[idx] 
	public void borrowFromPrev(int idx) 
	{ 
	  
	    BTreeNode child=this.child[idx]; 
	    BTreeNode sibling=this.child[idx-1]; 
	  
	    // The last key from C[idx-1] goes up to the parent and key[idx-1] 
	    // from parent is inserted as the first key in C[idx]. Thus, the  loses 
	    // sibling one key and child gains one key 
	  
	    // Moving all key in C[idx] one step ahead 
	    for (int i=child.count-1; i>=0; --i) 
	        child.key[i+1] = child.key[i]; 
	  
	    // If C[idx] is not a leaf, move all its child pointers one step ahead 
	    if (!child.leaf) 
	    { 
	        for(int i=child.count; i>=0; --i) 
	            child.child[i+1] = child.child[i]; 
	    } 
	  
	    // Setting child's first key equal to keys[idx-1] from the current node 
	    child.key[0] = this.key[idx-1]; 
	  
	    // Moving sibling's last child as C[idx]'s first child 
	    if(!child.leaf) 
	        child.child[0] = sibling.child[sibling.count]; 
	  
	    // Moving the key from the sibling to the parent 
	    // This reduces the number of keys in the sibling 
	    this.key[idx-1] = sibling.key[sibling.count-1]; 
	  
	    child.count += 1; 
	    sibling.count -= 1; 
	  
	    return; 
	} 
	  
	// A function to borrow a key from the C[idx+1] and place 
	// it in C[idx] 
	public void borrowFromNext(int idx) 
	{ 
	  
	    BTreeNode child=this.child[idx]; 
	    BTreeNode sibling=this.child[idx+1]; 
	  
	    // keys[idx] is inserted as the last key in C[idx] 
	    child.key[(child.count)] = this.key[idx]; 
	  
	    // Sibling's first child is inserted as the last child 
	    // into C[idx] 
	    if (!(child.leaf)) 
	        child.child[(child.count)+1] = sibling.child[0]; 
	  
	    //The first key from sibling is inserted into keys[idx] 
	    this.key[idx] = sibling.key[0]; 
	  
	    // Moving all keys in sibling one step behind 
	    for (int i=1; i<sibling.count; ++i) 
	        sibling.key[i-1] = sibling.key[i]; 
	  
	    // Moving the child pointers one step behind 
	    if (!sibling.leaf) 
	    { 
	        for(int i=1; i<=sibling.count; ++i) 
	            sibling.child[i-1] = sibling.child[i]; 
	    } 
	  
	    // Increasing and decreasing the key count of C[idx] and C[idx+1] 
	    // respectively 
	    child.count += 1; 
	    sibling.count -= 1; 
	  
	    return; 
	} 
	  
	// A function to merge C[idx] with C[idx+1] 
	// C[idx+1] is freed after merging 
	public void merge(int idx) 
	{ 
	    BTreeNode child = this.child[idx]; 
	    BTreeNode sibling = this.child[idx+1]; 
	  
	    // Pulling a key from the current node and inserting it into (t-1)th 
	    // position of C[idx] 
	    child.key[t-1] = this.key[idx]; 
	  
	    // Copying the keys from C[idx+1] to C[idx] at the end 
	    for (int i=0; i<sibling.count; ++i) 
	        child.key[i+t] = sibling.key[i]; 
	  
	    // Copying the child pointers from C[idx+1] to C[idx] 
	    if (!child.leaf) 
	    { 
	        for(int i=0; i<=sibling.count; ++i) 
	            child.child[i+t] = sibling.child[i]; 
	    } 
	  
	    // Moving all keys after idx in the current node one step before - 
	    // to fill the gap created by moving keys[idx] to C[idx] 
	    for (int i=idx+1; i<count; ++i) 
	        this.key[i-1] = key[i]; 
	  
	    // Moving the child pointers after (idx+1) in the current node one 
	    // step before 
	    for (int i=idx+2; i<=count; ++i) 
	        this.child[i-1] = this.child[i]; 
	  
	    // Updating the key count of child and the current node 
	    child.count += sibling.count+1; 
	    count--;
	    // Freeing the memory occupied by sibling 
	    
	    return; 
	} 
	  
	
}