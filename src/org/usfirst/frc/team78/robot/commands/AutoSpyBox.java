package org.usfirst.frc.team78.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSpyBox extends CommandGroup {
    
    public  AutoSpyBox() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addSequential(new MoveIntake("down"));
    	addSequential(new DoNothing(.5));
    	addSequential(new MoveShooter("up"));
    	addSequential(new SetShooterSpeed(1));
    	addSequential(new DoNothing(.75));
    	addSequential(new PunchPancake());
    	addSequential(new DoNothing(.75));
    	addSequential(new SetShooterSpeed(0));
    	addSequential(new MoveShooter("down"));
    	addSequential(new MoveIntake("down"));
    	
    	
    }
}
