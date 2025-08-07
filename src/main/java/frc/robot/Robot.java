package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.TankSubsystem;

public class Robot extends TimedRobot{
  private RobotContainer m_robotContainer;
  TankSubsystem tankSubsystem;
  XboxController controller;
  private Command m_autonomousCommand;

  @Override
  public void robotInit() {
    System.out.println("robo ligado!");
    m_robotContainer = new RobotContainer();

    tankSubsystem = new TankSubsystem();
    controller = new XboxController(0);
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
  }
  
  @Override
  public void teleopPeriodic() {
    tankSubsystem.driveTank(controller.getLeftY(), controller.getRightX());
  }
}