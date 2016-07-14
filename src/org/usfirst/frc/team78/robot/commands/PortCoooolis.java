package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PortCoooolis extends Command {
	double m_speed = .2;

    public PortCoooolis() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.setIntakeSpeed(m_speed);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_speed = m_speed + .02;
    	
    	Robot.intake.setIntakeSpeed(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopIntake();
    	m_speed = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();  
    }
}
