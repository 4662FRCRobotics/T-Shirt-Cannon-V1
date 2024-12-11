// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CannonConstants;

/** Add your docs here. */
public class Cannon extends SubsystemBase {
    private WPI_TalonSRX m_activator;
    private WPI_TalonSRX m_ArmingValve;
    //private Timer m_timer = new Timer();
    private AnalogInput m_ShotTankPressure;
    private double m_ShotTankPSI;
    private double m_ShotTankPSITarget;
    private boolean m_IsArmed;
    private boolean m_IsLoaded;

    public Cannon() {
        m_activator = new WPI_TalonSRX(CannonConstants.kCANNON_SHOOT_PORT);
        m_ArmingValve = new WPI_TalonSRX(CannonConstants.kCANNON_ARMING_PORT);
        m_ShotTankPressure = new AnalogInput(0);
        m_ShotTankPSITarget = 25;
        m_IsArmed = false;
        m_IsLoaded = false;
    }

   /*  public void Open() {
        m_activator.set(1);
        m_timer.reset();
        m_timer.start();

    }*/

    public void periodic() {
       /*  if (m_timer.hasElapsed(CannonConstants.kSHOOT_DURATION)) {
            m_activator.set(0);
        }*/
        m_ShotTankPSI = 250*(m_ShotTankPressure.getVoltage()/5)-25;
        SmartDashboard.putNumber("Shot Tank Pressure", m_ShotTankPSI);
    }

    public void Close() {
        m_activator.set(0);
    }

    private void openShotValve() {
        m_activator.set(1);
    }

    private void closeShotValve() {
        m_activator.set(0);
        m_IsArmed = false;
        m_IsLoaded = false;
    }


    public Command cmdShoot() {
        return Commands.race(Commands.waitSeconds(CannonConstants.kSHOOT_DURATION),
                Commands.startEnd(() -> openShotValve(), () -> closeShotValve(), this)) 
                .unless(() ->!m_IsArmed);
    }

    private void openArmingValve() {
        m_ArmingValve.set(1);
    }

    private boolean isCannonArmed() {
        if (m_ShotTankPSI >= m_ShotTankPSITarget) 
        {return true;} else 
        {return false;}
    }

    private void closeArmingValve() {
        m_ArmingValve.set(0);
        m_IsArmed = true;
        //set LED lights on the cannon to red//
    }

    public Command armShotTank() {
        return new FunctionalCommand(
            () -> openArmingValve(),
             null,
              (interrupeted) -> closeArmingValve(),
               () -> isCannonArmed(),
                this)
                .withTimeout(10);
    }
}
