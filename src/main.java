
public class main {
	public static void main(String[] args) {
			BTree btree = new BTree("3");
			btree.createFullTree("bad_passwords.txt");
			btree.insert(btree,"nitzan");
			print(btree.getRoot());
	}
	public static void print(BTreeNode n)
	{
		for(int i = 0; i < n.getCount(); i++)
		{
			System.out.print(n.getValue(i)+" " );//this part prints root node
		}

		if(!n.isLeaf())//this is called when root is not leaf;
		{

			for(int j = 0; j <= n.getCount()  ; j++)//in this loop we recurse
			{				  //to print out tree in
				if(n.getChild(j) != null) //preorder fashion.
				{			  //going from left most
				System.out.println();	  //child to right most
				print(n.getChild(j));     //child.
				}
			}
		}
	}
}
