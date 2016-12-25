package xyz.girudo.jetset.helpers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;

import java.util.List;

public abstract class ValidationHelper implements ValidationListener {
    private Context context;
    private Validator validator;

    public ValidationHelper(Context context, Object controller) {
        this.context = context;
        validator = new Validator(controller);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        View view = errors.get(0).getView();
        List<Rule> failedRules = errors.get(0).getFailedRules();
        String message = failedRules.get(0).getMessage(context);

        // Display error messages ;)
        if (view instanceof EditText) {
            ((EditText) view).setError(message);
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public Validator getValidator() {
        return validator;
    }
}
