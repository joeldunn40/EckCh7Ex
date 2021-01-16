/** Eck Exercise 7.7
 * Go Moku game.
 * Play alternates between 2 users (black & white).
 * Each turn: place a piece ("checker") on an empty square of the board.
 * If player has 5 pieces in a row, the player wins.
 * Options to start a New Game and to Resign.
 * Based on Checkers by Eck Ch7
 * @author jd07
 *
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GoMoku extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	
	// TO BE DEFINED
	GoMokuBoard board; // A canvas on which a go moku board is drawn,
	// defined by a static nested subclass.  Much of
	// the game logic is defined in this class.

	private Button newGameButton;  // Button for starting a new game.

	private Button resignButton;   // Button that a player can use to end 
	// the game by resigning.

	private Label message;  // Label for displaying messages to the user.

	/** TO BE EDITED
	 * The constructor creates the Board (which in turn creates and manages
	 * the buttons and message label), adds all the components, and sets
	 * the bounds of the components.  A null layout is used.  (This is
	 * the only thing that is done in the main Checkers class.)
	 */
	public void start(Stage stage) {

		/* Create the label that will show messages. */

		message = new Label("Click \"New Game\" to begin.");
		message.setTextFill( Color.rgb(100,255,100) ); // Light green.
		message.setFont( Font.font(null, FontWeight.BOLD, 18) );

		/* Create the buttons and the board.  The buttons MUST be
		 * created first, since they are used in the CheckerBoard
		 * constructor! */

		newGameButton = new Button("New Game");
		resignButton = new Button("Resign");

		board = new GoMokuBoard(); // a subclass of Canvas, defined below
		board.drawBoard();  // draws the content of the checkerboard

		/* Set up ActionEvent handlers for the buttons and a MousePressed handler
		 * for the board.  The handlers call instance methods in the board object. */

		newGameButton.setOnAction( e -> board.doNewGame() );
		resignButton.setOnAction( e -> board.doResign() );
		board.setOnMousePressed( e -> board.mousePressed(e) );

		/* Set the location of each child by calling its relocate() method */

		board.relocate(20,20);
		newGameButton.relocate(370, 120);
		resignButton.relocate(370, 200);
		message.relocate(20, 370);

		/* Set the sizes of the buttons.  For this to have an effect, make
		 * the butons "unmanaged."  If they are managed, the Pane will set
		 * their sizes. */

		resignButton.setManaged(false);
		resignButton.resize(100,30);
		newGameButton.setManaged(false);
		newGameButton.resize(100,30);

		/* Create the Pane and give it a preferred size.  If the
		 * preferred size were not set, the unmanaged buttons would 
		 * not be included in the Pane's computed preferred size. */

		Pane root = new Pane();

		root.setPrefWidth(500);
		root.setPrefHeight(420);

		/* Add the child nodes to the Pane and set up the rest of the GUI */

		root.getChildren().addAll(board, newGameButton, resignButton, message);
		root.setStyle("-fx-background-color: darkgreen; "
				+ "-fx-border-color: darkred; -fx-border-width:3");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("GoMoku!");
		stage.show();

	} // end start()
	
    // --------------------  Nested Classes -------------------------------

	
	private class GoMokuBoard extends Canvas {
		
		CheckersData board;
		boolean gameInProgress;
		int currentPlayer;
        /**
         * Constructor.  Creates a CheckersData to represent the
         * contents of the checkerboard, and calls doNewGame to 
         * start the first game.
         */
        GoMokuBoard() {
            super(329,329);  // canvas is 329-by-329 pixels: 13 x 25 + 2 x 2
            board = new CheckersData();
            doNewGame();
            drawBoard();
        }
        
		/**
         * Draw a checkerboard pattern in gray and lightGray.  Draw the
         * checkers.  
         */
		public void drawBoard() {
            GraphicsContext g = getGraphicsContext2D();
            g.setFont( Font.font(18) );

//            /* Draw a two-pixel black border around the edges of the canvas. */
//
//            g.setStroke(Color.DARKRED);
//            g.setLineWidth(2);
//            g.strokeRect(1, 1, 327, 327);

            /* Draw the squares of the checkerboard and the checkers. */

            for (int row = 0; row < 13; row++) {
                for (int col = 0; col < 13; col++) {
                	
                	// draw board
                	g.setFill(Color.LIGHTGRAY);
                    g.fillRect(2 + col*25, 2 + row*25, 25, 25);
                    g.setStroke(Color.BLACK);
                    g.setLineWidth(2);
                    g.strokeRect(2 + col*25, 2 + row*25, 25, 25);
                    
                    // draw checkers
                    switch (board.pieceAt(row,col)) {
                    case CheckersData.WHITE:
                        g.setFill(Color.WHITE);
                        g.fillOval(8 + col*25, 8 + row*25, 15, 15);
                        g.setStroke(Color.BLACK);
                        g.strokeOval(8 + col*25, 8 + row*25, 15, 15);
                        break;
                    case CheckersData.BLACK:
                        g.setFill(Color.BLACK);
                        g.fillOval(8 + col*25, 8 + row*25, 15, 15);
                        break;
                    } // end switch piece.At
                } // end for col
            } // end for col
	
		} // end drawBoard

		public void mousePressed(MouseEvent evt) {
			// TODO Auto-generated method stub
			/** if gameInProgress 
			 * find square indices matching coordinates
			 *  if square empty:
			 *  	add piece for currentPlayer:
			 *  		update board.pieceAt[row][col]
			 *  		redraw
			 *  		if there is a winner
			 *  			message = currentPlayer wins
			 *  else
			 *  	do nothing
			 */
            if (gameInProgress == false) {
                message.setText("Click \"New Game\" to start a new game.");
            } else {
                int col = (int)((evt.getX() - 2) / 25);
                int row = (int)((evt.getY() - 2) / 25);
                if (col >= 0 && col < 13 && row >= 0 && row < 13)
                    doClickSquare(row,col);
            }

		}

		void doClickSquare(int row, int col ) {
			// Place currentplayers piece on square if possible
			if (board.pieceAt(row, col) != CheckersData.EMPTY) {
				message.setText("Square "+row+","+col+" is not empty. Try another.");
			} else {
				board.setPieceAt(row,col,currentPlayer);

				//Check for a win or a draw
				int hasWon = isWinner(row, col);
				boolean boardFull = CheckersData.boardFull;
				if (hasWon > 0) {
					String tempStr;
					String[] fiveDir
						= {"row", "column", "forward diagonal", "backward diagonal"};
					if (currentPlayer == CheckersData.BLACK) {
						tempStr = "Black wins!";
					} else {
						tempStr = "White wins!";
					}
					gameInProgress = false;
					newGameButton.setDisable(false);
					resignButton.setDisable(true);
					message.setText(tempStr + " Five on a " + fiveDir[hasWon-1] + "!");
				} else if (boardFull) {
					gameInProgress = false;
					newGameButton.setDisable(false);
					resignButton.setDisable(true);
					message.setText("Board is full. It's a draw!");

				} else {

					// Next players turn
					if (currentPlayer == CheckersData.WHITE) {
						currentPlayer = CheckersData.BLACK;
						message.setText("Black's go.");
					} else {
						currentPlayer = CheckersData.WHITE;
						message.setText("White's go.");
					}

				} // end if: hasWon, boardFull, toggle currentPlayer
			} // endif: setPiece
			drawBoard();
		}// end doClickSquare
		
		public void doResign() {
			if (gameInProgress == false) {
				// This should not be possible, but it doesn't hurt to check.
				message.setText("Start a game first!");
				return;
			}
			gameInProgress = false;
			newGameButton.setDisable(false);
			resignButton.setDisable(true);
			
			if (currentPlayer == CheckersData.WHITE)
				message.setText("White resigned! Black wins!");				
			else
				message.setText("Black resigned! White wins!");
			drawBoard();			
		}

		public void doNewGame() {
			if (gameInProgress == true) {
				// This should not be possible, but it doesn't hurt to check.
				message.setText("Finish the current game first!");
				return;
			}
			board.setUpGame();   // Set up the pieces.
			currentPlayer = CheckersData.WHITE;   // WHITE moves first.
			message.setText("White:  Make your move.");
			gameInProgress = true;
			newGameButton.setDisable(true);
			resignButton.setDisable(false);
			drawBoard();
		}
		
		/** Check board to see if there are 5 in row around new checker
		 * 
		 * @return 0 1 2 3 4 : no winner, row, col, +diag, -diag
		 */
		private int isWinner(int row, int col) {
			/* Get line counts in 4 directions (row,col,2 diags)
			* break if 5 found
			* dirX,Y:
			*	0  1 row
			*	1  0 col
			*	1 -1 +diag
			*	1  1 -diag
			*/
			int ct;
			int flag = 0;
			int[][] dirXY = {{ 0, 1, 1, 1},
							 { 1, 0,-1, 1}
							};
			for (int i = 0; i < 4; i++) {
				ct = countLine(row,col,dirXY[0][i],dirXY[1][i]);
				if (ct >= 5) { // we found a winner!
					flag = i+1;
					break;
				}
			}
			return flag;
		}
		
		private int countLine(int row, int col, int dirX, int dirY) {
			// Eck's code to count on row
			int ct = 1;  // Number of pieces in a row belonging to the player.

			int r, c;    // A row and column to be examined

			r = row + dirX;  // Look at square in specified direction.
			c = col + dirY;
			
			while ( r >= 0 && r < 13 && c >= 0 && c < 13 
					&& board.pieceAt(r,c) == currentPlayer ) {
				// Square is on the board, and it 
				// contains one of the player's pieces.
				ct++;
				r += dirX;  // Go on to next square in this direction.
				c += dirY;
			}

			r = row - dirX;  // Now, look in the opposite direction.
			c = col - dirY;
			while ( r >= 0 && r < 13 && c >= 0 && c < 13 
					&& board.pieceAt(r,c) == currentPlayer ) {
				ct++;
				r -= dirX;   // Go on to next square in this direction.
				c -= dirY;
			}
			
			return ct;
		} // end countLine		
		
	}// end GoMokuBoard

	private static class CheckersData {
        /*  The following constants represent the possible contents of a square
        on the board.  The constants RED and BLACK also represent players
        in the game. */

		static final int
					EMPTY = 0,
					WHITE = 1,
					BLACK = 2;
		
//		static final String[] playerName = {"EMPTY","WHITE","BLACK"}; 
		
		static boolean boardFull = false;
		
		int[][] board;  // board[r][c] is the contents of row r, column c.
		
        CheckersData() {
            board = new int[13][13];
            setUpGame();
        } // end constructor CheckersData
        
        
        void setUpGame() {
        	// basically initialise board array: all EMPTY
        	for (int i = 0; i < 13; i++) {
        		for (int j = 0; j < 13; j++) {
        			board[i][j] = EMPTY;
        		}
        	}
        } // end setUpGame
        
        /**
         * Return the contents of the square in the specified row and column.
         */
        int pieceAt(int row, int col) {
            return board[row][col];
        } // end pieceAt

        void setPieceAt(int row,int col, int player) {
        	board[row][col]=player;
        	//check if board full
        	int countEmpty = 0;
        	for (int i = 0; i < 13; i++) {
        		for (int j = 0; j < 13; j++) {
        			if (board[i][j]==EMPTY)
        				countEmpty++;
        		}
        	}
        	if (countEmpty == 0)
        		boardFull = true;
        } // end setPieceAt


	} // end CheckersData
	
} // end GoMoku
