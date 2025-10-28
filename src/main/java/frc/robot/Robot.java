package frc.robot;

import edu.wpi.first.net.WebServer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.mechanisms.RackSubsystem;

public class Robot extends TimedRobot{
  Command m_autonomousCommand;
  RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    System.out.println("robo ligado!");
    WebServer.start(5800, Filesystem.getDeployDirectory().getPath());
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    RackSubsystem.getInstance().resetEncoder();
  }
}