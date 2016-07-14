package org.usfirst.frc.team78.robot.subsystems;

import org.usfirst.frc.team78.robot.RobotMap;
import org.usfirst.frc.team78.robot.commands.DefaultShooter;
import org.usfirst.frc.team78.robot.commands.SetShooterSpeed;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
    

	//MOTORS
	CANTalon rightShooter = new CANTalon(RobotMap.RIGHT_SHOOTER); //change back to these
	CANTalon leftShooter = new CANTalon(RobotMap.LEFT_SHOOTER);
	
	//Victor rightShooter = new Victor(RobotMap.RIGHT_SHOOTER);
	//Victor leftShooter = new Victor(RobotMap.LEFT_SHOOTER);
	
	//Solenoid
	DoubleSolenoid lift = new DoubleSolenoid(RobotMap.SHOOTER_FOREWARD, RobotMap.SHOOTER_REVERSE);
	DoubleSolenoid pan = new DoubleSolenoid(RobotMap.SHOOTER_PAN_FOREWARD, RobotMap.SHOOTER_PAN_REVERSE);
	
	//SENSORS
	//Encoder rightEnc = new Encoder(RobotMap.RIGHT_SHOOTER_ENC_A, RobotMap.RIGHT_SHOOTER_ENC_B);
	//Encoder leftEnc = new Encoder(RobotMap.LEFT_SHOOTER_ENC_A, RobotMap.LEFT_SHOOTER_ENC_B);
	
	//VARIABLES
	public double shooterSpeed = 0;
	public double pComponent;
	public double iComponent = 0;
	public double rateError;
	boolean timerStart = false;
	public boolean shooterPnState = false; //TRUE = UP
	public boolean pancakePnState = false; //TRUE = UP
	
	//CONSTANTS
	final double SHOOTER_P = 0.0000045;
	final double SHOOTER_I = 0.0000009;//.0000037;
	
	//TIMER
	Timer timer = new Timer();

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DefaultShooter()); // TODO Tim added this may need to remove
    }
    
//____________________________________________________________________________________________________________________________
//MOTOR METHODS
    
    public void setShooterSpeed(double speed){
    	rightShooter.set(speed);
    	leftShooter.set(-speed);
    }
    
    public void setShooterCurve(double lspeed, double rspeed){
    	rightShooter.set(rspeed);
    	leftShooter.set(-lspeed);
    }
    
   /* public void setRightShooterRate(double rate){
    	rateError = rate - getRightShooterRate();
    	pComponent = (SHOOTER_P)*rateError;
    	iComponent = (SHOOTER_I)*(rateError + (2/3)*iComponent);
    	//iComponent = rateError + iComponent;
    	
    	
    	shooterSpeed = shooterSpeed + pComponent + iComponent;
    	setShooterSpeed(shooterSpeed);

    }//end setShooterRate*/
    
    public void stopShooter(){
    	setShooterSpeed(0);
    }
    
   /* public boolean isAtShooterRate (double targetRate){
    	boolean atRate = false;
    	
    	double current = getRightShooterRate();
    	
    	if (current < (targetRate+250) && current > (targetRate-250)){
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
    		atRate = true;
    	}
    	return atRate;
    	
    }// end isAtTurnTarget*/
    

    
//____________________________________________________________________________________________________________________________
//SENSOR METHODS
    
    /*public double getRightShooterEnc(){
    	return -rightEnc.getRaw();
    }
    
    public double getRightShooterRate(){
    	return -rightEnc.getRate() * 10;//convert to wheel rpm output
    }
    
    public void resetSensorData(){
    	rightEnc.reset();
    	leftEnc.reset();
    }*/
//____________________________________________________________________________________________________________________________________________
//pneumatic methods
    
    public void shooterDown(){
    	lift.set(DoubleSolenoid.Value.kReverse);
    	shooterPnState = false;
    }
    
    public void shooterUp(){
    	lift.set(DoubleSolenoid.Value.kForward);
    	shooterPnState = true;
    }
    
    
    public void moveShooter(boolean state){
    	if(state == false){
    		lift.set(DoubleSolenoid.Value.kReverse);
    		shooterPnState = false;
    	}
    	else{
    		lift.set(DoubleSolenoid.Value.kForward);
    		shooterPnState = true;
    	}
    }
    
    public void pancakeIn(){
    	pan.set(DoubleSolenoid.Value.kReverse);
    	pancakePnState = false;
    }
    
    public void pancakeOut(){
    	pan.set(DoubleSolenoid.Value.kForward);
    	pancakePnState = true;
    }
    
    
    public void alternatePancake(boolean state){
    	if(state == true){
    		pan.set(DoubleSolenoid.Value.kForward);
    		pancakePnState = true;
    	}
    	else{
    		pan.set(DoubleSolenoid.Value.kReverse);
    		pancakePnState = false;
    	}
    }
    
}

