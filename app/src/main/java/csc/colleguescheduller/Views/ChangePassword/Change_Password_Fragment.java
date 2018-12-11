package csc.colleguescheduller.Views.ChangePassword;

// Mohamed Mostafa

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import csc.colleguescheduller.Controllers.ChangePassword.Change_Password_Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class Change_Password_Fragment extends MyFragment implements Change_Password_Interface {
    Change_Password_Controller controller;
    EditText oldPassword;
    EditText newPassword;
    EditText repeatPassword;

    public Change_Password_Fragment() {
        // Required empty public constructor
    }

    public Change_Password_Fragment(ActivitytoFragment_interface activity) {
        super(activity, Main_Activity.CHANGE_PASSWORD_TITLE);
        this.controller = new Change_Password_Controller(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_password, container, false);
        oldPassword = v.findViewById(R.id.change_password_old_pass_editxt);
        newPassword = v.findViewById(R.id.change_password_new_pass_editxt);
        repeatPassword = v.findViewById(R.id.change_password_repeat_pass_editxt);
        Button btn = v.findViewById(R.id.change_password_save_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validate = Validate();
                if (validate.equals("")) {
                    activity.on_connecting();
                    controller.changePassword(
                            oldPassword.getText().toString(),
                            newPassword.getText().toString()
                    );
                } else {
                    on_authentication_error(validate);
                }

            }
        });
        return v;

    }

    @Override
    public void onSuccess() {
        activity.abort_connection(null);
        Toast.makeText((Context) activity, "SUCCESS", Toast.LENGTH_SHORT).show();
    }

    public String Validate() {
        if (oldPassword.getText().toString().equals("")) {
            return "Old Password's Id Can't Be Empty";
        } else if (newPassword.getText().toString().equals("")) {
            return "New Password's Id Can't Be Empty";
        } else if (repeatPassword.getText().toString().equals("")) {
            return "Repeat Password's Id Can't Be Empty";
        } else if (!newPassword.getText().toString().equals(repeatPassword.getText().toString())) {
            return "Password Doesn't Match";
        } else if (!Globals.session.user.getAccessCode().equals(oldPassword.getText().toString())) {
            return "Wrong Old Password";

        } else {
            return "";
        }

    }
}