package frc.robot.subsystems.mechanisms;

import frc.FRC9485.motors.SparkMaxMotor;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.robot.Constants.Rack;
import frc.robot.subsystems.mechanisms.IO.RackSubsystemIO;

import java.util.function.DoubleSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RackSubsystem extends SubsystemBase implements RackSubsystemIO {
    private static RackSubsystem m_instance;

    // private SparkMaxMotor rackMotor;
    private SparkMaxMotor rackMotor;

    // Logging
    private CustomDoubleLog speedLogger;
    private CustomDoubleLog encoderPositionLogger;

    private RackSubsystem() {
        SmartDashboard.putData("Rack/Subsystem", this);
        speedLogger = new CustomDoubleLog("Rack/Speed");
        encoderPositionLogger = new CustomDoubleLog("Rack/Encoder Position");
        this.rackMotor = new SparkMaxMotor(
            Rack.RACK_MOTOR_ID, 
            MotorType.kBrushless,
            true,
            "rack-motor"
        );

        // this.rackMotor = new SparkMaxMotor(
        //     Rack.RACK_MOTOR_ID, 
        //     MotorType.kBrushless,
        //     true,
        //     "rack"
        // );
    }

    public static RackSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new RackSubsystem();
        }

        return m_instance;
    } 

    public RelativeEncoder getEncoder() {
        return this.rackMotor.getRelativeEncoder();
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
            } 
            else if (position <= Rack.MAX_DOWN && doubleSpeed < 0.0) {
                doubleSpeed = 0;
            }
            this.rackMotor.set(doubleSpeed);
        });
    }

    @Override
    public void periodic() {
        speedLogger.append(getSpeed());
        encoderPositionLogger.append(getPosition());
    }
}
