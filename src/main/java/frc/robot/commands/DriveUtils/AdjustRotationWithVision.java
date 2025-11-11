package frc.robot.commands.DriveUtils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.FRC9485.utils.Util;
import frc.FRC9485.utils.logger.CustomBooleanLog;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;

public class AdjustRotationWithVision extends Command {
    private DriveBaseSubsystem driveBase;
    private RaspberrySubsystem raspberrySubsystem;

    private double tx;
    private double rotationSpeed;

    private boolean safeExit;
    private boolean startPredicting;

    private final Timer timer;
    
    private final double SETPOINT = 0;
    private final double LOOP_TIME = 1;
    
    private final double MAX_LOOP_TIME = 3;
    private final double TX_PER_SECOND = 6.65;
    private final double ERROR_TOLERANCE = 2.0;

    private final CustomDoubleLog txLogger;
    private final CustomBooleanLog safeExitLogger;
    private final CustomDoubleLog rotationSpeedLogger;
    private final CustomBooleanLog startPredictingLogger;

    public AdjustRotationWithVision() {
        timer = new Timer();
        driveBase = DriveBaseSubsystem.getInstance();
        raspberrySubsystem = RaspberrySubsystem.getInstance();

        txLogger = new CustomDoubleLog("Vision/TX");
        safeExitLogger = new CustomBooleanLog("Vision/Safe Exit");
        rotationSpeedLogger = new CustomDoubleLog("Vision/Rotation Speed");
        startPredictingLogger = new CustomBooleanLog("Vision/Start Predicting");
    }

    @Override
    public void initialize() {
        tx = 0;
        rotationSpeed = 0.45;

        safeExit = false;
        startPredicting = false;

        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        safeExit = timer.hasElapsed(MAX_LOOP_TIME);
        startPredicting = timer.hasElapsed(LOOP_TIME);

        tx = startPredicting ? Math.round(raspberrySubsystem.getTX() + TX_PER_SECOND)
        : raspberrySubsystem.getTX();

        rotationSpeed = tx > 0 ? 0.4 : -0.4;

        System.out.println(SETPOINT - ERROR_TOLERANCE);
        System.out.println(SETPOINT + ERROR_TOLERANCE);

        if (Util.inRange(tx, SETPOINT - ERROR_TOLERANCE, SETPOINT + ERROR_TOLERANCE)) {
            System.out.println("Ta no setpoint");
            rotationSpeed = 0;
            this.cancel();
        }

        txLogger.append(tx);
        safeExitLogger.append(safeExit);
        rotationSpeedLogger.append(rotationSpeed);
        startPredictingLogger.append(startPredicting);

        driveBase.drive(0, -rotationSpeed);

        // if (!timer.hasElapsed(LOOP_TIME)) {
        //     rotation = 0.45;

        //     System.out.println("Rotation: " + rotation);
        // } else {
        //     rotation = 0;
        // }

        // driveBase.drive(0, rotation);
    }

    @Override
    public boolean isFinished() {
        if (safeExit) {
            System.out.println("=-=-=-=-=-=");
            System.out.println("SAFE EXIT!!");
            System.out.println("SAFE EXIT!!");
            System.out.println("SAFE EXIT!!");
            System.out.println("SAFE EXIT!!");
            System.out.println("SAFE EXIT!!");
            System.out.println("=-=-=-=-=-=");
        }
        return !raspberrySubsystem.getTV() ||
        safeExit;
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        driveBase.stopMotor();
    }
}
