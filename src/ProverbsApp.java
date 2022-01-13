import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ProverbsApp {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model);

        model.addObserver(view);
        view.setController(controller);
    }
}

class Model extends Observable {
    List<String> proverbs;

    public Model() {
        this.proverbs = new ArrayList<String>();

        this.initiateList();
    }

    private void initiateList(){
        for (int i = 0; i < 10; i++) {
            String temp = "Proverb number" + String.valueOf(i);
            this.proverbs.add(temp);
        }
    }

    public void getRandomProverb(){
        String proverb = proverbs.get((int)(Math.random() * (9 - 0)));

        setChanged();
        notifyObservers(proverb);
    }
}

class View extends JFrame implements Observer{
    private JButton button;
    private JLabel display;
    private JPanel panel;

    public View() {
        super("Proverbi");

        this.panel = new JPanel();
        this.panel.setBackground(Color.GRAY);
        super.add(panel);

        this.button = new JButton();
        this.button.setText("Chiedi proverbio");
        this.panel.add(this.button);

        this.display = new JLabel();
        this.panel.add(this.display);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 100);
        setVisible(true);
    }

    public void updateView(String proverb) {
        this.display.setText(proverb);
    }

    public void setController(Controller controller){
        this.button.addActionListener(controller);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o != null && o instanceof Model)
            this.display.setText((String) arg);
    }
}

class Controller implements ActionListener {
    private Model model;

    public Controller (Model model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.getRandomProverb();
        //this.view.updateView(proverb);
    }
}