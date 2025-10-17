package frc.FRC9485.motors;

import java.util.function.Supplier;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class SparkMaxMotorBase implements MotorIO, MotorController {
    @Override
    public abstract void configureSpark(Supplier<REVLibError> config);

    @Override
    public abstract void setReferencePosition(double position);

    @Override
    public abstract void updateConfig(SparkMaxConfig config);

    @Override
    public abstract void clearStickyFaults();

    @Override
    public abstract void disable();

    @Override
    public abstract void setInverted(boolean inverted);
        
    @Override
    public abstract void setSpeed(double speed);

    @Override
    public abstract void setPosition(double position);

    @Override
    public abstract void set(double voltage);

    @Override
    public abstract void followMotor(int id, boolean inverted);

    @Override
    public abstract void setPID(double Kp, double Ki, double Kd);

    @Override
    public abstract void setRampRate(double ramp);
    
    @Override
    public abstract int getMotorId();

    @Override
    public abstract String getMotorName();
    
    @Override
    public abstract double get();
    
    @Override
    public abstract double getVoltage();

    @Override
    public abstract double getPosition();

    @Override
    public abstract double getMotorOutput();

    @Override
    public abstract double getMotorTemperature();

    @Override
    public abstract boolean getInverted();
    
    @Override
    public abstract boolean usingInternalEncoder();

    @Override
    public abstract SparkMax getSpark();
    
    @Override
    public abstract AbsoluteEncoder getAbsoluteEncoder();

    @Override
    public abstract RelativeEncoder getAlternativeEncoder(boolean usingRelativeEncoder);

    @Override
    public abstract RelativeEncoder getRelativeEncoder();

    @Override
    public boolean atSetpoint(double setpoint){
        return Math.abs(getPosition() - setpoint) <= 0.001;
    }
}
