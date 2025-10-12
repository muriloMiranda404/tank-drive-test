package frc.robot.subsystems.IO;

import java.util.function.DoubleSupplier;
import frc.FRC9485.joysticks.DriverController;
import edu.wpi.first.wpilibj2.command.Command;

public interface DriveBaseSubsystemIO {
    public void stopMotor();
    public Command driveTank(DriverController controller, DoubleSupplier translation, DoubleSupplier rotation);
}
