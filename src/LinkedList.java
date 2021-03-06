
public class LinkedList<T> {
	private Link<T> first;
	
	public LinkedList(){		
		first = null;
	}
	
	//Returns the first link in this list
	public Link<T> getFirst() {
		return first;
	}

	//Returns the number of elements in this list
	public int size() {
		int counter = 0;
		for(Link<T> curr=first; curr!=null; curr=curr.getNext())
			counter = counter + 1;
		return counter;
	}
	
	//Returns true if this list is empty
	public boolean isEmpty() {
		return first == null;
	}
	
	//Adds element to the beginning of this list
	public void addFirst(T element) {
		if (element == null)
			throw new NullPointerException();
		first = new Link<T>(element, first);
	}


}
