package RoboCodeAI;
import robocode.*;
import java.awt.Color;

/******************************************************************
 * A robot designed to defeat the Tracker robot
 * @Author: Gage Vander Clay & Mitch Couturier
 ******************************************************************/

public class TrackerBeater extends Robot
{
	/* The distance the the TrackerBeater remains from the wall as it moves */
	private final int DISTANCE_FROM_WALL = 100;
	
	/* Is true if the TrackerBeater is moving clockwise; is false if moving counter-clockwise */
	boolean clockwise;
	
	/* Coordinate of the last corner that TrackerBeater was in */
	double previousX, previousY;
	
	/* An integer that represent the current color of TrackerBeater */
	int color;
	
	/******************************************************************
	 * run: TrackerBeater's default behavior
	 * (Change Color & search for enemy)
	 ******************************************************************/
	public void run() {
		// Initialization of the robot
		color = 0;
		clockwise = true;
		// Get to the closest corner
		findClosestCorner();
		// Robot main loop
		while(true) {
			//Change Color
			randomColors();
			//Find the enemy
			turnGunRight(10);
		}
	}

	/****************************************************************
	 * onScannedRobot: What to do when you see another robot
	 ****************************************************************/
	public void onScannedRobot(ScannedRobotEvent e) {
		//GET EM!!
		fire(3);
		//NOW RUN AWAY!!
		goInCircle();
	}
	
	/****************************************************************
	 * onHitRobot: What to do when you collide with another robot
	 ****************************************************************/
	public void onHitRobot(HitRobotEvent e){
		//change direction
		clockwise = !clockwise;
		//get out of there
		goTo(previousX,previousY);
		goInCircle();
	}

	
	/*********************************************************************
	 * onHitWall: What to do when you hit a wall
	 *********************************************************************/
	public void onHitWall(HitWallEvent e) {
		findClosestCorner();
	}

	/*********************************************************************
	 * Moves the robot along the circle in the specified direction
	 *********************************************************************/
	public void goInCircle(){
		if(clockwise){
			goClockWise();
		}
		else{
			goCounterClockWise();
		}
	}

	/***********************************************************************
	 * Moves the robot in a clockwise rotation around the map
	 **********************************************************************/
	public void goClockWise(){
		int x = (int) getX();
		x = roundToNearestHundred(x);
		int y = (int) getY();
		y = roundToNearestHundred(y);
		if(x == DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL, getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		else if(x == DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL,
					getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		else if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL,DISTANCE_FROM_WALL);
		}
		else if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL, DISTANCE_FROM_WALL);
		}
		else{
			findClosestCorner();
		}
	}	

	/***********************************************************************
	 * Moves the robot in a counter-clockwise rotation around the map
	 **********************************************************************/
	public void goCounterClockWise(){
		int x = (int) getX();
		x = roundToNearestHundred(x);
		int y = (int) getY();
		y = roundToNearestHundred(y);

		if(x == DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL, DISTANCE_FROM_WALL);
		}
		else if(x == DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL, DISTANCE_FROM_WALL);
		}
		else if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL,
					getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		else if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL,
					getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		else{
			findClosestCorner();
		}
	}

	/*******************************************************************
	 * Finds the corner the robot is closest to and moves there
	 *******************************************************************/
	private void findClosestCorner(){
		double xCoordinate = getX();
		double yCoordinate = getY();
		double arenaWidth = getBattleFieldWidth();
		double arenaHeight = getBattleFieldHeight();
		double xDestination = DISTANCE_FROM_WALL;
		double yDestination = DISTANCE_FROM_WALL;
		previousX = arenaWidth - DISTANCE_FROM_WALL;
		previousY = arenaHeight - DISTANCE_FROM_WALL;
		if(xCoordinate >= arenaWidth / 2){
			xDestination = arenaWidth - DISTANCE_FROM_WALL;
			previousX = DISTANCE_FROM_WALL;
		}
		if(yCoordinate >= arenaHeight /2){
			yDestination = arenaHeight - DISTANCE_FROM_WALL;
			previousY = DISTANCE_FROM_WALL;
		}
		previousX = xCoordinate;
		previousY = yCoordinate;
		goTo(xDestination,yDestination);
	}

	/*******************************************************************
	 * Moves the robot to a specific coordinate on the map
	 *******************************************************************/
	private void goTo(double destinationX, double destinationY) {
		previousX = getX();
		previousY = getY();
		destinationX -= getX();
		destinationY -= getY();
		double angle = robocode.util.Utils.normalRelativeAngle(Math.atan2(destinationX, destinationY) - Math.toRadians(getHeading()) );
		double turnAngle = Math.atan(Math.tan(angle));
		turnRight(Math.toDegrees(turnAngle));
		ahead(Math.hypot(destinationX, destinationY) * (angle == turnAngle ? 1 : -1));
	}

	/*********************************************************************
	 * Rounds an integer to the nearest hundred
	 *********************************************************************/
	public int roundToNearestHundred(int n){
		return (n + 50) / 100 * 100;
	}

	/********************************************************************
	 * Set a new color for the robot
	 ********************************************************************/
	public void randomColors(){
		//get new color
		if(color < 9)
			color++;
		else
			color = 0;		

		//set new color
		if(color == 0){
			setAllColors(Color.white);
		}else if(color == 1){
			setAllColors(Color.black);
		}else if(color == 2){
			setAllColors(Color.red);
		}else if(color == 3){
			setAllColors(Color.orange);
		}else if(color == 4){
			setAllColors(Color.yellow);
		}else if(color == 5){
			setAllColors(Color.green);
		}else if(color == 6){
			setAllColors(Color.blue);
		}else if(color == 7){
			setAllColors(Color.cyan);
		}else if(color == 8){
			setAllColors(Color.pink);
		}else if(color == 9){
			setAllColors(Color.magenta);
		}
	}
}