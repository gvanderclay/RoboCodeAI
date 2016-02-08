package RoboCodeAI;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * RamBeater 
 * @Author: Gage Vander Clay & Mitch Couturier
 */



public class RamBeater extends Robot
{

	private final int DISTANCE_FROM_WALL = 100;
	boolean clockwise;
	double previousX;
	double previousY;

	int color;
	/**
	 * run: RamBeater's default behavior
	 */
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
			//Find the enemy again
			turnGunRight(10);
		}
	}
	
	/**
	 * Set random colors for the robot
	 */
	public void randomColors(){
		if(color < 9)
			color++;
		else
			color = 0;		

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

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(3);
		
		goInCircle();
	}
	
	public void onHitRobot(HitRobotEvent e){
		clockwise = !clockwise;
		goTo(previousX,previousY);
		goInCircle();
	}

	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		findClosestCorner();
	}


	public void goInCircle(){
		if(clockwise){
			goClockWise();
		}
		else{
			goCounterClockWise();
		}
	}

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


	public int roundToNearestHundred(int n){
		return (n + 50) / 100 * 100;
	}

	/*
	 * Finds the corner the robot is closest to and moves there
	 */
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

	/*
	 * Moves the robot to a specific coordinate on the map
	 */
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
}
