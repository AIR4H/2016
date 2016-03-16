package org.usfirst.frc.team78.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team78.robot.commands.AlternateIntake;
import org.usfirst.frc.team78.robot.commands.AntiIntake;
import org.usfirst.frc.team78.robot.commands.AntiIntakeGroup;
import org.usfirst.frc.team78.robot.commands.DefaultIntake;
import org.usfirst.frc.team78.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team78.robot.commands.DriveTime;
import org.usfirst.frc.team78.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team78.robot.commands.HeadingCorrection;
import org.usfirst.frc.team78.robot.commands.Intake;
import org.usfirst.frc.team78.robot.commands.IntakeGroup;
import org.usfirst.frc.team78.robot.commands.MoveIntake;
import org.usfirst.frc.team78.robot.commands.MovePancake;
import org.usfirst.frc.team78.robot.commands.VisionTurnAuto;
import org.usfirst.frc.team78.robot.commands.PortCoooolis;
import org.usfirst.frc.team78.robot.commands.PunchPancake;
import org.usfirst.frc.team78.robot.commands.AlternateShooter;
import org.usfirst.frc.team78.robot.commands.MoveShooter;
import org.usfirst.frc.team78.robot.commands.ReadyShoot;
import org.usfirst.frc.team78.robot.commands.ResetSensors;
import org.usfirst.frc.team78.robot.commands.RumbleTest;
import org.usfirst.frc.team78.robot.commands.SetIntakeSpeed;
import org.usfirst.frc.team78.robot.commands.SetShooterRate;
import org.usfirst.frc.team78.robot.commands.SetShooterSpeed;
import org.usfirst.frc.team78.robot.commands.StUCK;
import org.usfirst.frc.team78.robot.commands.StopShooter;
import org.usfirst.frc.team78.robot.commands.TestCommand;
import org.usfirst.frc.team78.robot.commands.Turn;
import org.usfirst.frc.team78.robot.commands.TurnAdditional;
import org.usfirst.frc.team78.robot.commands.VisionSnapshot;
import org.usfirst.frc.team78.robot.commands.VisionTurnDriver;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//JOYSTICKS
	public static Joystick driverStick;
	public static Joystick manipulatorStick;
	public static Joystick tStick;
	public Joystick weekZeroMStick;
	
	
	//DRIVER BUTTONS
	public Button btn1;
	public Button btn2;
	public Button btn3;
	public Button btn4;
	public Button btn5;
	public Button btn6;
	public Button btn7;
	public Button btn8;
		
	//MANIPULATOR BUTTONS
	public Button manShooterMid;
	public Button manPnPancake;
	public Button manShooterHigh;
	public Button manPnIntake;
	public Button manPnShooter;
	public Button manIntake;
	
	//TEST STICK
	public Button btn1T;
	public Button btn2T;
	public Button btn3T;
	public Button btn4T;
	public Button btn5T;
	public Button btn6T;
	public Button btn7T;
	public Button btn8T;
	public Button btn9T;
	
	//WEEK ZERO MISTAKES STICK
	public Button btn1W;
	public Button btn2W;
	public Button btn3W;
	public Button btn4W;
	public Button btn5W;
	public Button btn6W;
	public Button btn7W;
	public Button btn8W;
	public Button btn9W;
	
	
	
	//CONSTANTS
	final static double STICK_DEADZONE = 0.05;
	
	
	
	
	
	public OI(){
		driverStick = new Joystick(0);
		manipulatorStick = new Joystick(1);
		tStick = new Joystick(2);
		weekZeroMStick = new Joystick(3);
		
		
		
		
		
		
		// DRIVER BUTTONS
		btn6 = new JoystickButton(driverStick, 6);
		btn6.whileHeld(new PortCoooolis());
		
		btn5 = new JoystickButton(driverStick, 5);
		btn5.whileHeld(new VisionTurnDriver());
		//btn5.whenReleased(new RumbleTest(0));
		
		
//__________________________________________________________________________________________________________________________________
//WEEK ZERO WEIRD TEMPORARY BUTTONS
		
		/*btn1W = new JoystickButton(weekZeroMStick, 1);
		btn1W.whenPressed(new Intake());
		btn1W.whenReleased(new SetShooterSpeed(0));
		
		btn6W = new JoystickButton(weekZeroMStick, 6);
		btn6W.whenPressed(new MoveShooter("up"));
		btn6W.whenReleased(new MoveShooter("down"));
		
		btn5W = new JoystickButton(weekZeroMStick, 5);
		btn5W.whenPressed(new AlternateIntake());
		
		btn4W = new JoystickButton(weekZeroMStick, 4);
		btn4W.whenPressed(new ReadyShoot());
		btn4W.whenReleased(new AntiReadyShoot());
					
		btn3W = new JoystickButton(weekZeroMStick, 3);
		btn3W.whenPressed(new PunchPancake());
		
		btn2W = new JoystickButton(weekZeroMStick, 2);
		btn2W.whenPressed(new ReadyShoot90());
		btn2W.whenReleased(new AntiReadyShoot());
		
		btn7W = new JoystickButton(weekZeroMStick, 7);
		btn7W.whileHeld(new StUCK());
		
		btn8W = new JoystickButton(weekZeroMStick, 8);
		btn8W.whenPressed(new LowGoal());
		btn8W.whenReleased(new SetShooterSpeed(0));*/
		
		
		//btn6W = new JoystickButton(weekZeroMStick, 6);
		//btn6W.whenPressed(new SetIntakeSpeed(.5));
		
		
		//toggle intake
		//toggle shooter
		//spin up 
		//fire
		
		
		
//__________________________________________________________________________________________________________________________________________
		
		//*****************************************
		//NEED TO FIGURE OUT BUTTON NUMBERS ON XBOX STICK
		//*****************************************
		
		// MANIPULATOR BUTTONS
		manPnIntake = new JoystickButton(manipulatorStick, RobotMap.INTAKE_PN);
		manPnIntake.whenPressed(new MoveIntake("down"));
		manPnIntake.whenReleased(new MoveIntake("up"));
		
		manIntake = new JoystickButton(manipulatorStick, 3);
		manIntake.whenPressed(new IntakeGroup());
		manIntake.whenReleased(new AntiIntakeGroup());
		
		manPnShooter = new JoystickButton(manipulatorStick, RobotMap.SHOOTER_PN);
		manPnShooter.whenPressed(new MoveShooter("up"));
		manPnShooter.whenReleased(new MoveShooter("down"));
		
		manShooterHigh = new JoystickButton(manipulatorStick, RobotMap.SHOOTER_HIGH_BTN); 
		manShooterHigh.whileHeld(new SetShooterSpeed(1)); // This may need to be changed to while held
		manShooterHigh.whenReleased(new StopShooter());
		
		manPnPancake = new JoystickButton(manipulatorStick, RobotMap.PANCAKE_PN);
		manPnPancake.whenPressed(new MovePancake("out"));
		manPnPancake.whenReleased(new MovePancake("in"));
				
		manShooterMid = new JoystickButton(manipulatorStick, RobotMap.SHOOTER_MID_BTN);
		manShooterMid.whenPressed(new SetShooterSpeed(.67));
		manShooterMid.whenReleased(new StopShooter());
				
		
		/*btn5M = new JoystickButton(manipulatorStick, 5);
		btn5M.whenPressed(new AlternateIntake());
		
		btn6M = new JoystickButton(manipulatorStick, 6);
		btn6M.whenPressed(new AlternateShooter());
		
		btn8M = new JoystickButton(manipulatorStick, 8);
		btn8M.whileHeld(new LowGoal());
		btn8M.whenReleased(new SetShooterSpeed(0));
		//btn8M.whenReleased(new SetShooterSpeed(0));
		
		//btn9M = new JoystickButton(manipulatorStick, 9);
		//btn9M.whenPressed(new ResetSensors());*/
		
		
//__________________________________________________________________________________________________________________________________________
	
		/*btn1T = new JoystickButton(tStick, 1);
		btn1T.whenPressed(new AlternateIntake());*/
		
		btn2T = new JoystickButton(tStick, 2);
		btn2T.whenPressed(new VisionTurnAuto());
		
		
		
		
		
	}
	
	///DRIVER STICK
	public double getDriverLeftStick() {
		double stick = driverStick.getY();
		if (Math.abs(stick) < STICK_DEADZONE){
			return 0;
		}
		else
			return -stick;
	}
	
	public double getDriverRightStick() {
		double stick = driverStick.getThrottle();
		if (Math.abs(stick) < STICK_DEADZONE){
			return 0;
		}
		else
			return -stick;
	}
	
	public double getManipulatorStick() {
		double stick = manipulatorStick.getRawAxis(1);
		if (Math.abs(stick) < STICK_DEADZONE){
			return 0;
		}
		else
			return -stick;
	}
	
	public boolean isStickPushed(){
		boolean state;
		if(getManipulatorStick() > STICK_DEADZONE || getManipulatorStick() < STICK_DEADZONE){
			state = true;
		}
		else{
			state = false;
			
		}
		return state;
	}
	
	public boolean isTriggerPushed(){
		boolean state;
		if(Math.abs(manipulatorStick.getRawAxis(3)) > STICK_DEADZONE) return true;
		else return false;
	}
	
}

