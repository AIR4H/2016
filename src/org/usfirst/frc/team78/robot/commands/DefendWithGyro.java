package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DefendWithGyro extends Command {

	double leftStick;
	double rightStick;
	double leftSpeed;
	double rightSpeed;
	double startHeading;
	
    public DefendWithGyro() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startHeading = Robot.chassis.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftStick = Robot.oi.getDriverLeftStick();
    	rightStick = leftStick;
    	
    	if (Robot.oi.driverStick.getRawButton(5)){
    		leftSpeed = leftStick*.5;
    		leftSpeed = rightStick*.5;
    	}
    	else if (Robot.oi.driverStick.getRawButton(7) && Robot.oi.driverStick.getRawButton(8)){
    		leftSpeed = leftStick;
    		rightSpeed = rightStick;
    	}
    	else{
    		leftSpeed = leftStick*.87;
    		rightSpeed = rightStick*.87;
    	}
    
    	leftSpeed = leftSpeed + Robot.chassis.headingCorrection(startHeading);
    	rightSpeed = rightSpeed - Robot.chassis.headingCorrection(startHeading);
    	
    	
    	Robot.chassis.setSpeed(leftSpeed, rightSpeed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stopAllDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
