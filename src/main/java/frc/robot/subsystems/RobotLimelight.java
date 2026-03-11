// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.controller.PIDController;


public class RobotLimelight extends SubsystemBase {
  /** Creates a new RobotLimelight. */
  String limelightName = "limelight";
  static double angleToGoalRadians;
  //limelight = work;
  public RobotLimelight() {
    LimelightHelpers.setPipelineIndex(limelightName, 0);
    LimelightHelpers.getBotPose2d(limelightName);

  

  }
  public static double GetDist(){
    //NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    //NetworkTableEntry ty = table.getEntry("ty");
    //double targetOffsetAngle_Vertical = ty.getDouble(0.0);
    double targetOffsetAngle_Vertical = LimelightHelpers.getTY("limelight");
    double distanceFromLimelightToGoalInches;
    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 0; 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = 17.25;//15.25; 
    double goalHeightInches;
    if(LimelightHelpers.getFiducialID("limelight") > 0){
    goalHeightInches = (Constants.LimelightConstants.tagInfoArray[(int) LimelightHelpers.getFiducialID("limelight")][0]);
    } else{
      goalHeightInches = 0;
    }
  angleToGoalRadians = (limelightMountAngleDegrees + targetOffsetAngle_Vertical)* (3.14159 / 180.0);
    SmartDashboard.putNumber("angle", angleToGoalRadians);
    //calculate distance
    if(angleToGoalRadians != 0){
    distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
    } else {
      angleToGoalRadians = angleToGoalRadians+0.0005;
    distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
    }
    return distanceFromLimelightToGoalInches;
  }


  public static double Reginald(double Y){
    //omly works with swerve drive
    double outputSpeed;
    double LimelightDist = GetDist();
    double xOffset = LimelightDist * (Math.sin(Math.toRadians(LimelightHelpers.getTX("limelight"))));
    double goalWithOffset = (0 + Y);
    double kp;
    double ki;
    kp = 0.017;
    ki = 0.025;
    PIDController yPID = new PIDController(kp, ki, 0);
    

    
    SmartDashboard.putNumber("yOffset", xOffset);
    
   
  //    outputSpeed = 0;

  //    if(xOffset >= goalWithOffset + 15){
  //     outputSpeed = 0;
  //    }
  //  else if(xOffset >= goalWithOffset + 10 & xOffset < goalWithOffset + 15 ){
  //     outputSpeed = -0.5;
  //   } 
  //   else if( xOffset < goalWithOffset + 10 & xOffset >= goalWithOffset + 1){
  //     outputSpeed = -0.5 * Math.abs(goalWithOffset/ (10 - goalWithOffset));
  //   } 
  //   else if(xOffset > goalWithOffset + 1 & xOffset < goalWithOffset - 1){
  //     outputSpeed = 0;
  //   } 
  //   else if( xOffset > goalWithOffset - 10 & xOffset >= goalWithOffset - 1){
  //     outputSpeed = 0.5 * Math.abs(goalWithOffset/ (10 - goalWithOffset));
  //   } 
  //   else if(xOffset <= goalWithOffset - 10 & xOffset > goalWithOffset - 15){
  //     outputSpeed = 0.5;
  //   }
  //   else if(xOffset <= goalWithOffset - 15 ){
  //     outputSpeed = 0;
  //   }

    //return outputSpeed;



    //New layout

    // + is left
    // - is right
  
    outputSpeed = 0;
    outputSpeed = yPID.calculate(xOffset, goalWithOffset);
/* 
    //If goal with offset is less than 0 then want to move right
    if(goalWithOffset < 0){
    //NEED TO CHECK POS/NEG SPEED DIRECTION
      if( xOffset > goalWithOffset - 10 & xOffset <= goalWithOffset - 1){
        outputSpeed = 0.5 * Math.abs(goalWithOffset/ (10 - goalWithOffset));
      } 

      else if(xOffset <= goalWithOffset - 10 & xOffset > goalWithOffset - 15){
        outputSpeed = 0.5;
      }

      else if(xOffset <= goalWithOffset - 15 ){
        outputSpeed = 0;
      }

    }

    //If goal with offset is greater 0 then want to move left

    else if(goalWithOffset > 0){
      //NEED TO CHECK POS/NEG SPEED DIRECTION
      if(xOffset >= goalWithOffset + 15){
        outputSpeed = 0;
       }

     else if(xOffset >= goalWithOffset + 10 & xOffset < goalWithOffset + 15 ){
        outputSpeed = -0.5;
      } 

      else if( xOffset < goalWithOffset + 10 & xOffset >= goalWithOffset + 1){
        outputSpeed = -0.5 * Math.abs(goalWithOffset/ (10 - goalWithOffset));
      } 

    }

      // If not greater or less then goal is 0
    else{

      if(xOffset < -1){
        outputSpeed = 0; 
      }

      else if(xOffset == 0){
        outputSpeed = 0;
      }

      else if(xOffset > 1){
        outputSpeed = -1;
      }

      //  1 <= xOffset >= -1
      else{
        outputSpeed = 0;
      } 

    }
*/
    return outputSpeed;
    
  }

  public static double limelight_aim_proportional()
  {    
    // kP (constant of proportionality)
    // this is a hand-tuned number that determines the aggressiveness of our proportional control loop
    // if it is too high, the robot will oscillate around.
    // if it is too low, the robot will never reach its target
    // if the robot never turns in the correct direction, kP should be inverted.
    double kP = 0.0025; //0.003

    // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
    // your limelight 3 feed, tx should return roughly 31 degrees.
    double targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP;
    if(LimelightHelpers.getTX("limelight") == 0){
      targetingAngularVelocity = 0;
    }

    // convert to radians per second for our drive method
    targetingAngularVelocity *= DriveConstants.kMaxAngularSpeed;

    //invert since tx is positive when the target is to the right of the crosshair
    targetingAngularVelocity *= -1.0;

    return targetingAngularVelocity;
  }

  public static double limelight_range_proportional()
  {    
    //double positionOffset = 0;
    //double kP = .008;
    //double targetingForwardSpeed = (LimelightHelpers.getTY("limelight") + positionOffset) * kP;
    //targetingForwardSpeed *= DriveConstants.kMaxSpeedMetersPerSecond;
    //targetingForwardSpeed *= -1.0;



   double LimelightDist = GetDist();
    //12+    -> -1
    //12-4   -> scale from -1 to 0
    //4-0    -> scale from 0 to 1

    double targetingForwardSpeed = 0;

    //distance is done in inches
    //Robot is greater too far so go full speed
    if(LimelightDist >= 122){
      targetingForwardSpeed = 1;
    }
    //Tried setting multiplication value to 2 but was too fast (stick to 1)
    //Robot is to distant from target, scale down speed as we move forward
    else if(LimelightDist < 122 && LimelightDist >= 24){
      targetingForwardSpeed = -1.5 * ((LimelightDist - 24) / (122 - 24));
    }
    //Tried setting multiplication value to 2 but was too fast (stick to 1)
    //Robot is to distant from target, scale down speed as we move backward
    else if(LimelightDist < 24 && LimelightDist > 0){
      targetingForwardSpeed = 1.5 * ((24 - LimelightDist) / (24));
    }
    else {
      targetingForwardSpeed = 0;
    }
    if(LimelightHelpers.getTX("limelight") == 0){
      targetingForwardSpeed = 0;
    }
    return targetingForwardSpeed;

  }

public Pose2d GetLimelightPose() {

return LimelightHelpers.getBotPose2d(limelightName);
}
//drive, please.

  @Override
  public void periodic() {
    if(LimelightHelpers.getFiducialID("limelight") >= 0){

    SmartDashboard.putNumber("limelightID", LimelightHelpers.getFiducialID("limelight"));
    SmartDashboard.putNumber("limelightHeight", Constants.LimelightConstants.tagInfoArray[(int)LimelightHelpers.getFiducialID("limelight")][0]);
    SmartDashboard.putNumber("limelightdist", GetDist());
    // SmartDashboard.putNumber("angle", angleToGoalRadians);
    SmartDashboard.putNumber("TestTx", LimelightHelpers.getTX(limelightName));
    SmartDashboard.putNumber("TestTy", LimelightHelpers.getTY(limelightName));
    SmartDashboard.putNumber("RangeProportional", limelight_range_proportional());
    SmartDashboard.putNumber("AimProportional", limelight_aim_proportional());
    } 
  }
}
