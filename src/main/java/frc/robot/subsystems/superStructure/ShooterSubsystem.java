package frc.robot.subsystems.superStructure;

import frc.FRC9485.motors.SparkMaxMotors;
import frc.robot.Constants.Shooter;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private static ShooterSubsystem m_instance;

    private SparkMaxMotors shooterDown;
    private SparkMaxMotors shooterUp;

    private ShooterSubsystem() {
        this.shooterDown = new SparkMaxMotors(
            Shooter.SHOOTER_DOWN_MOTOR_ID, 
            MotorType.kBrushless,
            true,
            "shooter-down"
        );
        
        this.shooterUp = new SparkMaxMotors(
            Shooter.SHOOTER_UP_MOTOR_ID, 
            MotorType.kBrushless,
            true,
            "shooter-up"
        );
    }

    public static ShooterSubsystem getInstance() {
        if (m_instance == null) {
            m_instance = new ShooterSubsystem();
        }

        return m_instance;
    } 

    public void setSpeed(double down, double up) {
        this.shooterDown.set(down);
        this.shooterUp.set(up);
    }

    public void setUpSpeed(double speed) {
        this.shooterUp.set(speed);
    }

    public void setDownSpeed(double speed) {
        this.shooterDown.set(speed);
    }

    public void stopUpMotor() {
        this.shooterUp.set(0);
    }

    public void stopDownMotor() {
        this.shooterDown.set(0);
    }

    public void stopMotors() {
        this.shooterDown.set(0);
        this.shooterUp.set(0);
    }
    
    public double getSpeedUp() {
        return this.shooterUp.get();
    }
    
    public double getspeedDown() {
        return this.shooterDown.get();
    }

}
