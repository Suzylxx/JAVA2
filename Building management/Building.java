package assignment3;

public class Building {

	OneBuilding data;
	Building older;
	Building same;
	Building younger;
	
	public Building(OneBuilding data){
		this.data = data;
		this.older = null;
		this.same = null;
		this.younger = null;
	}
	
	public String toString(){
		String result = this.data.toString() + "\n";
		if (this.older != null){
			result += "older than " + this.data.toString() + " :\n";
			result += this.older.toString();
		}
		if (this.same != null){
			result += "same age as " + this.data.toString() + " :\n";
			result += this.same.toString();
		}
		if (this.younger != null){
			result += "younger than " + this.data.toString() + " :\n";
			result += this.younger.toString();
		}
		return result;
	}
	
	public Building addBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		int bYear = b.yearOfConstruction;
		if(this.data==null) {
			data = b;
			return this;
		}else if(bYear<data.yearOfConstruction) {
			if(this.older==null) {
				this.older = new Building(b);
			}else {
				this.older = this.older.addBuilding(b);
			}
		}else if(bYear==data.yearOfConstruction){
			if(this.data.height<b.height) {
				OneBuilding temp = this.data;
				this.data = b;
				Building newB = new Building(temp);
				newB.same = this.same;
				this.same = newB;
			}else if(this.same!=null) {
				if(this.data.height<b.height) {
					Building newB = new Building(b);
					newB.same = this.same;
					this.same = newB;
				}else {
					this.same.addBuilding(b);
				}
			}else {
				this.same = new Building(b);
			}
		}else if(bYear>data.yearOfConstruction) {
			if(this.younger==null) {
				this.younger = new Building(b);
			}else {
				this.younger = this.younger.addBuilding(b);
			}
		}
		return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public Building addBuildings (Building b){
		// ADD YOUR CODE HERE
		this.addBuilding(b.data);
		if(b.older!=null) {
			this.addBuildings(b.older);
		}
		if(b.same!=null) {
			this.addBuildings(b.same);
		}
		if(b.younger!=null) {
			this.addBuildings(b.younger);
		}
		return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public Building removeBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		if(this.data.equals(b)) {
			if(this.same!=null) {
				this.data = this.same.data;
				this.same = this.same.same;
			}else if(this.older!=null&&same==null) {
				Building preYounger = this.younger;
				this.data = this.older.data;
				this.same = this.older.same;
				this.younger = this.older.younger;
				this.older = this.older.older;
				if(preYounger!=null) {
					this.addBuildings(preYounger);
				}
			}else if(this.younger!=null&&same==null) {
				Building preOlder = this.older;
				this.data = this.younger.data;
				this.same = this.younger.same;
				this.older = this.younger.older;
				this.younger = this.younger.younger;
				if(preOlder!=null) {
					this.addBuildings(preOlder);
				}
			}else{
				return null;
			}
		}else if(b.yearOfConstruction<this.data.yearOfConstruction) {
			if(this.older!=null) {
				this.older = this.older.removeBuilding(b);
			}
		}else if(b.yearOfConstruction>this.data.yearOfConstruction) {
			if(this.younger!=null) {
				this.younger = this.younger.removeBuilding(b);
			}
		}else {
			if(this.same!=null) {
				this.same = this.same.removeBuilding(b);
			}
		}
		return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int oldest(){
		// ADD YOUR CODE HERE
		int oldest = 0;
		if(this.older!=null) {
			oldest = this.older.oldest();
		}else {
			oldest = this.data.yearOfConstruction;
		}
		return oldest; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int highest(){
		// ADD YOUR CODE HERE
		int highest = this.data.height;
		if(this.older!=null) {
			if(this.older.highest()>this.data.height) {
				highest = this.older.highest();
			}
		}
		if(this.younger!=null) {
			if(this.younger.highest()>highest) {
				highest = this.younger.highest();
			}
		}
		return highest; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public OneBuilding highestFromYear (int year){
		// ADD YOUR CODE HERE
		OneBuilding newB = new OneBuilding("",0,0,0,0);
		if(this.data.yearOfConstruction==year) {
			newB = this.data;
		}else if(this.data.yearOfConstruction<year) {
			if(this.younger!=null) {
				newB = this.younger.highestFromYear(year);
			}else {
				return null;
			}
		}else if(this.data.yearOfConstruction>year) {
			if(this.older!=null) {
				newB = this.older.highestFromYear(year);
			}else {
				return null;
			}
		}else {
			return null;
		}
		return newB; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int numberFromYears (int yearMin, int yearMax){
		// ADD YOUR CODE HERE
		int number = 0;
		if(yearMin<=this.data.yearOfConstruction&&this.data.yearOfConstruction<=yearMax) {
			number++;
		}
		if(this.older!=null) {
			number = number + this.older.numberFromYears(yearMin, yearMax);
		}
		if(this.same!=null) {
			number = number + this.same.numberFromYears(yearMin, yearMax);
		}
		if(this.younger!=null) {
			number = number + this.younger.numberFromYears(yearMin, yearMax);
		}
		return number; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int[] costPlanning (int nbYears){
		// ADD YOUR CODE HERE
		int[] cost = new int[nbYears];
		int year = 2018;
		for(int i=0;i<nbYears;i++) {
			if(year+i==this.data.yearForRepair) {
				cost[i] = cost[i] + this.data.costForRepair;
			}
			if(this.older!=null) {
				int temp[] = this.older.costPlanning(nbYears);
				cost[i] = cost[i] + temp[i];
			}
			if(this.same!=null) {
				int temp[] = this.same.costPlanning(nbYears);
				cost[i] = cost[i] + temp[i];
			}
			if(this.younger!=null) {
				int temp[] = this.younger.costPlanning(nbYears);
				cost[i] = cost[i] + temp[i];
			}
		}
		return cost; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
}
