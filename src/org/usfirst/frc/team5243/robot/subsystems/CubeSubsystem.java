package org.usfirst.frc.team5243.robot.subsystems;

import org.usfirst.frc.team5243.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;


public class CubeSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	DoubleSolenoid solenoidCUBE; //clamp piston
	DoubleSolenoid solenoidELEV; //elevator piston
	AnalogInput potentiometer;
	WPI_TalonSRX actuator;
	
	public Compressor compressor;

	
	/**
	 * The cube subsystem that relates to the cube mechanism.
	 */
	public CubeSubsystem() {
		solenoidCUBE = new DoubleSolenoid(RobotMap.cubeSolenoidCubeF, RobotMap.cubeSolenoidCubeR);
		solenoidELEV = new DoubleSolenoid(RobotMap.cubeSolenoidElevF, RobotMap.cubeSolenoidElevR);
		actuator = new WPI_TalonSRX(RobotMap.cubeActuator);
		actuator.setSafetyEnabled(false);
		potentiometer = new AnalogInput(RobotMap.cubePotentiometer);
		
		try {
			compressor = new Compressor();
			compressor.start();
			//compressor.setClosedLoopControl(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		//setDefaultCommand(new CubePSICommand());
	}
	
	public AnalogInput getPot() {
		return potentiometer;
		//LIMIT POT VOLTAGE TO .1-4.2 ***VERY IMPORTANT***
	}
	
	public double getCubeDartSpeed() {
		return actuator.get();
	}
	/**
	 * Extends the cube mechanism
	 */
	public void extendCubeDart() {
		if(potentiometer.getVoltage() < 4.3)  //may need to change .3 to something smaller. Must be bigger than .15
			actuator.set(1);
		else actuator.set(0);
	}

	/**
	 * Retracts the cube mechanism.
	 */
	public void retractCubeDart() {
		if(potentiometer.getVoltage() > .8) actuator.set(-1);
		else actuator.set(0);
	}
	
	/**
	 * Extends the cube mechanism all the way. Used in auton.
	 */
	public void fullExtend() {
		if (potentiometer.getVoltage() < 4.3) {
			actuator.set(1);
		}
		else actuator.set(0.0);
	}
	
	/**
	 * Retracts the cube mechanism all the way. Used in auton.
	 */
	public void fullRetract() {
		if (potentiometer.getVoltage() > .8) {
			actuator.set(-1);
			return;
		}
		actuator.set(0.0);
	}
	
	/**
	 * Extends the dart actuator to the specified voltage value during the autonomous period.
	 * @param voltage determines how far the cube dart will extend. Should be .93 for the partial
	 * extension needed to lift Cube Elevator Solenoid
	 */
	public void extendDartAuton(double voltage) {
		if(potentiometer.getVoltage() > 4.3) voltage = 4.3;
		if(potentiometer.getVoltage() < .8) voltage = .8;
		if(potentiometer.getVoltage() < voltage) {
			actuator.set(1);
		} else actuator.set(0.0);
	}
	
	public void retractDartAuton(double voltage) {
		if(potentiometer.getVoltage() > 4.3) voltage = 4.3;
		if(potentiometer.getVoltage() < .8) voltage = .8;
		if(potentiometer.getVoltage() > voltage) {
			actuator.set(-1);
		} else actuator.set(0.0);
	}

	/**
	 * Sets the state of the solenoid
	 * @param on Solenoid is on or off depending on whether "on" is true or false
	 */
	public void setCubeSolenoid(Value direction) {
		solenoidCUBE.set(direction);
	}
	
	public void setElevSolenoid(Value direction) {
		solenoidELEV.set(direction);
	}

	/**x
	 * Toggles the cube solenoid between the on and off state
	 */
	public void toggleCube() {
		if (solenoidCUBE.get().equals(Value.kReverse) || solenoidCUBE.get().equals(Value.kOff)) {
			solenoidCUBE.set(Value.kForward);
			//System.out.println("Cube: Forward"); **Release**
		} else {
			solenoidCUBE.set(Value.kReverse);
			//System.out.println("Cube: Reverse"); **Clamp Down**
		}
	}
	
	public void toggleElev() {
		if (solenoidELEV.get().equals(Value.kReverse) || solenoidELEV.get().equals(Value.kOff)) {
			solenoidELEV.set(Value.kForward);
			//System.out.println("Elev: Forward"); **Elev Sol Drop** 
		} else {
			solenoidELEV.set(Value.kReverse);
			//System.out.println("Elev: Reverse"); **Elev Sol Raise**
		}
	}
	
	public void setClosedLoopControl(boolean on) {
		if (compressor != null)
			compressor.setClosedLoopControl(on);
	}
	
	public void disableCompressor() {
		compressor.stop();
	}
	
	public void stopCubeDart() {
		actuator.set(0);
	}
	public DoubleSolenoid getSolenoidCube() {
		return solenoidCUBE;
	}
	public DoubleSolenoid getSolenoidElev() {
		return solenoidELEV;
	}
	
	public boolean compressorEnabled() {
		return compressor.enabled();
	}
	
}