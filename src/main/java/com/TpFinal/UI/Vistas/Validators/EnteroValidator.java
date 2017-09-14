package com.TpFinal.UI.Vistas.Validators;

import com.vaadin.data.Validator;

import javax.management.Notification;

/**
 * Created by Hugo on 13/06/2017.
 */
public class EnteroValidator implements Validator {
    @Override
    public void validate(Object value)
            throws InvalidValueException {
        //if (!(value instanceof Integer) )
          if(value.toString() == null || value.toString().trim()=="")
          {
              throw new InvalidValueException("El valor no es entero");
          }

          try
          {
              Integer.parseInt(value.toString());
          }
          catch (Exception e)
          {
              throw new InvalidValueException("El valor no es un entero válido");
          }




    }
}