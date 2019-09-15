package assignment4Graph;

public class Graph {
	
	boolean[][] adjacency;
	int nbNodes;
	
	public Graph (int nb){
		this.nbNodes = nb;
		this.adjacency = new boolean [nb][nb];
		for (int i = 0; i < nb; i++){
			for (int j = 0; j < nb; j++){
				this.adjacency[i][j] = false;
			}
		}
	}
	
	public void addEdge (int i, int j){
		// ADD YOUR CODE HERE
		if (i<this.nbNodes+1 && j<this.nbNodes+1) {
			this.adjacency[i][j] = true;
			this.adjacency[j][i] = true;
		}
	}
	
	public void removeEdge (int i, int j){
		// ADD YOUR CODE HERE
		if (i<this.nbNodes+1 && j<this.nbNodes+1 && adjacency[i][j]) {
			this.adjacency[i][j] = false;
			this.adjacency[j][i] = false;
		}
	}
	
	public int nbEdges(){
		// ADD YOUR CODE HERE
		int nbEdges = 0;
		for(int i=0;i<this.nbNodes;i++){
			for(int j=0;j<=i;j++){
				if(this.adjacency[i][j]==true) {
					nbEdges++;
				}
			}
		}
		return nbEdges; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public boolean cycle(int start){
		// ADD YOUR CODE HERE
		boolean visited[] = new boolean[this.nbNodes];
		return dfs(start, visited, -1); // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public boolean dfs (int start, boolean[] visited, int parent) {
		visited[start] = true;
		for (int i=0;i<this.nbNodes;i++) {
			if (this.adjacency[start][i]==true) {
				if (visited[i]==false) {
					dfs(i, visited, start);
				}
				else if (visited[i]==true) {
					if (i!=parent) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int shortestPath(int start, int end){
		// ADD YOUR CODE HERE
		int length = 0;
		boolean[] visited = new boolean[this.nbNodes];
		int[] queue = new int[this.nbNodes];
		for (int i=0;i<queue.length;i++) {
			queue[i]=-1;
		}
		visited[start] = true;
		this.enQ(queue, start);
		while (!empty(queue)) {
			int parent = deQ(queue);
			for (int i=0; i<this.nbNodes; i++) {
				if (adjacency[parent][i]==true&&!visited[i]) {
					if (i==end) {
						length++;
						return length;
					}
					visited[i] = true;
					enQ(queue, i);
				}
			}
			length++;
		}
		return this.nbNodes+1; // DON'T FORGET TO CHANGE THE RETURN
	}

	public boolean empty(int[] q) {
		for (int i=0;i<q.length; i++) {
			if (q[i] != -1) {
				return false;
			}
		}
		return true;
	}
	
	public void enQ(int[] q,int node) {
		for (int i=0;i<q.length;i++) {
			if (q[i]==-1) {
				q[i]=node;
			}
		}
	}
	
	public int deQ(int[] q) {
		for (int i=0;i<q.length;i++) {
			if (q[i]!=-1) {
				int d = q[i];
				q[i] = -1;
				return d;
			}
		}
		return -1;
	}
	
	
}
