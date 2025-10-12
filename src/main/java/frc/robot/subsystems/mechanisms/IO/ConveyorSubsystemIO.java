package frc.robot.subsystems.mechanisms.IO;

public interface ConveyorSubsystemIO {
    public void stopMotor();
    public boolean getHasBall();
    public void setSpeed(double speed);
}
