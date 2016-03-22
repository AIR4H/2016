package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionSnapshot extends Command {

	double angleCorrection;
	double speed;
	
    public VisionSnapshot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angleCorrection = Robot.chassis.getGyroVisionTarget();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = Robot.chassis.turnAngleAdditional(angleCorrection);
    	Robot.chassis.setTurnSpeed(speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.chassis.isAtTurnTarget(angleCorrection);
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
