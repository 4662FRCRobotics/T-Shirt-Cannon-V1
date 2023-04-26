// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CannonConstants;

public class Lifter extends SubsystemBase {
  /** Creates a new Lifter. */
  //an idea for how to adust the angle of the cannon for aiming
  //add an absulute encoder to prevent breaking it
  private WPI_TalonSRX m_motor;

  public Lifter() {
m_motor = new WPI_TalonSRX(CannonConstants.kLIFTERMOTORPORT);
  }

public void AimLifter(double Speed){
  m_motor.set(Speed*CannonConstants.kLIFTERSPEED);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
