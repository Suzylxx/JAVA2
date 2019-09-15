package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		// ADD YOUR CODE HERE
		try {
			System.out.println("Welcome to Connect 4:");
			c.print();
			System.out.println("Please enter the number of the column:");
			String key = keyboard.readLine();
			while(!(key.equals("0")||key.equals("1")||key.equals("2")||key.equals("3")||key.equals("4")||key.equals("5")||key.equals("6"))) {
				System.out.println("The input of the column is not valid, please try again.");
				key = keyboard.readLine();
			}
			int wantToPlay = Integer.parseInt(key);
			return wantToPlay;
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // DON'T FORGET TO CHANGE THE RETURN
		return 0;
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// ADD YOUR CODE HERE
		int move = 0;
		int left = columnPlayed2-1;
		int right= columnPlayed2+1;
		int player1 = 1;
		int canWinNext = c.canWinNextRound(player1);
		int canWinTwo = c.canWinTwoTurns(player1);
		if(canWinNext !=-1) {
			move = canWinNext;
			return move;
		}else if(canWinTwo!=-1) {
			move = canWinTwo;
			return move;
		}else {
			while(c.spaceLeft) {
				if(c.available[columnPlayed2]<6) {
					move = columnPlayed2;
					return move;
				}
				if(left>=0) {
					if(c.available[left]<6){
						move = left;
						return move;
					}else {
						left--;
					}
				}
				if(right<7) {
					if(c.available[right]<6) {
						move = columnPlayed2+1;
						return move;
					}else {
						right++;
					}
				}
			}
		}
		return move; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
