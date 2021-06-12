package wildfire;

import bh.greenfoot.runner.GreenfootRunner;

import java.io.File;

/**
 * The standalone launcher for the Greenfoot scenario.
 * See: https://github.com/benoitheinrich/greenfoot-runner
 */
public class WildfireLauncher extends GreenfootRunner {

    static {
        // 2. Bootstrap the runner class.
        bootstrap(WildfireLauncher.class,
                // 3. Prepare the configuration for the runner based on the world class
                Configuration.forWorld(MyWorld.class)
                        // Set the project name as you wish
                        .projectName("Escape the Wildfire!")
        );
    }

//    public static void main(String[] args) {
//        GreenfootRunner.main(args);
//    }

}
