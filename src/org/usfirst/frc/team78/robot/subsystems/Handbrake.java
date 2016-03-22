package org.usfirst.frc.team78.robot.subsystems;

import org.usfirst.frc.team78.robot.RobotMap;
import org.usfirst.frc.team78.robot.commands.DefaultIntake;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Handbrake extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	
	//Solenoids
	DoubleSolenoid brake = new DoubleSolenoid(RobotMap.BRAKE_FOREWARD, RobotMap.BRAKE_REVERSE);
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new Intake());
    	//setDefaultCommand(new DefaultIntake()); // TODO tim added may need to take out
    }
    

    
    public void brakeDown(){
    	brake.set(DoubleSolenoid.Value.kForward);
    }
    
    public void brakeUp(){
    	brake.set(DoubleSolenoid.Value.kReverse);
    }
    

   
}

//	//handbrake
//DoubleSolenoid brake = new DoubleSolenoid(RobotMap.BRAKE_FOREWARD, RobotMap.BRAKE_REVERSE);