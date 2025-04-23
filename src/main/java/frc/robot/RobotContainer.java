// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.CannonConstants;
//import frc.robot.commands.ArcadeDrive;
//import frc.robot.commands.Shoot;
import frc.robot.libraries.CommandGamepadX;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Lifter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drive m_Drive = new Drive();
  private final Lifter m_lifter = new Lifter();
  private final CommandGamepadX m_driverController = new CommandGamepadX(0);
  private final Cannon m_cannon = new Cannon();
  private final DigitalInput m_loadingButton = new DigitalInput(0);
  private final Trigger m_loadingTrigger = new Trigger(m_loadingButton::get);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public RobotContainer() {
    m_Drive.setDefaultCommand(
        Commands.run(
            () -> m_Drive.arcadeDrive(m_driverController.getLeftY(), -m_driverController.getRightX()),
            m_Drive));

   // m_lifter.setDefaultCommand(
       // Commands.run(
           // () -> m_lifter.AimLifter(m_driverController.getRightY()), m_lifter));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
   /*  m_driverController
        .LB()

        .onTrue(Commands.runOnce(() -> m_cannon.Open()));
    */
    m_driverController
        .button(8)
        .onTrue(m_cannon.cmdShoot())
       ;

    m_driverController
        .button(6)
        .onTrue(m_cannon.armShotTank());

    m_loadingTrigger
      .onTrue(m_cannon.cmdLoadedness())
      .debounce(3);
      

    m_driverController.povUp()
      .whileTrue(m_lifter.cmdBarrelUp())
      .onFalse(m_lifter.cmdStopBarrel());

    m_driverController.povDown()
      .whileTrue(m_lifter.cmdBarrelDown())
      .onFalse(m_lifter.cmdStopBarrel());
     }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
