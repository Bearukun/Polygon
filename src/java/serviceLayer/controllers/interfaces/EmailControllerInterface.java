package serviceLayer.controllers.interfaces;


public interface EmailControllerInterface {
    
     public void send(String to, String title, String body) throws Exception;
     
}
