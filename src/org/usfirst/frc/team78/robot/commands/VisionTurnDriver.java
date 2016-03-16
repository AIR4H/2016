package org.usfirst.frc.team78.robot.commands;

import org.usfirst.frc.team78.robot.OI;
import org.usfirst.frc.team78.robot.Robot;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionTurnDriver extends Command {

	double speed;
	
    public VisionTurnDriver() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = Robot.chassis.visionTurn();
    	Robot.chassis.setTurnSpeed(speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.chassis.isAtVisionHeading();
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.chassis.stopAllDrive();
    	Robot.chassis.timerStart = false;
    	Robot.chassis.atTarget = false;
    	Robot.chassis.timer.stop();
    	Robot.chassis.timer.reset();
    	//OI.manipulatorStick.setRumble(RumbleType.kLeftRumble, 1);
    	//OI.manipulatorStick.setRumble(RumbleType.kRightRumble, 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
