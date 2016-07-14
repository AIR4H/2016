package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveOverDefense extends Command {

	double startHeading;
	double rightSpeed;
	double leftSpeed;
	double m_speed;
	
	
    public DriveOverDefense(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startHeading = Robot.chassis.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	leftSpeed = m_speed;
    	rightSpeed = m_speed;
    	
    	leftSpeed = leftSpeed + Robot.chassis.headingCorrection(startHeading);
    	rightSpeed = rightSpeed - Robot.chassis.headingCorrection(startHeading);
    	
    	
    	Robot.chassis.setSpeed(leftSpeed, rightSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.chassis.isOverDefense();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stopAllDrive();
    	Robot.chassis.timerStart = false;
    	Robot.chassis.atTarget = false;
    	Robot.chassis.timer.stop();
    	Robot.chassis.timer.reset();
    	Robot.chassis.onDefense = false;
    	Robot.chassis.overDefense = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
