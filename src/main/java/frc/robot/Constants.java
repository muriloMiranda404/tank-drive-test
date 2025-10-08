package frc.robot;

public final class Constants {

  public static final class Controllers {
    public static final int DRIVER_CONTROLLER = 0;
    public static final int SUBSYSTEMS_CONTROLLER = 1;
  }

  public static final class DriveBase {
    public static final int LEFT_MOTOR_LEADER_ID = 2;
    public static final int LEFT_MOTOR_FOLLOW_ID = 4;
    public static final int RIGHT_MOTOR_LEADER_ID = 1; 
    public static final int RIGHT_MOTOR_FOLLOW_ID = 3; 
  }

  public static final class Shooter {
    public static final int SHOOTER_UP_MOTOR_ID = 8;  
    public static final int SHOOTER_DOWN_MOTOR_ID = 6;     
  }

  public static final class Rack {
    public static final double MAX_UP = 118.0;
    public static final double MAX_DOWN = 1.0;
    public static final int RACK_MOTOR_ID = 5;
  }

  public static final class Intake {
    public static final int INTAKE_MOTOR_ID = 9;
    public static final int PNEUMATIC_HUB_ID = 1;
    public static final int DOUBLE_SOLENOID_FORWARD_CHANNEL = 0;
    public static final int DOUBLE_SOLENOID_REVERSE_CHANNEL = 2;
  }

  public static final class Conveyor {
    public static final int CONVEYOR_MOTOR_ID = 7;
    public static final int OPTICAL_SENSOR_ID = 1;
  }  

}
