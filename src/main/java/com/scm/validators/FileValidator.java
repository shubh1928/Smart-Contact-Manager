package com.scm.validators;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
	
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 5; //5MB

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

		//Check if file/image is empty/null or not
		if (file == null || file.isEmpty()) {
			
//			context.disableDefaultConstraintViolation();
//			context.buildConstraintViolationWithTemplate("File can't be empty!")
//				   .addConstraintViolation();
			return true;
			
		}
		
		//Check image's size
		if (file.getSize() > MAX_FILE_SIZE) {
			
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File size should not exceed 5MB")
				   .addConstraintViolation();
			return false;
			
		}
		
		//check the resolution of image
		try 
		{
			
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			
            if (bufferedImage == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid image file!")
                       .addConstraintViolation();
                return false;
            }
			
		    int width = bufferedImage.getWidth();
		    int height = bufferedImage.getHeight();

		    // Example: Require minimum 500x500 resolution
		    if (width < 500 || height < 500) {
		        context.disableDefaultConstraintViolation();
		        context.buildConstraintViolationWithTemplate("Image resolution must be at least 500x500 pixels")
		        	   .addConstraintViolation();
		        return false;
		    }
			
		} catch (IOException e) {
            e.printStackTrace();
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Error reading image file")
                   .addConstraintViolation();
            return false;
		}
		
		return true;
		
	}

}
