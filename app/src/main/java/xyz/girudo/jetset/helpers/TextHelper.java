package xyz.girudo.jetset.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TextHelper {
    private static final String FONT_TYPE = "fonts/robotoregular.ttf";
    private static TextHelper textHelper;
    private Context context;

    public static TextHelper getInstance(Context context) {
        if (textHelper == null) {
            textHelper = new TextHelper();
        }
        textHelper.context = context;
        return textHelper;
    }

    public void setFont(ViewGroup group) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), FONT_TYPE);
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button)
                ((TextView) v).setTypeface(font);
            else if (v instanceof ViewGroup)
                setFont((ViewGroup) v);
        }
    }

    public String limitString(String value, int length) {
        StringBuilder buf = new StringBuilder(value);
        if (buf.length() > length) {
            buf.setLength(length);
            buf.append(" ...");
        }
        return buf.toString();
    }

    public String setCurrencyText(double value) {
        NumberFormat formatter = new DecimalFormat("'$'###");
        return formatter.format(value);
    }
}