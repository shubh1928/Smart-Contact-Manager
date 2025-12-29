package com.scm.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//Makes sure that if someone generates Javadoc for your project, this annotation will show up in the docs
@Documented

//Defines where you can put this annotation
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})

//Says the annotation will be available at runtime (not just at compile time).
//Needed because validation happens while the program is running.
@Retention(RetentionPolicy.RUNTIME)

//This connects your annotation to the actual logic that will check the file.
//FileValidator is another class (you should have it in the same package) that 
//implements ConstraintValidator<ValidFile, MultipartFile> and contains the isValid() method 
//where you write your checks (e.g., file type, file size).
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile 
{
	
	String message() default "Invalid File!!!"; //default error message when validation fails.
	 
	Class<?>[] groups() default {}; //allows grouping of constraints (usually left empty unless you have advanced validation setups).
	
	Class<? extends Payload>[] payload() default {}; //can be used by clients to assign extra information about the error (rarely used in simple apps).

}
