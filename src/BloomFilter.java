import java.io.BufferedReader;
import java.io.FileReader;

public class BloomFilter {

	private int[] hashTable;
	private LinkedList hashFunctions;
	
	//Receives the size of the hash table and a path for hash_functions.txt
	public BloomFilter(String m1, String path)  {
		try {
			hashTable=new int[Integer.parseInt(m1)]; 
		}
		catch(Exception e) {
			throw new RuntimeException("The first value in the constructor must be a number");
		}		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			String[] function;
			hashFunctions=new LinkedList();
			while ((next = reader.readLine()) != null) {
				function=next.split("_");
	        	hashFunctions.addFirst(new HashFunction(Integer.parseInt(function[0]),Integer.parseInt(function[1])));
			}
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

	// convert ASCII strings into natural numbers with Horner's Rule
	private long stringToNumber(String pwd) {
		long key=pwd.charAt(pwd.length()-1);
		for(int i=pwd.length()-2;i>=0;i--) {
			key=(pwd.charAt(i) + 256*key)%15486907;
		}
		return key;
	}
	
	public void updateTable(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			int key;
			Link currentFunc;
			while ((next = reader.readLine()) != null) { 
				key=(int)stringToNumber(next);
				currentFunc=hashFunctions.getFirst();
				while(currentFunc.getNext()!=null) {
					hashTable[currentFunc.getData().getIndex(key, hashTable.length)]=1;		
					currentFunc=currentFunc.getNext();
				}
			}			
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}
	
}
