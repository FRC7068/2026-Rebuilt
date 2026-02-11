// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.commands.LimelightMove;
import frc.robot.commands.ShootUp;
import frc.robot.commands.IntakeIn;
import frc.robot.commands.Shoot;
import frc.robot.commands.Store;

import frc.robot.subsystems.RobotLimelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Joystick m_stick = new Joystick(0);

  private final Shooter m_Shooter = new Shooter();
  private final Intake m_Intake = new Intake();
  private final RobotLimelight m_Limelight = new RobotLimelight();
  private final Drive m_Drive = new Drive();

  private final ShootUp m_shootup  = new ShootUp(m_Shooter);
  private final IntakeIn m_intakeIn = new IntakeIn(m_Intake);
  private final LimelightMove m_limelightMove = new LimelightMove(m_Drive, m_Limelight);
  private final Shoot m_shoot = new Shoot(m_Intake, m_Shooter);
  private final Store m_store = new Store(m_Shooter);
  //private final ToggleTurnInPlace m_TurnInPlace = new ToggleTurnInPlace(m_Drive);

  
    


  
  // Replace with CommandPS4Controller or CommandJoystick if needed     
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    m_Drive.setDefaultCommand(
    new RunCommand(()->
     m_Drive.curvatureDrive(
      // Setup for xbox axis. For joystick operation set axis to 1
     MathUtil.applyDeadband(m_stick.getRawAxis(5), OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(-m_stick.getRawAxis(0), OIConstants.kThetaDriveDeadband),
     m_stick.getRawButton(2)
  ),
     m_Drive
    )
    );
  }


  

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    new JoystickButton(m_stick, XboxController.Button.kA.value).whileTrue(m_shoot);
    new JoystickButton(m_stick, XboxController.Button.kB.value).whileTrue(m_intakeIn);
    new JoystickButton(m_stick, XboxController.Button.kX.value).whileTrue(m_store);


    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));
    /*
    ============================
                                  ||
    Lime light activation button  ||
                                  \/
    ============================
    */
    //new JoystickButton(m_stick, 1).whileTrue(m_limelightMove);


    //new JoystickButton(m_stick, 2).whileTrue(m_TurnInPlace);
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
