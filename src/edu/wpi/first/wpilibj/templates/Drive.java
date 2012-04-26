/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * Drive system with two Jaguars driven in tank drive.
 *
 * @author Derek
 */
public class Drive implements PIDOutput {

    SafeJaguar rjag = null;
    SafeJaguar ljag = null;
    double lastSpeedLeft;
    double lastSpeedRight;

    /**
     * Drive system with two Jaguars driven in tank drive.
     *
     * @param Integer l is the port of the left Jaguar.
     * @param Integer r is the port of the right Jaguar.
     * @author Derek
     */
    public Drive(int l, int r) {
        rjag = new SafeJaguar(r);
        ljag = new SafeJaguar(l);

        lastSpeedLeft = 0;
        lastSpeedRight = 0;

    }

    /**
     * Sets the speed of the left drive motor
     *
     * @param speed
     */
    public void setLeft(double speed) {
        ljag.set(-speed);
    }

    /**
     * Sets the speed of the right drive motor.
     *
     * @param speed
     */
    public void setRight(double speed) {
        rjag.set(speed);
    }

    public void pidWrite(double output) {
        setRight(output);
        setLeft(-output);
    }
}
