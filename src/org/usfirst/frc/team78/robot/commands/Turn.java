package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn extends Command {

	double m_angle;
	double startAngle;
	double target;
	double speed;
	
    public Turn(double angle) {;
    	requires(Robot.chassis);
    	 m_angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = Robot.chassis.turnAngleAdditional((m_angle));
    	
    	Robot.chassis.setTurnSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.chassis.isAtTurnTargetFine(m_angle);
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
