package frc.robot.subsystems.IO;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;

public interface DriveBaseSubsystemIO {
    public void stopMotor();
    public Command driveTank(DoubleSupplier translation, DoubleSupplier rotation);
}
