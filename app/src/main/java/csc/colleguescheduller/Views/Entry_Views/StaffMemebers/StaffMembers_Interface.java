package csc.colleguescheduller.Views.Entry_Views.StaffMemebers;

import java.util.ArrayList;

import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.MyInterface;

public interface StaffMembers_Interface extends MyInterface {
    void on_members_loaded(ArrayList<StaffMember> members);
    void on_member_removed();
    void on_save_success();
}
