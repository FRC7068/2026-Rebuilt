// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class Drive extends SubsystemBase {
   private SparkMax m_leftMotor1  = new SparkMax(1, MotorType.kBrushed);
//   private final WPI_TalonSRX m_leftMotor1 = new WPI_TalonSRX(1);
   private SparkMax m_leftMotor2  = new SparkMax(2, MotorType.kBrushed);
//   private final WPI_TalonSRX m_leftMotor2 = new WPI_TalonSRX(2);
   private SparkMax m_rightMotor1  = new SparkMax(3, MotorType.kBrushed);
//   private final WPI_TalonSRX m_rightMotor1 = new WPI_TalonSRX(3);
   private SparkMax m_rightMotor2  = new SparkMax(4, MotorType.kBrushed);
//   private final WPI_TalonSRX m_rightMotor2 = new WPI_TalonSRX(4);

public static final SparkMaxConfig drivingL1Config = new SparkMaxConfig();
public static final SparkMaxConfig drivingL2Config = new SparkMaxConfig();
public static final SparkMaxConfig drivingR1Config = new SparkMaxConfig();
public static final SparkMaxConfig drivingR2Config = new SparkMaxConfig();

  private final DifferentialDrive m_robotDrive =
      //new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
      new DifferentialDrive(m_leftMotor1, m_rightMotor1);
      
  private boolean turnInPlace = false;

  
  /** Creates a new Drive. */
  public Drive() {

    SendableRegistry.addChild(m_robotDrive, m_leftMotor1);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor1);

    // Set up configurations for each drive motor
    drivingL1Config
            .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(false);
            

    drivingL2Config
            .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(true)
            .follow(m_leftMotor1);
    

    drivingR1Config
            .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(true);
            

    drivingR2Config
            .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(true)
            .follow(m_rightMotor1);

    //Set spark configuration
    m_leftMotor1.configure(drivingL1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_leftMotor2.configure(drivingL2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_rightMotor1.configure(drivingR1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_rightMotor2.configure(drivingR2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      

    // Set master followers to sync drive train sides
    // m_leftMotor2.set(m_leftMotor1.get());
    // m_rightMotor2.set(m_rightMotor1.get());

    // m_leftMotor2.follow(m_leftMotor1);
    // m_rightMotor2.follow(m_rightMotor1);


    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    // m_rightMotor1.setInverted(true);
    // m_rightMotor2.setInverted(true); 
  }

   public void curvatureDrive( double stickY, double stickZ, boolean turnInPlace){ 
    this.turnInPlace = turnInPlace;
    m_robotDrive.curvatureDrive(-stickY, stickZ, turnInPlace);
}


public boolean getTurnInPlace(){
  return this.turnInPlace;
}

public void setTurnInPlace(boolean toggle){
  if(toggle == true){
    turnInPlace = true;
  }
  if(toggle == false){
    turnInPlace = false;
  }
}

 

  @Override
  public void periodic() {

   SmartDashboard.putBoolean("Button 2 is Pressed", turnInPlace);


  }

  public SparkMax getM_leftMotor2() {
    return m_leftMotor2;
  }

  public SparkMax getM_rightMotor1() {
    return m_rightMotor1;
  }

  public SparkMax getM_rightMotor2() {
    return m_rightMotor2;
  }

  public DifferentialDrive getM_robotDrive() {
    return m_robotDrive;
  }
}
