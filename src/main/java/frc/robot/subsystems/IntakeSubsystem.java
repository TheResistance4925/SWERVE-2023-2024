package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.Constants.Swerve;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    
    private TalonFX intakeMotor;
    

    /** Creates a new ExampleSubsystem. */
    public IntakeSubsystem() {
        intakeMotor = new TalonFX(16);
        intakeMotor.config_kP(0, 0.01);

    }
/*nate was here */
    /**
     * Example command factory method.
     *
     * @return a command
     */
    public CommandBase intakeDirection(double powah) {
        intakeMotor.set(TalonFXControlMode.PercentOutput,((powah)));
       
        System.out.println("Intake Active");
        return runOnce(
            () -> {
                /* one-time action goes here */
            });
}


   

   

    @Override
    public void periodic() {
        
        SmartDashboard.putNumber("IntakeData",intakeMotor.getSelectedSensorPosition());
       
     
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}