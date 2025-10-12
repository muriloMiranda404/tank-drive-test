package frc.robot.subsystems.mechanisms.IO;

public interface ConveyorSubsystemIO {
    public void stopMotor();
    public boolean getHasBall();
    public double getSpeed();
    public void setSpeed(double speed);
}
