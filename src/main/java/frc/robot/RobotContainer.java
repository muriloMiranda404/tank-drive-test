package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.commands.Shooter.EnableShooter;
import frc.robot.commands.Conveyor.EnableConveyor;
import frc.robot.commands.Intake.IntakeMotorCatchBall;

import frc.robot.subsystems.RackSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;
import frc.robot.subsystems.joysticks.DriverController;
import frc.robot.subsystems.joysticks.SubsystemController;

public class RobotContainer {
  // Controles
  DriverController driverController = DriverController.getInstance();
  SubsystemController subsystemController = SubsystemController.getInstance();

  // Subsistemas
  IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
  DriveBaseSubsystem driveBaseSubsystem = DriveBaseSubsystem.getInstance();
  ShooterSubsystem shooterSubsystem = ShooterSubsystem.getInstance();
  ConveyorSubsystem conveyorSubsystem = ConveyorSubsystem.getInstance();
  RackSubsystem rackSubsystem = RackSubsystem.getInstance();
  RaspberrySubsystem raspberrySubsystem = RaspberrySubsystem.getInstance("raspberry");

  public RobotContainer() {
    rackSubsystem.resetEncoder();

    driveBaseSubsystem.setDefaultCommand(
      driveBaseSubsystem.driveTank(
          driverController,
          () -> MathUtil.applyDeadband(driverController.getTranslationAxis(), 0),
          () -> MathUtil.applyDeadband(driverController.getRotationAxis(), 0)

        )
    );
    
    rackSubsystem.setDefaultCommand(
      rackSubsystem.setAngulation(subsystemController)
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
    subsystemController.getIntakeButton().whileTrue(new IntakeMotorCatchBall());
    subsystemController.getIntakeAndConveyorButton().whileTrue(new ParallelCommandGroup(new EnableConveyor(true), new IntakeMotorCatchBall()));
  }
}
