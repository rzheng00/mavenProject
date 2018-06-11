package seleniumTest;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Collections;


public class mainClass {
    public static void main(String[] args) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("pom.xml"));
        request.setGoals(Collections.singletonList("install"));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }

    }
}
