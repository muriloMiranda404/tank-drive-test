package frc.robot.subsystems.mechanisms;

import frc.robot.Constants.Pneumatics;
import frc.robot.subsystems.mechanisms.IO.PneumaticsSubsystemIO;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticSubsystem extends SubsystemBase implements PneumaticsSubsystemIO{
    private static PneumaticSubsystem m_instance;

    private double min;
    private double max;
    private PneumaticHub pneumaticHub;
    private DoubleSolenoid doubleSolenoid;

    private PneumaticSubsystem(double min, double max) {
        this.min = min;
        this.max = max;

        this.pneumaticHub = new PneumaticHub(Pneumatics.PNEUMATIC_HUB_ID);
        this.pneumaticHub.clearStickyFaults();
        
        this.doubleSolenoid = new DoubleSolenoid(
            PneumaticsModuleType.REVPH,
            Pneumatics.DOUBLE_SOLENOID_FORWARD_CHANNEL, 
            Pneumatics.DOUBLE_SOLENOID_REVERSE_CHANNEL
        );

    }

    public static PneumaticSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new PneumaticSubsystem(
                    Pneumatics.MIN_PRESSURE, 
                    Pneumatics.MAX_PRESSURE
                );
        }
        return m_instance;
    }

    @Override
    public void toggleSolenoid() {
        this.doubleSolenoid.toggle();
    }

    @Override
    public double getPressure() {
        return this.pneumaticHub.getPressure(0);
    }

    @Override
    public String getChannelEnabled() {
        if (!this.doubleSolenoid.isFwdSolenoidDisabled()) { return "fwd"; }
        return "rev";
    }

    @Override
    public boolean isFwdChannelDisabled() {
        return this.doubleSolenoid.isFwdSolenoidDisabled();
    }

    @Override
    public boolean isRevChannelDisabled() {
        return this.doubleSolenoid.isRevSolenoidDisabled();
    }
    
    @Override
    public void periodic() {
        this.pneumaticHub.enableCompressorAnalog(min, max);
    }
}
