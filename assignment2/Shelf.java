package assignment2;

public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		int shelfL = this.availableLength;
		shelfL = shelfL - b.length;
		this.availableLength = shelfL;
		if(lastBox==null) {
			this.firstBox = b;
			this.lastBox = b;
			b.previous = null;
			b.next = null;
		}else{
			this.lastBox.next = b;
			b.previous = this.lastBox;
			this.lastBox = b;
			this.lastBox.next = null;
		}
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
		Box b = firstBox;
		Box found = null;
		while(b!=null) {
			if(b.id==identifier) {
				found = b;
				int shelfL = this.availableLength;
				shelfL = shelfL + b.length;
				this.availableLength = shelfL;
				if(b.previous==null) {
					if(b.next==null) {
						this.firstBox = null;
						this.lastBox = null;
						found.previous = null;
						found.next = null;
						return found;
					}else {
						this.firstBox = b.next;
						this.firstBox.previous = null;
						found.previous = null;
						found.next = null;
						return found;
					}
				}else if(b.next==null){
					this.lastBox = b.previous;
					this.lastBox.next = null;
					found.previous = null;
					found.next = null;
					return found;
				}else {
				b.previous.next = b.next;
				b.next.previous = b.previous;
				found.previous = null;
				found.next = null;
				return found;
				}
			}else{
				b = b.next;
			}
		}
		return found;
	}
	
}
