/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import com.sun.squawk.util.MathUtils;

/**
 *
 * @author laptop
 */
public class PlaceTracker {

    private double x, y, dis, oldEncoderValue;
    private double lastDestinationX, lastDestinationY;
    private Encoder encoder;
    private Gyro gyro;
    private Drive drive;
    private PassivePIDController steeringPID, drivePID;

    public PlaceTracker(Encoder encoder, Gyro gyro, Drive drive,
            double steeringP, double steeringI, double steeringD,
            double driveP, double driveI, double driveD) {
        steeringPID = new PassivePIDController(steeringP, steeringI, steeringD);
        steeringPID.setInputRange(0, 360);
        steeringPID.setOutputRange(-1, 1);
        steeringPID.setContinuous();
        steeringPID.setSetpoint(0);
        drivePID = new PassivePIDController(-driveP, -driveI, -driveD);
        drivePID.setOutputRange(-0.4, 0.4);
        drivePID.setSetpoint(0);
        this.encoder = encoder;
        this.gyro = gyro;
        this.drive = drive;
    }

    public void step() {
        double encoderValue = encoder.get();
        double rot = gyro.getAngle() % 360;

        x += Math.cos(Math.toRadians(rot)) * (oldEncoderValue - encoderValue);
        y += Math.sin(Math.toRadians(rot)) * (oldEncoderValue - encoderValue);

        oldEncoderValue = encoderValue;
    }

    public double goTo(double x, double y) {
        double goalDirection = MathUtils.atan2(y - this.y, x - this.x);
        double speed = 0;
        double realDirection = gyro.getAngle() % 360;
        double directionError = goalDirection - realDirection;

        //make sure speed comes out correct
        if (directionError > 180) {
            goalDirection -= 360;
        }

        directionError = goalDirection - realDirection;//recalculate

        dis = Math.sqrt(MathUtils.pow(x - this.x, 2)
                + MathUtils.pow(y - this.y, 2));
        speed = (90 - Math.abs(directionError)) / 90;

        double forwardSpeed, rotationSpeed;

        forwardSpeed = speed * drivePID.pidWriteAndGet(dis);
        rotationSpeed = steeringPID.pidWriteAndGet(directionError);

        double left = forwardSpeed + rotationSpeed;
        double right = forwardSpeed - rotationSpeed;

        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1) {
            left = left/max;
            right = right/max;
        }
        
        drive.setLeft(left);
        drive.setRight(right);

        return dis;
    }
}