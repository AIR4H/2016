package org.usfirst.frc.team78.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team78.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team78.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team78.robot.commands.ExampleCommand;
import org.usfirst.frc.team78.robot.commands.HeadingCorrection;
import org.usfirst.frc.team78.robot.commands.RicksDemands;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//JOYSTICKS
	public Joystick driverStick;
	
	
	//BUTTONS
	public Button btnHoldHeading;
	public Button btnFiveFeet;
	
	
	//CONSTANTS
	final static double STICK_DEADZONE = 0.05;
	
	
	
	
	
	public OI(){
		driverStick = new Joystick(0);
		
		btnHoldHeading = new JoystickButton(driverStick, 2);
		btnHoldHeading.whileHeld(new HeadingCorrection());
		
		btnFiveFeet = new JoystickButton(driverStick, 1);
		btnFiveFeet.whenPressed(new RicksDemands());
		btnFiveFeet.whenReleased(new DriveWithJoysticks());
		
		/*btnFiveFeet = new JoystickButton(driverStick, 4);
		btnFiveFeet.whenPressed(new DriveStraightDistance(5));
		btnFiveFeet.whenReleased(new DriveWithJoysticks());*/
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
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

