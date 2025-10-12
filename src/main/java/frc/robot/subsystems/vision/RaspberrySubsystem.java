package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.vision.IO.RaspberrySubsystemIO;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class RaspberrySubsystem extends SubsystemBase implements RaspberrySubsystemIO{
    private static RaspberrySubsystem m_instance;
    
    // NetworkTables config
    private NetworkTable visionTable;
    private NetworkTable configTable;
    private NetworkTable raspberryTable;
    private NetworkTable detectionsTable;

    // Shuflleboard Logging
    private ShuffleboardTab shuffleboardTab;
    private GenericEntry shuffleboardEntrys[] = new GenericEntry[3]; 

    private RaspberrySubsystem(String raspberryTablePath) {
        this.raspberryTable = NetworkTableInstance.getDefault().getTable(raspberryTablePath);
        this.detectionsTable = raspberryTable.getSubTable("detections");
        this.visionTable = detectionsTable.getSubTable("vision");
        this.configTable = detectionsTable.getSubTable("config");
        this.shuffleboardEntrys = this.initializeShuffleboardLogging(raspberryTablePath);
    }

    private GenericEntry[] initializeShuffleboardLogging(String raspberryTablePath) {
        final double MIN_CONF = 0.7;

        GenericEntry entrys[] = new GenericEntry[3];

        this.shuffleboardTab = Shuffleboard.getTab(raspberryTablePath);
        ShuffleboardLayout targetInformationsLayout =
          this.shuffleboardTab.getLayout("Target Informations", BuiltInLayouts.kList);
        
        targetInformationsLayout
          .withPosition(0, 0)
          .withSize(3, 2);
        
        GenericEntry tx = 
          targetInformationsLayout
          .add("TX", 0)
          .getEntry();

        GenericEntry ty = 
          targetInformationsLayout
          .add("TY", 0)
          .getEntry();
        
        GenericEntry tv =
          targetInformationsLayout
          .add("TV", false)
          .getEntry();

        entrys[0] = tx;
        entrys[1] = ty;
        entrys[2] = tv;
        
        this.shuffleboardTab
        .add("min_conf", MIN_CONF)
        .withWidget("Number Slider")
        .withPosition(3, 0)
        .withSize(1, 1);

        return entrys;
    }

    private void updateShuffleboardLoggingValues(GenericEntry[] entrys) {
        GenericEntry tx = entrys[0];
        GenericEntry ty = entrys[1];
        GenericEntry tv = entrys[2];

        tx.setDouble(getTX());
        ty.setDouble(getTY());
        tv.setBoolean(getTV());
    }

    public static RaspberrySubsystem getInstance(String raspberryTablePath) {
        if (m_instance == null) {
            m_instance = new RaspberrySubsystem(raspberryTablePath);
        }
        return m_instance;
    }

    @Override
    public String getClsName() {
        return this.visionTable.getEntry("cls_name").getString("");
    }

    @Override
    public boolean getTV() {
        return this.visionTable.getEntry("tv").getBoolean(false);
    }

    @Override
    public double getTX() {
        return this.visionTable.getEntry("tx").getDouble(0);
    }

    @Override
    public double getTY() {
        return this.visionTable.getEntry("ty").getDouble(0);
    }

    @Override
    public double getMinConf() {
        return configTable.getEntry("min_conf").getDouble(0);
    }

    @Override
    public void setMinConf(double val) {
        configTable.getEntry("min_conf").setDouble(val);
    }

    @Override
    public void periodic() {
        this.updateShuffleboardLoggingValues(shuffleboardEntrys);
    }
}
