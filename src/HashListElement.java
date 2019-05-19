
public class HashListElement <E>{

	private E key;
	private HashListElement<E> next;
	
	public HashListElement(E data, HashListElement<E> next) {
		this.key = data;
		this.next = next;
	}
	
	public HashListElement(E data) {
		this(data, null);
	}

	public HashListElement<E> getNext() { 
		return next;
	}
	
	public void setNext(HashListElement<E> next){
		this.next = next;
	}
	
	public E getKey() {
	    return key;
	}
	
	public void setKey(E data) {
	    this.key = data;
	}

	
}
