package xyz.girudo.jetset.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.entities.User;
import xyz.girudo.jetset.helpers.AlertHelper;
import xyz.girudo.jetset.helpers.DateHelper;
import xyz.girudo.jetset.helpers.NetworkHelper;
import xyz.girudo.jetset.helpers.ValidationHelper;

/**
 * Created by Novyandi Nurahmad on 2/5/17
 */

public class AccountFragment extends BaseFragment implements View.OnClickListener {
    @NotEmpty(message = "First Name is required")
    @Length(trim = true)
    @Order(1)
    @BindView(R.id.til_name_first)
    TextInputLayout tilNameFirst;
    @NotEmpty(message = "Last Name is required")
    @Length(trim = true)
    @Order(2)
    @BindView(R.id.til_name_last)
    TextInputLayout tilNameLast;
    @NotEmpty(message = "Credit Card Number is required")
    @Length(sequence = 2, min = 16, max = 16, message = "Enter valid Credit Card Number")
    @Order(3)
    @BindView(R.id.til_cc_number)
    TextInputLayout tilCCNumber;
    @NotEmpty(message = "CCV Number is required")
    @Length(sequence = 2, min = 3, max = 3, message = "Enter valid CCV Number")
    @Order(4)
    @BindView(R.id.til_ccv)
    TextInputLayout tilCCV;
    @BindView(R.id.sp_month)
    AppCompatSpinner spMonth;
    @BindView(R.id.sp_year)
    AppCompatSpinner spYear;
    @BindView(R.id.cb_shipping_status)
    CheckBox cbShippingStat;
    @NotEmpty(message = "Address 1 is required")
    @Length(trim = true)
    @Order(5)
    @BindView(R.id.til_address_1)
    TextInputLayout tilAddress1;
    @BindView(R.id.til_address_2)
    TextInputLayout tilAddress2;
    @NotEmpty(message = "City is required")
    @Length(trim = true)
    @Order(6)
    @BindView(R.id.til_city)
    TextInputLayout tilCity;
    @NotEmpty(message = "State is required")
    @Length(trim = true)
    @Order(7)
    @BindView(R.id.til_state)
    TextInputLayout tilState;
    @NotEmpty(message = "Zip is required")
    @Length(trim = true)
    @Order(8)
    @BindView(R.id.til_zip)
    TextInputLayout tilZip;
    @BindView(R.id.btn_process)
    Button btnProcess;
    @BindView(R.id.sv_account)
    ScrollView svAcount;

    private ArrayAdapter<Integer> monthsAdapter;
    private ArrayAdapter<Integer> yearsAdapter;
    private User user;
    private Validator validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User(RealmDataControl.getInstance(activity).getUserData());
        prepareMonthsYearsData();
        ValidationHelper validationHelper = new ValidationHelper(activity, this) {
            @Override
            public void onValidationSucceeded() {
                NetworkHelper.getInstance().checkConnection(activity, new NetworkHelper.CheckCallback() {
                    @Override
                    public void onSuccess() {
                        new Handler(activity.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                saveProfile();
                            }
                        });
                    }

                    @Override
                    public void onFailed() {
                        new Handler(activity.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                NetworkHelper.getInstance().showDialogNoConnection(activity);
                            }
                        });
                    }
                });
            }
        };
        validator = validationHelper.getValidator();
        getBaseActivity().setColorActionBar(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        spMonth.setAdapter(monthsAdapter);
        spYear.setAdapter(yearsAdapter);
    }

    @Override
    public void setUICallbacks() {
        btnProcess.setOnClickListener(this);
    }

    @Override
    public void updateUI() {
        svAcount.requestFocus();
        if (user != null) {
            tilNameFirst.getEditText().setText(user.getFirstName());
            tilNameLast.getEditText().setText(user.getLastName());
            tilCCNumber.getEditText().setText(user.getCcNumber());
            tilCCV.getEditText().setText(user.getCcvNumber());
            spMonth.setSelection(monthsAdapter.getPosition(user.getMonth()));
            spYear.setSelection(yearsAdapter.getPosition(user.getYear()));
            cbShippingStat.setChecked(user.isStatusShipping());
            tilAddress1.getEditText().setText(user.getAddress1());
            tilAddress2.getEditText().setText(user.getAddress2());
            tilCity.getEditText().setText(user.getCity());
            tilState.getEditText().setText(user.getState());
            tilZip.getEditText().setText(user.getZip());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnProcess) {
            closeKeyboard();
            validator.validate();
        }
    }

    @Override
    public String getPageTitle() {
        return getString(R.string.acc_title);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_account;
    }

    private void saveProfile() {
        user.setFirstName(tilNameFirst.getEditText().getText().toString());
        user.setLastName(tilNameLast.getEditText().getText().toString());
        user.setCcNumber(tilCCNumber.getEditText().getText().toString());
        user.setCcvNumber(tilCCV.getEditText().getText().toString());
        user.setCcvNumber(tilCCV.getEditText().getText().toString());
        user.setMonth((Integer) spMonth.getSelectedItem());
        user.setYear((Integer) spYear.getSelectedItem());
        user.setStatusShipping(cbShippingStat.isChecked());
        user.setAddress1(tilAddress1.getEditText().getText().toString());
        user.setAddress2(tilAddress2.getEditText().getText().toString());
        user.setCity(tilCity.getEditText().getText().toString());
        user.setState(tilState.getEditText().getText().toString());
        user.setZip(tilZip.getEditText().getText().toString());
        RealmDataControl.getInstance(activity).saveUserData(user);
        AlertHelper.getInstance().showAlertDialog(activity, getString(R.string.acc_txt_btn_process), user.toString());
    }

    private void prepareMonthsYearsData() {
        ArrayList<Integer> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) months.add(i + 1);
        monthsAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, months);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int startYear = Integer.valueOf(DateHelper.getInstance().getDate("yyyy"));
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = startYear; i < startYear + 21; i++) years.add(i);
        yearsAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}