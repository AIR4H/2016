package org.usfirst.frc.team78.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoHighGoal4 extends CommandGroup {
    
    public  AutoHighGoal4() {

    	addSequential(new DriveOverDefense(-.8));//cross the defense. it'll overshoot, stop when it's sure its on flat ground
    	addSequential(new Turn(0));//should be pretty close to straight, just be sure
    	addSequential(new DriveToPlatform(.2));//drive forward slowly back to the platform
    	addSequential(new DriveStraightDistance(-2));//drive back a bit just to get off the outer works so it can turn
    	addSequential(new Turn(-157.5));//roughly face the goal from position 2
    	addSequential(new MoveIntake("down"));//self explanatory but I feel the need to comment on every step
    	addSequential(new DoNothing(.5));//give the intake time to get out of the way
    	addSequential(new DriveStraightDistance(73.3));//get close to the batter quickly. should not hit the batter-tune until correct
    	addSequential(new Turn(180));//face tower
    	addSequential(new DriveToPlatform(.2));//drive slowly onto the batter
    	//addSequential(new DriveStraightDistance(-1));//may have to drive back a bit off the batter
    	addSequential(new MoveShooter("up"));//again, self explanatory
    	addSequential(new VisionSnapshot());//line up
    	addSequential(new SetShooterSpeed(.65));//will have to tune, guess for now
    	addSequential(new PunchPancake());//shoot
    	addSequential(new SetShooterSpeed(0));//kill the shooter, prep for teleop
    	addSequential(new MoveShooter("down"));//down again
    	addSequential(new MoveIntake("up"));//ready for teleop
    	
    }
}
