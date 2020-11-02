package simulator;

import model.Location;
import model.Transition;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Simulator {

    private int steps;

    public void setSteps(int steps) {
        this.steps = steps;
    }

    private boolean checkInputTokens(Transition transition){
        boolean status = true;
        if(transition.getInput().size() == 0)
            return false;
        for(int i = 0; i < transition.getInput().size(); i++){
            Location location = PetriNet.getInstance().getLocationById(transition.getInput().get(i));
            if(location.getTokens() == 0)
                status = false;
        }
        return status;
    }

    private void takeInputTokens(Transition transition){
        for(int i = 0; i < transition.getInput().size(); i++){
            Location location = PetriNet.getInstance().getLocationById(transition.getInput().get(i));
            if(location.decreaseTokens())
                System.out.println("Token token from "+location.getId());

            else
                System.out.println("Error ! No tokens in "+location.getId());
        }
        transition.addTempToken();
        System.out.println("Token added in "+transition.getId());
        transition.setDelay();
    }

    private void putOutputTokens(Transition transition){
        if(transition.getDelay() == 0) {
            if (transition.decreaseTokens()) {
                System.out.println("Temp token taken from " + transition.getId());
                for (int i = 0; i < transition.getOutput().size(); i++) {
                    Location location = PetriNet.getInstance().getLocationById(transition.getOutput().get(i));
                    location.addToken();
                    System.out.println("Token added in " + location.getId());
                }
            } else {
                //System.out.println("No temp token in " + transition.getId());
            }
        } else {
            System.out.println(transition.getId()+" has delay: "+transition.getDelay());
        }
    }

    private void checkAllTransitions(){
        for (int i = 0; i < PetriNet.getInstance().getTransitions().size(); i++) {

            Transition transition = PetriNet.getInstance().getTransitions().get(i);

            if(transition.getDelay() > 0){
                transition.decreaseDelay();
            }

            if(transition.getTempTokens() == 0) {
                if (true == checkInputTokens(transition)) {
                    System.out.println("Valid input for " + transition.getId());
                    takeInputTokens(transition);
                } else {
                    //System.out.println("No valid input for " + transition.getId());
                }
            } else
                System.out.println("Transition "+transition.getId()+" already activated");
        }
        for (int i = 0; i < PetriNet.getInstance().getTransitions().size(); i++) {
            Transition transition = PetriNet.getInstance().getTransitions().get(i);
            putOutputTokens(transition);
        }
    }

    public void simulatePetriNet(PrintWriter printWriter){
        for(int i = 0; i < steps; i++){
            printPetriNetState(i, printWriter);
            checkAllTransitions();
        }
    }

    private void printPetriNetState(int i, PrintWriter printWriter){
        printWriter.print("STEP "+i+" : ");
        PetriNet.getInstance().getLocations().forEach(location ->{
            System.out.print("| "+location.getId() + ": "+location.getTokens()+" ");
            printWriter.print("| "+location.getId() + ": "+location.getTokens()+" ");
        });
        printWriter.println();
        System.out.println();
        PetriNet.getInstance().getTransitions().forEach(
                transition -> System.out.print("| "+transition.getId()+" : "+transition.getTempTokens()+" ")
        );
        System.out.println();
    }
}
