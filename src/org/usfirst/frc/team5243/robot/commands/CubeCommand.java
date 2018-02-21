package org.usfirst.frc.team5243.robot.commands;

import org.usfirst.frc.team5243.robot.Robot;
import org.usfirst.frc.team5243.robot.subsystems.CubeSubsystem;

import edu.wpi.first.wpilibj.command.Command;


public class CubeCommand extends Command {
	
	CubeSubsystem cubeSubsystem;
	boolean isExtending;
    	
	/**
	 * Constructor for extending or retracting the cube mechanism
	 * @param isEx true if the cube mechanism should be extending, false if the cube mechanism should be retracting
	 */
	public CubeCommand(boolean isEx) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		cubeSubsystem = Robot.cubeSubsystem;
		requires(cubeSubsystem);
		isExtending = isEx;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(isExtending)
    		cubeSubsystem.extend();
    	else 
    		cubeSubsystem.retract();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	cubeSubsystem.stop();
    }
}