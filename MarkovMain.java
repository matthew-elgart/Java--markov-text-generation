/**
 * Name: Matt Elgart
 * NetID: mje21
 * Collaborators: Matthew Faw
 * Resources: StackOverflow
 * Main for Markov Text Generation Program
 */
public class MarkovMain {
    public static void main(String[] args){
        IModel model = new MapMarkovModel();
        SimpleViewer view = new SimpleViewer("CompSci 201 Markov Generation", "k count>");
        view.setModel(model);
    }
}
