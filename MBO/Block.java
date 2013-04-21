
public class Block{
	
	public String id;
	public Block next;
	public Block prev;
	public Train trainOnBlock;
	public double length = 500;
	public double speedLimit = 50;
		
	
	public Block(String id){
		this.id = id;
	}
}
