// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShootCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LimelightHelpers;
import frc.robot.subsystems.RobotLimelight;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ShootUp extends Command {
  /** Creates a new Shoot. */
  private Shooter shooter;
  private RobotLimelight limelight;
  double percentSpeed;
  public ShootUp(Shooter shooter, RobotLimelight limelight) {
    this.shooter = shooter;
    this.limelight = limelight;
    addRequirements(shooter);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(LimelightHelpers.getFiducialID("limelight") > 0){
    percentSpeed = ((limelight.GetDist()*0.0011)+0.465);
    shooter.shootTop(percentSpeed*6000); //6000max rpm
    shooter.shootBot(100); // 5676max rpm
    SmartDashboard.putNumber("Speed Factor", percentSpeed);
    } else{
      shooter.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
