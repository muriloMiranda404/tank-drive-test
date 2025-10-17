package frc.FRC9485.motors;

import java.util.function.Supplier;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public interface MotorIO {

    void set(double voltage);

    void setSpeed(double speed);

    void followMotor(int id, boolean inverted);

    void setPosition(double position);

    void setPID(double Kp, double Ki, double Kd);

    void clearStickyFaults();

    void setReferencePosition(double position);

    void setRampRate(double ramp);

    void configureSpark(Supplier<REVLibError> config); 

    void updateConfig(SparkMaxConfig config);

    int getMotorId();

    String getMotorName();

    SparkMax getSpark();
    
    double getVoltage();

    double getPosition();

    double getMotorTemperature();

    double getMotorOutput();

    boolean usingInternalEncoder();

    boolean atSetpoint(double setpoint);
    
    AbsoluteEncoder getAbsoluteEncoder();

    RelativeEncoder getAlternativeEncoder(boolean usingRelativeEncoder);

    RelativeEncoder getRelativeEncoder();

}
