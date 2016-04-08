package org.usfirst.frc.team78.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoHighGoal3 extends CommandGroup {
    
    public  AutoHighGoal3() {
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
    	
    	addSequential(new DriveOverDefense(-.8));//cross the defense. it'll overshoot, stop when it's sure its on flat ground
    	addSequential(new DriveToPlatform(.2));//drive forward slowly back to the platform
    	addSequential(new DriveStraightDistance(-2));//drive back a bit just to get off the outer works so it can turn
    	addSequential(new Turn(165));//roughly face the goal from position 3
    	addSequential(new MoveIntake("down"));//self explanatory but I feel the need to comment on every step
    	addSequential(new DoNothing(.5));//give the intake time to get out of the way
    	addSequential(new VisionSnapshot());//face the goal
    	addSequential(new DriveStraightDistance(5));//get close to the batter quickly. should not hit the batter-tune until correct
    	addSequential(new DriveToPlatform(.2));//drive slowly onto the batter
    	//addSequential(new DriveStraightDistance(-1));//may have to drive back a bit off the batter
    	addSequential(new MoveShooter("up"));//again, self explanatory
    	addSequential(new VisionSnapshot());//line up one more time
    	addSequential(new SetShooterSpeed(.65));//will have to tune, guess for now
    	addSequential(new PunchPancake());//shoot
    	addSequential(new SetShooterSpeed(0));//kill the shooter, prep for teleop
    	addSequential(new MoveShooter("down"));//down again
    	addSequential(new MoveIntake("up"));//ready for teleop
    	
    	
    	
    	
    }
}
