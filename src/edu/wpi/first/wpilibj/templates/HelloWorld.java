package edu.wpi.first.wpilibj.templates;
//
//import lejos.nxt.Button;
//import lejos.nxt.Motor;
//
///**
// * Example leJOS Project with an ant build file
// *
// */
//public class HelloWorld {
//
//    static double x, y, r;
//    static final double MDTR = 0.00335999204934;//0.007933314781794; //Motor Difference Turn Ratio
//    static boolean isOn = true;
//    static PIDController steeringController, speedController;
//
//    public static void drive(int power, int turn) {
//        int b = power + turn;
//        int c = power - turn;
//
//        if (b > 900) {
//            b = 900;
//        } else if (b < -900) {
//            b = -900;
//        }
//
//        if (c > 900) {
//            c = 900;
//        } else if (c < -900) {
//            c = -900;
//        }
//
//        Motor.B.setSpeed(Math.abs(b));
//        Motor.C.setSpeed(Math.abs(c));
//        if (b > 0) {
//            Motor.B.forward();
//        } else {
//            Motor.B.backward();
//        }
//        if (c > 0) {
//            Motor.C.forward();
//        } else {
//            Motor.C.backward();
//        }
//    }
//
//    public static boolean goTo(double x, double y) {
//        boolean done = false;
//        double dir = Math.atan2(y - HelloWorld.y, x - HelloWorld.x);
//        double speed = 0;
//
//        if (dir - (r * MDTR) > Math.PI) {
//            dir -= 2 * Math.PI;
//        } else if ((r * MDTR) - dir > Math.PI) {
//            dir += 2 * Math.PI;
//        }
//
//        if (Math.abs((r * MDTR) - dir) < Math.PI / 2) {
//            double dis = Math.sqrt(Math.pow(x - HelloWorld.x, 2)
//                    + Math.pow(y - HelloWorld.y, 2));
//            speed = ((Math.PI / 2) - Math.abs((r * MDTR) - dir)) / (Math.PI / 2);
//            speed *= speedController.calculate(dis, 0);
//            if (dis < 10) {
//                done = true;
//            }
//        }
//
//        drive((int) speed, (int) steeringController.calculate(dir, (r * MDTR)));
//
//        return done;
//    }
//
//    public static void main(String[] args) {
//        x = 0;
//        y = 0;
//        r = 0;
//        speedController = new PIDController(3, 0, 0);
//        steeringController = new PIDController(300, 0, 0);
//        Motor.B.setAcceleration(2000);
//        Motor.C.setAcceleration(2000);
//        new Thread(new Runnable() {
//
//            int oldB = 0, oldC = 0;
//
//            public void run() {
//                while (isOn) {
//                    int b = Motor.B.getTachoCount();
//                    int c = Motor.C.getTachoCount();
//
//                    r += (b - oldB) - (c - oldC);
//
//                    if (r > 1848) {
//                        r -= 1848;
//                    }
//                    
//                    double TPF = 1;
//
//                    x += Math.cos(r * MDTR) * ();
//                    y += Math.sin(r * MDTR) * ();
//
//                    oldB = b;
//                    oldC = c;
//                }
//            }
//        }).start();
//
//        int stage = 0;
//
//        while (!Button.ESCAPE.isPressed()) {
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException ex) {
//            }
//            System.out.println("x:" + ((int) x) + " y:" + ((int) y));
//
//            switch (stage) {
//                case 0:
//                    if (goTo(0, 0)) {
//                        stage++;
//                    }
//                    break;
//                case 1:
//                    if (goTo(0, 2000)) {
//                        stage++;
//                    }
//                    break;
//                case 2:
//                    if (goTo(2000, 2000)) {
//                        stage++;
//                    }
//                    break;
//                case 3:
//                    if (goTo(2000, 0)) {
//                        stage = 0;
//                    }
//                    break;
//            }
//
//            //System.out.println((int) x);
//            //System.out.println((int) y);
//            //System.out.println(r * MDTR);
//        }
//        isOn = false;
//    }
//}