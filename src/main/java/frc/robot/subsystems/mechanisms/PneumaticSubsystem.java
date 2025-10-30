package frc.robot.subsystems.mechanisms;

import frc.FRC9485.utils.logger.CustomBooleanLog;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.FRC9485.utils.logger.CustomStringLog;
import frc.robot.Constants.Pneumatics;
import frc.robot.subsystems.mechanisms.IO.PneumaticsSubsystemIO;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticSubsystem extends SubsystemBase implements PneumaticsSubsystemIO{
    private static PneumaticSubsystem m_instance;

    private double min;
    private double max;
    private PneumaticHub pneumaticHub;
    private DoubleSolenoid doubleSolenoid;

    // Logging
    private CustomDoubleLog currentPressureLogger;
    private CustomBooleanLog fwdChannelEnabledLogger;
    private CustomBooleanLog revChannelEnabledLogger;
    private CustomStringLog currentChannelEnabledLogger;

    private PneumaticSubsystem(double min, double max) {
        SmartDashboard.putData("Pneumatics/Subsystem", this);
        currentPressureLogger = new CustomDoubleLog("Pneumatics/Current Pressure");
        fwdChannelEnabledLogger = new CustomBooleanLog("Pneumatics/Fwd Channel Enabled");
        revChannelEnabledLogger = new CustomBooleanLog("Pneumatics/Rev Channel Enabled");
        currentChannelEnabledLogger = new CustomStringLog("Pneumatics/Current Channel Enabled");

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
    public void closeSolenoid() {
        this.doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    @Override
    public void openSolenoid() {
        this.doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public double getPressure() {
        return this.pneumaticHub.getPressure(0);
    }

    @Override
    public String getChannelEnabled() {
        if (doubleSolenoid.get() == DoubleSolenoid.Value.kForward) { return "fwd"; }
        else if (doubleSolenoid.get() == DoubleSolenoid.Value.kReverse) { return "rev"; }
        return "off";
    }

    @Override
    public boolean isFwdChannelEnabled() {
        return doubleSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    @Override
    public boolean isRevChannelEnabled() {
        return doubleSolenoid.get() == DoubleSolenoid.Value.kReverse;
    }
    
    @Override
    public void periodic() {
        pneumaticHub.enableCompressorAnalog(min, max);
        currentPressureLogger.append(getPressure());
        revChannelEnabledLogger.append(isRevChannelEnabled());
        fwdChannelEnabledLogger.append(isFwdChannelEnabled());
        currentChannelEnabledLogger.append(getChannelEnabled());
    }

    // @Override
    // public SequentialCommandGroup openIntakeWithVision() {
    //     if (raspberrySubsystem.getTV()) {
    //         return new SequentialCommandGroup(
    //             new OpenIntake(),
    //             new EnableIntakeMotor(),
    //             new EnableConveyorMotor(true)
    //         );
    //     } else {
    //         return new SequentialCommandGroup(
    //             new CloseIntake(),
    //             new StopIntakeMotor(),
    //             new StopConveyorMotor()
    //         );
    //     }
    // }
}
