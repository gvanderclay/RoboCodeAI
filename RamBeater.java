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


	Random rn;
	int color;
	/**
	 * run: RamBeater's default behavior
	 */
	public void run() {
		findClosestCorner();
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

	private void findClosestCorner(){
		double xCoordinate = getX();
		double yCoordinate = getY();
		double arenaWidth = getBattleFieldWidth();
		double arenaHeight = getBattleFieldHeight();
		double xDestination = 63;
		double yDestination = 63;
		if(xCoordinate >= arenaWidth / 2){
			xDestination = arenaWidth - 63;
		}
		if(yCoordinate >= arenaHeight /2){
			yDestination = arenaHeight - 63;
		}

	}
}
