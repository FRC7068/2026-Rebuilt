// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.lang.ModuleLayer.Controller;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

/*
 * The intake is the single axis that moves the balls into and out of the hopper.
 */
public class Intake extends SubsystemBase {  
   private static SparkMax m_intakeMotor  = new SparkMax(6, MotorType.kBrushless);

   public static final SparkMaxConfig intakeConfig = new SparkMaxConfig();
   public static final SparkMaxConfig velocityConfig = new SparkMaxConfig();
   public static SparkClosedLoopController controler = m_intakeMotor.getClosedLoopController();
  





  /** Creates a new Shooter. */
  public Intake() {
    
    // intakeConfig
    //         .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
    //         .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
    //         .inverted(true);


    m_intakeMotor.getEncoder();
     velocityConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(true)
            .closedLoopRampRate(1)
            .openLoopRampRate(1)
            .closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(0.001)
            .outputRange(-1, 1)
            .maxMotion
            .cruiseVelocity(6000)
            .maxAcceleration(10000)
            .allowedProfileError(1)
            ;
          velocityConfig
          .closedLoop
          .feedForward  
          .kV(12/473) //  12/5676
          ;

  m_intakeMotor.configure(velocityConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void intake(double speed){
    //m_intakeMotor.set(speed);
    controler.setSetpoint(speed, ControlType.kMAXMotionVelocityControl);
  }
   public void stop(){
    m_intakeMotor.set(0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
        SmartDashboard.putNumber("intake speed", m_intakeMotor.getEncoder().getVelocity());
  }
}
