package frc.robot;

import frc.robot.Constants.Controles;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.commands.Shooter.EnableShooter;
import frc.robot.commands.Conveyor.EnableConveyor;
import frc.robot.commands.Intake.IntakeMotorCatchBall;

import frc.robot.subsystems.TankSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.CremalheiraSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;
import frc.robot.subsystems.joysticks.SubsystemController;

public class RobotContainer {
  // Controles
  SubsystemController subsystemController = SubsystemController.getInstance();
  XboxController driverController = new XboxController(Controles.OPERATOR_CONTROLLER);

  // Subsistemas
  TankSubsystem tankSubsystem = TankSubsystem.getInstance();
  IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
  ShooterSubsystem shooterSubsystem = ShooterSubsystem.getInstance();
  ConveyorSubsystem conveyorSubsystem = ConveyorSubsystem.getInstance();
  CremalheiraSubsystem cremalheiraSubsystem = CremalheiraSubsystem.getInstance();
  RaspberrySubsystem raspberrySubsystem = RaspberrySubsystem.getInstance("raspberry");

  public RobotContainer() {
    cremalheiraSubsystem.resetEncoder();

    tankSubsystem.setDefaultCommand(
      tankSubsystem.driveTank(
          driverController,
          () -> MathUtil.applyDeadband(driverController.getLeftY(), 0),
          () -> MathUtil.applyDeadband(driverController.getRightX(), 0)

        )
    );
    
    cremalheiraSubsystem.setDefaultCommand(
      cremalheiraSubsystem.setAngulation(subsystemController)
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
