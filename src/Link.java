public class Link{

	private HashFunction data;
	private Link next;
	
	public Link(HashFunction data, Link next) {
		this.data = data;
		this.next = next;
	}
	
	public Link(HashFunction data) {
		this(data, null);
	}

	public Link getNext() { 
		return next;
	}
	
	public void setNext(Link next){
		this.next = next;
	}
	
	public HashFunction getData() {
	    return data;
	}
	
	public void setData(HashFunction data) {
	    this.data = data;
	}
	
	public String toString() {
	    return data.toString();
	}

}