public abstract class View extends Client {
  String msg = msgObj.msg;    // msgObj declared in Client class.
  String username = userObj.username;  // userObj declared somewhere in hierarchy


  public void displayMessage( String msg, String username );
  public void displayInfo();
}
