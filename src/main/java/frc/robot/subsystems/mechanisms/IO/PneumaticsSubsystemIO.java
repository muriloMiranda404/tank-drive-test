package frc.robot.subsystems.mechanisms.IO;

public interface PneumaticsSubsystemIO {
    public void toggleSolenoid();
    public double getPressure();
    public String getChannelEnabled();
    public boolean isFwdChannelDisabled();
    public boolean isRevChannelDisabled();
}
