// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CannonConstants;

/** Add your docs here. */
public class Cannon extends SubsystemBase {
    private WPI_TalonSRX m_activator;
    private Timer m_timer = new Timer();

    public Cannon() {
        m_activator = new WPI_TalonSRX(CannonConstants.kCANNON_SHOOT_PORT);
    }

    public void Open() {
        m_activator.set(1);
        m_timer.reset();
        m_timer.start();

    }

    public void periodic() {
        if (m_timer.hasElapsed(CannonConstants.kSHOOT_DURATION)) {
            m_activator.set(0);
        }
    }

    public void Close() {
        m_activator.set(0);
    }
}
