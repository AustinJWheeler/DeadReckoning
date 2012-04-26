/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    Encoder encoder;
    Gyro gyro;
    Drive drive;
    PlaceTracker pt;

    public void robotInit() {
        drive = new Drive(6, 8);
        encoder = new Encoder(1, 2);
        gyro = new Gyro(1);
        pt = new PlaceTracker(encoder, gyro, drive, 0.1, 0, 0, 0.001, 0, 0);
    }

    public void autonomousPeriodic() {
        pt.goTo(2000, 2000);
    }

    public void teleopPeriodic() {
    }
}
