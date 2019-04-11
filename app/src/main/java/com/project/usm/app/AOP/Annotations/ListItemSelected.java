package com.project.usm.app.AOP.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ListItemSelected {
    Item item();

    public enum Item {
        MAIN(0),
        PROFILE(1),
        SCHEDULE(2),
        MAP(3);
        public int e;

        Item(int e) {
            this.e = e;
        }
    }
}
