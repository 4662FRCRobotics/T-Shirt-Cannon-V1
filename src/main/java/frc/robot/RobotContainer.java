// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.CannonConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Shoot;
import frc.robot.libraries.CommandGamepadX;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
private final Drive m_Drive = new Drive();

  
  private final CommandGamepadX m_driverController = new CommandGamepadX(0);
  private final Cannon m_cannon = new Cannon();
  private final Shoot m_Shoot = new Shoot(m_cannon);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  
  public RobotContainer() {
    m_Drive.setDefaultCommand(
      Commands.run(
          () -> m_Drive.arcadeDrive(m_driverController.getLeftY(), -m_driverController.getRightX()),
          m_Drive));
        
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_driverController
    .LB()
    
    .onTrue(Commands.runOnce(() -> m_Shoot.withTimeout(CannonConstants.kSHOOT_DURATION)));
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
