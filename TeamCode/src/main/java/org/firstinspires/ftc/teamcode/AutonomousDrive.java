package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class AutonomousDrive {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(60.3034, 131.3401, 270);
    private final Pose path1 = poseFactory.of(84.7359, 23.7788, 90);
    private final Pose path1Control1 = poseFactory.of(140.9861, 118.798, 0);
    private final Pose path1Control2 = poseFactory.of(141.6842, 20.5474, 0);

    public Path path1() {
        // This will now compile cleanly using the static import
        return curve(start, path1Control1, path1Control2, path1).linear(start, path1);
    }
}
