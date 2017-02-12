package xyz.girudo.jetset.helpers;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.exception.ConversionException;

import java.util.List;

public abstract class ValidationHelper implements ValidationListener {
    private Context context;
    private Validator validator;

    public ValidationHelper(Context context, Object controller) {
        this.context = context;
        validator = new Validator(controller);
        validator.setValidationListener(this);
        validator.registerAdapter(TextInputLayout.class,
                new ViewDataAdapter<TextInputLayout, String>() {
                    @Override
                    public String getData(TextInputLayout flet) throws ConversionException {
                        return flet.getEditText().getText().toString();
                    }
                }
        );
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            List<Rule> failedRules = error.getFailedRules();
            String message = failedRules.get(0).getMessage(context);

            // Display error messages ;)
            if (view instanceof TextInputLayout) {
                final TextInputLayout inputLayout = (TextInputLayout) view;
                inputLayout.setError(message);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputLayout.setError(null);
                    }
                }, 2000);

            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public Validator getValidator() {
        return validator;
    }
}
