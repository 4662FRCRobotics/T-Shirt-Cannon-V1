// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CannonConstants;

/** Add your docs here. */
public class Cannon extends SubsystemBase{
    private DoubleSolenoid m_activator;
    public Cannon(){
        m_activator = new DoubleSolenoid(CannonConstants.kPCM_PORT, PneumaticsModuleType.CTREPCM, CannonConstants.kINTAKE_DOWN, CannonConstants.kINTAKE_UP);
    }

    public void Open(){
        m_activator.set(Value.kReverse);

    }
    public void Close(){
        m_activator.set(Value.kForward);
    }

    
}
