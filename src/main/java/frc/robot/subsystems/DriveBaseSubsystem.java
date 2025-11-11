package frc.robot.subsystems;

import frc.FRC9485.motors.SparkMaxMotor;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.FRC9485.joysticks.DriverController;

import frc.robot.Constants.DriveBase;
import frc.robot.subsystems.IO.DriveBaseSubsystemIO;

import java.util.function.DoubleSupplier;
 
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBaseSubsystem extends SubsystemBase implements DriveBaseSubsystemIO {
  private static DriveBaseSubsystem m_instance;

  private SparkMaxMotor rightLeader;
  private SparkMaxMotor rightFollow;
  private SparkMaxMotor leftLeader;
  private SparkMaxMotor leftFollow;

  private DifferentialDrive driver;
  private DriverController driverController;

  private SparkMaxConfig global;
  private SparkMaxConfig rightLeaderConfig;
  private SparkMaxConfig rightFollowConfig;
  private SparkMaxConfig leftLeaderConfig;
  private SparkMaxConfig leftFollowConfig;

  private CustomDoubleLog leftLeaderSpeedLogger = new CustomDoubleLog("Tank/Right Leader Speed");
  private CustomDoubleLog leftFollowSpeedLogger = new CustomDoubleLog("Tank/Right Follow Speed");
  private CustomDoubleLog rightLeaderSpeedLogger = new CustomDoubleLog("Tank/Left Leader Speed");
  private CustomDoubleLog rightFollowSpeedLogger = new CustomDoubleLog("Tank/Left Follow Speed");
  
  private DriveBaseSubsystem() {
    setupSparks();
    driverController = DriverController.getInstance();
    
    SmartDashboard.putData("Tank/Subsystem", this);
    SmartDashboard.putData("Tank/DifferentialDrive", driver);
  }

  private void setupSparks() {
    this.leftLeader = new SparkMaxMotor(
      DriveBase.LEFT_MOTOR_LEADER_ID, 
      SparkMax.MotorType.kBrushed, 
      false,
      "left-leader"
    );

    this.leftFollow = new SparkMaxMotor(
      DriveBase.LEFT_MOTOR_FOLLOW_ID,
      SparkMax.MotorType.kBrushed,
      false,
      "left-follow"
    );

    this.rightLeader = new SparkMaxMotor(
      DriveBase.RIGHT_MOTOR_LEADER_ID,
      SparkMax.MotorType.kBrushed,
      false,
      "right-leader"
    );

    this.rightFollow = new SparkMaxMotor(
      DriveBase.RIGHT_MOTOR_FOLLOW_ID,
      SparkMax.MotorType.kBrushed,
      false,
      "right-follow"
    );

    this.global = new SparkMaxConfig();
    this.rightLeaderConfig = new SparkMaxConfig();
    this.rightFollowConfig = new SparkMaxConfig();
    this.leftLeaderConfig = new SparkMaxConfig();
    this.leftFollowConfig = new SparkMaxConfig();

    this.global
    .idleMode(IdleMode.kCoast);

    this.rightLeaderConfig
    .inverted(true)
    .apply(global);

    this.rightFollowConfig
    .inverted(false)
    .follow(rightLeader.getSpark())
    .apply(global); 

    this.leftLeaderConfig
    .inverted(true)
    .apply(global);

    this.leftFollowConfig
    .inverted(false)
    .follow(leftLeader.getSpark())
    .apply(global);

    this.leftLeader.updateConfig(leftLeaderConfig);
    this.rightLeader.updateConfig(rightLeaderConfig);
    
    this.leftFollow.updateConfig(leftFollowConfig);
    this.rightFollow.updateConfig(rightFollowConfig);

    this.driver = new DifferentialDrive(leftLeader, rightLeader);
  }

  public static DriveBaseSubsystem getInstance() {
    if (m_instance == null) {
      m_instance = new DriveBaseSubsystem();
    }
    return m_instance;
  }

  private double[] adjustVelocity(double translation, double rotation, boolean useJoystickGear) {
    if (useJoystickGear) {
      // Marcha
      if (driverController.getSlowGear()) {
        translation *= 0.5;
      } else if (driverController.getFastGear()) {
        translation *= 1.0;
      } else if (driverController.getSlowGear() && driverController.getFastGear()) {
        translation *= 0.7;
      } else {
        translation *= 0.7;
      }
    }
    
    // Aliança
    var alliance = DriverStation.getAlliance();

    if (alliance.get() == Alliance.Blue) {
      translation *= 1;
      rotation *= 1;
    } else {
      translation *= -1;
      rotation *= -1;
    }
    double[] ajustado = new double[2];
    ajustado[0] = translation;
    ajustado[1] = rotation;

    return ajustado;
  }

// private double smoothAcceleration(double doubleTranslation) {
//   int mode = 0;
//   final int NORMAL = 1;
//   final int RAMPING_UP = 0;
//   final int RAMPING_DOWN = 2;
//   double capture_value = 0, output = 0, oldInput = 0;

//   double RAMP_UP_CONSTANT = 0.2;
//   double RAMP_DOWN_CONSTANT = 0.2;

//   //rate of change in joystick values.
//   double delta = doubleTranslation - oldInput;
//   double DELTA_LIMIT = 0.5;

//   //is joystick being moved too fast?
//   if(delta >= DELTA_LIMIT) { 
//     mode=RAMPING_UP; 
//     capture_value = doubleTranslation;
//   }else if(delta <= DELTA_LIMIT) { 
//     mode=RAMPING_DOWN; 
//     capture_value = doubleTranslation;
//   } 

//   //output integration
//   switch(mode){
//     case RAMPING_UP: 
//       output += RAMP_UP_CONSTANT;
//       if(output >= capture_value) { mode = NORMAL; }
//       break;
      
//     case RAMPING_DOWN:
//       output -= RAMP_DOWN_CONSTANT;
//       if(output <= capture_value) { mode = NORMAL; }
//       break;
      
//     case NORMAL:
//       output = doubleTranslation;
//       break;
//     default: break;
//   }

//   //Keep values for next loop
//   oldInput = doubleTranslation;

//   return doubleTranslation;
// }

  @Override
  public Command driveTank(DoubleSupplier translation, DoubleSupplier rotation) {    
    return run(() -> {
      double doubleTranslation = translation.getAsDouble();
      double doubleRotation = rotation.getAsDouble();

      double[] ajustado = adjustVelocity(doubleTranslation, doubleRotation, true);
      doubleTranslation = ajustado[0];
      doubleRotation = ajustado[1] * 0.7;

      driver.arcadeDrive(doubleRotation, doubleTranslation);
    });
  }

  public void drive(double translation, double rotation) {
    double[] ajustado = adjustVelocity(translation, rotation, false);
    driver.tankDrive(ajustado[0], ajustado[1]);
  }

  public double getSpeed() {
    double speeds = leftLeader.get() + leftFollow.get() + rightLeader.get() + rightFollow.get();
    return speeds / 4.0;
  }

  @Override
  public void stopRobot() {
    driver.stopMotor();
  }

  @Override
  public void periodic() {
    rightLeaderSpeedLogger.append(rightLeader.get());
    rightFollowSpeedLogger.append(rightFollow.get());
    leftLeaderSpeedLogger.append(leftLeader.get());
    leftFollowSpeedLogger.append(leftFollow.get());
  }

}