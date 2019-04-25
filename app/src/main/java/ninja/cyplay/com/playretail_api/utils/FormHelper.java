package ninja.cyplay.com.playretail_api.utils;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ninja.cyplay.com.apilibrary.models.business.FormField;
import ninja.cyplay.com.apilibrary.models.business.FormSection;
import ninja.cyplay.com.apilibrary.models.business.PR_Form;
import ninja.cyplay.com.apilibrary.models.business.PR_FormDescriptor;
import ninja.cyplay.com.apilibrary.models.business.PR_FormRow;
import ninja.cyplay.com.apilibrary.models.business.PR_FormSection;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormDatePickerLayoutLayout;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormEditTextInputLayoutLayout;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormSpinnerLayout;
import ninja.cyplay.com.playretail_api.ui.custom.form.FormSubViewLayout;

/**
 * Created by damien on 11/01/16.
 */
public class FormHelper {

    public static PR_Form getFormFromKey(String formKey) {
        PR_Form createForm = null;
        if (ConfigHelper.getInstance().getFeature() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig().getForms() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig().getForms().size() > 0) {
            for (int i = 0 ; i < ConfigHelper.getInstance().getFeature().getFormConfig().getForms().size() ; i++) {
                PR_Form form =  ConfigHelper.getInstance().getFeature().getFormConfig().getForms().get(i);
                if (form.getTag() != null && form.getTag().equals(formKey)) {
                    createForm = form;
                    break;
                }
            }
        }
        return createForm;
    }



    public static PR_FormSection getSectionForTag(String sectionTag) {
        PR_FormSection foundSection = null;
        if (ConfigHelper.getInstance().getFeature() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig().getSections() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig().getSections().size() > 0) {
            for (int i = 0 ; i < ConfigHelper.getInstance().getFeature().getFormConfig().getSections().size() ; i++) {
                PR_FormSection section =  ConfigHelper.getInstance().getFeature().getFormConfig().getSections().get(i);
                if (section.getTag() != null && section.getTag().equals(sectionTag)) {
                    foundSection = section;
                    break;
                }
            }
        }
        return foundSection;
    }


    private static Map<String, PR_FormRow> rowMap;

    private static void initMap() {
        rowMap = new HashMap<>();
        if (ConfigHelper.getInstance().getFeature() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig().getRows() != null
                && ConfigHelper.getInstance().getFeature().getFormConfig().getRows().size() > 0)
            for (int i = 0; i < ConfigHelper.getInstance().getFeature().getFormConfig().getRows().size(); i++) {
                PR_FormRow row = ConfigHelper.getInstance().getFeature().getFormConfig().getRows().get(i);
                rowMap.put(row.tag, row);
            }
    }

    public static PR_FormRow getRowForTag(String tag) {
        if (rowMap == null)
            initMap();
        if (rowMap.containsKey(tag))
            return rowMap.get(tag);
        return null;
    }

    public static Boolean isRequired(PR_FormDescriptor descriptor) {
        if (descriptor != null && descriptor.getPermissions() != null)
            return ((descriptor.getPermissions().intValue() & Constants.FORM_MASK_REQUIRED) == Constants.FORM_MASK_REQUIRED);
        return false;
    }

    public static Boolean isEditableOnce(PR_FormDescriptor descriptor) {
        if (descriptor != null && descriptor.getPermissions() != null)
            return ((descriptor.getPermissions().intValue() & Constants.FORM_MASK_EDITONCE) == Constants.FORM_MASK_EDITONCE);
        return false;
    }

    public static Boolean isDisabled(PR_FormDescriptor descriptor) {
        if (descriptor != null && descriptor.getPermissions() != null)
            return ((descriptor.getPermissions().intValue() & Constants.FORM_MASK_DISABLED) == Constants.FORM_MASK_DISABLED);
        return false;
    }

    public static FormSubViewLayout getViewFromTypeName(Context context, String type) {
        FormSubViewLayout subViewLayout = null;
        switch (type) {
            case "string":
                subViewLayout = new FormEditTextInputLayoutLayout(context);
                break;
            case "phone":
                subViewLayout = new FormEditTextInputLayoutLayout(context);
                ((FormEditTextInputLayoutLayout) subViewLayout).setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case "email":
                subViewLayout = new FormEditTextInputLayoutLayout(context);
                ((FormEditTextInputLayoutLayout) subViewLayout).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case "uniqueChoice":
                subViewLayout = new FormSpinnerLayout(context);
                break;
            case "date":
                subViewLayout = new FormDatePickerLayoutLayout(context);
        }
        return subViewLayout;
    }


    // -------------------------------------------------------------------------------------------
    //                                      Validator(s)
    // -------------------------------------------------------------------------------------------

    public static boolean isEditTextEmpty(EditText editText) {
        if (editText != null)
            return editText.getText().toString().trim().length() == 0;
        return false;
    }

    public static boolean testPattern(PR_FormRow row, String val) {
        if (row != null && row.getValidator() != null && row.getValidator().length() > 0) {
            Pattern p = Pattern.compile(row.getValidator());
            Matcher m = p.matcher(val);
            return m.matches();
        }
        return true;
    }



//
//    public static List<PR_FormRow> getRowsForSection(PR_FormSection section) {
//
//        List<PR_FormRow> rows = null;
//        if (section != null && section.getRowsDescriptors() != null) {
//            rows = new ArrayList<>();
//            // Prefill list of rows
//            for (int i = 0 ; i < section.getRowsDescriptors().size() ; i++)
//                rows.add(null);
//            // Loop on all Rows
//            if (ConfigHelper.getInstance().getFeature() != null
//                    && ConfigHelper.getInstance().getFeature().getFormConfig() != null
//                    && ConfigHelper.getInstance().getFeature().getFormConfig().getRows() != null
//                    && ConfigHelper.getInstance().getFeature().getFormConfig().getRows().size() > 0) {
//                for (int i = 0; i < ConfigHelper.getInstance().getFeature().getFormConfig().getRows().size(); i++) {
//                    // row that will be checked
//                    PR_FormRow rowToCheck = ConfigHelper.getInstance().getFeature().getFormConfig().getRows().get(i);
//                    // Loop on the section's rows
//                    for (int j = 0 ; j < section.getRowsDescriptors().size() ; j++) {
//                        // row to find
//                        PR_FormDescriptor descriptorToCheckWith = section.getRowsDescriptors().get(j);
//                        // if row match, add it and keep its position
//                        if (rowToCheck.getTag().equals(descriptorToCheckWith.getTag())) {
//                            rows.set(j, rowToCheck);
//                        }
//                    }
//
//                }
//            }
//        }
//        return rows;
//    }

    // -------------------------------------------------------------------------------------------
    //                                      old
    // -------------------------------------------------------------------------------------------


    public static void addRightFieldType(Context context, LinearLayout container, FormField field, boolean editMode) {
        addRightFieldTypeAndCheck(context, container, field, editMode, true);
    }

    public static void addRightFieldTypeNoValidationCheck(Context context, LinearLayout container, FormField field, boolean editMode) {
        addRightFieldTypeAndCheck(context, container, field, editMode, false);
    }

    private static void addRightFieldTypeAndCheck(Context context, LinearLayout container, FormField field, boolean editMode, boolean addCheck) {
//        if (field == null)
//            return;
//        // check if flag = 0 then hide field
//        if ((editMode && field.getEditFlags() == 0) || (!editMode && field.getCreateFlags() == 0))
//            return;
//        switch (field.getType()) {
//            case "name":
//                FormEditTextInputLayoutLayout editTextLayout = new FormEditTextInputLayoutLayout(context);
//                editTextLayout.setEditMode(editMode);
//                editTextLayout.setField(field);
//                if (addCheck)
//                    DisplayedFormFieldsManager.getInstance().addField(editTextLayout);
//                container.addView(editTextLayout);
//                break;
//            case "email":
//                FormEditTextInputLayoutLayout mailEditText = new FormEditTextInputLayoutLayout(context);
//                mailEditText.setEditMode(editMode);
//                mailEditText.setField(field);
//                if (addCheck)
//                    DisplayedFormFieldsManager.getInstance().addField(mailEditText);
//                container.addView(mailEditText);
//                break;
//            case "date":
//                FormDatePickerLayoutLayout formDatePickerLayout = new FormDatePickerLayoutLayout(context);
//                formDatePickerLayout.setEditMode(editMode);
//                formDatePickerLayout.setField(field);
//                if (addCheck)
//                    DisplayedFormFieldsManager.getInstance().addField(formDatePickerLayout);
//                container.addView(formDatePickerLayout);
//                break;
//            case "selectorPickerView" :
//                FormSpinnerLayout formSpinner = new FormSpinnerLayout(context);
//                formSpinner.setEditMode(editMode);
//                formSpinner.setField(field);
//                if (addCheck)
//                    DisplayedFormFieldsManager.getInstance().addField(formSpinner);
//                container.addView(formSpinner);
//                break;
//            default:
//                TextView textView = new TextView(context);
//                textView.setText(field.getLabel());
//                container.addView(textView);
//                break;
//        }
    }

    public static List<FormField> findSectionFields(FormSection section, List<FormField> allFields) {
        List<FormField> sectionFields = null;
        if (section.getFields() != null) {
            // Prefill array with empty objects
            sectionFields = Arrays.asList(new FormField[section.getFields().size()]);
            // find all sections's fields
            if (allFields != null)
                for (int i = 0 ; i < allFields.size() ; i++) {
                    FormField f = allFields.get(i);
                    String tag = f.getTag();
                    if (section.getFields().contains(tag))
                        sectionFields.set(section.getFields().indexOf(tag), f);
                }
        }
        return sectionFields;
    }

    public static FormField findFieldByTag(List<FormField> allFields, String searchedTag) {
        if (allFields != null)
            for (int i = 0 ; i < allFields.size() ; i++) {
                FormField f = allFields.get(i);
                if (f.getTag() != null && f.getTag().equals(searchedTag))
                    return f;
            }
        return null;
    }

    public static Boolean isFieldRequired(Boolean editMode, FormField field) {
        return ((editMode ? field.getEditFlags() : field.getCreateFlags()) & Constants.MASK_REQUIRED) == Constants.MASK_REQUIRED;
    }

    public static Boolean isFieldEditable(Boolean editMode, FormField field) {
        return ((editMode ? field.getEditFlags() : field.getCreateFlags()) & Constants.MASK_EDITABLE) == Constants.MASK_EDITABLE;
    }

}
