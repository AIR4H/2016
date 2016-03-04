package org.usfirst.frc.team78.robot.subsystems;

import org.usfirst.frc.team78.robot.Robot;
import org.usfirst.frc.team78.robot.RobotMap;
import org.usfirst.frc.team78.robot.commands.DefaultIntake;
import org.usfirst.frc.team78.robot.commands.SetIntakeSpeed;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//MOTORS
	CANTalon leftIntake = new CANTalon(RobotMap.LEFT_INTAKE);
	CANTalon rightIntake = new CANTalon(RobotMap.RIGHT_INTAKE);
	
	//Solenoids
	DoubleSolenoid lift = new DoubleSolenoid(RobotMap.INTAKE_FOREWARD, RobotMap.INTAKE_REVERSE);
	
	//VARIBALES
	public boolean intakePnState = false;//TRUE = DOWN
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new Intake());
    	setDefaultCommand(new DefaultIntake()); // TODO tim added may need to take out
    }
    
    public void setIntakeSpeed(double speed){
    	leftIntake.set(speed);
    	rightIntake.set(-speed);
    }
    
    public void stopIntake(){
    	setIntakeSpeed(0);
    }
    
    public void intakeDown(){
    	lift.set(DoubleSolenoid.Value.kForward);
    	intakePnState = true;
    }
    
    public void intakeUp(){
    	lift.set(DoubleSolenoid.Value.kReverse);
    	intakePnState = false;
    }
    
    public void alternateIntake(boolean state){
    	if(state == true){
    		lift.set(DoubleSolenoid.Value.kForward);
    		intakePnState = true;
    	}
    	else{
    		lift.set(DoubleSolenoid.Value.kReverse);
    		intakePnState = false;
    	}
    }
   
}

