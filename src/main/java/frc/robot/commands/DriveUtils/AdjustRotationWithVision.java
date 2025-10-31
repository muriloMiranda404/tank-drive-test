package frc.robot.commands.DriveUtils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.FRC9485.utils.Util;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;

public class AdjustRotationWithVision extends Command {
    private DriveBaseSubsystem driveBase;
    private RaspberrySubsystem raspberrySubsystem;

    private double tx;
    private boolean txIsNegative;

    private double rotation;
    private double lastLoopTime;
    private double currentLoopTime;

    private final Timer timer;

    private final double LOOP_TIME = 0.5;
    private final double CENTER_TX = 8.0;
    private final double MAX_TOLERANCE = 8.2;
    private final double MIN_TOLERANCE = 8.4;

    private final CustomDoubleLog startTX;
    private final CustomDoubleLog difrenceLogger;
    private final CustomDoubleLog lastTimeLogger;
    private final CustomDoubleLog currentTimeLogger;

    public AdjustRotationWithVision() {
        startTX = new CustomDoubleLog("Vision/Start TX");
        lastTimeLogger = new CustomDoubleLog("Vision/Last Time");
        currentTimeLogger = new CustomDoubleLog("Vision/Current Time");
        difrenceLogger = new CustomDoubleLog("Vision/Difference Timer Logger");
        timer = new Timer();
        this.driveBase = DriveBaseSubsystem.getInstance();
        this.raspberrySubsystem = RaspberrySubsystem.getInstance();
    }

    @Override
    public void initialize() {
        timer.start();
        timer.reset();
        lastLoopTime = timer.get();

        tx = raspberrySubsystem.getTX();
        txIsNegative = tx <= CENTER_TX ? true : false;

        System.out.println("TX Atual: " + tx);
        System.out.println("TX Negativo: " + txIsNegative);

        rotation = 0;

        // if (tx == 0) {
        //     rotation = 0;
        // } else {
        // if (txIsNegative) {
        //     rotation = 0.4;
        // } else {
        //     rotation = -0.4;
        // }
    // }
    }

    @Override
    public void execute() {
        currentLoopTime = timer.get();

        lastTimeLogger.append(lastLoopTime);
        currentTimeLogger.append(currentLoopTime);
        difrenceLogger.append(currentLoopTime - lastLoopTime);

        if (currentLoopTime < LOOP_TIME) {
            lastLoopTime = currentLoopTime;

            tx = raspberrySubsystem.getTX();
            txIsNegative = tx <= CENTER_TX ? true : false;
            startTX.append(tx);

            System.out.println("TX Atual: " + tx);
            System.out.println("TX Negativo: " + txIsNegative);
            
            if (tx == 0) {
                rotation = 0;
            } else {
                if (txIsNegative) {
                    rotation = 0.5;
                } else {
                    rotation = -0.5;
                }
            }
        } else {
            rotation = 0;
        }

        driveBase.drive(0, rotation);
    }

    @Override
    public boolean isFinished() {
        return !raspberrySubsystem.getTV() ||
        Util.inRange(tx, MIN_TOLERANCE, MAX_TOLERANCE);
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        driveBase.stopMotor();
    }
}
