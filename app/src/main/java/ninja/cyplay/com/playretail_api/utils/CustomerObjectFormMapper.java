package ninja.cyplay.com.playretail_api.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

import ninja.cyplay.com.playretail_api.model.business.CustomerDetails;
import retrofit.client.Client;

/**
 * Created by damien on 12/01/16.
 */
public class CustomerObjectFormMapper {

    public static <T> T getFieldByName(CustomerDetails customerDetails, String fieldName) {
        if (customerDetails == null)
            return null;
        if (ClientSpec.getFieldMapping().containsKey(fieldName)) {
            String nameOfFieldSearched = ClientSpec.getFieldMapping().get(fieldName);
            for (Field field : customerDetails.getClass().getFields()) {
                if (field.getName() != null && field.getName().equals(nameOfFieldSearched))
                    try {
                        return (T) field.get(customerDetails);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        break;
                    }
            }
        }
        return null;
    }

    public static <T> void setFieldByName(CustomerDetails customerDetails, String fieldName, T value) {
        if (ClientSpec.getFieldMapping().containsKey(fieldName)) {
            String nameOfFieldSearched = ClientSpec.getFieldMapping().get(fieldName);
            for (Field field : customerDetails.getClass().getFields()) {
                if (field.getName() != null && field.getName().equals(nameOfFieldSearched))
                    try {
                        field.set(customerDetails, value);
//                        // here
//                        return (T) field.get(customerDetails);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        break;
                    } catch (Exception e) {}
            }
        }
//        return null;
    }




}
