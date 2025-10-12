package frc.robot.subsystems.mechanisms;

import frc.robot.Constants.Intake;
import frc.FRC9485.motors.SparkMaxMotor;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.robot.subsystems.mechanisms.IO.IntakeSubsystemIO;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase implements IntakeSubsystemIO{
    private static IntakeSubsystem m_instance;

    private SparkMaxMotor intakeMotor;

    // Logging
    private CustomDoubleLog speedLogger;

    private IntakeSubsystem() {
        SmartDashboard.putData("Intake/Subsystem", this);
        speedLogger = new CustomDoubleLog("Intake/Speed");

        this.intakeMotor = new SparkMaxMotor(
            Intake.INTAKE_MOTOR_ID,
            MotorType.kBrushless,
            true,
            "intake"
        );
    }

    public static IntakeSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new IntakeSubsystem();
        }

        return m_instance;
    } 

    @Override
    public void setSpeed(double speed) {
        this.intakeMotor.set(speed);
    }

    @Override
    public void stopMotor() {
        this.intakeMotor.set(0);
    }

    @Override
    public double getSpeed() {
        return this.intakeMotor.get();
    }

    @Override
    public void periodic() {
        speedLogger.append(getSpeed());
    }
}
