package grapheur;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Graphic extends JComponent implements Runnable {

    float min, max, pas, plage;
    String eq;
    int mouseX, mouseY;

    int width = 1200;
    int height = 800;

    public Graphic(float min, float max, float pas, String eq) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        this.min = min;
        this.max = max;
        this.pas = pas;
        this.plage = Math.abs(min) + max;
        this.eq = eq;

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX -= e.getX();
                mouseY += e.getY();
                System.out.println("x = " + e.getX() + " y = " + e.getY());
//                repaint();
            }
        });

        setFocusable(true);
        requestFocusInWindow();

    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);

        // tracer des axes
        g.drawLine(width / 2, 0, width / 2, height); // vertical
        g.drawLine(0, height / 2, width, height / 2); // horizontal
        g.drawString("0", width / 2 + 5, height / 2 + 15);

        // tra�age des traits de pas
        if (pas > 0) {
            for (float i = 0; i <= plage; i++) {
                g.drawLine((int) (width - ((width / plage) * i)), height / 2, (int) (width - ((width / plage) * i)),
                        height / 2 - 4);
                g.drawLine(width / 2, (int) (height - ((height / plage) * i)), width / 2 + 4,
                        (int) (height - ((height / plage) * i)));
                float y;
                if (i > plage / 2) {
                    y = min + i;
                    g.drawString("-" + (pas < 1 ? String.format("%.02f", y) : String.valueOf(y)), (int) (width - ((width / plage) * i)) + 5, height / 2 + 15);
                    g.drawString((pas < 1 ? String.format("%.02f", y) : String.valueOf(y)), width / 2 + 5, (int) (height - ((height / plage) * i)) + 15);
                } else if (i < plage / 2) {
                    y = max - i;
                    g.drawString(pas < 1 ? String.format("%.02f", y) : String.valueOf(y), (int) (width - ((width / plage) * i)) + 5, height / 2 + 15);
                    g.drawString("-" + (pas < 1 ? String.format("%.02f", y) : String.valueOf(y)), width / 2 + 5, (int) (height - ((height / plage) * i)) + 15);
                }
            }
        }

        if (eq != null && !eq.isEmpty()) {
            String eq1, eq2;
            for (float i = min; i <= max; i += pas) {
                float next = i+pas;
                if (eq.contains("x")) {
                    eq1 = eq.replaceAll("x", String.valueOf(i));
                    eq2 = eq.replaceAll("x", String.valueOf(next));
                } else {
                    eq1 = eq;
                    eq2 = eq;
                }

                float x1 = Evaluateur.Calcul(Evaluateur.createArbre(eq1));
                float x2 = Evaluateur.Calcul(Evaluateur.createArbre(eq2));

                if (x1 < 0) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }

                // tra�age d'une ligne reliant le point de coodonn�es (x,f(x)) au point de
                // coordonn�es (x+1,f(x+1))
                float xStart = ((float) width / 2) / (plage / 2) * i;
                float xEnd = x1 * (((float) height / 2) / (plage / 2));
                float yStart = ((float) width / 2) / (plage / 2) * (next);
                float yEnd = x2 * (((float) height / 2) / (plage / 2));
                g.drawLine(
                        (int) (width / 2 - xStart),
                        (int) (height / 2 - xEnd),
                        (int) (width / 2 - yStart),
                        (int) (height / 2 - yEnd));

            }

        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
