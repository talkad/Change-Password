
public class HashFunction {

	private final int p=15486907; //great primary number
	private int a;
	private int b;
	
	public HashFunction(int a,int b) {
		this.a=a;
		this.b=b;
	}
	
	public int hashFunction(int key,int m) {
		return ((a*key+b)%p)%m;
	}
}
