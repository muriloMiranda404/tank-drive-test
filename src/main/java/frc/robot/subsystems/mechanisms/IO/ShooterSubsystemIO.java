package frc.robot.subsystems.mechanisms.IO;

public interface ShooterSubsystemIO {
    public void stopMotors();
    public void stopUpMotor();
    public void stopDownMotor();
    public void setUpSpeed(double speed);
    public void setDownSpeed(double speed);
    public void setSpeed(double down, double up);
    
    public double getUpSpeed();
    public double getDownSpeed();
}