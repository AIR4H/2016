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
import edu.wpi.first.wpilibj.Relay;
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
	
	//Solenoids
	DoubleSolenoid brake = new DoubleSolenoid(RobotMap.BRAKE_FOREWARD, RobotMap.BRAKE_REVERSE);
	
	//Relays
	Relay spike = new Relay(RobotMap.FLASHLIGHT);

	
	//VARIABLES
	public Boolean timerStart = false;
	public boolean atTarget = false;
	public boolean noGoal;
	Boolean onObstacle = false;
	public boolean onDefense = false;
	public boolean overDefense = false;
	public boolean brakeState = false;
	public double counts = 0;
	public boolean lightState = false;
	
	
	//CONSTANTS
	//final double GYRO_P = (.003);	//test bot
	//final double GYRO_P = (.017);	//Pre 15March value
	final double GYRO_P = (.025);//.003; test bot
	final double DISTANCE_P = 0.00035;
	final double VISIONX_GOAL = 0.0005;//TUNE -20
	final double VISIONY_GOAL = 200;
	final double PIXELS_TO_ANGLE = 9.5969;
	final double DEFENSE_GYRO = 1;
	final double PLATFORM_GYRO = 2.5;
	public final double MAX_TURN_SPEED = .8;
	public final double MIN_TURN_SPEED =.6;


	
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
    	
    	if (Robot.oi.driverStick.getRawButton(5)){
    		setSpeed(left*0.4, right*0.4);
    	}
    	/*else if (Robot.oi.driverStick.getRawButton(7) && Robot.oi.driverStick.getRawButton(8)){
    		setSpeed(left, right);
    	}*/
    	else{
    		setSpeed(left, right);
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
    
    
    public void setTurnSpeed(double speed){//positive is a right turn
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
    	
    	if (speed > .5){
    		speed = .5;
    	}
    	else if(speed < -.5){
    		speed = -.5;
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

    	speed = headingCorrection(target);//.355555So that turns aren't as aggressive as heading correction. Makes constant effectively .005555
    	
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
   
    public int fastVision(){
    	int rightLeft;
    	
    	if(Robot.vision.getVisionX() < VISIONX_GOAL){
    		rightLeft = (-1);
    	}
    	else{
    		rightLeft = (1);
    	}
    	
    	return rightLeft;
    }

    public double getGyroVisionTarget(){
    	double pixelError = Robot.vision.getVisionX() - VISIONX_GOAL;
    	double gyroAngle = (pixelError/PIXELS_TO_ANGLE);
    	//testAngle = gyroAngle;
    	return gyroAngle;//when positive, need to turn right. same as set turn speed
    }
    
    
//_________________________________________________________________________________________________________________________________________
//LOGIC METHODS
                                   
    public boolean isAtTurnTargetRough(double target){
    	atTarget = false;
    	
    	double error = target - getAngle();
    	
    	if (error < -180){
    		error = error + 360;
    	}
    	else if (error > 180){
    		error = error - 360;
    	}

    	if ((error < 4) && (error > -4)){
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
    	
    }// end isAtTurnTargetRough
    
    public boolean isAtTurnTargetFine(double target){
    	atTarget = false;
    	
    	double error = target - getAngle();
    	
    	if (error < -180){
    		error = error + 360;
    	}
    	else if (error > 180){
    		error = error - 360;
    	}

    	if(Math.abs(error) < 2){
    		atTarget = true;
    	}
    	
    	return atTarget;
    	
    }// end isAtTurnTargetFine
    
    public boolean isAtDistanceTarget(double target){
    	boolean atTarget = false;
    	double current = (getRightEnc()); //+ getRightEnc())/2;
    	double error = target - current;
    	
    	if(Math.abs(error) < 30){
    		atTarget = true;
    	}
    	
    	/*if (current < (target+75) && current > (target-75)){
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
    
    	}*/
    	
    	return atTarget;
    	
    }// end isAtDistanceTarget
    
    
    public boolean isAtVisionHeading(){
    	
    	double current = Robot.vision.getVisionX();
    	
    	if (current == -1000){
    		noGoal = true;
    	}
    	
    	if ((current < (VISIONX_GOAL + 13)) && (current > (VISIONX_GOAL - 13))){
    		atTarget = true;
    	}
    	
    	/*if ((current < (VISIONX_GOAL + 13)) && (current > (VISIONX_GOAL - 13))){
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
    	}*/
    	
    	return atTarget;
    	
    }// end isAtTurnTarget

    public Boolean isOverCounterDefense(){
    	Boolean over = false;
    	double current = getRoll();
    	
    	if(current < -DEFENSE_GYRO){
    		onDefense = true;
    	}
    	
    	if(current < 2 && current > -5 && onDefense){
    		counts = counts + 1;
    	}
    	
    	if(counts > 8888){//need to figure out the big number
    		over = true;
    	}
    		
    	
    	return over;
    }
    
    
    public Boolean isOverDefense(){
    	
    	boolean over = false;
    	double current = getRoll();
    	
    	if(Math.abs(current) >= 10){//check for pitch down
    		onDefense = true;
    	}
    	
    	if(current < 6 && current > -6 && onDefense){
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
    	
   		if(timer.get() >.5){
   			over = true;
    	}
    	
    	return over;
    }
  /*  
    public Boolean getIsOverDefense(){
    	
    	return isOverDefense();
    }*/
    
    public Boolean isOnRamp(){
    	boolean ramp = false;
    	double current = getRoll();
    	
    	if(Math.abs(current) >= PLATFORM_GYRO){
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
    	return -leftEnc.getRaw();
    }
    
    public double getRightEnc(){
    	return rightEnc.getRaw();
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
    	return -ahrs.getRoll();//just look at all the different gets, figure out what is going on
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
    
   
    /*public boolean robotLevel(){
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
    }*/
    
//______________________________________________________________________________ 
//HAND BRAKE
    public void brakeDown(){
    	brake.set(DoubleSolenoid.Value.kForward);
    	brakeState = true;
    }
    
    public void brakeUp(){
    	brake.set(DoubleSolenoid.Value.kReverse);
    	brakeState = false;
    }

//______________________________________________________________________________ 
//FLASHLIGHT
    public void lightOn(){
    	spike.set(Relay.Value.kForward);
    	lightState = true;
    }
    
    public void lightOff(){
    	spike.set(Relay.Value.kOff);
    	timer.delay(.1);
    	spike.set(Relay.Value.kForward);
    	timer.delay(.1);
    	spike.set(Relay.Value.kOff);
    	timer.delay(.1);
    	spike.set(Relay.Value.kForward);
    	timer.delay(.1);
    	spike.set(Relay.Value.kOff); 
    	lightState = false;
    }

}



