public class Gameboard {

    //global vars
    
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char EMPTY = ' ';
    public char[][] board = {{EMPTY, EMPTY, EMPTY},
                                        {EMPTY, EMPTY, EMPTY},
                                        {EMPTY, EMPTY, EMPTY}};
    
    public Gameboard(){
        //initialize gameboard as blank
        //edit: done above
        
    }

    //try place piece in square
    //returns -1 if place is taken
    int placePiece(int playerNum, int row, int col){
        char playerChar;
        if(playerNum == 1){
            playerChar = X;
        } else {
            playerChar = O;
        }
        if(board[row][col]!=EMPTY){
            System.out.println("Error, piece exists there");
            return -1;
        } else {
            this.board[row][col]= playerChar;
            return 0;
        }
    }

    //checks whether spot is empty, if not return false otherwise true
    public boolean isEmpty(int row, int col){
        if(board[row][col]==EMPTY){
            return true;
        } else {
            return false;
        }
    }

    //prints out board
    void printboard() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println();
        for(int i = 0; i < 3; i++){
            System.out.printf(" %c | %c | %c %n", board[i][0], board[i][1], board[i][2]);
            if(i==2){
                break;
            }
            System.out.println("---+---+---");
        }
        System.out.println();
        

    }

    //1: win
    //0: no win yet
    int checkWin(int player){
        char startChar;
        if(player==1){
            startChar=X;
        } else {
            startChar=O;
        }
        //check accross
        for (int col = 0; col < 3; col++){
            //startChar=board[0][col];
            for(int row = 0; row < 3; row++){
                if(board[row][col] != startChar){
                    break;
                }
                if(row==2){
                    System.out.println("player won on accr");
                    return 1;
                }
            }
        }

        //check down
        for (int row = 0; row < 3; row++){
            //startChar=board[0][col];
            for(int col = 0; col < 3; col++){
                if(board[row][col] != startChar){
                    break;
                }
                if(col==2){
                    System.out.println("player won on down");

                    return 1;
                    
                }
            }
        }

        //check diag
        //startChar=board[0][0];
        for(int diag =0; diag < 3; diag++){
            if(board[diag][diag]!=startChar){
                break;
            }
            if(diag==2){
                System.out.println("player won on reg diag");

                return 1;
            }
        }

        if(board[0][2]==startChar){
            if(board[1][1]==startChar){
                if(board[2][0]==startChar){
                    System.out.println("player won on reverse diag");

                    return 1;
                }
            }
        }

       
        return 0;
    }

    /*
    evaluates board
    pos: X has higher score
    neg: O two has higher score
    */
    public int evaluate(){
        //check down
        int xCounter = 0;
        int oCounter = 0;
        int score= 0;

        //check col
        for(int col = 0; col < 3; col ++){
            for(int row = 0; row < 3; row ++){
                
                if(board[row][col]==X){
                    xCounter++;
                }
                if(board[row][col]==O){
                    oCounter++;
                }

                if(xCounter>0 && oCounter>0){
                    xCounter=0;
                    oCounter=0;
                    break;
                }
            }

            switch(xCounter){
                case 1:
                score += 1;
                break;
                case 2:
                score += 3;
                break;
                case 3:
                score += 500;
                break;
            }
            switch(oCounter){
                case 1:
                score -= 1;
                break;
                case 2:
                score -= 3;
                break;
                case 3:
                score -= 500;
                break;
            }
            xCounter=0;
            oCounter=0;
        }
        
        
        //check row
        for(int row =0; row < 3; row ++){
            for(int col = 0; col < 3; col ++){
                if(board[row][col]==X){
                    xCounter++;
                }
                if(board[row][col]==O){
                    oCounter++;
                }

                if(xCounter>0 && oCounter>0){
                    xCounter=0;
                    oCounter=0;
                    break;
                }     
                
            }
            switch(xCounter){
                case 1:
                score += 1;
                break;
                case 2:
                score += 3;
                break;
                case 3:
                score += 500;
                break;
            }
            switch(oCounter){
                case 1:
                score -= 1;
                break;
                case 2:
                score -= 3;
                break;
                case 3:
                score -= 500;
                break;
            }
            xCounter=0;
            oCounter=0;
        }
        

        // check reg diag \
        for(int diag = 0; diag < 3; diag ++){
            if(board[diag][diag]==X){
                xCounter++;
            }
            if(board[diag][diag]==O){
                oCounter++;
            }

            if(xCounter>0 && oCounter>0){
                xCounter=0;
                oCounter=0;
                break;
            }     
        }

        switch(xCounter){
            case 1:
            score += 1;
            break;
            case 2:
            score += 3;
            break;
            case 3:
                score += 500;
                break;
        }
        switch(oCounter){
            case 1:
            score -= 1;
            break;
            case 2:
            score -= 3;
            break;
            case 3:
                score -= 500;
                break;
        }
        xCounter=0;
        oCounter=0;

        //check reverse diag /
        int rowD = 2;
        for(int colD = 0; colD < 3; colD ++){
            if(board[rowD][colD]==X){
                xCounter++;
            }
            if(board[rowD][colD]==O){
                oCounter++;
            }

            if(xCounter>0 && oCounter>0){
                xCounter=0;
                oCounter=0;
                break;
            } 
            rowD--;
        }
        switch(xCounter){
            case 1:
            score += 1;
            break;
            case 2:
            score += 3;
            break;
            case 3:
                score += 500;
                break;
        }
        switch(oCounter){
            case 1:
            score -= 1;
            break;
            case 2:
            score -= 3;
            break;
            case 3:
                score -= 500;
                break;
        }
        return score;
        

    }

    

    //returns remaining opening spaces
    public int getRemainingSpaces(){
        int counter = 0;
        for(int i = 0; i < 3; i++){
            for (int j = 0; j< 3; j++){
                if(board[i][j]==EMPTY){
                    counter++;
                }
            }
        }
        return counter;
    }

    //makes a copy of the working gameboard
    public Gameboard makeCopy(){
        Gameboard gbCopy = new Gameboard();
        for(int i = 0; i < 3; i++){
            for (int j = 0; j< 3; j++){
                gbCopy.board[i][j] = this.board[i][j];
            }
        }
        return gbCopy;
    }
}
