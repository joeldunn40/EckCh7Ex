import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/** Eck Exercise 7.4
 * User (left) clicks on the canvas to create a square
 * Shift-click or right click & drag moves rectangles
 * Dragging off the canvas deletes the square
 * Squares are created as objects of square class
 * Squares are stored in an ArrayList of type Square
 * Square class stores
 * 	position of x & y coordinates of square
 * 	setter & getter methods for x  & y coords
 * 	constructor includes fill & border colours (randomly assigned)
 * Mouse events captured on canvas
 * @author jd07
 *
 */


public class MoveRectangles2 extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private Canvas canvas;
    private GraphicsContext g;
    private boolean dragging; // is mouse being dragged
    private int selectedSquare = -1; // index of ArrayList<Square>: -1 = no square selected
    // Create empty array of squares
    private ArrayList<Square> squares;

    public void start(Stage stage) {
        
        /* Create the canvas, and set up the GUI */
        
        canvas = new Canvas(550,400);
        Pane root = new Pane(canvas);
        Scene scene = new Scene( root );
        stage.setScene(scene);
        stage.setTitle("Move Squares");
        stage.setResizable(false);
        
        /* Set up canvas */
        resetCanvas();
        
        // Create empty array of squares
        squares = new ArrayList<Square>();
        
        /* Install event handlers for common mouse events on the canvas.*/        
        canvas.setOnMousePressed( e -> mousePressed(e) );
        canvas.setOnMouseReleased( e -> mouseReleased(e) );
        canvas.setOnMouseDragged( e -> mouseDragged(e) );
        canvas.setOnMouseExited( e -> mouseExited(e) ); // Used to remove square from ArrayList
        scene.setOnKeyPressed( e -> keyPressed(e) ); // Used for ESC key (reset canvas)
        stage.show();  // make the window visible
        
    } // end start()

    // Key pressed event for ESC key
    private void keyPressed(KeyEvent evt) {
        KeyCode key = evt.getCode();  // keyboard code for the pressed key
    	if (key == KeyCode.ESCAPE) {
            /* Reset canvas & squares */
            resetCanvas();
            System.out.println("Canvas Reset");
    	}
    }

    /**
     * Redraws canvas with any squares in relevant positions
     * clearCanvas
     * Loop through square array calling square.draw() method
     */
    private void redraw() {
    	resetCanvas();
    	for (Square square : squares)
    		square.draw(g);
    }
    
    /**
     * Get index of square array around mouse coords
     * @param x double x-coord of mouse position
     * @param y double y-coord of mouse position
     * @return integer: index of square from array: -1 if no square exists or mouse not in any square
     */
    private void setSquareIndex(double x, double y) {
    	/* Let nS = number of squares
    	 * Let count = square index = 0
    	 * While (selectedSquare > -1 && count < nS)
    	 * 	double sx = squares.get(0).getX(); 
    	 * 	double sy = squares.get(0).getY(); 
    	 * 	if ( (x > (sx-15)) && (x < (sx+15)) && (y > (sy-15)) && (y < (sy+15)) ) {
    	 * 		selectedSquare = count;
    	 *  }
    	 *  count++;
    	 */
    	int nS = squares.size();
    	int count = 0;
    	boolean isSelected = false; // has square been found at mouse position?
    	while ( !isSelected && count < nS) {
    		double sx = squares.get(count).getX(); 
    		double sy = squares.get(count).getY(); 
    		System.out.println(":"+x+":"+sx+":"+count);
    		if ( (x > (sx-15)) && (x < (sx+15)) && (y > (sy-15)) && (y < (sy+15)) ) {
    			selectedSquare = count;
    			isSelected = true;
    		}
    		count++;
    	}
    }
    
    /**
     * If left-click: add new square at coordinates
     * If dragging == true: do nothing (return)
     * If shift down: 
     * 	get square with centre closest to mouse coords
     * 	set selectedSqaure index
     * 	set dragging = true 
     * @param evt
     */
    private void mousePressed(MouseEvent evt) {
    	if (dragging)
    		// do nothing
    		return;

    	// Mouse position on click
    	double x = evt.getX();
    	double y = evt.getY();

    	//    	Alt: evt.isSecondaryButtonDown();
    	if (evt.isShiftDown() || evt.getButton().equals(MouseButton.SECONDARY)) {
    		// find square and do the dragging
        	dragging = true; // set dragging flag
        	
        	// code to find square in click position
        	setSquareIndex(x,y);
        	
    	} else if (evt.getButton().equals(MouseButton.PRIMARY)) {
    		// create new square at location
    		squares.add(new Square(x,y));
    	}
    	redraw();
   	
    } //end mousePressed
    
    /**
     * If dragging == false: do nothing (return)
     * Else:
     * 	check if/which square selected
     * 		change coords of selected square
     * 		redraw 
     * @param evt
     */
    private void mouseDragged(MouseEvent evt) {
    	if (dragging == false) 
    		return;
    	
    	if (selectedSquare < 0 )
    		return; // no square selected
 
    	// ensure shift key or right mouse button is being used
    	if (evt.isShiftDown() || evt.getButton().equals(MouseButton.SECONDARY)) {
        	// Mouse coordinates
        	double x = evt.getX();
        	double y = evt.getY();
        	
        	// Update selected square coordinates
        	squares.get(selectedSquare).setX(x);
        	squares.get(selectedSquare).setY(y);
        	        	
        	// redraw
        	redraw();
    	}
    } // end mouseDragged
    
    
    /**
     * Set dragging = false
     * @param evt
     */
    private void mouseReleased(MouseEvent evt) {
    	dragging = false;
    	selectedSquare = -1; // "release" selected square.
    } // end mouseReleased
    
    /**
     * If dragging == false: do nothing (return)
     * Else:
     * 	check if/which square selected
     * 	remove square from ArrayList
     * 	redraw
     * @param evt
     */
    private void mouseExited(MouseEvent evt) {
    	if (dragging == false)
    		return; // only continue if dragging
    	
    	if (selectedSquare < 0)
    		return; // no square selected to remove
    	
    	// remove selected square
    	squares.remove(selectedSquare);
    	selectedSquare = -1; // "release" selected square
    	// then redraw
    	redraw();
    } // end mouseExited

    // Class for Square objects
    private static class Square {
    	private double x, y;
    	private Color color = Color.hsb(360*Math.random(), 1.0, 1.0, Math.random());
    	
    	// create square object with initialised coordinates
    	public Square(double x, double y) {
    		this.x = x;
    		this.y = y;
    	}
    	
    	public double getX() {
    		return x;
    	}
    	public double getY() {
    		return y;
    	}
    	public void setX(double x) {
    		this.x = x;
    	}
    	public void setY(double y) {
    		this.y = y;
    	}
    	public void draw(GraphicsContext g) {
    		g.setStroke( Color.BLACK);
    		g.strokeRect( this.x - 15, this.y - 15, 30, 30 );
    		g.setFill( this.color);
    		g.fillRect( this.x - 15, this.y - 15, 30, 30 );
    	}
    } // end square class
    
    private void clearCanvas() {
    	// Create cleared canvas
    	g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0,0,550,400);
    }

    // Clear canvas and add reset squares
    private void resetCanvas() {
    	clearCanvas();
    }

}
