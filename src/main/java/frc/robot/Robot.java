package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot{
  RobotContainer m_robotContainer;
  Command m_autonomousCommand;

  @Override
  public void robotInit() {
    System.out.println("robo ligado!");
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
    m_robotContainer.rackSubsystem.resetEncoder();
  }
  
  @Override
  public void teleopPeriodic() {}
}