//imports
import java.util.*;

/*
 * driving force of program, tree of nodes with possible moves for this bot and opponent
 * change MAX_DEPTH to effect how 'smart' the bot is
 */
public class GameTreeNode {
    private List <GameTreeNode> children;
    private Gameboard gameBoard;
    private int minimaxVal;
    private static final int MAX_DEPTH = 3;
    public int row;
    public int col;
    private int me;
    private int other;
    public int nodesExpanded;

    //constructor 
    public GameTreeNode(Gameboard gb, int me){
        nodesExpanded++;
        this.gameBoard = gb.makeCopy(); 
        this.children = new ArrayList<GameTreeNode>();
        this.me = me;
        if(me==1){
            this.other = 2;
        } else {
            this.other = 1;
        }
    }

    public void printBoard(){
        this.gameBoard.printboard();
    }

    //recursively creates board tree to depth MAX_DEPTH
    public void expandChildren (int depthLimit){
        if(depthLimit==MAX_DEPTH){
            this.minimaxVal = this.gameBoard.evaluate();
            this.nodesExpanded=1;
            return;
        }
        for(int i =0; i < 3; i++){
            for(int j = 0; j< 3; j++){
                
                if(this.gameBoard.isEmpty(i, j)){
                    
                    GameTreeNode newChild = new GameTreeNode(gameBoard.makeCopy(), me);
                    int player;
                    if((depthLimit%2)==0){
                        player=me;
                    } else {
                        player = other;
                    }
                    newChild.gameBoard.placePiece(player, i, j);
                    newChild.row = i;
                    newChild.col = j;
                    
                    this.children.add(newChild);
                    newChild.expandChildren(depthLimit+1);
                    this.nodesExpanded+= newChild.nodesExpanded;

                }
            }
            
        }
    }

    //for internal use | prints board of all children
    private void printChildren(){
        if(this.children.isEmpty()){
            this.gameBoard.printboard();
            System.out.println("end of tree");
            return;
        }
        for(int i = 0; i < this.children.size(); i++){
            this.children.get(i).printChildren();
            
            
        }
    }

    /*
     * runs minimax on expanded children
     * if we want to min or max the level is passed in recursively
     * check all children and find who is min/max
     * set my minmax val to child val
     * return child
     */
    public GameTreeNode runMiniMax(boolean max){
        if(this.children.isEmpty()){
            return this;
        }
        if(max){
            int maxVal = Integer.MIN_VALUE;
            GameTreeNode maxChild= null;
            for(int i = 0; i < this.children.size(); i++){
                int val = this.children.get(i).runMiniMax(false).minimaxVal;
                if(val>maxVal){
                    maxVal = val;
                    maxChild= this.children.get(i);
                    this.minimaxVal = maxVal;
                }
            }
            return maxChild;
        } else {
            int minValue = Integer.MAX_VALUE;
            GameTreeNode minChild = null;
            for(int i = 0; i < this.children.size(); i++){
                int val = this.children.get(i).runMiniMax(true).minimaxVal;
                if(val < minValue){
                    minValue = val;
                    minChild = this.children.get(i);
                    this.minimaxVal = minValue;
                }
            }
            return minChild;
        }
        
        
    }

    /*
     * checks whether next move could be winning for 'me'
     * true: player 1
     * false: player 2
     */
    public GameTreeNode winningBoard(boolean max){
        //GameTreeNode winner=null;
        if(max){
            
            for(int i =0; i< children.size(); i++){
                if(children.get(i).gameBoard.evaluate()>=200){
                    return children.get(i);
                }
            }
        } else {
            for(int i =0; i< children.size(); i++){
                if(children.get(i).gameBoard.evaluate()<=-200){
                    return children.get(i);
                }
            }
        }
        return null;
    }

    

}



