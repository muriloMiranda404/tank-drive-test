package frc.FRC9485.motors;

import java.util.function.Supplier;

import com.revrobotics.REVLibError;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.math.controller.PIDController;

import static edu.wpi.first.units.Units.Milliseconds;
import static edu.wpi.first.units.Units.Seconds;

import frc.FRC9485.exceptions.IllegalRobotState;

public class SparkMaxMotor extends SparkMaxMotorBase {

    private int id;
    private String name;
    private boolean isInverted;
    private boolean usingInternalEncoder;
    private SparkMaxConfig config;
    private SparkMax motor;
    private PIDController controller;
    private double percentOutput = 0;

    public SparkMaxMotor(int id, MotorType motorType, boolean usingInternalEncoder, String name){
        this.id = id;
        this.name = name;
        this.isInverted = false;
        this.config = new SparkMaxConfig();
        this.usingInternalEncoder = usingInternalEncoder;
        this.motor = new SparkMax(id, motorType);
        clearStickyFaults();
    }
    
    @Override
    public void set(double voltage) {
       motor.set(voltage);
    }

    @Override
    public void setSpeed(double speed) {
        if(speed != percentOutput){
            motor.getClosedLoopController().setReference(speed, ControlType.kDutyCycle, ClosedLoopSlot.kSlot0, 0);
        }

        this.percentOutput = speed;
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.set(0);
    }

    @Override
    public void setInverted(boolean inverted) {
        config.inverted(inverted);
    }

    @Override
    public void clearStickyFaults() {
        configureSpark(motor::clearFaults);
    }

    @Override
    public void setPosition(double position) {
        motor.getEncoder().setPosition(position);
    }

    @Override
    public void setReferencePosition(double position){
        if(!atSetpoint(position)){
            motor.getClosedLoopController().setReference(position, ControlType.kPosition);
        }
    }

    @Override
    public void updateConfig(SparkMaxConfig config) {
        if(DriverStation.isEnabled()){
            throw new IllegalRobotState("The config cannot be changed while the robot is enabled");
        }

        this.config.apply(config);
        configureSpark(() -> motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters));
    }

    @Override
    public void followMotor(int id, boolean inverted) {
        config.follow(id);
        config.inverted(inverted);
        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void setPID(double Kp, double Ki, double Kd) {
        controller.setP(Kp);
        controller.setI(Ki);
        controller.setD(Kd);
    }

    @Override
    public void setRampRate(double ramp) {
        config.openLoopRampRate(ramp)
        .closedLoopRampRate(ramp);

        if(DriverStation.isEnabled()){
            throw new IllegalRobotState("The ramp cannot be changed while the robot is enabled");
        }
        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void configureSpark(Supplier<REVLibError> config) {
        for(int i = 0; i < 5; i++){
            if(config.get() == REVLibError.kError){
                DriverStation.reportError("falha no motor: " + getMotorId(), true);
                return;
            }
            Timer.delay(Milliseconds.of(5).in(Seconds));
        }
    }

    @Override
    public int getMotorId() {
        return id;
    }

    @Override
    public String getMotorName() {
        return name;
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public double getPosition(){
        return motor.getEncoder().getPosition();
    }

    @Override
    public double getMotorOutput(){
        return motor.getAppliedOutput();
    }

    @Override
    public double getMotorTemperature() {
       return motor.getMotorTemperature();
    }

    @Override
    public double getVoltage() {
       return motor.getAppliedOutput() * motor.getBusVoltage();
    }

    @Override
    public boolean getInverted() {
        return isInverted;
    }

    @Override
    public boolean usingInternalEncoder() {
        return usingInternalEncoder;
    }

    @Override
    public SparkMax getSpark(){
        return motor;
    }

    @Override
    public AbsoluteEncoder getAbsoluteEncoder(boolean usingAbsoluteEncoder) {
       if(usingAbsoluteEncoder){
        return motor.getAbsoluteEncoder();
       } else{
       return null;
        }
    }

    @Override
    public RelativeEncoder getAlternativeEncoder(boolean usingRelativeEncoder) {
        if(usingRelativeEncoder){
            return motor.getAlternateEncoder();
        } else{
        return null;
    }
    }

}
