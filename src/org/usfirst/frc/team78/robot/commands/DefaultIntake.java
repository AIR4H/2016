package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.Robot;
import org.usfirst.frc.team78.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class DefaultIntake extends Command {
	
	double i_speed;

    public DefaultIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (Math.abs(Robot.oi.getManipulatorStick()) < .15 ){
    		Robot.intake.setIntakeSpeed(0);
    	}
    	else Robot.intake.setIntakeSpeed(Robot.oi.getManipulatorStick() * RobotMap.INTAKE_SPEED);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
