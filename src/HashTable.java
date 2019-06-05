import java.io.BufferedReader;
import java.io.FileReader;

public class HashTable {

	private HashList[] hashTable;

	public HashTable(String m2) {
		hashTable = new HashList[validInput(m2)];
		// initialize values in hashTable
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = null;
		}

	}

	private int validInput(String m2) {
		int num = Integer.parseInt(m2);
		if (num > 0)
			return num;
		else if (num <= 0)
			throw new RuntimeException("zero or negative number cannot be a size of array");
		else
			throw new RuntimeException("The input was invalid");
	}

	public int hashFunction(int key) {
		return key % hashTable.length;
	}

	// convert ASCII strings into natural numbers with Horner's Rule
	private int stringToNumber(String pwd) {
		long key = pwd.charAt(0);
		for (int i = 1; i < pwd.length(); i++) {
			key = (pwd.charAt(i) + 256 * key) % 15486907;
		}
		return (int) key;
	}

	public void updateTable(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			int key;
			while ((next = reader.readLine()) != null) {
				key = stringToNumber(next);
				if (hashTable[hashFunction(key)] == null)
					hashTable[hashFunction(key)] = new HashList();
				hashTable[hashFunction(key)].addFirst(key);
			}
		} catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

	// check if key exists in HashList
	private boolean isExistsList(HashList list, int key) {
		if (list != null) {
			HashListElement link = list.getFirst();
			while (link != null) {
				if (link.getKey() == key)
					return true;
				link = link.getNext();
			}
		}
		return false;
	}

	// check if key exists in the hashTable
	public boolean isExistsTable(int key) {
		return isExistsList(hashTable[hashFunction(key)], key);
	}

	public String getSearchTime(String string) {
		double time1 = System.nanoTime() / 1000000.0;
		double time2 = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(string));
			String next;
			while ((next = reader.readLine()) != null) {
				this.isExistsTable(this.stringToNumber(next));
			}
			time2 = System.nanoTime() / 1000000.0;
			String s = Double.toString(time2 - time1);
			return s.substring(0, 5);
		} catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

}
