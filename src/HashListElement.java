
public class HashListElement{

	private int key;
	private HashListElement next;
	
	public HashListElement(int data, HashListElement next) {
		this.key = data;
		this.next = next;
	}
	
	public HashListElement(int data) {
		this(data, null);
	}

	public HashListElement getNext() { 
		return next;
	}
	
	public void setNext(HashListElement next){
		this.next = next;
	}
	
	public int getKey() {
	    return key;
	}
	
	public void setKey(int data) {
	    this.key = data;
	}

	
}
