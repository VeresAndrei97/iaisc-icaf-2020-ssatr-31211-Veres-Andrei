package simulator;

import model.Location;
import model.Transition;

import java.util.ArrayList;
import java.util.List;

public class PetriNet {

    private PetriNet(){ 
        
    };

    private static PetriNet instance = new PetriNet();

    public static PetriNet getInstance() {
        return instance;
    }

    private List<Location> locations = new ArrayList<Location>();
    private List<Transition> transitions = new ArrayList<Transition>();

    public void addLocation(Location location){
        locations.add(location);
    }

    public void addTransition(Transition transition){
        transitions.add(transition);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public Location getLocationById(String id){
        for(Location location : locations){
            if(location.getId().equals(id))
                return location;
        }
        System.out.println("Location not found");
        return null;
    }
}
