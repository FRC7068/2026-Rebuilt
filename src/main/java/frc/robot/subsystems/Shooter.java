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


public class Shooter extends SubsystemBase {  
   private SparkMax m_shootMotor  = new SparkMax(5, MotorType.kBrushed);

   public static final SparkMaxConfig shootingConfig = new SparkMaxConfig();


  /** Creates a new Shooter. */
  public Shooter() {
    shootingConfig
            .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(false);

  m_shootMotor.configure(shootingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void shoot(double speed){
    m_shootMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
