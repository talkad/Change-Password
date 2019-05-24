import java.io.BufferedReader;
import java.io.FileReader;

public class HashTable {

	private HashList<Integer>[] hashTable;
	
	public HashTable(String m2) {
		try {
			hashTable=new HashList[Integer.parseInt(m2)]; 
		    //initialize values in hashTable
	        for(int i=0; i<hashTable.length; i++) {
	            hashTable[i] = null;
	        }
		}
		catch(Exception e) {
			throw new RuntimeException("The first value in the constructor must be a number");
		}	
	}

	public int hashFunction(int key) {
		return key%hashTable.length;
	}
	
	// convert ASCII strings into natural numbers with Horner's Rule
	private int stringToNumber(String pwd) {
		long key=pwd.charAt(pwd.length()-1);
		for(int i=pwd.length()-2;i>=0;i--) {
			key=(pwd.charAt(i) + 256*key)%15486907;
		}
		return (int)key;
	}
	
	public void updateTable(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			int key;
			while ((next = reader.readLine()) != null) { 
				key=(int)stringToNumber(next);
				if(hashTable[hashFunction(key)]==null) 
					hashTable[hashFunction(key)]=new HashList<>();
				hashTable[hashFunction(key)].addFirst(key);
			}			
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}
	
	// check if key exists in HashList
	private boolean isExistsList(HashList<Integer> list, int key) {
		if(list!=null) {
			HashListElement<Integer> link=list.getFirst();
			while(link!=null) {
				if(link.getKey()==key)
					return true;
				link=link.getNext();
			}
		}
		return false;
	}
	
	// check if key exists in the hashTable
	public boolean isExistsTable(int key) {
		return isExistsList(hashTable[hashFunction(key)], key);
	}

	public String getSearchTime(String string) {
		double time1=System.nanoTime()/1000000.0;
		double time2=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(string));
			String next;
			while ((next = reader.readLine()) != null) { 
				this.isExistsTable(this.stringToNumber(next));
			}		
			time2=System.nanoTime()/1000000.0;
			String s=Double.toString(time2-time1);
			return s.substring(0, 5);
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}
	
}
