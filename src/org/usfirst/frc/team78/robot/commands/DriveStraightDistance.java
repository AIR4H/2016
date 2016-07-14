package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistance extends Command {

	double m_distance_clicks;
	double startHeading;
	double rightSpeed;
	double leftSpeed;
	
	
    public DriveStraightDistance(double feet) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    	m_distance_clicks = feet * 300;

    	//setTimeout(17);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetEncs();
    	startHeading = Robot.chassis.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftSpeed = Robot.chassis.driveStraightDistance(m_distance_clicks);
    	rightSpeed = leftSpeed;
    	
    
    	leftSpeed = leftSpeed + Robot.chassis.headingCorrection(startHeading);
    	rightSpeed = rightSpeed - Robot.chassis.headingCorrection(startHeading);
    	
    	
    	Robot.chassis.setSpeed(leftSpeed, rightSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.chassis.isAtDistanceTarget(m_distance_clicks);
    	//return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stopAllDrive();
    	Robot.chassis.timerStart = false;
    	Robot.chassis.atTarget = false;
    	Robot.chassis.timer.stop();
    	Robot.chassis.timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
