package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Tracao;

public class TankSubsystem extends SubsystemBase{

  SparkMax rightleader;
  SparkMax rightfollow;
  SparkMax leftleader;
  SparkMax leftfollow;

  DifferentialDrive driver;

  private SparkMaxConfig global;
  private SparkMaxConfig right_leader_config;
  private SparkMaxConfig right_follow_config;
  private SparkMaxConfig left_leader_config;
  private SparkMaxConfig left_follow_config;
  
  public TankSubsystem() {
    rightleader  = new SparkMax(Tracao.RIGHT_MOTOR_LEADER_ID, SparkMax.MotorType.kBrushed);
    // rightfollow = new SparkMax(Tracao.RIGHT_MOTOR_FOLLOW_ID, SparkMax.MotorType.kBrushed);
    leftleader = new SparkMax(Tracao.LEFT_MOTOR_LEADER_ID, SparkMax.MotorType.kBrushed);
    leftfollow = new SparkMax(Tracao.LEFT_MOTOR_FOLLOW_ID, SparkMax.MotorType.kBrushed);

    global = new SparkMaxConfig();
    right_leader_config = new SparkMaxConfig();
    right_follow_config = new SparkMaxConfig();
    left_leader_config = new SparkMaxConfig();
    left_follow_config = new SparkMaxConfig();

    driver = new DifferentialDrive(rightleader, leftleader);

    global
    .idleMode(IdleMode.kBrake);

    right_leader_config
    .inverted(true)
    .apply(global);

    right_follow_config
    .inverted(false)
    .follow(rightleader)
    .apply(global);

    left_leader_config
    .inverted(false)
    .apply(global);

    left_follow_config
    .inverted(true)
    .follow(leftleader)
    .apply(global);

    leftleader.configure(left_leader_config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    rightleader.configure(right_leader_config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    leftfollow.configure(left_follow_config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    rightfollow.configure(right_follow_config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public void driveTank(double FirstInput, double secondInput){
      driver.arcadeDrive(FirstInput, secondInput);
  }

  public void stopMotor(){
    driver.stopMotor();
  }

}