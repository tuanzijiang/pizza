package edu.ecnu.scsse.pizza.consumer.server.utils;

public class ThrowableCaptor {

    public static Throwable thrownBy(ThrowableCaptor.Actor actor) {
        try {
            actor.act();
            return null;
        } catch (Throwable var2) {
            return var2;
        }
    }

    @FunctionalInterface
    public interface Actor {
        void act() throws Throwable;
    }
}
