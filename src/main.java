
public class main {
	public static void main(String[] args) {
		BTree btree = new BTree("2");
		btree.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
		System.out.println(btree.toString());
		btree.deleteKeysFromTree(System.getProperty("user.dir")+"/delete_keys.txt");
		System.out.println(btree.toString());
		btree.insert("zzzzzzzz");
		System.out.println(btree.toString());

	}
}
