package main;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainContainerLauncher {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.GUI, "true");
            profile.setParameter(Profile.MAIN, "true");

            AgentContainer mainContainer = runtime.createMainContainer(profile);

            System.out.println("Main Container JADE started successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}