package frc.robot.subsystems.superStructure;

import frc.robot.Constants.Intake;

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

        this.pneumaticHub = new PneumaticHub(Intake.PNEUMATIC_HUB_ID);
        this.pneumaticHub.clearStickyFaults();
        
        this.doubleSolenoid = new DoubleSolenoid(
            PneumaticsModuleType.REVPH,
            Intake.DOUBLE_SOLENOID_FORWARD_CHANNEL, 
            Intake.DOUBLE_SOLENOID_REVERSE_CHANNEL
        );

    }

    public static PneumaticSubsystem getInstance(double min, double max) {
        if (m_instance == null) {
            m_instance = new PneumaticSubsystem(min, max);
        }
        return m_instance;
    }

    public void toggledSolenoid() {
        this.doubleSolenoid.toggle();
    }

    public double getPressure() {
        return this.pneumaticHub.getPressure(0);
    }

    public String getChaneelEnable() {
        if (!this.doubleSolenoid.isFwdSolenoidDisabled()) { return "fwd"; }
        return "rev";
    }

    public boolean getFwdChannelDisabled() {
        return this.doubleSolenoid.isFwdSolenoidDisabled();
    }

    public boolean getRevChannelDisabled() {
        return this.doubleSolenoid.isRevSolenoidDisabled();
    }
    
    @Override
    public void periodic() {
        this.pneumaticHub.enableCompressorAnalog(min, max);
    }
}
