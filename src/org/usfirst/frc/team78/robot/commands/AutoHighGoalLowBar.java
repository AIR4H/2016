package org.usfirst.frc.team78.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoHighGoalLowBar extends CommandGroup {
    
    public  AutoHighGoalLowBar() {

    	addSequential(new MoveIntake("down"));
    	addSequential(new DoNothing(1));
    	addSequential(new DriveStraightDistance(-21));//-21
    	addSequential(new TurnSwing(-122.5));//-120
    	
    	//test high
    	addSequential(new DriveStraightDistance(-1));
    	addSequential(new MoveShooter("up"));
    	addSequential(new DoNothing(1));
    	addSequential(new SetShooterSpeed(.55));
    	addSequential(new DoNothing(1));
    	addSequential(new PunchPancake());
    	addSequential(new DoNothing(.6));
    	addSequential(new SetShooterSpeed(0));
    	
    	/*addSequential(new MoveIntake("up"));
    	addSequential(new DoNothing(1));
    	addSequential(new DriveStraightDistance(7.5));//9
    	addSequential(new SetShooterSpeed(.9));
    	addSequential(new DoNothing(1));
    	addSequential(new PunchPancake());
    	addSequential(new DoNothing(.6));
    	addSequential(new SetShooterSpeed(0));
    	addSequential(new DriveStraightDistance(-5));*/
    	
    }
}
