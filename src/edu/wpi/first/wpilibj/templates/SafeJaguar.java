/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Jaguar capable of setting speeds relative to values received from the PID
 * controller in either PWM or CAN networks.
 *
 * @author Developer
 */
public class SafeJaguar implements PIDOutput {

    CANJaguar jaguar;
    double speed = 0;
    Encoder encoder = null;
    int myCANID = 0;
    /**
     * Counter for check mode.
     */
    int t = 0;
    /**
     * Speed given to the jaguar during encoder fail mode
     */
    double failSpeed = 0.1;
    /**
     * Determines whether or not the jaguar is in encoder fail mode
     */
    boolean failing = false;
    /**
     * Determines whether or not the jaguar outputs values relative to current
     * speed. Defaults to false
     */
    boolean relative = false;

    /**
     * Creates a new jaguar capable of setting speeds relative to values from
     * the PID controller in either PWM or CAN networks.
     *
     * @param Integer j is the port number of the jaguar.
     */
    public SafeJaguar(int j) {
        try {
            jaguar = new CANJaguar(j);
            myCANID = j;
        } catch (CANTimeoutException ex) {
            System.out.println("cantimeout");
        }

    }

    /**
     * Creates a new jaguar capable of setting speeds relative to values from
     * the PID controller in either PWM or CAN networks.
     *
     * @param Integer j is the port number of the jaguar.
     * @param Encoder e is the encoder attached to the motor which the jaguar
     * controls.
     */
    public SafeJaguar(int j, Encoder e) {
        try {
            jaguar = new CANJaguar(j);
            myCANID = j;
        } catch (CANTimeoutException ex) {
            System.out.println("cantimeout");
        }
        encoder = e;
    }

    /**
     * Sets the jaguar to determine speeds relative to the speed it is already
     * outputting. Relative mode is default.
     */
    public void setRelative() {
        relative = true;
    }

    /**
     * Sets the jaguar to assign speeds solely from the outputs from the PID
     * controller. Relative mode is default.
     */
    public void setAbsolute() {
        relative = false;
    }

    private int getCANID() {
        return myCANID;
    }

    /**
     * Sets the output of the jaguar
     *
     * @param output
     */
    public void set(double output) {
        if (relative) {
            speed += output;
        } else {
            speed = output;
        }
        if (speed > 1) {
            speed = 1;
        } else if (speed < -1) {
            speed = -1;
        }
        //check();

        if (jaguar != null) {
            jaguar.set(speed);
        }
    }

    /**
     * Writes output to the PID controller
     *
     * @param output
     */
    public void pidWrite(double output) {
        set(output);
    }

    /**
     * First checks if an encoder is initialized. Then, checks that either the
     * motor is moving or the robot is in fail mode, and that the encoder is
     * reading 0. If all these conditions are true, and the counter integer t is
     * greater than 1, meaning that the conditions have been met more than
     * twice. If all these conditions are met, the speed is changed to the fail
     * speed and jaguar enters fail mode. If t is less than 1, one is added to
     * t. If all these conditions are not met then t is set to 0 and fail mode
     * is turned off.
     */
    private void check() {
        if (!(encoder == null)
                && (speed > 0.15 || speed < -0.15 || failing)
                && (encoder.getRate() == 0)) {
            if (t > 1) {
                speed = failSpeed;
                failing = true;
                DriverStation.getInstance().setDigitalOut(1, true);
            } else {
                t++;
            }
        } else {
            t = 0;
            failing = false;
        }
        if (failing) {
            System.out.println("CAN FAILING MODE");
        }
    }

    /**
     * Sets the fail speed of the jaguar
     *
     * @param double x is the new fail speed
     */
    public void setFailSpeed(double x) {
        failSpeed = x;
    }
    
    public double getSpeed() {
        return speed;
    }
}
