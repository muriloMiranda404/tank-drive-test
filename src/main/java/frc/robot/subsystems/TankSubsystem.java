package frc.robot.subsystems;

import frc.robot.Constants.Tracao;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankSubsystem extends SubsystemBase {
  private static TankSubsystem m_instance;

  private SparkMax rightLeader;
  private SparkMax rightFollow;
  private SparkMax leftLeader;
  private SparkMax leftFollow;

  private DifferentialDrive driver;

  private SparkMaxConfig global;
  private SparkMaxConfig rightLeaderConfig;
  private SparkMaxConfig rightFollowConfig;
  private SparkMaxConfig leftLeaderConfig;
  private SparkMaxConfig leftFollowConfig;
  
  private TankSubsystem() {
    this.leftLeader = new SparkMax(Tracao.LEFT_MOTOR_LEADER_ID, SparkMax.MotorType.kBrushed);
    this.leftFollow = new SparkMax(Tracao.LEFT_MOTOR_FOLLOW_ID, SparkMax.MotorType.kBrushed);

    this.rightLeader  = new SparkMax(Tracao.RIGHT_MOTOR_LEADER_ID, SparkMax.MotorType.kBrushed);
    this.rightFollow = new SparkMax(Tracao.RIGHT_MOTOR_FOLLOW_ID, SparkMax.MotorType.kBrushed);

    this.global = new SparkMaxConfig();
    this.rightLeaderConfig = new SparkMaxConfig();
    this.rightFollowConfig = new SparkMaxConfig();
    this.leftLeaderConfig = new SparkMaxConfig();
    this.leftFollowConfig = new SparkMaxConfig();

    this.driver = new DifferentialDrive(rightLeader, leftLeader);

    this.global
    .idleMode(IdleMode.kBrake);

    this.rightLeaderConfig
    .inverted(true)
    .apply(global);

    this.rightFollowConfig
    .inverted(false)
    .follow(rightLeader)
    .apply(global);

    this.leftLeaderConfig
    .inverted(false)
    .apply(global);

    this.leftFollowConfig
    .inverted(true)
    .follow(leftLeader)
    .apply(global);

    this.leftLeader.configure(leftLeaderConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    this.rightLeader.configure(rightLeaderConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    this.leftFollow.configure(leftFollowConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    this.rightFollow.configure(rightFollowConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public static TankSubsystem getInstance() {
    if (m_instance == null) {
      m_instance = new TankSubsystem();
    }
    return m_instance;
  }

  private double[] adjustVelocity(XboxController joystick, double speed, double rotation) {
    // Marcha
    if (joystick.getLeftBumperButton()) {
      speed *= 0.5;
    } else if (joystick.getRightBumperButton()) {
      speed *= 1.0;
    } else if (joystick.getLeftBumperButton() && joystick.getRightBumperButton()) {
      speed *= 0.7;
    } else {
      speed *= 0.7;
    }
    
    // Aliança
    var alliance = DriverStation.getAlliance();

    if (alliance.get() == Alliance.Blue) {
      speed *= 1;
      rotation *= 1;
    } else {
      speed *= -1;
      rotation *= -1;
    }
    double[] ajustado = new double[2];
    ajustado[0] = speed;
    ajustado[1] = rotation;

    return ajustado;
  }

  private double smoothAcceleration(double doubleSpeed) {
    double capture_value = 0, output = 0, oldInput = 0;
      int mode = 0;
      final int NORMAL = 1;
      final int RAMPING_UP = 0;
      final int RAMPING_DOWN = 2;

      double RAMP_UP_CONSTANT = 0.2;
      double RAMP_DOWN_CONSTANT = 0.5;

      //rate of change in joystick values.
      double delta = doubleSpeed - oldInput;
      double DELTA_LIMIT = 0.5;

      //is joystick being moved too fast?
      if(delta >= DELTA_LIMIT) { 
        mode=RAMPING_UP; 
        capture_value = doubleSpeed;
      }else if(delta <= DELTA_LIMIT) { 
        mode=RAMPING_DOWN; 
        capture_value = doubleSpeed;
      } 

      //output integration
      switch(mode){
        case RAMPING_UP: 
          output += RAMP_UP_CONSTANT;
          if(output >= capture_value) { mode = NORMAL; }
          break;
          
        case RAMPING_DOWN:
          output -= RAMP_DOWN_CONSTANT;
          if(output <= capture_value) { mode = NORMAL; }
          break;
          
        case NORMAL:
          output = doubleSpeed;
          break;
        default: break;
      }

      //Keep values for next loop
      oldInput = doubleSpeed;

      return doubleSpeed;
  }

  public Command driveTank(XboxController controller, DoubleSupplier speed, DoubleSupplier rotation){
    return run(() -> {
      double doubleSpeed = speed.getAsDouble();
      double doubleRotation = rotation.getAsDouble();

      double[] ajustado = adjustVelocity(controller, doubleSpeed, doubleRotation);
      doubleSpeed = ajustado[0];
      doubleRotation = ajustado[1] * -0.7;

      doubleSpeed = smoothAcceleration(doubleSpeed);
      
      driver.arcadeDrive(doubleSpeed, doubleRotation);
    });
  }

  public void stopMotor(){
    driver.stopMotor();
  }

}