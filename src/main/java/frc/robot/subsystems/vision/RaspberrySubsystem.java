package frc.robot.subsystems.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RaspberrySubsystem extends SubsystemBase {
    private static RaspberrySubsystem m_instance;

    // Shuflleboard config
    private ShuffleboardTab shuffleboardTab;
    private ShuffleboardLayout targetInformationLayout;

    // NetworkTables config
    private NetworkTable visionTable;
    private NetworkTable configTable;
    private NetworkTable raspberryTable;
    private NetworkTable detectionsTable;

    private RaspberrySubsystem(String raspberryTablePath) {
        this.shuffleboardTab = Shuffleboard.getTab(raspberryTablePath);
        this.targetInformationLayout = 
            this.shuffleboardTab.getLayout("Target Info", BuiltInLayouts.kGrid);

        this.raspberryTable = NetworkTableInstance.getDefault().getTable(raspberryTablePath);
        this.detectionsTable = raspberryTable.getSubTable("detections");
        this.visionTable = detectionsTable.getSubTable("vision");
        this.configTable = detectionsTable.getSubTable("config");
    }

    public static RaspberrySubsystem getInstance(String raspberryTablePath) {
        if (m_instance == null) {
            m_instance = new RaspberrySubsystem(raspberryTablePath);
        }
        return m_instance;
    }

    // VISION
    public String getClassName() {
        return visionTable.getEntry("cls_name").getString("");
    }

    public boolean getTargetValid() {
        return visionTable.getEntry("tv").getBoolean(false);
    }

    public double getTargetX() {
        return visionTable.getEntry("tx").getDouble(0);
    }

    public double getTargetY() {
        return visionTable.getEntry("ty").getDouble(0);
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
        
    }
}
