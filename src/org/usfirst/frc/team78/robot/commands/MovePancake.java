package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MovePancake extends Command {

	String m_direction;
	
    public MovePancake(String direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.shooter);
    	m_direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if(m_direction == "out"){
    		Robot.shooter.pancakeOut();
    	}
    	else if(m_direction == "in"){
    		Robot.shooter.pancakeIn();
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
