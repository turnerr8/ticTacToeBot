//imports
import java.util.Random;
import java.util.Scanner;

public class GameController {

    //global vars
    Scanner scan;
    Gameboard gb;
   

    //constructor, creates a scanner, new gameboard, 
    public GameController(){
        this.scan = new Scanner(System.in);
        this.gb = new Gameboard();
       
    }


    public int startGame(){
       
        int numAI = selectOpponent();
        if(numAI==2){
            aiVersAI();
        } else {
            playerVersAI();
        }
        
        return 1;
    }

    //scans for opponent w/ error checking
    //returns number of ais

    //ERROR: Sometimes when this method is called and 1 is selected, 2 is arbitrarily returned and the ai plays against itself
    private int selectOpponent(){
        System.out.printf("Select the AI's opponent:\n[1] Human\n[2] AI\n");
        int choice;
        do {
            System.out.print("==> ");
                choice = scan.nextInt();
            
        } while (choice !=1 && choice != 2);

        return choice;
    }

    /* if player chooses to play against ai, this method is called
     * loops through rounds until a) someone wins the round or b) roundNumber = 9 aka all spaces are taken and no one has won
    */
    private int playerVersAI(){
        int roundNum = 0;
        int curPlayer;
        int human =  new Random().nextInt(3);

        while (true){
            gb.printboard();
            
            if(roundNum>8){
                System.out.println("Draw!");
                return 1;
            }
            curPlayer = (roundNum%2)+1;

            System.out.printf("Player %d's turn...\n", curPlayer);

            if(curPlayer==human){
                int err = playerTurn(curPlayer);
            } else {
                botTurn(curPlayer);
            }

            if(gb.checkWin(curPlayer)==1){
                System.out.printf("player %d won!\n", curPlayer);
                System.out.println();
                System.out.println("=====================================");
                System.out.println();
                return 1;
            }
            roundNum++;
            
        }
    }

    /*
     * Same as player vs ai but there playerTurn() is never called
     * botTurn is called every time with the player number
     */
    private int aiVersAI(){
        int roundNum = 0;
            int curPlayer;
        while (true){
            
            gb.printboard();
            if(roundNum>8){
                System.out.println("Tie!");
                return 1;
            }
            curPlayer = (roundNum%2)+1;
            
            botTurn(curPlayer);

            if(gb.checkWin(curPlayer)==1){
                System.out.printf("player %d won!\n", curPlayer);
                return 1;
            }
            roundNum++;
        }
    }


    /*
     * players turn
     * loop through making sure the player enters a valid spot
     * if this has happened, it will check the spot to make sure it isn't occupied
     * places piece if not and returns 1 on success
     */
    private int playerTurn(int playerNum){

        
        int row;
        int col;
        

        //keep in this loop while player has entered a taken spot
        do {
            System.out.printf("Player %d's turn:\n", playerNum);

            do{
                System.out.print("Enter row [0 to 2]: ");
                row =scan.nextInt();
            } while(row != 0 && row!=1 && row != 2);
        
            do {
            System.out.print("Enter col [0 to 2]: ");
            col =scan.nextInt();
            } while(col != 0 && col!=1 && col != 2);
            
        } while (gb.placePiece(playerNum, row, col)==-1);
        return 1;
    }

    /*
     * Similar to player turn:
     * minMaxBool determines whether it is x (wants score high: max) or o (wants score low: min)
     * expands child tree
     * checks whether there is a winning move for it in the possible next moves
     * if not, run minimax and determine best next move
     */
    private int botTurn(int playerNum){
        
        boolean minMaxBool;
        if(playerNum==2){
            minMaxBool = false;
        } else {
            minMaxBool = true;
        }
        GameTreeNode root = new GameTreeNode(gb, playerNum);
        root.expandChildren(0);
        GameTreeNode possibleWin;
        if(( possibleWin= root.winningBoard(minMaxBool)) != null){
            gb.placePiece(playerNum, possibleWin.row, possibleWin.col);
            System.out.println("toeBot placed: ("+possibleWin.row+","+possibleWin.col+")");

        } else {
            GameTreeNode best = root.runMiniMax(minMaxBool);
            gb.placePiece(playerNum, best.row, best.col);
            System.out.println("toeBot placed: ("+best.row+","+best.col+")");
            System.out.printf("Nodes Expanded: %d\n", root.nodesExpanded);
        }
        return 1;

    } 
}