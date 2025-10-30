package frc.robot.commands.DriveUtils;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;

import frc.FRC9485.utils.Util;

public class AdjustRotationWithVision extends Command {
    private DriveBaseSubsystem driveBase;
    private RaspberrySubsystem raspberrySubsystem;

    private double tx;
    private final double MAX_TOLERANCE = 0.2;
    private final double MIN_TOLERANCE = -0.2;

    private final double ROTATION_kP = 0.0;
    private final double ROTATION_kI = 0.0;
    private final double ROTATION_kD = 0.0;

    private final PIDController pidController = new PIDController(ROTATION_kP, ROTATION_kI, ROTATION_kD);

    public AdjustRotationWithVision() {
        this.driveBase = DriveBaseSubsystem.getInstance();
        this.raspberrySubsystem = RaspberrySubsystem.getInstance();
        this.pidController.setTolerance(MAX_TOLERANCE);
    }

    @Override
    public void initialize() {
        pidController.reset();
    }

    @Override
    public void execute() {
        tx = raspberrySubsystem.getTX();

        DoubleSupplier translation = () -> 0;
        DoubleSupplier rotation = () -> tx;
        System.out.println(rotation.getAsDouble());
        driveBase.driveTank(() -> 0.5, () -> 0.5);
    }

    @Override
    public boolean isFinished() {
        return !raspberrySubsystem.getTV() ||
        Util.inRange(tx, MIN_TOLERANCE, MAX_TOLERANCE);
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.stopMotor();
    }
}
