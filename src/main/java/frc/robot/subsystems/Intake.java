// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;


public class Intake extends SubsystemBase {  
   private SparkMax m_intakeMotor  = new SparkMax(6, MotorType.kBrushed);

   public static final SparkMaxConfig intakeConfig = new SparkMaxConfig();


  /** Creates a new Shooter. */
  public Intake() {
    intakeConfig
            .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(false);

  m_intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void intake(double speed){
    m_intakeMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
