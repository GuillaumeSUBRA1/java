package grapheur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

public class Graphic extends JComponent implements Runnable {
    int width = 800;
    int height = 800;

    float min, max, pas, plage;
    String eq;
    int mouseX = 0;
    int mouseY = 0;
    Map<Float, int[]> coordonneesMap = new HashMap<>();
    Map<Float, int[]> coordonneesLineMap = new HashMap<>();
    Map<Float, Float> fx = new HashMap<>();

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
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();

            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseX = 0;
                mouseY = 0;
                repaint();
            }
        });

        setFocusable(true);
        requestFocusInWindow();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawAxes(g);
        drawSteps(g);
        g.setColor(Color.GREEN);

        calculateLineValues();

        for(Map.Entry<Float, int[]> px : coordonneesLineMap.entrySet()) {
            if (canDrawValue(px.getValue()[0], px.getValue()[1], px.getValue()[2], px.getValue()[3])) {
                int[] coordonnees = new int[2];
                coordonnees[0] = width / 2 - px.getValue()[0];
                coordonnees[1] = height / 2 - px.getValue()[1];
                coordonneesMap.put(px.getKey(), coordonnees);
                g.drawLine(
                        width / 2 - px.getValue()[0],
                        height / 2 - px.getValue()[1],
                        width / 2 - px.getValue()[2],
                        height / 2 - px.getValue()[3]);
            }
        }

        updateMouse(g);
    }

    public void calculateLineValues(){
        String eq1, eq2;
        if (eq != null && !eq.isEmpty()) {
            for (float i = min; i <= max; i += 0.01F) {
                float next = i + pas;
                if (eq.contains("x")) {
                    eq1 = eq.replaceAll("x", String.valueOf(i));
                    eq2 = eq.replaceAll("x", String.valueOf(next));
                } else {
                    eq1 = eq;
                    eq2 = eq;
                }

                float x1 = Evaluateur.Calcul(Evaluateur.createArbre(eq1));
                float x2 = Evaluateur.Calcul(Evaluateur.createArbre(eq2));

                // tra�age d'une ligne reliant le point de coordonnees (x,f(x)) au point de coordonnees (x+1,f(x+1))

                int xStart = (int) (((float) width / 2) / (plage / 2) * i);
                int xEnd = (int) (x1 * (((float) height / 2) / (plage / 2)));
                int yStart = (int) (((float) width / 2) / (plage / 2) * (next));
                int yEnd = (int) (x2 * (((float) height / 2) / (plage / 2)));

                int[] points = new int[4];
                points[0] = xStart;
                points[1] = xEnd;
                points[2] = yStart;
                points[3] = yEnd;
                fx.put(i,x1);
                coordonneesLineMap.put(i, points);
            }
        }
    }

    public void updateMouse(Graphics g) {
        if (isMouseIn() && eq != null && !eq.isEmpty()) {
            g.drawLine(mouseX, 0, mouseX, height);
            int yMouse = getYByX(mouseX);
            float valString = getValByX(mouseX);
            if(valString !=0) {
                g.setColor(Color.BLACK);
                g.drawRect(mouseX, yMouse, 1, 1);
                g.drawString("x = " + String.format("%.02f", valString*-1), mouseX + 5, yMouse + 10);
                g.drawString("f(x) = " + String.format("%.02f", fx.get(valString)), mouseX + 5, yMouse + 20);
            }
        }
    }

    public void drawAxes(Graphics g) {
        g.drawLine(width / 2, 0, width / 2, height); // vertical
        g.drawLine(0, height / 2, width, height / 2); // horizontal
        g.drawString("0", width / 2 + 5, height / 2 + 15);
    }

    public void drawSteps(Graphics g) {
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
    }


    public boolean isMouseIn() {
        return mouseX > 0 && mouseY > 0 && mouseX < width && mouseY < height;
    }

    public boolean canDrawValue(float x1, float x2, float y1, float y2) {
        return x1 <= (float) width && x2 <= (float) width && y1 <= (float) height && y2 <= (float) height;
    }

    public int getYByX(int x) {
        for (Map.Entry<Float, int[]> entry : coordonneesMap.entrySet()) {
            if (entry.getValue()[0] == x) {
                return entry.getValue()[1];
            }
        }
        return 0;
    }

    public float getValByX(int x) {
        for (Map.Entry<Float, int[]> entry : coordonneesMap.entrySet()) {
            if (entry.getValue()[0] == x) {
                return entry.getKey();
            }
        }
        return 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
