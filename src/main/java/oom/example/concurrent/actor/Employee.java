package oom.example.concurrent.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Actor 1
 * Created by shuayang on 5/15/15.
 */
public class Employee extends UntypedActor {
    @Override
    public void preStart() {
        // create the greeter actor
        final ActorRef boss = getContext().actorOf(Props.create(Boss.class), "Boss");
        // tell it to perform the greeting
        System.out.println("Employee said: \"How are you, boss?\"");
        boss.tell("How are you, boss?", getSelf());
    }

    @Override
    public void onReceive(Object msg) {
        if (msg == "I'm fine, thank you, and you?") {
            // when the greeter is done, stop this actor and with it the application
            getContext().stop(getSelf());
        } else
            unhandled(msg);
    }
}
