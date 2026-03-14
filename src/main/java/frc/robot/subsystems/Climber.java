// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import org.opencv.features2d.FlannBasedMatcher;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  /** Creates a new climber. */

  boolean ForwardLimit;
  boolean ReverseLimit;

  private TalonFX climber;
  private TalonFXConfiguration climbConfig;
  private CurrentLimitsConfigs currentConfig;
  private MotorOutputConfigs outputConfigs;

  public Climber() {
          climber = new TalonFX(7);
          climbConfig = new TalonFXConfiguration();
          currentConfig = new CurrentLimitsConfigs();
          outputConfigs = new MotorOutputConfigs();

          climbConfig
          .SoftwareLimitSwitch
          .ForwardSoftLimitEnable = true;
          
          climbConfig
          .SoftwareLimitSwitch
          .ForwardSoftLimitThreshold = 125;


          climbConfig
          .SoftwareLimitSwitch
          .ReverseSoftLimitEnable = true;
          
          climbConfig
          .SoftwareLimitSwitch
          .ReverseSoftLimitThreshold = -217;

       


        climber.getConfigurator().apply(climbConfig);

        currentConfig
        .StatorCurrentLimitEnable = false;

        currentConfig
        .StatorCurrentLimit = 80;

        climber.getConfigurator().apply(currentConfig);

        outputConfigs
        .Inverted = InvertedValue.Clockwise_Positive;

        climber.getConfigurator().apply(outputConfigs);

  }


  public void climb(double speed){
  climber.set(speed);
  }





  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(climber.getPosition().getValueAsDouble() == 125){
      ForwardLimit = true;
    } else{
      ForwardLimit = false;
    }

    if(climber.getPosition().getValueAsDouble() == -217){
      ReverseLimit = true;
    } else{
      ReverseLimit = false;
    }
    SmartDashboard.putNumber("Climber Counts", climber.getPosition().getValueAsDouble());
    SmartDashboard.putBoolean("ForwardLimit", ForwardLimit);
    SmartDashboard.putBoolean("Reverse Limit", ReverseLimit);
  }
}
