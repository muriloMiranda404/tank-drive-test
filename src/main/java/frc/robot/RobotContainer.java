package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.FRC9485.joysticks.DriverController;
import frc.FRC9485.joysticks.SubsystemController;

import frc.robot.commands.Intake.CloseIntake;
import frc.robot.commands.Conveyor.EnableConveyorMotor;
import frc.robot.commands.Intake.EnableIntakeMotor;
import frc.robot.commands.Shooter.EnableShooter;
import frc.robot.subsystems.SuperStructure;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.mechanisms.PneumaticSubsystem;
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
  PneumaticSubsystem pneumaticSubsystem;
  RaspberrySubsystem raspberrySubsystem;

  public RobotContainer() {
    rackSubsystem = RackSubsystem.getInstance();
    superStructure = SuperStructure.getInstance();
    pneumaticSubsystem = PneumaticSubsystem.getInstance();
    driveBaseSubsystem = DriveBaseSubsystem.getInstance();
    raspberrySubsystem = RaspberrySubsystem.getInstance("raspberry-frc9485");
    
    driveBaseSubsystem.setDefaultCommand(
      driveBaseSubsystem.driveTank(
          () -> MathUtil.applyDeadband(driverController.getTranslationAxis(), 0),
          () -> MathUtil.applyDeadband(driverController.getRotationAxis(), 0)
        )
    );

    rackSubsystem.setDefaultCommand(
      rackSubsystem.setAngulation(
        () -> subsystemController.getRackSpeed()
      )
    );

    new Trigger(() -> subsystemController.getCatchBallAndAdjustTranslationButton().getAsBoolean() ||
      driverController.getCatchBallAndAdjustTranslationButton().getAsBoolean()
    )
    .whileTrue(superStructure.catchBallWithVision())
    .whileFalse(new CloseIntake());

    configureIntakeBindings();
    configureShooterBindings();
    configureConveyorBindings();
  }

  public void configureShooterBindings() {
    subsystemController.getShooterButton().whileTrue(new EnableShooter());
  }

  public void configureConveyorBindings() {
    subsystemController.getConveyorWithPauseButton().whileTrue(new EnableConveyorMotor(true));
    subsystemController.getConveyorWithNoPauseButton().whileTrue(new EnableConveyorMotor(false));
  }

  public void configureIntakeBindings() {
    subsystemController.getCatchBallButton().onFalse(new CloseIntake());
    subsystemController.getIntakeButton().whileTrue(new EnableIntakeMotor());
    subsystemController.getCatchBallButton().whileTrue(superStructure.catchBall());
  }
}
