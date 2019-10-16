package com.ecart.easyshopping.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemCannotUpdateException extends RuntimeException {
    private String resourceName;
    private Object fieldValue;

    public ItemCannotUpdateException( String resourceName, Object fieldValue) {
        super(String.format("Sorry, you can only update the quanity of %s with Id: %s", resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}