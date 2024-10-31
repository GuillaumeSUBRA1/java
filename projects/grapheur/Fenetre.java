package grapheur;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Fenetre extends JFrame implements ActionListener {

    // composantes
    Float xmin, ymin, xmax, ymax, pas;

    JPanel coordonnees = new JPanel();
    JPanel grapher = new JPanel();
    JPanel action = new JPanel();

    // composants NORTH
    JLabel x = new JLabel("x = ");
    JLabel y = new JLabel("y = ");
    JLabel fonctionPos = new JLabel("f(x) = ");

    JTextArea sourisXText = new JTextArea(0, 6);
    JTextArea sourisYText = new JTextArea(0, 6);
    JTextArea fonctionText = new JTextArea(0, 6);

    // composants WEST
    GridBagLayout grid = new GridBagLayout();
    GridBagConstraints bag = new GridBagConstraints();

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

    Insets padding = new Insets(2, 5, 2, 2);

    JButton refresh = new JButton("Refresh") {
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getBackground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }

        @Override
        public void updateUI() {
            super.updateUI();
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    };

    JButton eval = new JButton("Evaluate") {
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getBackground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }

        @Override
        public void updateUI() {
            super.updateUI();
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    };
    JLabel fonctionEval = new JLabel("f(x) = ");
    JTextArea fonctionEvalText = new JTextArea(0, 30);

    public Fenetre() {
        super("Grapheur");
        setSize(1200, 1200);
        setLocation(50, 50);
        setVisible(true);

        coordonnees.add(x);
        coordonnees.add(sourisXText);
        coordonnees.add(y);
        coordonnees.add(sourisYText);
        coordonnees.add(fonctionPos);
        coordonnees.add(fonctionText);
        coordonnees.setBackground(new Color(94, 31, 255));

        refresh.setBackground(new Color(0, 120, 255));
        refresh.setFont(new Font("Arial", Font.BOLD, 12));

        xminText.setText("-10");
        xminText.setMargin(padding);
        xmaxText.setText("10");
        xmaxText.setMargin(padding);
        yminText.setText("-10");
        yminText.setMargin(padding);
        ymaxText.setText("10");
        ymaxText.setMargin(padding);
        pasText.setText("0.1");
        pasText.setMargin(padding);

        int[] widths = new int[2];
        widths[0] = 150;
        widths[1] = 150;
        grid.columnWidths = widths;
        action.setLayout(grid);
        action.setBorder(new EmptyBorder(5, 5, 5, 5));
        action.setPreferredSize(new Dimension(300, 800));
        bag.insets = new Insets(10, 10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL;

        bag.gridx = 0;
        bag.gridy = 0;
        action.add(xminLabel, bag);
        bag.gridy = 1;
        action.add(xmaxLabel, bag);
        bag.gridy = 2;
        action.add(yminLabel, bag);
        bag.gridy = 3;
        action.add(ymaxLabel, bag);
        bag.gridy = 4;
        action.add(xgridLabel, bag);
        bag.gridy = 5;
        action.add(ygridLabel, bag);
        bag.gridy = 6;
        action.add(pasLabel, bag);

        bag.gridx = 1;
        bag.gridy = 0;
        action.add(xminText, bag);
        bag.gridy = 1;
        action.add(xmaxText, bag);
        bag.gridy = 2;
        action.add(yminText, bag);
        bag.gridy = 3;
        action.add(ymaxText, bag);
        bag.gridy = 4;
        action.add(ygridText, bag);
        bag.gridy = 5;
        action.add(xgridText, bag);
        bag.gridy = 6;
        action.add(pasText, bag);

        bag.gridx = 0;
        bag.gridy = 7;
        bag.gridwidth = 2;
        action.add(refresh, bag);
        action.setBackground(new Color(0, 153, 255));

        bag.gridx = 0;
        bag.gridy = 8;
        bag.gridwidth = 2;

        action.add(new JSeparator(), bag);

        bag.gridx = 0;
        bag.gridy = 10;
        action.add(fonctionEval, bag);
        bag.gridx = 1;
        fonctionEvalText.setMargin(padding);
        action.add(fonctionEvalText, bag);

        bag.gridx = 0;
        bag.gridwidth = 2;
        bag.gridy = 11;
        action.add(eval, bag);


        refresh.addActionListener(this);

        fonctionEvalText.setText("x*2+2");
        eval.setBackground(new Color(0, 120, 255));

        float min = Float.parseFloat(xminText.getText());
        float max = Float.parseFloat(xmaxText.getText());
        float pas = Float.parseFloat(pasText.getText());
        String function = fonctionEvalText.getText();

        grapher.add(new Graphic(min, max, pas, function));
        grapher.setBackground(new Color(0, 200, 255));
        eval.addActionListener(this);

        this.getContentPane().add(coordonnees, BorderLayout.NORTH);
        this.getContentPane().add(action, BorderLayout.WEST);
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
