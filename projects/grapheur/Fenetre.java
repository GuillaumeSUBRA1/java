package grapheur;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {

    // composantes
    Float xmin, ymin, xmax, ymax, pas;

    JPanel coordonnees = new JPanel();
    JPanel action = new JPanel();
    JPanel grapher = new JPanel();
    JPanel evaluation = new JPanel();

    // composants NORTH
    JLabel x = new JLabel("x = ");
    JLabel y = new JLabel("y = ");
    JLabel fonctionPos = new JLabel("f(x) = ");

    JTextArea sourisXText = new JTextArea(0, 6);
    JTextArea sourisYText = new JTextArea(0, 6);
    JTextArea fonctionText = new JTextArea(0, 6);

    // composants WEST
    JLabel xminLabel = new JLabel("x min");
    JLabel xmaxLabel = new JLabel("x max");
    JLabel yminLabel = new JLabel("y min");
    JLabel ymaxLabel = new JLabel("y max");
    JLabel pasLabel = new JLabel("pas");
    JLabel xgridLabel = new JLabel("x grid");
    JLabel ygridLabel = new JLabel("y grid");

    JTextArea xminText = new JTextArea(1, 10);
    JTextArea xmaxText = new JTextArea(1, 10);
    JTextArea yminText = new JTextArea(1, 10);
    JTextArea ymaxText = new JTextArea(1, 10);
    JTextArea pasText = new JTextArea(1, 10);
    JTextArea xgridText = new JTextArea(1, 10);
    JTextArea ygridText = new JTextArea(1, 10);

    JButton refresh = new JButton("Refresh");
    JButton zoomPlus = new JButton("+");
    JButton zoomMoins = new JButton("-");

    JButton eval = new JButton("Evaluate");
    JLabel fonctionEval = new JLabel("f(x) = ");
    JTextArea fonctionEvalText = new JTextArea(0, 30);

    int coordonneeHeight = 30;

    public Fenetre() {
        super("Grapheur");
        setSize(1200, 1200);
        setVisible(true);

        coordonnees.add(x);
        coordonnees.add(sourisXText);
        coordonnees.add(y);
        coordonnees.add(sourisYText);
        coordonnees.add(fonctionPos);
        coordonnees.add(fonctionText);
        coordonnees.setBackground(new Color(94, 31, 255));

        xminText.setText("-10");
        xmaxText.setText("10");
        yminText.setText("-10");
        ymaxText.setText("10");
        pasText.setText("0.1");

        action.setLayout(null);
        action.setPreferredSize(new Dimension(120, 800));
        xminLabel.setBounds(5, 5, 50, coordonneeHeight);
        xmaxLabel.setBounds(5, coordonneeHeight + 5, 50, coordonneeHeight);
        yminLabel.setBounds(5, coordonneeHeight * 2 + 5, 50, coordonneeHeight);
        ymaxLabel.setBounds(5, coordonneeHeight * 3 + 5, 50, coordonneeHeight);
        pasLabel.setBounds(5, coordonneeHeight * 4 + 5, 50, coordonneeHeight);
        xgridLabel.setBounds(5, coordonneeHeight * 5 + 5, 50, coordonneeHeight);
        ygridLabel.setBounds(5, coordonneeHeight * 6 + 5, 50, coordonneeHeight);
        zoomPlus.setBounds(5, 215, 50, 20);
        refresh.setBounds(5, 245, 100, 20);
        xminText.setBounds(55, 5, 50, coordonneeHeight);
        xmaxText.setBounds(55, coordonneeHeight + 5, 50, coordonneeHeight);
        yminText.setBounds(55, coordonneeHeight * 2 + 5, 50, coordonneeHeight);
        ymaxText.setBounds(55, coordonneeHeight * 3 + 5, 50, coordonneeHeight);
        pasText.setBounds(55, coordonneeHeight * 4 + 5, 50, coordonneeHeight);
        xgridText.setBounds(55, coordonneeHeight * 5 + 5, 50, coordonneeHeight);
        ygridText.setBounds(55, coordonneeHeight * 6 + 5, 50, coordonneeHeight);
        zoomMoins.setBounds(55, 215, 50, 20);
        zoomPlus.setBackground(new Color(0, 120, 255));
        zoomMoins.setBackground(new Color(0, 120, 255));
        refresh.setBackground(new Color(0, 120, 255));

        action.add(refresh);
        action.add(xminLabel);
        action.add(xminText);
        action.add(xmaxLabel);
        action.add(xmaxText);
        action.add(yminLabel);
        action.add(yminText);
        action.add(ymaxLabel);
        action.add(ymaxText);
        action.add(pasLabel);
        action.add(pasText);
        action.add(xgridLabel);
        action.add(xgridText);
        action.add(ygridLabel);
        action.add(ygridText);
        action.add(zoomPlus);
        action.add(zoomMoins);
        action.setBackground(new Color(0, 153, 255));


        refresh.addActionListener(this);

        fonctionEvalText.setText("x*2+2");
        eval.setBackground(new Color(0, 120, 255));
        evaluation.add(eval);
        evaluation.add(fonctionEval);
        evaluation.add(fonctionEvalText);
        evaluation.setBackground(new Color(94, 31, 255));

        float min = Float.parseFloat(xminText.getText());
        float max = Float.parseFloat(xmaxText.getText());
        float pas = Float.parseFloat(pasText.getText());
        String function = fonctionEvalText.getText();

        grapher.add(new Graphic(min, max, pas, function));
        grapher.setBackground(new Color(0, 200, 255));
        eval.addActionListener(this);

        this.getContentPane().add(coordonnees, BorderLayout.NORTH);
        this.getContentPane().add(action, BorderLayout.WEST);
        this.getContentPane().add(evaluation, BorderLayout.SOUTH);
        this.getContentPane().add(grapher, BorderLayout.CENTER);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String f = fonctionEvalText.getText();
        xmin = Float.parseFloat(xminText.getText());
        xmax = Float.parseFloat(xmaxText.getText());
        ymin = Float.parseFloat(yminText.getText());
        ymax = Float.parseFloat(ymaxText.getText());
        pas = Float.parseFloat(pasText.getText());

        float min = Float.parseFloat(xminText.getText());
        float max = Float.parseFloat(xmaxText.getText());
        float pas = Float.parseFloat(pasText.getText());

        Graphic g = new Graphic(min, max, f.isEmpty() ? (float) 0.1 : pas, f);
        grapher.removeAll();
        grapher.add(g);
        if (source == eval) {
            Noeud n = Evaluateur.createArbre(fonctionEvalText.getText());
            float res = Evaluateur.Calcul(n);
            if (!f.contains("x")) {
                fonctionEvalText.setText(Float.toString(res));
            }
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        JFrame f = new Fenetre();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
