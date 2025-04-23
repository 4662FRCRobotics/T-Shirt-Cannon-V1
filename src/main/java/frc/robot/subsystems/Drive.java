// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class Drive extends SubsystemBase {
  private WPI_TalonSRX m_right1;
  private WPI_TalonSRX m_right2;
  private WPI_TalonSRX m_right3;
  private WPI_TalonSRX m_left1;
  private WPI_TalonSRX m_left2;
  private WPI_TalonSRX m_left3;
  private DifferentialDrive m_differentialdrive;

  /** Creates a new Drive. */
  public Drive() {
    m_right1 = new WPI_TalonSRX(DriveConstants.kRIGHTMOTOR1PORT);
    m_right1.setInverted(DriveConstants.kIS_INVERTED);
    m_right2 = new WPI_TalonSRX(DriveConstants.kRIGHTMOTOR2PORT);
    m_right2.setInverted(DriveConstants.kIS_INVERTED);
    m_right3 = new WPI_TalonSRX(DriveConstants.kRIGHTMOTOR3PORT);
    m_right3.setInverted(DriveConstants.kIS_INVERTED);

    m_left1 = new WPI_TalonSRX(DriveConstants.kLEFTMOTOR1PORT);
    m_left1.setInverted(!DriveConstants.kIS_INVERTED);
    m_left2 = new WPI_TalonSRX(DriveConstants.kLEFTMOTOR2PORT);
    m_left2.setInverted(!DriveConstants.kIS_INVERTED);
    m_left3 = new WPI_TalonSRX(DriveConstants.kLEFTMOTOR3PORT);
    m_left3.setInverted(!DriveConstants.kIS_INVERTED);

    m_right2.follow(m_right1);
    m_right3.follow(m_right1);
    m_left2.follow(m_left1);
    m_left3.follow(m_left1);
    m_differentialdrive = new DifferentialDrive(m_left1,m_right1);
  }
    public void arcadeDrive(double velocity, double heading){
      //double dDriveInvert = -1; //NTU We need to add a muipiter, if we want to flip around the drivices.
      double throttle = 0.50 ;
      m_differentialdrive.arcadeDrive(velocity * throttle, heading * throttle);
    }
  @Override
  public void periodic() {

  }
}