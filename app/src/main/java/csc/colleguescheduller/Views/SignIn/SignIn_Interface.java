package csc.colleguescheduller.Views.SignIn;

/*
Ahmed Mokhtar Hassanin
 */

import csc.colleguescheduller.Views.MyInterface;

public interface SignIn_Interface extends MyInterface {

    void on_success();
    void on_signin();
    void on_database_error(String message);

    /*
        NOTE :-
            this Interface extends MyInterface Which Contains the Function on_connection_error()
            So This Interface Contains This Function As Well So Every Future Interface To Be Made
     */
}
