// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
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

    private enum LEDColor {
        LED_GREEN(0,0,255),
        LED_YELLOW(255,0,255),
        LED_RED(255,0,0);

        private final int m_LED_RED;
        private final int m_LED_GREEN;
        private final int m_LED_BLUE;

        private LEDColor(int red, int blue, int green) {
            this.m_LED_RED = red;
            this.m_LED_GREEN = green;
            this.m_LED_BLUE = blue;
        }

        private int getRed() {
            return m_LED_RED;
        }

        private int getGreen() {
            return m_LED_GREEN;
        }

        private int getBlue() {
            return m_LED_BLUE;
        }
    }

    private WPI_TalonSRX m_activator;
    private WPI_TalonSRX m_ArmingValve;
    //private Timer m_timer = new Timer();
    private AnalogInput m_ShotTankPressure;
    private double m_ShotTankPSI;
    private double m_ShotTankPSITarget;
    private boolean m_IsArmed;
    private boolean m_IsLoaded;
    private final AddressableLED m_cannonLED;
    private final AddressableLEDBuffer m_cannonLEDBuffer;
    private LEDColor m_LEDNowColor;

    public Cannon() {
        m_activator = new WPI_TalonSRX(CannonConstants.kCANNON_SHOOT_PORT);
        m_ArmingValve = new WPI_TalonSRX(CannonConstants.kCANNON_ARMING_PORT);
        m_ShotTankPressure = new AnalogInput(0);
        m_ShotTankPSITarget = 25;
        m_IsArmed = false;
        m_IsLoaded = false;
        m_cannonLED = new AddressableLED(0);
        m_cannonLEDBuffer = new AddressableLEDBuffer(20);
        m_cannonLED.setLength(m_cannonLEDBuffer.getLength());
        m_cannonLED.start();
        m_LEDNowColor = LEDColor.LED_GREEN;
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
        SmartDashboard.putNumber("Shot Tank Target Pressure", m_ShotTankPSITarget);
        SmartDashboard.putBoolean("Is Cannon Loaded", m_IsLoaded);
        SmartDashboard.putBoolean("Cannon Armed", m_IsArmed);

        for (var ix = 0; ix < m_cannonLEDBuffer.getLength(); ix++) {
            m_cannonLEDBuffer.setRGB(ix, m_LEDNowColor.getRed(), m_LEDNowColor.getGreen(), m_LEDNowColor.getBlue());
        }
        m_cannonLED.setData(m_cannonLEDBuffer);
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
        m_LEDNowColor = LEDColor.LED_GREEN;
    }


    public Command cmdShoot() {
        return Commands.sequence(Commands.race(Commands.waitSeconds(CannonConstants.kSHOOT_DURATION),
        Commands.run(() -> openShotValve(), this)), 
        Commands.runOnce(() -> closeShotValve() , this))
        .unless(() ->!m_IsArmed);
       // return Commands.race(Commands.waitSeconds(CannonConstants.kSHOOT_DURATION),
               // Commands.startEnd(() -> openShotValve(), () -> closeShotValve() , this)) 
               // .unless(() ->!m_IsArmed);
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
        m_LEDNowColor = LEDColor.LED_RED;
    }

    private void setLoaded() {
        m_IsLoaded = true;
        m_LEDNowColor = LEDColor.LED_YELLOW;
    }
    public Command cmdLoadedness() {
        return Commands.runOnce(()->setLoaded(), this)
            .unless(() -> m_IsLoaded)
            .ignoringDisable(true);
    }

    public Command armShotTank() {
        return new FunctionalCommand(
            () -> openArmingValve(),
             () -> {},
              (interrupeted) -> closeArmingValve(),
               () -> isCannonArmed(),
                this)
                .withTimeout(2)
                .unless(() -> !m_IsLoaded);
    }
}
