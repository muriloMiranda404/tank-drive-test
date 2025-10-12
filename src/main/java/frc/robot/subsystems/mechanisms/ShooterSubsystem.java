package frc.robot.subsystems.mechanisms;

import frc.robot.Constants.Shooter;
import frc.FRC9485.motors.SparkMaxMotor;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.robot.subsystems.mechanisms.IO.ShooterSubsystemIO;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends SubsystemBase implements ShooterSubsystemIO{
    private static ShooterSubsystem m_instance;

    private SparkMaxMotor shooterUp;
    private SparkMaxMotor shooterDown;

    // Logging
    private CustomDoubleLog upSpeedLogger;
    private CustomDoubleLog downSpeedLogger;

    private ShooterSubsystem() {
        SmartDashboard.putData("Shooter/Subsystem", this);
        this.upSpeedLogger = new CustomDoubleLog("Shooter/Up Speed");
        this.downSpeedLogger = new CustomDoubleLog("Shooter/Down Speed");

        this.shooterDown = new SparkMaxMotor(
            Shooter.SHOOTER_DOWN_MOTOR_ID, 
            MotorType.kBrushless,
            true,
            "shooter-down"
        );
        
        this.shooterUp = new SparkMaxMotor(
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

    @Override
    public void setSpeed(double down, double up) {
        this.shooterDown.set(down);
        this.shooterUp.set(up);
    }
        
    @Override
    public void setUpSpeed(double speed) {
        this.shooterUp.set(speed);
    }
    
    @Override
    public void setDownSpeed(double speed) {
        this.shooterDown.set(speed);
    }

    @Override
    public void stopUpMotor() {
        this.shooterUp.set(0);
    }

    @Override
    public void stopDownMotor() {
        this.shooterDown.set(0);
    }

    @Override
    public void stopMotors() {
        this.shooterDown.set(0);
        this.shooterUp.set(0);
    }
    
    @Override
    public double getUpSpeed() {
        return this.shooterUp.get();
    }
    
    @Override
    public double getDownSpeed() {
        return this.shooterDown.get();
    }

    @Override
    public void periodic() {
        this.upSpeedLogger.append(getUpSpeed());
        this.downSpeedLogger.append(getDownSpeed());
    }
}
