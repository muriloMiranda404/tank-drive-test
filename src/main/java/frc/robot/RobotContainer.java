package frc.robot;

import frc.robot.Constants.Controles;

import frc.robot.commands.Shooter.EnableShooter;
import frc.robot.commands.Conveyor.EnableConveyor;
import frc.robot.commands.Intake.IntakeMotorCatchBall;

import frc.robot.subsystems.TankSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.CremalheiraSubsystem;
import frc.robot.subsystems.utils.SubsystemController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // Controles
  SubsystemController subsystemController = new SubsystemController();
  XboxController driverController = new XboxController(Controles.OPERATOR_CONTROLLER);

  // Subsistemas
  TankSubsystem tankSubsystem = new TankSubsystem();
  IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
  CremalheiraSubsystem cremalheiraSubsystem = new CremalheiraSubsystem();

  public RobotContainer() {
    cremalheiraSubsystem.resetEncoder();

    // intakeSubsystem.periodic();

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
    new JoystickButton(subsystemController.getHID(), subsystemController.getLeftBumperID()).whileTrue(new EnableShooter(this));
  }

  public void configureConveyorBindings() {
    new JoystickButton(subsystemController.getHID(), subsystemController.getXButtonID()).whileTrue(new EnableConveyor(this, false));
  }

  public void configureIntakeBindings() {
    new JoystickButton(subsystemController.getHID(), subsystemController.getAButtonID()).whileTrue(new IntakeMotorCatchBall(this));
    new JoystickButton(subsystemController.getHID(), subsystemController.getRightBumperID()).whileTrue(new ParallelCommandGroup(new EnableConveyor(this, true), new IntakeMotorCatchBall(this)));
  }

  public ShooterSubsystem getShooterSubsystemInstance() {
    if (shooterSubsystem == null) {
      return new ShooterSubsystem();
    }
    return shooterSubsystem;
  }

  public CremalheiraSubsystem getCremalheiraSubsystemInstance() {
    if (cremalheiraSubsystem == null) {
      return new CremalheiraSubsystem();
    }
    return cremalheiraSubsystem;
  }

  public ConveyorSubsystem getConveyorSubsystemInstance() {
    if (conveyorSubsystem == null) {
      return new ConveyorSubsystem();
    }
    return conveyorSubsystem;
  }

  public IntakeSubsystem getIntakeSubsystemInstance() {
    if (intakeSubsystem == null) {
      return new IntakeSubsystem();
    }
    return intakeSubsystem;
  }
}
