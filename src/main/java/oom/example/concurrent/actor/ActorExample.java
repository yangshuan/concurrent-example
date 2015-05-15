package oom.example.concurrent.actor;

/**
 * Use akka to show the actor concurrent model.
 * Created by yangshuan on 15/5/13.
 */
public class ActorExample {
    public static void main(String[] args) {
        akka.Main.main(new String[] { Employee.class.getName() });
    }
}
