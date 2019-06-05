import java.io.BufferedReader;
import java.io.FileReader;

public class BTree {
	private int order;
	private BTreeNode root;

	public BTree(String tVal) {
		order = Integer.parseInt(tVal);
		root = new BTreeNode(order);
	}

	public BTreeNode getRoot() {
		return this.root;
	}

	public BTreeNode search(BTreeNode root, String key) {
		int i = 0;
		while (i < root.getCount() - 1 && key.compareTo(root.getKey()[i]) > 0)
			i++;
		if (i <= root.getCount() && key.equals(root.getKey()[i]))
			return root;
		if (root.getIsLeaf())
			return null;
		else
			return search(root.getChild(i), key);
	}

	public void insert(String key) {
		BTreeNode r = this.root;
		if (r.getCount() == 2 * order - 1) {
			BTreeNode s = new BTreeNode(order);
			this.root = s;
			s.setIsLeaf(false);
			s.setCount(0);
			s.getChild()[0] = r;
			split(s, 0, r);
			nonfullInsert(s, key);
		} else
			nonfullInsert(r, key);
	}

	public void split(BTreeNode x, int i, BTreeNode y) {
		BTreeNode z = new BTreeNode(order);
		z.setIsLeaf(y.getIsLeaf());
		z.setCount(order - 1);
		for (int j = 0; j < order - 1; j++)
			z.getKey()[j] = y.getKey()[j + order];
		if (!y.getIsLeaf()) {
			for (int k = 0; k < order; k++)
				z.getChild()[k] = y.getChild()[k + order];
		}
		y.setCount(order - 1);
		rearrangeChildren(x,i,y,z);
		x.setCount(x.getCount() + 1);
	}

	private void rearrangeChildren(BTreeNode x, int i, BTreeNode y, BTreeNode z) {
		for (int j = x.getCount(); j > i; j--)
			x.getChild()[j + 1] = x.getChild()[j];
		x.getChild()[i + 1] = z;
		for (int j = x.getCount(); j > i; j--)
			x.getKey()[j] = x.getKey()[j - 1];
		x.getKey()[i] = y.getKey()[order - 1];
		y.getKey()[order - 1] = null;
		for (int j = 0; j < order - 1; j++)
			y.getKey()[j + order] = null;
	}
	
	public void nonFullInsertLeaf(BTreeNode x, String key)
	{
		int i = x.getCount();
		while (i >= 1 && key.compareTo(x.getKey()[i - 1]) < 0) {
			x.getKey()[i] = x.getKey()[i - 1];
			i--;
		}
		x.getKey()[i] = key;
		x.setCount(x.getCount() + 1);
	}
	
	public void nonFullInsertInternal(BTreeNode x, String key)
	{
		int j = 0;
		while (j < x.getCount() && key.compareTo(x.getKey()[j]) >= 0)
			j++;
		if (x.getChild()[j].getCount() == order * 2 - 1) {
			split(x, j, x.getChild()[j]);
			if (key.compareTo(x.getKey()[j]) > 0)
				j++;
		}
		nonfullInsert(x.getChild()[j], key);
	}
	
	public void nonfullInsert(BTreeNode x, String key) {
		if (x.getIsLeaf()) 
			nonFullInsertLeaf(x,key);
		else 
			nonFullInsertInternal(x,key);
	}

	public void delete(String key) {
		if (this.root == null) {
			System.out.println("The tree is empty");
			return;
		}
		this.root.delete(key);
		while (this.root.getChild(0)!=null&&this.root.getCount() == 0 )
			this.root = this.root.getChild(0);
	}

	public void createFullTree(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			while ((next = reader.readLine()) != null) {
				this.insert(next.toLowerCase());
			}
		} catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

	public void deleteKeysFromTree(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			while ((next = reader.readLine()) != null) {
				this.delete(next.toLowerCase());
			}
		} catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

	public String toString() {
		this.root.UpdateTreeDepth(this.root, 0);
		String s = this.root.toString(0);
		if(s.length()>0)
			return s.substring(0, s.length() - 1);
		return s;

	}

	public String getSearchTime(String string) {
		double time1 = System.nanoTime() / 1000000.0;
		double time2 = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(string));
			String next;
			while ((next = reader.readLine()) != null) {
				this.search(this.root, next.toLowerCase());
			}
			time2 = System.nanoTime() / 1000000.0;
			String s = Double.toString(time2 - time1);
			if(s.length()>5)
				return s.substring(0, 5);
			return s;
		} catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

}
