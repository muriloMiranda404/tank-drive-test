package frc.robot;

import edu.wpi.first.math.MathUtil;

import frc.FRC9485.joysticks.DriverController;
import frc.FRC9485.joysticks.SubsystemController;

import frc.robot.subsystems.vision.RaspberrySubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.superStructure.RackSubsystem;


public class RobotContainer {
  // Controles
  DriverController driverController = DriverController.getInstance();
  SubsystemController subsystemController = SubsystemController.getInstance();

  // Subsistemas
  DriveBaseSubsystem driveBaseSubsystem = DriveBaseSubsystem.getInstance();
  RaspberrySubsystem raspberrySubsystem = RaspberrySubsystem.getInstance("raspberry");

  RackSubsystem rackSubsystem;

  public RobotContainer() {
    rackSubsystem = RackSubsystem.getInstance();

    driveBaseSubsystem.setDefaultCommand(
      driveBaseSubsystem.driveTank(
          driverController,
          () -> MathUtil.applyDeadband(driverController.getTranslationAxis(), 0),
          () -> MathUtil.applyDeadband(driverController.getRotationAxis(), 0)

        )
    );
    
    // rackSubsystem.setDefaultCommand(
    //   rackSubsystem.setAngulation(subsystemController)
    // );

    // configureIntakeBindings();
    // configureShooterBindings();
    // configureConveyorBindings();
  }

  // public void configureShooterBindings() {
  //   subsystemController.getShooterButton().whileTrue(new EnableShooter());
  // }

  // public void configureConveyorBindings() {
  //   subsystemController.getConveyorButton().whileTrue(new EnableConveyor(false));
  // }

  // public void configureIntakeBindings() {
  //   subsystemController.getIntakeButton().whileTrue(new IntakeMotorCatchBall());
  //   subsystemController.getIntakeAndConveyorButton().whileTrue(new ParallelCommandGroup(new EnableConveyor(true), new IntakeMotorCatchBall()));
  // }
}
