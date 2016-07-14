package org.usfirst.frc.team78.robot.subsystems;

import org.usfirst.frc.team78.robot.Robot;
import org.usfirst.frc.team78.robot.RobotMap;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class Vision extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
    double X = 0;
	
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand();
    }
    
    
    /*public void moveCam(){
    	pan.set((Robot.oi.getCamX()/2) + 0.5);
    	tilt.set((Robot.oi.getCamY()/2) + 0.5);
    }*/
    
    public double getVisionX(){

    	try {
    		X = Robot.table.getDouble("X");
    	} catch ( Exception e){
    		X = -1000;
    	}
    	return X;

    }
    
    public double getVisionY(){
    	//return Robot.table.getDouble("Y");
    	double Y = 0;
    	try {
    		Y = Robot.table.getDouble("Y");
    	} catch ( Exception e){
    		return -1000;
    	}
    	return Y;
    
    
    
    
    }

}

