package frc.robot.subsystems;

import frc.robot.Constants.Intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private static IntakeSubsystem m_instance;

    private SparkMax intakeMotor;
    // private PneumaticHub pneumaticHub;
    // private DoubleSolenoid doubleSolenoid;

    private IntakeSubsystem() {
        // this.pneumaticHub = new PneumaticHub(Intake.PNEUMATIC_HUB_ID);
        this.intakeMotor = new SparkMax(Intake.INTAKE_MOTOR_ID, MotorType.kBrushless);
        
        // this.doubleSolenoid = new DoubleSolenoid(
        //     PneumaticsModuleType.REVPH,
        //     Intake.DOUBLE_SOLENOID_FORWARD_CHANNEL, 
        //     Intake.DOUBLE_SOLENOID_REVERSE_CHANNEL
        // );
    }

    public static IntakeSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new IntakeSubsystem();
        }

        return m_instance;
    } 

    public void setSpeed(double speed) {
        this.intakeMotor.set(speed);
    }

    public void stopMotor() {
        this.intakeMotor.set(0);
    }

    @Override
    public void periodic() {
        // pneumaticHub.clearStickyFaults();
        // System.out.println("pressao: " + ptneumaticHub.getPressure(0));
        // pneumaticHub.enableCompressorAnalog(30, 50);
    }
    
}
