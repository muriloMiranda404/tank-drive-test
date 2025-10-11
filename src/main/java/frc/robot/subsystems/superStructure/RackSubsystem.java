package frc.robot.subsystems.superStructure;

import frc.FRC9485.motors.SparkMaxMotors;
import frc.robot.Constants.Rack;

import java.util.function.DoubleSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RackSubsystem extends SubsystemBase {
    private static RackSubsystem m_instance;
    
    private SparkMaxMotors rackMotor;

    private RackSubsystem() {
        this.rackMotor = new SparkMaxMotors(
            Rack.RACK_MOTOR_ID, 
            MotorType.kBrushless,
            true,
            "rack"
        );
    }

    public static RackSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new RackSubsystem();
        }

        return m_instance;
    } 

    public RelativeEncoder getEncoder() {
        return this.rackMotor.getAlternativeEncoder(true);
    }
    
    public double getSpeed() {
        return this.getEncoder().getVelocity();
    }

    public double getPosition() {
        return this.getEncoder().getPosition();
    }

    public void setSpeed(double speed) {
        this.rackMotor.set(speed);
    }

    public void resetEncoder() {
        this.getEncoder().setPosition(0);
    }

    public Command setAngulation(DoubleSupplier speed) {
        return run(() -> {
            double position = this.getPosition();
            double doubleSpeed = speed.getAsDouble();

            if (position >= Rack.MAX_UP && doubleSpeed > 0.0) {
                doubleSpeed = 0;
            } else if (position <= Rack.MAX_DOWN && doubleSpeed < 0.0) {
                doubleSpeed = 0;
            }

            this.rackMotor.set(speed.getAsDouble());
        });
    }

    @Override
    public void periodic() {
    }
}
