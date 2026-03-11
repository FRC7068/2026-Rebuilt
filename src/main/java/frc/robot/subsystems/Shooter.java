// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.MAXMotionConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;



/*
 * The shooter is actually the loader and the shooter together since they are driven together mechanically
 */
public class Shooter extends SubsystemBase {  
    private TalonFX m_shoot2 = new TalonFX(8);

   
   private TalonFXConfiguration shootConfig;
   private Slot0Configs slot0Configs;
   private MotionMagicConfigs krakenMagic;

   private static SparkMax m_shootMotor  = new SparkMax(5, MotorType.kBrushless);
   private SparkClosedLoopController m_ClosedLoopControl = m_shootMotor.getClosedLoopController();

   public static final SparkMaxConfig shootingConfig = new SparkMaxConfig();
   public static final SparkMaxConfig velocitySConfig = new SparkMaxConfig();
   public static SparkClosedLoopController controler = m_shootMotor.getClosedLoopController();

   //These are constants for the speed 
  final MotionMagicVelocityVoltage m_speed = new MotionMagicVelocityVoltage(0);
   

  /** Creates a new Shooter. */
  public Shooter() {
          m_shoot2 = new TalonFX(8);
          shootConfig = new TalonFXConfiguration();

          

    // shootingConfig
    //         .idleMode(Constants.ModuleConstants.kDrivingMotorIdleMode)
    //         .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
    //         .inverted(true);
    slot0Configs = shootConfig.Slot0;
    slot0Configs.kV = 0.12; // volts per RPS
    slot0Configs.kA = 0.01; // acc
    slot0Configs.kP = 0.13;

    krakenMagic = shootConfig.MotionMagic;
    krakenMagic.MotionMagicAcceleration = 800; //(r/s)/s
    krakenMagic.MotionMagicJerk = 8000; // acc for acc


    m_shoot2.getConfigurator().apply(shootConfig);

    velocitySConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(Constants.ModuleConstants.kDrivingMotorCurrentLimit)
            .inverted(true)
            .closedLoopRampRate(1)
            .openLoopRampRate(1)
            .closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(0.002)
            .outputRange(-1, 1)
            .maxMotion
            .cruiseVelocity(6000)
            .maxAcceleration(10000)
            .allowedProfileError(1)
            ;
        velocitySConfig
          .closedLoop
          .feedForward
          .kV(12/473)
          ;

    //m_velocityConfig
            

  m_shootMotor.configure(velocitySConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void shootBot(double speed){
    //m_shootMotor.set(speed);
    controler.setSetpoint(speed, ControlType.kMAXMotionVelocityControl);
     //max rpm of the neo
    //Velocity set in RPMS. A SIM has 5310 RMPs free speed
    //So setpoint max around 95% 
  }
  public void shootTop(double speed){
    //m_shootMotor.set(speed);
    m_shoot2.setControl(m_speed.withVelocity(speed/60));
    //shoot2.set(speed);
    //max rpm of the neo
    //Velocity set in RPMS. A SIM has 5310 RMPs free speed
    //So setpoint max around 95% 
  }
  public void stop(){
    m_shootMotor.set(0);
    m_shoot2.set(0);
  }

  /**
   * Converts distance to optimal motor speed
   * 
   * @param distance
   * @return
   */
  
  public static double calculateSpeed(double distance){
    return 0.0;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("shoot speed", m_shootMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("shoot speedK", m_shoot2.getVelocity().getValueAsDouble()*60);

  }
}
