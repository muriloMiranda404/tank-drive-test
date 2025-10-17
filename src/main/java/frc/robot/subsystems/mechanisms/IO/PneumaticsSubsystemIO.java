package frc.robot.subsystems.mechanisms.IO;

public interface PneumaticsSubsystemIO {
    public void openSolenoid();
    public void closeSolenoid();
    public double getPressure();
    public String getChannelEnabled();
    public boolean isFwdChannelDisabled();
    public boolean isRevChannelDisabled();
}
