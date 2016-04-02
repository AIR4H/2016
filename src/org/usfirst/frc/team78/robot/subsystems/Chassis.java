package org.usfirst.frc.team78.robot.subsystems;



import org.usfirst.frc.team78.robot.Robot;
import org.usfirst.frc.team78.robot.RobotMap;
import org.usfirst.frc.team78.robot.commands.DriveWithJoysticks;

//import com.kauailabs.navx.frc.AHRS;




import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */

public class Chassis extends Subsystem {
	
	//MOTORS
	Victor leftDrive1 = new Victor(RobotMap.LEFT_DRIVE_1);
	Victor rightDrive1 = new Victor(RobotMap.RIGHT_DRIVE_1);
	Victor leftDrive2 = new Victor(RobotMap.LEFT_DRIVE_2);
	Victor rightDrive2 = new Victor(RobotMap.RIGHT_DRIVE_2);
	
	//SENSORS
	//AnalogGyro gyro = new AnalogGyro(RobotMap.GYRO);
	Encoder leftEnc = new Encoder(RobotMap.LEFT_ENC_A, RobotMap.LEFT_ENC_B);
	Encoder rightEnc = new Encoder(RobotMap.RIGHT_ENC_A, RobotMap.RIGHT_ENC_B);
	AnalogInput ultrasonic = new AnalogInput(1);
	AHRS ahrs = new AHRS(SPI.Port.kMXP);
		//download from here http://www.pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/
		//install it all
		//Window->Preferences->Java->Build Path->Classpath Variables->New
		//make a new variable called navx-mxp from <HomeDirectory>\navx-mxp\java\lib\navx_frc.jar
		//right click Referenced Libraries, add the new variable
	

	
	//VARIABLES
	public Boolean timerStart = false;
	public boolean atTarget = false;
	public boolean noGoal;
	Boolean onObstacle = false;
	public boolean onDefense = false;
	public boolean overDefense = false;
	
	
	//CONSTANTS
	//final double GYRO_P = (.003);	//test bot
	//final double GYRO_P = (.017);	//Pre 15March value
	final double GYRO_P = (.025);//.003; test bot
	final double DISTANCE_P = 0.00035;
	final double VISIONX_GOAL = 22;//TUNE
	final double VISIONY_GOAL = 200;
	final double PIXELS_TO_ANGLE = 9.5969;
	final double DEFENSE_GYRO = 4;
	final double PLATFORM_GYRO = 4;
	public final double MAX_TURN_SPEED = .4;
	public final double MIN_TURN_SPEED =.15;


	
	//TIMER
	public Timer timer = new Timer();
	
	
	
	//TEST VARIABLES
	public boolean didTurnStart = false;
	public double testAngle;
	
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new DriveWithJoysticks());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
 //______________________________________________________________________________   
 //DRIVE METHODS
    
    public void driveWithJoysticks(){
    	
    	double left = Robot.oi.getDriverLeftStick();
    	double right = Robot.oi.getDriverRightStick();
    	
    	if (Robot.oi.driverStick.getRawButton(7)){
    		setSpeed(left*0.5, right*0.5);
    	}
    	else if (Robot.oi.driverStick.getRawButton(8)){
    		setSpeed(left, right);
    	}
    	else{
    		setSpeed(left*.78, right*.78);
    	}
    }
    
    public void setSpeed(double left, double right){
    	leftDrive1.set(left);
    	rightDrive1.set(-right);
    	leftDrive2.set(left);
    	rightDrive2.set(-right);
    }
    
    public void stopAllDrive(){
    	setSpeed(0,0);
    }
    
    
    public void setTurnSpeed(double speed){
    	setSpeed(speed, -speed);
    }
    
    
 //______________________________________________________________________________ 
 //AUTO METHODS  
    
    public double driveStraightDistance(double distance){
    	double distanceError = (distance - ((getRightEnc()))); //+ getLeftEnc()) / 2));
    	double speed = distanceError*DISTANCE_P;
    	
    	
    	if (speed < .25 && speed > 0){
    		speed = .25;
    	}
    	else if(speed > -.25 && speed < 0){
    		speed = -.25;
    	}
    	
    	return speed;
    	
    }//end driveStraightDistance
    
    
    public double headingCorrection (double heading){
    	double driftError = heading - getAngle();
    	
    	if (driftError < -180){
    		driftError = driftError + 360;
    	}
    	else if (driftError > 180){
    		driftError = driftError - 360;
    	}
    	
    	
    	return ((GYRO_P)*driftError);
    	
    }//end headingCorrection
    
    
    public double turnAngleAdditional(double target){
    	double speed;

    	speed = headingCorrection(target)*(.22222222);//So that turns aren't as aggressive as heading correction. Makes constant effectively .005555
    	
    	if (speed > MAX_TURN_SPEED){
    		speed = MAX_TURN_SPEED;
    	}
    	if(speed < -MAX_TURN_SPEED){ 
    		speed = -MAX_TURN_SPEED;
    	}
    	
    	if (speed < MIN_TURN_SPEED && speed > 0){
    		speed = MIN_TURN_SPEED;
    	}
    	if(speed > -MIN_TURN_SPEED && speed < 0){ 
    		speed = -MIN_TURN_SPEED;
    	}
    	
    	return speed;
    	//setTurnSpeed(speed);
    }
   
    public double fastVision(){
    	double rightLeft;
    	
    	if(Robot.vision.getVisionX() < VISIONX_GOAL){
    		rightLeft = (-1);
    	}
    	else{
    		rightLeft = (1);
    	}
    	
    	return rightLeft;
    }

    public double getGyroVisionTarget(){
    	double pixelError = (VISIONX_GOAL) - Robot.vision.getVisionX();
    	double gyroAngle = (pixelError/PIXELS_TO_ANGLE)*(-1);
    	//gyroAngle = gyroAngle*(-1);
    	testAngle = gyroAngle;
    	return gyroAngle;//when positive, need to turn right. same as set turn speed
    }
    
    
//_________________________________________________________________________________________________________________________________________
//LOGIC METHODS
    
    public boolean isAtTurnTarget(double target){
    	atTarget = false;
    	
    	double error = target - getAngle();
    	
    	if (error < -180){
    		error = error + 360;
    	}
    	else if (error > 180){
    		error = error - 360;
    	}

    	if ((error < 2) && (error > -2)){
    		if(timerStart == false){
   				timerStart = true;
   				timer.start();
   			}
    		
   		}
   	
   		else{
   		
   			if(timerStart == true){
    			timer.stop();
    			timer.reset();
    			timerStart = false;
   			}
   		}
    	
   		if(timer.get() >.17){
   			atTarget = true;
    	}
    	
    	return atTarget;
    	
    }// end isAtTurnTarget
    
    public boolean isAtDistanceTarget(double target){
    	boolean atTarget = false;
    	double current = (getLeftEnc()); //+ getRightEnc())/2;
    	
    	if (current < (target+75) && current > (target-75)){
    		if(timerStart == false){
    			timerStart = true;
    			timer.start();
    		}
    		
    	}
    	
    	else{
    		
    		if(timerStart == true){
    			timer.stop();
    			timer.reset();
    			timerStart = false;
    		}
    	}
    	
    	if(timer.get() >.25){
    		atTarget = true;
    
    	}
    	
    	return atTarget;
    	
    }// end isAtDistanceTarget
    

    public boolean isAtVisionDistance(){
    	double current = Robot.vision.getVisionY();
    	
    	if (current == -1000){
    		noGoal = true;
    	}
    	
    	if ((current < (VISIONY_GOAL + 15)) && (current > (VISIONY_GOAL - 15))){
    		if(timerStart == false){
   				timerStart = true;
   				timer.start();
   			}
    		
   		}
   	
   		else{
   		
   			if(timerStart == true){
    			timer.stop();
    			timer.reset();
    			timerStart = false;
   			}
   		}
    	
   		if(timer.get() >.15){
   			atTarget = true;
    	}
    	
    	return atTarget;
    	
    }// end isAtDistanceTarget
    
    
    public boolean isAtVisionHeading(){
    	
    	double current = Robot.vision.getVisionX();
    	
    	if (current == -1000){
    		noGoal = true;
    	}
    	
    	if ((current < (VISIONX_GOAL + 5)) && (current > (VISIONX_GOAL - 5))){
    		if(timerStart == false){
   				timerStart = true;
   				timer.start();
   			}
    		
   		}
   	
   		else{
   		
   			if(timerStart == true){
    			timer.stop();
    			timer.reset();
    			timerStart = false;
   			}
   		}
    	
   		if(timer.get() >.1){
   			atTarget = true;
    	}
    	
    	return atTarget;
    	
    }// end isAtTurnTarget

    
    
    public Boolean isOverDefense(){ //IN COMMAND, NEED TO RESET ON AND OVER DEFENSE	TIMER
    	
    	boolean over = false;
    	double current = getRoll();
    	
    	if(current >= DEFENSE_GYRO){
    		onDefense = true;
    	}
    	
    	if(current < 3 && current > -3 && onDefense){
    		overDefense = true;
    	}
    	else{
    		overDefense = false;
    	}
    	
    	if (overDefense){
    		if(timerStart == false){
   				timerStart = true;
   				timer.start();
   			}
    		
   		}
   	
   		else{
   		
   			if(timerStart == true){
    			timer.stop();
    			timer.reset();
    			timerStart = false;
   			}
   		}
    	
   		if(timer.get() >.3){
   			over = true;
    	}
    	
    	return over;
    }
    
    public Boolean isOnRamp(){
    	boolean ramp = false;
    	double current = getRoll();
    	
    	if(current >= PLATFORM_GYRO){
    		ramp = true;
    	}
    	
    	return ramp;
    }
    
 //______________________________________________________________________________ 
 //SENSOR METHODS  
  
   // public double getGyro(){
    	//return gyro.getAngle();
    //}
    
    public void resetSensorData(){
    	//gyro.reset();
    	leftEnc.reset();
    	rightEnc.reset();
    	ahrs.reset();
    	ahrs.resetDisplacement();
  
    	Timer.delay(.25);
    }
    
    public void resetEncs(){
    	leftEnc.reset();
    	rightEnc.reset();
    }
    
    public double getLeftEnc(){
    	return leftEnc.getRaw();
    }
    
    public double getRightEnc(){
    	return -rightEnc.getRaw();
    }
    
    public double getUltra(){
    	return ((double)ultrasonic.getValue() - 20.0)/120.0;//converts to feet, established through test distances
    }
    
    
    public double getAngle(){
    	return ahrs.getAngle();
    }
    
    public double getPitch(){
    	return ahrs.getPitch();//just look at all the different gets, figure out what is going on
    }
    
    public double getRoll(){
    	return ahrs.getRoll();//just look at all the different gets, figure out what is going on
    }
    
    public float getDisplacementX(){
    	return ahrs.getDisplacementX();//just look at all the different gets, figure out what is going on
    }
    
    public float getDisplacementY(){
    	return ahrs.getDisplacementY();//just look at all the different gets, figure out what is going on
    }
    
    public float getDisplacementZ(){
    	return ahrs.getDisplacementZ();//just look at all the different gets, figure out what is going on
    }
    
    public boolean robotLevel(){
    	if(Math.abs(getRoll()) > 2){
    		return false;
    	}else{
    		return true;
    	}
    		
    }
    
    public boolean RollOverDefense(double start){
    	boolean startGoOver = false;
    	boolean finalBool = false;
    	if(Math.abs(start-getRoll()) > 0.3){
    		startGoOver = true; 
    	}
    	if((Math.abs(start-getRoll()) < 0.3) && startGoOver){
    		timer.reset();
    		timer.start();
    		if(timer.get() > 0.5){
    			finalBool = true;
    		}
    	}
    	return finalBool;
    }
    
}
