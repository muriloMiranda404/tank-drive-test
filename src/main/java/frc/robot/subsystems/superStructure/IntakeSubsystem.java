package frc.robot.subsystems.superStructure;

import frc.robot.Constants.Intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private static IntakeSubsystem m_instance;

    private SparkMax intakeMotor;


    private IntakeSubsystem() {
        this.intakeMotor = new SparkMax(Intake.INTAKE_MOTOR_ID, MotorType.kBrushless);
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
}
