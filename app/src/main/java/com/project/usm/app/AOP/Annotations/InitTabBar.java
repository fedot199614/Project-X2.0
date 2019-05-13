package com.project.usm.app.AOP.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InitTabBar {
    Check check();

    public enum Check {
        GONE(true),
        VISIBLE(false);

        public boolean flag;

        Check(boolean flag) {
            this.flag = flag;
        }
    }
}
