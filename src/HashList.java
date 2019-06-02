
public class HashList {
	private HashListElement first;
	
	public HashList(){		
		first = null;
	}
	
	//Returns the first link in this list
	public HashListElement getFirst() {
		return first;
	}

	//Returns the number of elements in this list
	public int size() {
		int counter = 0;
		for(HashListElement curr=first; curr!=null; curr=curr.getNext())
			counter = counter + 1;
		return counter;
	}
	
	//Returns true if this list is empty
	public boolean isEmpty() {
		return first == null;
	}
	
	//Adds element to the beginning of this list
	public void addFirst(int element) {
		first = new HashListElement(element, first);
	}


}
