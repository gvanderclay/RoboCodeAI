package RoboCodeAI;
import robocode.*;
import java.awt.Color;
import java.util.Random;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * RamBeater 
* @author: Gage Vander Clay & Mitch Couturier
 */



public class RamBeater extends Robot
{

	private final int DISTANCE_FROM_WALL = 100;
	boolean clockwise;
	double previousX;
	double previousY;

	Random rn;
	int color;
	/**
	 * run: RamBeater's default behavior
	 */
	public void run() {
		findClosestCorner();
		clockwise = true;
		// Initialization of the robot should be put here
		rn = new Random();
		color = 0;
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:
		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar
		// Robot main loop
		while(true) {
			//Change Color
			randomColors();
			// Replace the next 4 lines with any behavior you would like
			//ahead(100);
			turnGunRight(10);
			//back(100);
			//turnGunRight(360);
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
		// Replace the next line with any behavior you would like
		fire(1);
		
		if(clockwise){
			goClockWise();
		}else{
			goCounterClockWise();
		}
	}
	
	public void HitRobotEvent(ScannedRobotEvent e){
		if(clockwise)
			clockwise = false;
		else
			clockwise = true;
		goTo(previousX,previousY);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}


	public void goClockWise(){
		int x = (int) getX();
		x = roundToNearestHundred(x);
		int y = (int) getY();
		y = roundToNearestHundred(y);
		System.out.println("Ayy: " + x + "," + y);

		if(x == DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL, getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		if(x == DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL,
					getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL,DISTANCE_FROM_WALL);
		}
		if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL, DISTANCE_FROM_WALL);
		}
	}	


	public void goCounterClockWise(){
		int x = (int) getX();
		x = roundToNearestHundred(x);
		int y = (int) getY();
		y = roundToNearestHundred(y);
		System.out.println("Ayy: " + x + "," + y);

		if(x == DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL, DISTANCE_FROM_WALL);
		}
		if(x == DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL, DISTANCE_FROM_WALL);
		}
		if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL &&
				y == getBattleFieldHeight() - DISTANCE_FROM_WALL){
			goTo(DISTANCE_FROM_WALL,
					getBattleFieldHeight() - DISTANCE_FROM_WALL);
		}
		if(x == getBattleFieldWidth() - DISTANCE_FROM_WALL && y == DISTANCE_FROM_WALL){
			goTo(getBattleFieldWidth() - DISTANCE_FROM_WALL,
					getBattleFieldHeight() - DISTANCE_FROM_WALL);
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
		if(xCoordinate >= arenaWidth / 2){
			xDestination = arenaWidth - DISTANCE_FROM_WALL;
		}
		if(yCoordinate >= arenaHeight /2){
			yDestination = arenaHeight - DISTANCE_FROM_WALL;
		}
		System.out.println("current: " + xCoordinate + "," + yCoordinate);
		System.out.println("destination: " + xDestination + "," + yDestination);
		goTo(xDestination,yDestination);
		System.out.println("newcurrent: " + getX() + "," + getY());
	}


/*	private void goTo(double x, double y) {
		double a;
		turnRight(Math.tan(
			a = Math.atan2(x -= getX(), y -= getY())
				  - (getHeading() * (Math.PI / 180))) * (180 / Math.PI));
		ahead(Math.hypot(x, y) * Math.cos(a));
	}*/

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
