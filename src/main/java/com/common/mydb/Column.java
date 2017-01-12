package com.common.mydb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	// Method descriptor #5 ()Ljava/lang/String;
	String name() default "";

	// Method descriptor #9 ()Z
	boolean unique() default false;

	// Method descriptor #9 ()Z
	boolean nullable() default true;

	// Method descriptor #9 ()Z
	boolean insertable() default true;

	// Method descriptor #9 ()Z
	boolean updatable() default true;

	// Method descriptor #5 ()Ljava/lang/String;
	String columnDefinition() default "";

	// Method descriptor #5 ()Ljava/lang/String;
	String table() default "";

	// Method descriptor #18 ()I
	int length() default (int) 255;

	// Method descriptor #18 ()I
	int precision() default (int) 0;

	// Method descriptor #18 ()I
	int scale() default (int) 0;
}
