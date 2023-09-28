package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    // private final Joystick ROTJOY = new Joystick(1);
    private final GenericHID ButtonBoard = new GenericHID(1);
    

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;//POTENTIAL ROTATION AXIS?

    /* Driver Buttons */
    //private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kB.value);
    //private final JoystickButton CubeLED =  new JoystickButton(driver, XboxController.Button.kY.value);
    //private final JoystickButton ConeLED =  new JoystickButton(driver, XboxController.Button.kA.value);

    //INTAKE CONTROLLS
    private final JoystickButton intakeslow =  new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton intakefast =  new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton spit =  new JoystickButton(driver, XboxController.Button.kX.value);

    /*Button Board Buttons */
    private final JoystickButton extend  = new JoystickButton(ButtonBoard,6 );
    private final JoystickButton retract = new JoystickButton(ButtonBoard, 10);
    private final JoystickButton closeGripper = new JoystickButton(ButtonBoard, 9);
    private final JoystickButton openGripper = new JoystickButton(ButtonBoard, 8);
    //arm positins
    private final JoystickButton TopPoleArm = new JoystickButton(ButtonBoard, 7);
    private final JoystickButton TopArm = new JoystickButton(ButtonBoard, 3);
    private final JoystickButton MiddleArm = new JoystickButton(ButtonBoard,5);
    private final JoystickButton FloorArm = new JoystickButton(ButtonBoard, 4);

    private final JoystickButton balance = new JoystickButton(ButtonBoard, 11);
    private final JoystickButton scanforapril = new JoystickButton(ButtonBoard, 12);
    private final JoystickButton Up_Manuel_Override = new JoystickButton(ButtonBoard, 2);
    private final JoystickButton Down_Manuel_override = new JoystickButton(ButtonBoard, 1);
    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final PhotonSubsystem photon = new PhotonSubsystem();

    private final extendoSubsystem extendo = new extendoSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

    private final ClampSubsystem Grip = new ClampSubsystem();
    private final Lights LEDController = new Lights();
    

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), //ROTATION AXIS?
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        //zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        //Arm Positioning
            MiddleArm.onTrue(new InstantCommand(() -> armSubsystem.Raise(65)));
            TopArm.onTrue(new InstantCommand(() -> armSubsystem.Raise(80)));
            TopPoleArm.onTrue(new InstantCommand(() -> armSubsystem.Raise(85)));
            FloorArm.onTrue(new InstantCommand(() -> armSubsystem.Raise(21)));

        //ARM EXTENSION 
            extend.onTrue(new InstantCommand(() -> extendo.Extendcmd()));
            retract.onTrue(new InstantCommand(() -> extendo.Retractcmd()));

        //CLAW FUNCTIONS
            closeGripper.onTrue(new InstantCommand(() -> Grip.closeGripper()));
            openGripper.onTrue(new InstantCommand(() -> Grip.openGripper()));

        //APRIL TAGS
            scanforapril.onTrue(new InstantCommand(() -> photon.MoveOnAprilTag(s_Swerve)));
            
        //INTAKE USES
            intakefast.onTrue(new InstantCommand(() -> IntakeSubsystem.intakeDirection( 1)));
            intakeslow.onTrue(new InstantCommand(() -> IntakeSubsystem.intakeDirection( 1)));
            spit.onTrue(new InstantCommand(() -> IntakeSubsystem.intakeDirection( 1)));
        //ARM OVERIDE's
            Up_Manuel_Override.onTrue(new InstantCommand(()-> armSubsystem.ManualUp(8)));
            Down_Manuel_override.onTrue(new InstantCommand(()-> armSubsystem.Manual(1)));

        //LIGHTS
            //ConeLED.onTrue(new InstantCommand(()-> LEDController.set(-0.07)));
            //CubeLED.onTrue(new InstantCommand(()-> LEDController.set(-0.09 )));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}
