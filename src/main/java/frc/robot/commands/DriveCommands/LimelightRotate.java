// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.RobotLimelight;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class LimelightRotate extends Command {
  private final Drive drive;
  private final RobotLimelight limelight;
  /** Creates a new LimelightMove. */
  public LimelightRotate(Drive drive, RobotLimelight limelight) {
    this.drive = drive;
    addRequirements(drive);
    this.limelight = limelight;
    addRequirements(limelight);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Turn in place should be on so limelight can adjust even when not moving forward
    drive.curvatureDrive(0,
     -RobotLimelight.limelight_aim_proportional(),
     true
     );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
