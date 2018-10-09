package assignment2;

public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
		//ADD YOUR CODE HERE
		if(start<end) {
			int mid = (start + end)/2;
			mergeSort(start, mid);
			mergeSort(mid+1, end);
			merge(start, mid, end);
		}
	}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		//ADD YOUR CODE HERE
		Shelf[] lShelf = new Shelf[mid-start+1];
		Shelf[] rShelf = new Shelf[end-mid];
		for(int i=start;i<=end;i++) {
			if(i<=mid) {
				lShelf[i-start] = storage[i];
			}else {
				rShelf[i-mid-1] = storage[i];
			}
		}
		for(int j=start,a=0,b=0;j<=end;j++) {
			if(a<lShelf.length&&b<rShelf.length) {
				if(lShelf[a].height>=rShelf[b].height) {
					storage[j] = rShelf[b];
					b++;
				}else {
					storage[j] = lShelf[a];
					a++;
				}
			}else{
				if(a>=lShelf.length&&b<rShelf.length) {
					storage[j] = rShelf[b];
					b++;
				}else if(b>=rShelf.length&&a<lShelf.length) {
				storage[j] = lShelf[a];
				a++;
				}
			}
		}
	}
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		//ADD YOUR CODE HERE
		int boxH = b.height;
		int boxL = b.length;
		int newV = 0;
		for(int i=0;i<nbShelves;i++) {
			if(boxH<=storage[i].height&&boxL<=storage[i].availableLength) {
				newV++;
			}
		}
		if(newV==0) {
			return problem;
		}
		Shelf[] newS = new Shelf[newV];
		 for (int i=0; i<newS.length; i++){
	     int j=0;
	     	while (storage[j].height<boxH||storage[j].availableLength<boxL){
		      j++;
	     	}
		    newS[i]=storage[j];
		}
		Shelf minS = newS[0];
		int minH = newS[0].height;
		for(int i=0;i<newS.length;i++) {
			if(newS[i].height<minH) {
				minH = newS[i].height;
				minS = newS[i];
			}
		}
		int minL = newS[0].availableLength;
		for(int i=0;i<newS.length;i++) {
			if(newS[i].height==minH) {
				if(newS[i].availableLength<minL) {
					minS = newS[i];
				}
			}
		}
		minS.addBox(b);
		return noProblem;
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		String pr = problem;
		if(b.getClass().equals(UrgentBox.class)) {
			pr = noProblem;
			if(toShipUrgently!=null) {
				b.next = toShipUrgently;
				b.next.previous = b;
				b.next.previous.previous = null;
				toShipUrgently = (UrgentBox)b;
				return pr;
			}else if(toShipUrgently==null) {
				toShipUrgently = (UrgentBox) b;
				toShipUrgently.previous = null;
				toShipUrgently.next = null;
				return pr;
			}
		}else if(b.getClass().equals(Box.class)){
			pr = noProblem;
			if(toShip!=null) {
				b.next = toShip;
				b.next.previous = b;
				b.next.previous.previous = null;
				toShip = b;
				return pr;
			}else if(toShip==null) {
				toShip = b;
				toShip.previous = null;
				toShip.next = null;
				return pr;
			}
		}
		return pr;
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		//ADD YOUR CODE HERE
		String add = problem;
		for(int i=0;i<nbShelves;i++) {
			Box check = storage[i].removeBox(identifier);
			if(check!=null) {
				add = addToShip(check);
				return add;
			}
		}
		return add;
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		Box move = storage[position].removeBox(b.id);
		addBox(move);
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize (){
		//ADD YOUR CODE HERE
		for(int i=0;i<nbShelves;i++) {
			Box b = storage[i].firstBox;
			while(b!=null) {
				Box temp = b.next;
				moveOneBox(b,i);
				b = temp;
			}
		}
	}
}