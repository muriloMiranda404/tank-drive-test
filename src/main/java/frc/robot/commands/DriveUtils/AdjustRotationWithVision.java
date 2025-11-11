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

    private boolean excededMaxLoopTime;
    private boolean startPredicting;

    private final Timer timer;
    
    private final double SETPOINT = 0;
    private final double LOOP_TIME = 0.53;
    // 0.5297117449347027
    
    private final double MAX_LOOP_TIME = 2.0;
    private final double TX_PER_SECOND = 6.65;
    private final double ERROR_TOLERANCE = 2.1;

    private final CustomDoubleLog txLogger;
    private final CustomBooleanLog excededMaxLoopTimeLogger;
    private final CustomDoubleLog rotationSpeedLogger;
    private final CustomBooleanLog startPredictingLogger;

    public AdjustRotationWithVision() {
        timer = new Timer();
        driveBase = DriveBaseSubsystem.getInstance();
        raspberrySubsystem = RaspberrySubsystem.getInstance();

        txLogger = new CustomDoubleLog("Vision/TX");
        excededMaxLoopTimeLogger = new CustomBooleanLog("Vision/Safe Exit");
        rotationSpeedLogger = new CustomDoubleLog("Vision/Rotation Speed");
        startPredictingLogger = new CustomBooleanLog("Vision/Start Predicting");
    }

    @Override
    public void initialize() {
        tx = 0;
        rotationSpeed = 0.45;

        excededMaxLoopTime = false;
        startPredicting = false;

        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        startPredicting = timer.hasElapsed(LOOP_TIME);
        excededMaxLoopTime = timer.hasElapsed(MAX_LOOP_TIME);

        tx = startPredicting ? Math.round(raspberrySubsystem.getTX() + TX_PER_SECOND)
        : raspberrySubsystem.getTX();

        rotationSpeed = tx > 0 ? 0.4 : -0.4;

        if (Util.inRange(tx, SETPOINT - ERROR_TOLERANCE, SETPOINT + ERROR_TOLERANCE)) {
            System.out.println("Ta no setpoint");
            rotationSpeed = 0;
            this.cancel();
        }

        txLogger.append(tx);
        rotationSpeedLogger.append(rotationSpeed);
        startPredictingLogger.append(startPredicting);
        excededMaxLoopTimeLogger.append(excededMaxLoopTime);

        driveBase.drive(0, -rotationSpeed);
    }

    @Override
    public boolean isFinished() {
        if (excededMaxLoopTime) {
            System.out.println("=-=-=-=-=-=");
            System.out.println("TEMPO LIMITE!!");
            System.out.println("TEMPO LIMITE!!");
            System.out.println("TEMPO LIMITE!!");
            System.out.println("TEMPO LIMITE!!");
            System.out.println("TEMPO LIMITE!!");
            System.out.println("=-=-=-=-=-=");
        }

        return !raspberrySubsystem.getTV() ||
        excededMaxLoopTime;
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        driveBase.stopRobot();
    }
}
