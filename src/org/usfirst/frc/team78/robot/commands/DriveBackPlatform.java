package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveBackPlatform extends Command {

	double startHeading;
	double rightSpeed;
	double leftSpeed;
	double m_speed;
	
	
    public DriveBackPlatform() {
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
    	leftSpeed = -.2;
    	rightSpeed = -.2;
    	
    	leftSpeed = leftSpeed + Robot.chassis.headingCorrection(startHeading);
    	rightSpeed = rightSpeed - Robot.chassis.headingCorrection(startHeading);
    	
    	
    	Robot.chassis.setSpeed(leftSpeed, rightSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.chassis.isOnRamp();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stopAllDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
