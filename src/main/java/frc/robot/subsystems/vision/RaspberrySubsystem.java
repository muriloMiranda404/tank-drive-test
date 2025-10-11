package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class RaspberrySubsystem extends SubsystemBase {
    private static RaspberrySubsystem m_instance;
    
    // NetworkTables config
    private NetworkTable visionTable;
    private NetworkTable configTable;
    private NetworkTable raspberryTable;
    private NetworkTable detectionsTable;

    // Shuflleboard Logging
    private ShuffleboardTab shuffleboardTab;
    private GenericEntry logging_entrys[] = new GenericEntry[3]; 

    private RaspberrySubsystem(String raspberryTablePath) {
        this.raspberryTable = NetworkTableInstance.getDefault().getTable(raspberryTablePath);
        this.detectionsTable = raspberryTable.getSubTable("detections");
        this.visionTable = detectionsTable.getSubTable("vision");
        this.configTable = detectionsTable.getSubTable("config");
        this.logging_entrys = this.initializeShuffleboardLogging(raspberryTablePath);
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
        .withPosition(3, 0)
        .withSize(1, 1);

        return entrys;
    }

    private void updateShuffleboardLoggingValues(GenericEntry[] entrys) {
        GenericEntry tx = entrys[0];
        GenericEntry ty = entrys[1];
        GenericEntry tv = entrys[2];

        tx.setDouble(this.getTargetX());
        ty.setDouble(this.getTargetY());
        tv.setBoolean(this.getTargetValid());
    }

    public static RaspberrySubsystem getInstance(String raspberryTablePath) {
        if (m_instance == null) {
            m_instance = new RaspberrySubsystem(raspberryTablePath);
        }
        return m_instance;
    }

    // VISION
    public String getClassName() {
        return this.visionTable.getEntry("cls_name").getString("");
    }

    public boolean getTargetValid() {
        return this.visionTable.getEntry("tv").getBoolean(false);
    }

    public double getTargetX() {
        return this.visionTable.getEntry("tx").getDouble(0);
    }

    public double getTargetY() {
        return this.visionTable.getEntry("ty").getDouble(0);
    }

    // CONFIG
    public double getMinConf() {
        return configTable.getEntry("min_conf").getDouble(0);
    }

    public void setMinConf(double value) {
        configTable.getEntry("min_conf").setDouble(value);
    }

    @Override
    public void periodic() {
        this.updateShuffleboardLoggingValues(this.logging_entrys);
    }
}
