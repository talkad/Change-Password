public class LinkedList {
	
	private Link first;
	
	public LinkedList(){		
		first = null;
	}
	
	//Returns the first link in this list
	public Link getFirst() {
		return first;
	}

	//Returns the number of elements in this list
	public int size() {
		int counter = 0;
		for(Link curr = first; curr != null; curr = curr.getNext())
			counter = counter + 1;
		return counter;
	}
	
	//Returns true if this list is empty
	public boolean isEmpty() {
		return first == null;
	}
	
	//Adds element to the beginning of this list
	public void addFirst(HashFunction element) {
		if (element == null)
			throw new NullPointerException();
		first = new Link(element, first);
	}



}