package frc.robot.subsystems.mechanisms.IO;

import java.util.function.DoubleSupplier;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.Command;

public interface RackSubsystemIO {
    public void resetEncoder();
    public void setSpeed(double speed);
    
    public double getSpeed();
    public double getPosition();
    
    public RelativeEncoder getEncoder();
    public Command setAngulation(DoubleSupplier speed);
}
