package se.project.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import se.project.app.App;

import java.util.HashMap;

public class FullController extends AnchorPane {
    public static class zip{
        private Node node;
        private Controlled controlled;

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public Controlled getControlled() {
            return controlled;
        }

        public void setControlled(Controlled controlled) {
            this.controlled = controlled;
        }

        public zip(Node node, Controlled controlled) {
            this.node = node;
            this.controlled = controlled;
        }

    }
    public Controlled getController(String name){
        return screensx.get(name).getControlled();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    private int customerId;

    private final HashMap<String, Node> screens = new HashMap<>();
    private final HashMap<String, zip> screensx = new HashMap<>();

    public FullController(){
        super();
    }

    public void addSx(String name, zip screen){
        screensx.put(name, screen);
    }

    public Node getSx(String name){
        return screensx.get(name).getNode();
    }

    public boolean loadSx(String name, String resource){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(resource));
            Parent loadS = loader.load();
            Controlled mySController = loader.getController();
            mySController.setSParent(this);
            addSx(name, new zip(loadS, mySController));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean setSx(String name){

        if(screensx.get(name).getNode() != null){
            if(!getChildren().isEmpty()){
                getChildren().remove(0);
                getChildren().add(0, screensx.get(name).getNode());
                App.resizeScreen();
            }else{
                getChildren().add(screensx.get(name).getNode());
                App.resizeScreen();
            }
            return true;
        }else{
            System.out.println("Screen not load\n");
            return false;
        }
    }

    public boolean unloadSx(String name){
        if(screens.remove(name) == null){
            System.out.println("Screen not exist");
            return false;
        }
        return true;
    }

}

