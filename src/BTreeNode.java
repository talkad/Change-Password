
public class BTreeNode {
	static int t;
	private int count;
	private String key[];
	private BTreeNode child[];
	private boolean isLeaf;
	private int depth;

	public BTreeNode(int n) {
		t = n;
		this.key = new String[2 * t - 1];
		this.child = new BTreeNode[2 * t];
		this.isLeaf = true;
		this.count = 0;
		this.depth = 0;
	}

	public String getValue(int index) {
		return getKey()[index];
	}

	public BTreeNode getChild(int index) {
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

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public BTreeNode[] getChild() {
		return child;
	}

	public void setChild(BTreeNode child[]) {
		this.child = child;
	}

	public String toString(int count) {
		String result = "";
		for (int i = 0; i < this.count; i++) {
			if (this.child[i] != null)
				result = result + this.child[i].toString(depth);
			result = result + this.getValue(i) + "_" + this.depth + ",";
		}
		if (this.child[this.count] != null)
			result = result + this.child[this.count].toString(depth);
		return result;
	}

	public void UpdateTreeDepth(BTreeNode root, int level) {
		if (root == null)
			return;
		root.depth = level;
		for (int i = 0; i < root.count; i++)
			UpdateTreeDepth(root.child[i], level + 1);
		UpdateTreeDepth(root.child[root.count], level + 1);
	}

	public void deleteAt(int indexDelete) {
		if (this.isLeaf)
			removeFromLeaf(indexDelete);
		else
			removeFromNonLeaf(indexDelete);
	}
	
	public void deleteFromChild(int indexDelete, String key) {
		if (this.isLeaf) 
			System.out.println("The key " + key + " is does not exist in the tree");
		else {
		boolean flag = (indexDelete == this.count);
		if (this.child[indexDelete].count < t)
			increaseElements(indexDelete);
		if (flag && indexDelete > this.count)
			this.child[indexDelete - 1].delete(key);
		else
			this.child[indexDelete].delete(key);
		}
	}
	
	public void delete(String key) {
		int indexDelete = findKey(key);
		if (indexDelete < this.count && this.key[indexDelete].equals(key)) 
			deleteAt(indexDelete);
		 else 
			deleteFromChild(indexDelete,key);
	}

	public int findKey(String key) {
		int indexDelete = 0;
		while (indexDelete < this.count && this.key[indexDelete].compareTo(key) < 0)
			indexDelete++;
		return indexDelete;
	}

	// case 1
	public void removeFromLeaf(int indexDelete) {
		for (int i = indexDelete + 1; i < this.count; i++)
			this.key[i - 1] = this.key[i];
		this.count--;
	}

	//case 2
	private void removeFromPred(int indexDelete) {
		String predString = predKey(indexDelete);
		this.key[indexDelete] = predString;
		this.child[indexDelete].delete(predString);
	}
	
	//case 3
	private void removeFromSucc(int indexDelete) {
		String successor = succKey(indexDelete);
		this.key[indexDelete] = successor;
		this.child[indexDelete + 1].delete(successor);
	}
	
	//case 4
	private void mergeDelete(int indexDelete) {
		String key = this.key[indexDelete];
		merging(indexDelete);
		this.child[indexDelete].delete(key);
	}
	
	public void removeFromNonLeaf(int indexDelete) {
		if (this.child[indexDelete].count >= t) 
			removeFromPred(indexDelete);
		else if (this.child[indexDelete + 1].count >= t) 
			removeFromSucc(indexDelete);
		else 
			mergeDelete(indexDelete);
	}

	public String predKey(int indexDelete) {
		BTreeNode current = this.child[indexDelete];
		while (!current.isLeaf)
			current = current.child[current.count];
		return current.key[current.count - 1];
	}

	public String succKey(int indexDelete) {
		BTreeNode current = this.child[indexDelete + 1];
		while (!current.isLeaf)
			current = current.child[0];
		return current.key[0];
	}

	public boolean shifting(int indexDelete) {
		boolean isShift=true;
		if (indexDelete != 0 && this.child[indexDelete - 1].count >= t)
			moveElementFromPrev(indexDelete);
		else if (indexDelete != count && this.child[indexDelete + 1].count >= t)
			moveElementFromNext(indexDelete);
		else
			isShift = false;
		return isShift;
	}
		
	//Before entering a node v, if v has t − 1 elements,
	//perform shifting or merging in order to increase the
	//number of elements.
	public void increaseElements(int indexDelete) {
		if(!shifting(indexDelete)) {
			if (indexDelete != count)
				merging(indexDelete);
			else
				merging(indexDelete - 1);
		}
	}
	
	public void moveElementFromPrev(int indexDelete) {
		BTreeNode child1 = this.child[indexDelete];
		BTreeNode child2 = this.child[indexDelete - 1];
		for (int i = child1.count - 1; i >= 0; i--)
			child1.key[i + 1] = child1.key[i];
		if (!child1.isLeaf) 
			pushRight(child1);
		child1.key[0] = this.key[indexDelete - 1];
		if (!child1.isLeaf)
			child1.child[0] = child2.child[child2.count];
		this.key[indexDelete - 1] = child2.key[child2.count - 1];
		child1.count++;
		child2.count--;
	}
	
	private void pushRight(BTreeNode child) {
		for (int i = child.count; i >= 0; i--)
			child.child[i + 1] = child.child[i];
	}

	public void moveElementFromNext(int indexDelete) {
		BTreeNode child1 = this.child[indexDelete];
		BTreeNode child2 = this.child[indexDelete + 1];
		child1.key[(child1.count)] = this.key[indexDelete];
		if (!child1.isLeaf)
			child1.child[(child1.count) + 1] = child2.child[0];
		this.key[indexDelete] = child2.key[0];
		for (int i = 1; i < child2.count; i++)
			child2.key[i - 1] = child2.key[i];
		if (!child2.isLeaf)
			pushLeft(child2);
		child1.count++;
		child2.count--;
	}
	
	private void pushLeft(BTreeNode child) {
		for (int i = 1; i <= child.count; i++)
			child.child[i - 1] = child.child[i];
	}

	public void merging(int indexDelete) {
		BTreeNode child1 = this.child[indexDelete];
		BTreeNode child2 = this.child[indexDelete + 1];
		rearrangeNode(child1, child2, indexDelete);
		child1.count = child1.count + child2.count + 1;
		count--;
	}
	
	private void rearrangeNode(BTreeNode child1, BTreeNode child2, int indexDelete) {
		child1.key[t - 1] = this.key[indexDelete];
		for (int i = 0; i < child2.count; i++)
			child1.key[i + t] = child2.key[i];
		if (!child1.isLeaf) {
			for (int i = 0; i <= child2.count; i++)
				child1.child[i + t] = child2.child[i];
		}
		for (int i = indexDelete + 1; i < count; i++)
			this.key[i - 1] = key[i];
		for (int i = indexDelete + 2; i <= count; i++)
			this.child[i - 1] = this.child[i];
	}


}