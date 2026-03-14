// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.security.cert.X509CRL;

import com.fasterxml.jackson.databind.SequenceWriter;

import edu.wpi.first.math.MathUtil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.RobotLimelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Pneumatics;


import frc.robot.commands.ClimbCommands.*;
import frc.robot.commands.UtilityCommands.*;
import frc.robot.commands.IntakeCommands.*;
import frc.robot.commands.DriveCommands.*;
import frc.robot.commands.ShootCommands.*;
import frc.robot.commands.PneumaticsCommands.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Joystick m_Controller = new Joystick(0); //manipulating, Logitech
  private final Joystick m_stick = new Joystick(1); //driving, Xbox 1

  private final Shooter m_Shooter = new Shooter();
  private final Intake m_Intake = new Intake();
  private final RobotLimelight m_Limelight = new RobotLimelight();
  private final Drive m_Drive = new Drive();
  private final Climber m_Climber = new Climber();
  private final Pneumatics m_Pneumatics = new Pneumatics();

  private final ShootUp m_shootup  = new ShootUp(m_Shooter, m_Limelight);
  private final Store m_store = new Store(m_Shooter);
  private final Shoot m_shoot = new Shoot(m_Intake, m_Shooter, m_Limelight);
  private final Load m_load = new Load(m_Shooter, m_Intake);
  private final Release m_Release = new Release(m_Intake, m_Shooter);
  private final ShootFar m_Far = new ShootFar(m_Shooter, m_Intake);
  private final ShootA m_ShootA = new ShootA(m_Intake, m_Shooter, m_Limelight);

  private final IntakeIn m_intakeIn = new IntakeIn(m_Intake);

  private final LimelightMove m_limelightMove = new LimelightMove(m_Drive, m_Limelight);
  private final LimelightRotate m_LimelightRotate = new LimelightRotate(m_Drive, m_Limelight);
  private final LimeLightShoot m_LightShoot = new LimeLightShoot(m_Limelight, m_Shooter, m_Intake);

  private final Climbforward m_Climbforward = new Climbforward(m_Climber, m_Pneumatics);
  private final Climbreverse m_Climbreverse = new Climbreverse(m_Climber, m_Pneumatics);

  private final SoleniodOn m_SoleniodOn = new SoleniodOn(m_Pneumatics);
  private final SoleniodOff m_SoleniodOff = new SoleniodOff(m_Pneumatics);

  private final SendableChooser<String> m_Position = new SendableChooser<>();

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
     MathUtil.applyDeadband(
      m_stick.getRawAxis(1)*0.8, 
     OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(
      m_stick.getRawAxis(4), 
     OIConstants.kThetaDriveDeadband),
     (m_stick.getRawAxis(3) > 0.05)
     ?true
     :false
    // m_stick.getRawButton(2)
    ),
     m_Drive
    )
    );

    m_Position.setDefaultOption("Center", "Center");
    m_Position.addOption("Left", "Left");
    m_Position.addOption("Right", "Right");
    SmartDashboard.putData("Start Pos", m_Position);
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
    //Moves balls out of the hopper and shoots them

    //Spins the ball loader to send them into the hopper
    //new JoystickButton(m_Controller, XboxController.Button.kB.value).whileTrue(m_intakeIn);

    //Spins the intake and the shooter to send balls into the hopper
    //new JoystickButton(m_Controller, XboxController.Button.kX.value).whileTrue(m_store);

    //Spins the intake, loader, and the shooter to load balls into the hopper

    //Climber functions
    new JoystickButton(m_stick, XboxController.Button.kRightBumper.value).whileTrue(m_Climbforward);
    new JoystickButton(m_stick, XboxController.Button.kLeftBumper.value).whileTrue(m_Climbreverse);
    // new JoystickButton(m_stick, 3).whileTrue(m_Climbforward);
    // new JoystickButton(m_stick, 4).whileTrue(m_Climbreverse);

    new JoystickButton(m_Controller, XboxController.Button.kLeftBumper.value).whileTrue(m_load);
    new JoystickButton(m_Controller, XboxController.Button.kRightBumper.value).whileTrue(m_Far);
    new JoystickButton(m_Controller, XboxController.Button.kA.value).whileTrue(m_shoot);
    new JoystickButton(m_Controller, XboxController.Button.kB.value).whileTrue(m_LimelightRotate);
    new JoystickButton(m_Controller, XboxController.Button.kX.value).whileTrue(m_Release);



    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`


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
    if(m_Position.getSelected().equals("Left")){  
    return(
    new SequentialCommandGroup(

    new ParallelRaceGroup(
 
    new RunCommand(()->
     m_Drive.curvatureDrive(
      // Setup for xbox axis. For joystick operation set axis to 1
     MathUtil.applyDeadband(-0.5, OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(0, OIConstants.kThetaDriveDeadband),
     false
    ),
     m_Drive
    ),

    new DoTimeOut(1.25)
    ),

    new ParallelRaceGroup(new RunCommand(()->
     m_Drive.curvatureDrive(
      // Setup for xbox axis. For joystick operation set axis to 1
     MathUtil.applyDeadband(0, OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(0.25, OIConstants.kThetaDriveDeadband),
    true
    ),
     m_Drive
    ),
    new DoTimeOut(0.5)
    ),

    new ParallelRaceGroup(
      new DoTimeOut(1),
      new LimelightRotate(m_Drive,m_Limelight)
    ),

    new ParallelRaceGroup(
     m_ShootA,
     new DoTimeOut(5)
    )
     )
     );       
   }
  

  else if(m_Position.getSelected().equals("Right")){ 
   return( 
    new SequentialCommandGroup(

    new ParallelRaceGroup(
 
    new RunCommand(()->
     m_Drive.curvatureDrive(
      // Setup for xbox axis. For joystick operation set axis to 1
     MathUtil.applyDeadband(-0.5, OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(0, OIConstants.kThetaDriveDeadband),
     false
    ),
     m_Drive
    ),

    new DoTimeOut(1.25)
    ),

    new ParallelRaceGroup(new RunCommand(()->
     m_Drive.curvatureDrive(
      // Setup for xbox axis. For joystick operation set axis to 1
     MathUtil.applyDeadband(0, OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(-0.25, OIConstants.kThetaDriveDeadband),
    true
    ),
     m_Drive
    ),
    new DoTimeOut(0.5)
    ),

    new ParallelRaceGroup(
      new DoTimeOut(1),
      new LimelightRotate(m_Drive,m_Limelight)
    ),

    new ParallelRaceGroup(
     m_ShootA,
     new DoTimeOut(5)
    )
     )
     );    
   }

   else if(m_Position.getSelected().equals("Center")){
    return(
      new SequentialCommandGroup(
    new ParallelRaceGroup(new RunCommand(()->
     m_Drive.curvatureDrive(
      // Setup for xbox axis. For joystick operation set axis to 1
     MathUtil.applyDeadband(-0.5, OIConstants.kXDriveDeadband), 
     // Set for xbox axis. For joystick operation set to 3
     MathUtil.applyDeadband(0, OIConstants.kThetaDriveDeadband),
    false
    ),
     m_Drive
    ),
    new DoTimeOut(1.25)
    ),
    
    new ParallelRaceGroup(
      m_shoot,
      new DoTimeOut(5)
    )
    
    )
    );

   }else{

return null;
}
}
}