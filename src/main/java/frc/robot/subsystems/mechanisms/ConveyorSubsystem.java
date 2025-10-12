package frc.robot.subsystems.mechanisms;

import frc.robot.subsystems.mechanisms.IO.ConveyorSubsystemIO;
import frc.robot.Constants.Conveyor;

import frc.FRC9485.motors.SparkMaxMotor;
import frc.FRC9485.utils.logger.CustomBooleanLog;
import frc.FRC9485.utils.logger.CustomDoubleLog;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class ConveyorSubsystem extends SubsystemBase implements ConveyorSubsystemIO{
    private static ConveyorSubsystem m_instance;

    private DigitalInput sensor;
    private SparkMaxMotor conveyor;

    // Logging
    private CustomDoubleLog speedLogger;
    private CustomBooleanLog hasBallLogger;
    private CustomBooleanLog isRunningLogger;

    private ConveyorSubsystem() {
        SmartDashboard.putData("Conveyor/Subsystem", this);
        speedLogger = new CustomDoubleLog("Conveyor/Speed");
        hasBallLogger = new CustomBooleanLog("Conveyor/Has Ball");
        isRunningLogger = new CustomBooleanLog("Conveyor/Is Running");

        this.sensor = new DigitalInput(Conveyor.OPTICAL_SENSOR_ID);
        this.conveyor = new SparkMaxMotor(
                Conveyor.CONVEYOR_MOTOR_ID,
                MotorType.kBrushed,
                false,
                "conveyor"
            );
    }

    public static ConveyorSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new ConveyorSubsystem();
        }

        return m_instance;
    } 

    @Override
    public void setSpeed(double speed) {
        this.conveyor.set(speed);
    }

    @Override
    public boolean getHasBall(){
        return !sensor.get();
    }

    @Override
    public double getSpeed() {
        return this.conveyor.get();
    }

    @Override
    public void stopMotor() {
        this.conveyor.set(0);
    }
   
    @Override
    public void periodic() {
        speedLogger.append(getSpeed());
        hasBallLogger.append(getHasBall());
        isRunningLogger.append(getSpeed() != 0);
    }

}
