package frc.robot.subsystems.mechanisms;

import frc.robot.Constants.Pneumatics;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticSubsystem extends SubsystemBase{
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

    public void toggleSolenoid() {
        this.doubleSolenoid.toggle();
    }

    public double getPressure() {
        return this.pneumaticHub.getPressure(0);
    }

    public String getChaneelEnabled() {
        if (!this.doubleSolenoid.isFwdSolenoidDisabled()) { return "fwd"; }
        return "rev";
    }

    public boolean isFwdChannelDisabled() {
        return this.doubleSolenoid.isFwdSolenoidDisabled();
    }

    public boolean isRevChannelDisabled() {
        return this.doubleSolenoid.isRevSolenoidDisabled();
    }
    
    @Override
    public void periodic() {
        this.pneumaticHub.enableCompressorAnalog(min, max);
    }
}
