package csc.colleguescheduller.Views.Main;

/*
Ahmed Mokhtar Hassanin
 */

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.Permission;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.AchademicSchedule.AcademicSchedule_Fragment;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.AdminPanel.AdminPanel_Fragment;
import csc.colleguescheduller.Views.ChangePassword.Change_Password_Fragment;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.Profile.Profile_Fragment;
import csc.colleguescheduller.Views.MyActivity;
import csc.colleguescheduller.Views.MyFragment;

public class Main_Activity extends MyActivity implements ActivitytoFragment_interface {

    Controller controller;

    public static String PROFILE_TITLE = "Profile";
    public static String ACADEMIC_SCHEDULE_TITLE = "Academic Schedule";
    public static String ADMIN_PANEL_TITLE = "Admin Panel";
    public static String CHANGE_PASSWORD_TITLE = "Change Password";
    public static String DATABASE_MANAGMENT_TITLE = "Database Managment";
    public static String STAFF_CATEGORIES_TITLE = "Staff Managment";
    public static String STAFFMEMBER_DETAIL_TITLE = "Member Detailed";
    public static String STAFFMEMBER_Edit_TITLE = "Edit Member";
    public static String STAFFMEMBER_ADD_TITLE = "Add Member";
    public static String TEACHING_INFO_TITLE = "Teaching Info";
    public static String SUBJECTS_LIST_TITLE = "Subjects";
    public static String SUBJECTS_DETAILED_TITLE = "Subject Detailed";
    public static String SUBJECT_EDIT_TITLE = "Edit Subject";
    public static String HALLSANDLABS_LIST_TITLE = "Halls & Labs";
    public static String HALLSANDLABS_View_TITLE = "Hall or Lab Detailed";
    public static String HALLSANDLABS_Edit_TITLE = "Edit Hall or Lab";
    public static String SCHEMAS_LIST_TITLE = "Schemas";
    public static String SCHEMAS_VIEW_TITLE = "Scheme Info";
    public static String SCHEMAS_EDIT_TITLE = "Scheme Edit";
    public static String STAFFMEBERS_SETTINGS_TITLE = "Staff Settings";



    private MyFragment current_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        controller = new Controller();
        set_navigation();
        set_animation();
        set_font();
        move_to_fragment(new Profile_Fragment(this));
    }

    private void set_navigation() {
        StaffMember user = Globals.session.user;
        ((TextView) findViewById(R.id.mainview_nav_txtv_username)).setText(user.getName());
        ((TextView) findViewById(R.id.mainview_nav_txtv_degree)).setText(user.getPermission().toString());
        add_nav_item(ACADEMIC_SCHEDULE_TITLE, R.drawable.ic_schedule);
        if (user.getPermission() == Permission.Assistant || user.getPermission() == Permission.Admin) {
            add_nav_item(ADMIN_PANEL_TITLE, R.drawable.ic_admin_panel);
        }
        add_nav_item(CHANGE_PASSWORD_TITLE, R.drawable.ic_change_password);
    }

    private void add_nav_item(String label, @DrawableRes int icon) {
        LinearLayout list = findViewById(R.id.mainview_nav_list);
        View v = getLayoutInflater().inflate(R.layout.main_nav_item, null);
        ((TextView) v.findViewById(R.id.mainview_nav_item_txtv)).setText(label);
        ((ImageView) v.findViewById(R.id.mainview_nav_item_imgv)).setImageResource(icon);
        list.addView(v);
    }

    public void navigate(View v) {
        if (current_fragment.previous != null) {
            move_to_fragment(current_fragment.previous);
        } else {
            ((DrawerLayout) findViewById(R.id.mainview)).openDrawer(Gravity.START);
        }

    }

    @Override
    public void move_to_fragment(MyFragment fragment) {
        if(current_fragment != null && current_fragment instanceof Entry_Fragment && fragment.previous != current_fragment){
            if(((Entry_Fragment)current_fragment).getRequiredToSave()){
                show_askmessage(fragment);
            }else{
                move_to_fragment_(fragment);
            }
        }else{
            move_to_fragment_(fragment);
        }
    }
    private void move_to_fragment_(MyFragment fragment){
        clear_btns();
        ((TextView) findViewById(R.id.mainview_txtv_title)).setText(fragment.title);
        getFragmentManager().beginTransaction().replace(R.id.mainview_fragment, fragment).commit();
        current_fragment = fragment;
        if (fragment.previous != null) {
            ((ImageView) findViewById(R.id.mainview_btn_nav)).setImageResource(R.drawable.ic_back);
        } else {
            ((ImageView) findViewById(R.id.mainview_btn_nav)).setImageResource(R.drawable.ic_menu);
        }
    }

    private void clear_btns() {
        FrameLayout layout = findViewById(R.id.mainview_fragment_btn1);
        layout.removeAllViews();
        layout.setVisibility(View.GONE);
        layout = findViewById(R.id.mainview_fragment_btn2);
        layout.removeAllViews();
        layout.setVisibility(View.GONE);
    }

    public void nav_item_click(View v) {
        ((DrawerLayout) findViewById(R.id.mainview)).closeDrawer(Gravity.START);
        if (v.getId() == R.id.main_nav_profile) {
            move_to_fragment(new Profile_Fragment(this));
        } else {
            String target = ((TextView) v.findViewById(R.id.mainview_nav_item_txtv)).getText().toString();
            if (target.equals(ACADEMIC_SCHEDULE_TITLE)) {
                move_to_fragment(new AcademicSchedule_Fragment(this));
            } else if (target.equals(ADMIN_PANEL_TITLE)) {
                move_to_fragment(new AdminPanel_Fragment(this));
            } else if (target.equals(CHANGE_PASSWORD_TITLE)) {
                move_to_fragment(new Change_Password_Fragment(this));
            }
        }

    }

    @Override
    public void set_buttons(ImageButton btn2, ImageView btn1) {
        clear_btns();
        FrameLayout layout_btn1 = findViewById(R.id.mainview_fragment_btn1);
        FrameLayout layout_btn2 = findViewById(R.id.mainview_fragment_btn2);
        if (btn1 != null) {
            btn1.setBackgroundResource(android.R.color.transparent);
            btn1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            layout_btn1.setVisibility(View.VISIBLE);
            layout_btn1.addView(btn1);
            set_animation(layout_btn1);
        }
        if (btn2 != null) {
            btn2.setBackgroundResource(android.R.color.transparent);
            btn2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            layout_btn2.setVisibility(View.VISIBLE);
            layout_btn2.addView(btn2);
            set_animation(layout_btn2);
        }
    }

    @Override
    public void onBackPressed() {
        if (current_fragment.previous != null) {
            move_to_fragment(current_fragment.previous);
        } else {
            controller.Logout();
            on_logout();
        }
    }


    @Override
    public void show_askmessage(MyFragment target) {
        if(!Screen_Locked){
            View message = getLayoutInflater().inflate(R.layout.message_layout, null, false);
            message.findViewById(R.id.message_btn_retry).setVisibility(View.GONE);
            message.findViewById(R.id.message_lyt_ask).setVisibility(View.VISIBLE);
            message.findViewById(R.id.message_btn_retry);
            ((ImageView)message.findViewById(R.id.message_img_icon)).setImageResource(R.drawable.ic_ask);
            ((TextView)message.findViewById(R.id.message_txt_message)).setText("Do You Want To Save The Changes You Made ?");
            set_font((ViewGroup) message);
            set_animation((ViewGroup) message);
            get_main_view().addView(message);
            ((Entry_Fragment)current_fragment).setTargetfragment_onhold(target);
            Screen_Locked = true;
        }

    }

    @Override
    public void show_confirm_message(String message_1,String message_2) {
        if(!Screen_Locked){
            ViewGroup main_view = get_main_view();
            ViewGroup v = (ViewGroup) getLayoutInflater().inflate(R.layout.confirm_message_layout,main_view,false);
            ((TextView)v.findViewById(R.id.confirm_txtv_1)).setText(message_1);
            ((TextView)v.findViewById(R.id.confirm_txtv_2)).setText(message_2);
            set_font(v);
            set_animation(v);
            main_view.addView(v);
            Screen_Locked = true;
        }

    }

    @Override
    public void hide_message() {
        super.hide_message();
    }

    public void on_messageresult(View v) {
        hide_message();
        ((Entry_Fragment)current_fragment).on_askmessage_result(((TextView) v).getText().toString().equals("YES"));
    }

    public void logout(View v){
        controller.Logout();
        super.onBackPressed();
    }

    public void on_confirm(View v){
        current_fragment.on_confirm_authentication(
                ((TextView) v).getText().toString().equals("YES"),
                ((TextView)findViewById(R.id.confirm_txte_password)).getText().toString()
                );
        hide_message();
    }
}
