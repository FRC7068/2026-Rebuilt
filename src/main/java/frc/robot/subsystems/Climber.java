// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  /** Creates a new climber. */

  private TalonFX climber;
  private TalonFXConfiguration climbConfig;
  private CurrentLimitsConfigs currentConfig;

  public Climber() {
          climber = new TalonFX(7);
          climbConfig = new TalonFXConfiguration();
          currentConfig = new CurrentLimitsConfigs();

          climbConfig
          .SoftwareLimitSwitch
          .ForwardSoftLimitEnable = true;
          
          climbConfig
          .SoftwareLimitSwitch
          .ForwardSoftLimitThreshold = 0;


          climbConfig
          .SoftwareLimitSwitch
          .ReverseSoftLimitEnable = true;
          
          climbConfig
          .SoftwareLimitSwitch
          .ReverseSoftLimitThreshold = 0;

        climber.getConfigurator().apply(climbConfig);

        currentConfig
        .StatorCurrentLimitEnable = true;

        currentConfig
        .StatorCurrentLimit = 80;

        climber.getConfigurator().apply(currentConfig);

  }


  public void climb(double speed){
  climber.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
