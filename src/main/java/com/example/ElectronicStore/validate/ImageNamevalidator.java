package com.example.ElectronicStore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNamevalidator implements ConstraintValidator<ImageNameValid,String> {

    private Logger logger= LoggerFactory.getLogger(ImageNamevalidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("Message from isValid : {}",value);
        // logic
        if(value.isBlank())
            return false;
        else {
            return true;
        }
    }
}
