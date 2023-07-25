// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants{
        public static final int kRIGHTMOTOR1PORT = 2;
        public static final int kRIGHTMOTOR2PORT = 3;
        public static final int kRIGHTMOTOR3PORT = 4;
        public static final int kLEFTMOTOR1PORT = 5;
        public static final int kLEFTMOTOR2PORT = 6;
        public static final int kLEFTMOTOR3PORT = 7;

        public static final boolean kIS_INVERTED = true;
    }
    public static final class CannonConstants{
        public static final int kPCM_PORT = 1;
        public static final int kINTAKE_DOWN = 3;
        public static final int kINTAKE_UP = 2;
        public static final int kCANNON_SHOOT_PORT = 13;
        //the duration the valve is open in seconds
        public static final double kSHOOT_DURATION = 0.2;
        public static final int kLIFTERMOTORPORT = 8;
        public static final double kLIFTERSPEED = 0.25;
    }
}
