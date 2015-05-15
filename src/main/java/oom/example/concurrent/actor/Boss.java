package oom.example.concurrent.actor;

import akka.actor.UntypedActor;

/**
 * Actor 2
 * Created by shuayang on 5/15/15.
 */
public class Boss extends UntypedActor {
    @Override
    public void onReceive(Object msg) throws Exception {
        String returnMsg;
        if (msg == "How are you, boss?") {
            returnMsg = "I'm fine, thank you, and you?";
        } else {
            returnMsg = "What are you talking about?";
        }
        System.out.println("Boss said: \"" + returnMsg + "\"");
        getSender().tell(returnMsg, getSelf());
    }
}
