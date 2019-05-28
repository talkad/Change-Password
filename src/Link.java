
public class Link<E>{

	private E key;
	private Link<E> next;
	
	public Link(E data, Link<E> next) {
		this.key = data;
		this.next = next;
	}
	
	public Link(E data) {
		this(data, null);
	}

	public Link<E> getNext() { 
		return next;
	}
	
	public void setNext(Link<E> next){
		this.next = next;
	}
	
	public E getKey() {
	    return key;
	}
	
	public void setKey(E data) {
	    this.key = data;
	}

	
}
