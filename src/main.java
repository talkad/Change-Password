
public class main {
	public static void main(String[] args) {
		BTree btree = new BTree("2");
		//btree.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
		btree.insert("A");
		btree.insert("B");
		btree.insert("C");
		btree.insert("D");
		btree.insert("G");
		btree.insert("H");
		btree.insert("K");
		btree.insert("M");
		btree.insert("R");
		btree.insert("W");
		btree.insert("Z");
		System.out.println(btree.toString());
	}
}
