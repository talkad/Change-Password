import java.io.BufferedReader;
import java.io.FileReader;

public class BloomFilter {

	private int[] hashTable;
	private LinkedList<HashFunction> hashFunctions;
	
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
			hashFunctions=new LinkedList<>();
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
	private int stringToNumber(String pwd) {
		long key=pwd.charAt(0);
		for(int i=1;i<pwd.length();i++) {
			key=(pwd.charAt(i) + 256*key)%15486907;
		}
		return (int)key;
	}
	
	public void updateTable(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			int key;
			Link<HashFunction> currentFunc;
			while ((next = reader.readLine()) != null) { 
				key=stringToNumber(next);
				currentFunc=hashFunctions.getFirst();
				while(currentFunc!=null) {
					hashTable[currentFunc.getKey().hashFunction(key, hashTable.length)]=1;		
					currentFunc=currentFunc.getNext();
				}
			}			
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

	// check if key exists in Bloom Filter
	private boolean isExistsBloom(int key) {
		Link<HashFunction> currentFunc=hashFunctions.getFirst();
		while(currentFunc!=null) {
			if(hashTable[currentFunc.getKey().hashFunction(key, hashTable.length)]!=1)
				return false;
			currentFunc=currentFunc.getNext();
		}		
		return  true;
	}	
	
	// Returns the false positive percentage of the filter bloom
	public String getFalsePositivePercentage(HashTable hashTable2, String pathRequested) {
		try {
			int bloomFound=0; // the pwd that not exists in  the Hashtable but found by the bloom filter
			int requestedPwd=0; // the good requested pwd
			BufferedReader reader = new BufferedReader(new FileReader(pathRequested));
			String next;
			int key;
			while ((next = reader.readLine()) != null) { 
				key=stringToNumber(next);
				if(!hashTable2.isExistsTable(key) && isExistsBloom(key))
					bloomFound++;
				if(!hashTable2.isExistsTable(key))
					requestedPwd++;
			}
			return (double)bloomFound/requestedPwd+"";
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}

	// Returns the amount of requested passwords that rejected by the bloom filer
	public String getRejectedPasswordsAmount(String pathRequested) {
		try {
			int rejectAmount=0; 
			BufferedReader reader = new BufferedReader(new FileReader(pathRequested));
			String next;
			int key;
			while ((next = reader.readLine()) != null) { 
				key=stringToNumber(next);
				if(isExistsBloom(key))
					rejectAmount++;
			}
			return rejectAmount+"";
		}
		catch (Exception e) {
			throw new RuntimeException("reading file exception");
		}
	}
}
