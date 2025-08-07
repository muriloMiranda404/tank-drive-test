// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.Controles;
import frc.robot.command.DriveCommand;
import frc.robot.subsystems.TankSubsystem;

// import frc.robot.Constants.OperatorConstants;

public class RobotContainer {
  private XboxController controller = new XboxController(Controles.OPERATOR_CONTROLLER);
  TankSubsystem tankSubsystem = new TankSubsystem();

  public RobotContainer() {
    tankSubsystem.setDefaultCommand(new DriveCommand(
      tankSubsystem, 
      () -> controller.getLeftY(), 
      () -> controller.getRightX()
      ));

    configureBindings();
  }

  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    

  }

  // public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_exampleSubsystem);
  // }
}
