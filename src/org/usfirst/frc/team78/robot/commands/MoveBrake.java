package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveBrake extends Command {

	String m_direction;
	
    public MoveBrake(String direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.shooter);
    	
    	m_direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	//	Robot.brake.brakeDown();
    		

    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.chassis.brakeDown();
    	
    	if(m_direction == "up"){
    		Robot.chassis.brakeUp();
    	}
    	else if(m_direction == "down"){
    		Robot.chassis.brakeDown();;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    //Robot.brake.brakeUp();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
