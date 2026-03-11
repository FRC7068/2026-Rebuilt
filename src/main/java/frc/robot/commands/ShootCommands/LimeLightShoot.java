// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShootCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotLimelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class LimeLightShoot extends Command {
  private final Shooter shooter;
  private final RobotLimelight limelight;
  private final Intake intake;
  double Speedfactor;
  /** Creates a new LimeLightShoot. */
  public LimeLightShoot(RobotLimelight limelight, Shooter shooter, Intake intake) {
    this.limelight = limelight;
    this.shooter = shooter;
    this.intake = intake;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // limelight.LimelightDist()*xy = Speedfactor; xy will be replaced with the slope 
    // shooter.shoot(Speedfactor);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  //LL 116, RM 137, P 72.5.  (2) LL 88 RM 103, P 65, ES 0.204%/In (3) LL 63, RM 70, P 60 ES
  public void execute() {
    Speedfactor =(limelight.GetDist()*0.002)+0.465; // slope, % output per inch
    shooter.shootBot(Speedfactor*5676); //max speed of NEO
    shooter.shootTop(Speedfactor*6000); // max speed of Kraken
    intake.intake(-3500);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
