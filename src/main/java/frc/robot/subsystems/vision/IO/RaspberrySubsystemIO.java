package frc.robot.subsystems.vision.IO;

public interface RaspberrySubsystemIO {
    public boolean getTV();
    public void setMinConf(double val);
    public String getClsName();
    
    public double getTX();
    public double getTY();
    public double getMinConf();
} 
