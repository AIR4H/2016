package org.usfirst.frc.team78.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowGoalLowBar extends CommandGroup {
    
    public  AutoLowGoalLowBar() {

    	addSequential(new MoveIntake("down"));
    	addSequential(new DoNothing(1));
    	addSequential(new DriveStraightDistance(-21));
    	addSequential(new TurnSwing(-120));
    	
    
    	addSequential(new MoveIntake("up"));
    	addSequential(new DoNothing(1));
    	addSequential(new DriveStraightDistance(8));
    	addSequential(new SetShooterSpeed(.9));
    	addSequential(new DoNothing(1));
    	addSequential(new PunchPancake());
    	addSequential(new DoNothing(.6));
    	addSequential(new SetShooterSpeed(0));
    	addSequential(new DriveStraightDistance(-5));
    	
    }
}
