package frc.robot;

import edu.wpi.first.math.MathUtil;

import frc.FRC9485.joysticks.DriverController;
import frc.FRC9485.joysticks.SubsystemController;

import frc.robot.commands.Intake.CloseIntake;
import frc.robot.commands.Conveyor.EnableConveyor;
import frc.robot.commands.Intake.EnableIntakeMotor;
import frc.robot.commands.Shooter.EnableShooter;
import frc.robot.subsystems.SuperStructure;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.mechanisms.RackSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;


public class RobotContainer {
  // Controles
  DriverController driverController = DriverController.getInstance();
  SubsystemController subsystemController = SubsystemController.getInstance();

  // Subsistemas
  RackSubsystem rackSubsystem;
  SuperStructure superStructure;
  DriveBaseSubsystem driveBaseSubsystem;
  RaspberrySubsystem raspberrySubsystem;

  public RobotContainer() {
    rackSubsystem = RackSubsystem.getInstance();
    superStructure = SuperStructure.getInstance();
    driveBaseSubsystem = DriveBaseSubsystem.getInstance();
    raspberrySubsystem = RaspberrySubsystem.getInstance("raspberry");

    driveBaseSubsystem.setDefaultCommand(
      driveBaseSubsystem.driveTank(
          driverController,
          () -> MathUtil.applyDeadband(driverController.getTranslationAxis(), 0),
          () -> MathUtil.applyDeadband(driverController.getRotationAxis(), 0)

        )
    );

    rackSubsystem.setDefaultCommand(
      rackSubsystem.setAngulation(
        () -> subsystemController.getRackSpeed()
      )
    );


    configureIntakeBindings();
    configureShooterBindings();
    configureConveyorBindings();
  }

  public void configureShooterBindings() {
    subsystemController.getShooterButton().whileTrue(new EnableShooter());
  }

  public void configureConveyorBindings() {
    subsystemController.getConveyorButton().whileTrue(new EnableConveyor(false));
  }

  public void configureIntakeBindings() {
    subsystemController.getCatchBallButton().onFalse(new CloseIntake());
    subsystemController.getIntakeButton().whileTrue(new EnableIntakeMotor());
    subsystemController.getCatchBallButton().whileTrue(superStructure.catchBall());
  }
}
