package csc.colleguescheduller.Views;

/*
Ahmed Mokhtar Hassanin
 */

public interface MyInterface {
    void on_connection_error();
    void on_database_error(String message);
    void on_authentication_error(String message);
    void on_logout();
}
