package frc.robot.subsystems;

import frc.robot.Constants.Conveyor;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 


public class ConveyorSubsystem extends SubsystemBase {
    private SparkMax conveyor;
    private DigitalInput sensor;

    public ConveyorSubsystem() {
        this.sensor = new DigitalInput(Conveyor.SENSOR_ID);
        this.conveyor = new SparkMax(Conveyor.CONVEYOR_MOTOR_ID, MotorType.kBrushed);
    }

    public void setSpeed(double speed) {
        this.conveyor.set(speed);
    }

    public boolean ballInConveyor(){
        return !sensor.get();
    }

    public void stopMotor() {
        this.conveyor.set(0);
   }
   
}
