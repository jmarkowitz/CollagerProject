package controller;

/**
 * Represents how certain commands can be executed. Any command subclass that implements this will
 * be able to change the model in some way based on the command chosen.
 */
public interface CollagerCommand {

  /**
   * Method that will execute the command
   */
  void execute(); //TODO: need to add parameter for model here

}