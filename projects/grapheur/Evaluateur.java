package grapheur;


public class Evaluateur {
    public static float Calcul(Noeud n) {
        float x = 0;
        try {
            switch (n.getValeur()) {
                case "x":
                    break;
                case "+":
                    x = Calcul(n.getFilsGauche()) + Calcul(n.getFilsDroit());
                    break;
                case "-":
                    if (n.getFilsGauche() == null && n.getFilsDroit().onlyVal()) {
                        x = Float.parseFloat(n.getFilsDroit().getValeur()) * -1;
                    } else {
                        x = Calcul(n.getFilsGauche()) - Calcul(n.getFilsDroit());
                    }
                    break;
                case "*":
                    x = Calcul(n.getFilsGauche()) * Calcul(n.getFilsDroit());
                    break;
                case "/":
                    x = Calcul(n.getFilsGauche()) / Calcul(n.getFilsDroit());
                    break;
                case "cos":
                    x = (float) Math.cos(Calcul(n.getFilsDroit()));
                    break;
                case "sin":
                    x = (float) Math.sin(Calcul(n.getFilsDroit()));
                    break;
                case "tan":
                    x = (float) Math.tan(Calcul(n.getFilsDroit()));
                    break;
                case "abs":
                case "abs-":
                    x = Math.abs(Calcul(n.getFilsDroit()));
                    break;
                case "^":
                    x = (float) Math.pow(Calcul(n.getFilsGauche()), Calcul(n.getFilsDroit()));
                    break;
                case "rac":
                    x = (float) Math.sqrt(Calcul(n.getFilsDroit()));
                    break;
                default:
                    x += Float.parseFloat(n.getValeur());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public static Noeud createArbre(String eq) {
        Noeud n = new Noeud(null);
        if (!eq.isEmpty() && parentheses(eq)) {
            String fg, fd;

            // parenth�ses
            if (eq.indexOf("(") == 0 && posParentheseFermee(eq) == eq.length() - 1) {
                n = createArbre(eq.substring(1, posParentheseFermee(eq)));
            } else if (eq.indexOf("(") == 0 && posParentheseFermee(eq) < eq.length() - 1) {
                fg = eq.substring(1, posParentheseFermee(eq));
                fd = eq.substring(posParentheseFermee(eq) + 2);
                n = new Noeud(Character.toString(eq.charAt(posParentheseFermee(eq) + 1)), createArbre(fg),
                        createArbre(fd));
            } else if (eq.indexOf("(") > 0 && posParentheseFermee(eq) <= eq.length() - 1) {
                fg = eq.substring(0, eq.indexOf("(") - 1);
                if (posParentheseFermee(eq) == eq.length() - 1) {
                    fd = eq.substring(eq.indexOf("(") + 1, posParentheseFermee(eq));
                } else {
                    fd = eq.substring(eq.indexOf("("));
                }
                n = new Noeud(Character.toString(eq.charAt(eq.indexOf("(") - 1)), createArbre(fg), createArbre(fd));

                // op�rateurs + et -
            } else if (eq.contains("+") || (eq.contains("-") && eq.lastIndexOf("-") > 0
                    && (eq.charAt(eq.lastIndexOf("-") - 1) == ')' || Character.isDigit(eq.lastIndexOf("-") - 1)))) {

                if (eq.lastIndexOf("+") > eq.lastIndexOf("-")) {
                    fg = eq.substring(0, eq.lastIndexOf("+"));
                    fd = eq.substring(eq.lastIndexOf("+") + 1);
                    n = new Noeud("+", createArbre(fg), createArbre(fd));
                } else if (eq.lastIndexOf("+") < eq.lastIndexOf("-")) {
                    fg = eq.substring(0, eq.lastIndexOf("-"));
                    fd = eq.substring(eq.lastIndexOf("-") + 1);
                    n = new Noeud("-", createArbre(fg), createArbre(fd));
                }
                // op�rateurs * et /
            } else if ((eq.contains("*") && !eq.contains("**")) || eq.contains("/")) {
                if (eq.lastIndexOf("/") < eq.lastIndexOf("*") && !eq.contains("**")) {
                    fg = eq.substring(0, eq.lastIndexOf("*"));
                    fd = eq.substring(eq.lastIndexOf("*") + 1);
                    n = new Noeud("*", createArbre(fg), createArbre(fd));
                } else if (eq.lastIndexOf("/") > eq.lastIndexOf("*")) {
                    fg = eq.substring(0, eq.lastIndexOf("/"));
                    fd = eq.substring(eq.lastIndexOf("/") + 1);
                    n = new Noeud("/", createArbre(fg), createArbre(fd));
                }

                // puissance
            } else if (eq.contains("E")) {
                int debut = eq.lastIndexOf("E") - 1;
                int fin = eq.lastIndexOf("E") + 1;
                while (debut > 0) {
                    if (Character.isDigit(eq.charAt(debut)) || eq.charAt(debut) == '.') {
                        debut--;
                        if (eq.charAt(debut) == '-') {
                            break;
                        }
                    }
                }
                while (fin < eq.length()) {
                    if (Character.isDigit(eq.charAt(fin)) || eq.charAt(fin) == '.' || (eq.charAt(fin) == '-' && fin == eq.indexOf("E") + 1)) {
                        fin++;
                    } else {
                        break;
                    }
                }

                double power = 0.0;
                if (eq.contains("E")) {
                    power = Double.parseDouble(eq.substring(eq.indexOf("E") + 1, fin));
                }

                if (power < -3) {
                    n = new Noeud("0");
                } else {
                    float v = Float.parseFloat(eq.substring(debut, eq.lastIndexOf("E")));
                    float val = v * ((float) Math.pow(10, power));
                    n = new Noeud(Float.toString(val));
                }

                // fonctions trigonom�triques
            } else if (eq.contains("^")) {
                int debut = eq.lastIndexOf("^") - 1;
                int fin = eq.lastIndexOf("^") + 1;
                while (debut > 0) {
                    if (Character.isDigit(eq.charAt(debut)) || eq.charAt(debut) == '.') {
                        debut--;
                        if (eq.charAt(debut) == '-') {
                            break;
                        }
                    }
                }
                while (fin < eq.length()) {
                    if (Character.isDigit(eq.charAt(fin)) || eq.charAt(fin) == '.' || (eq.charAt(fin) == '-' && fin == eq.indexOf("E") + 1)) {
                        fin++;
                    } else {
                        break;
                    }
                }

                fg = eq.substring(debut, eq.lastIndexOf("^"));
                fd = eq.substring(eq.indexOf("^") + 1);
                n = new Noeud("^", new Noeud(fg), new Noeud(fd));

                // fonctions trigonom�triques
            } else if (eq.contains("cos")) {
                fd = eq.substring(eq.indexOf("cos") + 3);
                n = new Noeud("cos", new Noeud(fd));
            } else if (eq.contains("sin")) {
                fd = eq.substring(eq.indexOf("sin") + 3);
                n = new Noeud("sin", new Noeud(fd));
            } else if (eq.contains("tan")) {
                fd = eq.substring(eq.indexOf("tan") + 3);
                n = new Noeud("tan", new Noeud(fd));

                // valeur absolue
            } else if (eq.contains("abs")) {
                if (eq.charAt(3) == '-') {
                    fd = eq.substring(eq.indexOf("abs") + 3);
                } else {
                    fd = eq.substring(eq.indexOf("abs-") + 4);
                }
                n = new Noeud("abs", new Noeud(fd));

                // racine carr�e
            } else if (eq.contains("rac")) {
                fd = eq.substring(eq.indexOf("rac") + 3);
                n = new Noeud("rac", new Noeud(fd));

                // nombre n�gatif
            } else if (eq.lastIndexOf("-") == 0 && isDecimalNumber(eq.substring(1))) {
                n = new Noeud("-", null, new Noeud(eq));

                // nombre décimal
            } else if (eq.contains(".") && eq.split(".").length == 2 && isDecimalNumber(eq)) {
                n = new Noeud(eq);
                // constante ou variable
            } else if (Character.isDigit(eq.charAt(0)) || (eq.charAt(0) == 'x')) {
                n = new Noeud(eq);
            }
        }
        return n;
    }

    // v�rificaiton des parenth�ses
    public static boolean parentheses(String eq) {
        int parenthese = 0;
        for (int i = 0; i < eq.length(); i++) {
            if (eq.charAt(i) == '(') {
                parenthese++;
            } else if (eq.charAt(i) == ')') {
                parenthese--;
            }
        }
        return parenthese == 0;
    }

    public static boolean isDecimalNumber(String eq) {
        if (eq.indexOf(".") != eq.lastIndexOf(".")) {
            return false;
        }
        for (int i = 0; i < eq.length(); i++) {
            if (!Character.isDigit(eq.charAt(i)) && eq.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    // indice de la parenthese fermee correspondant a celle de l'indice 0
    public static int posParentheseFermee(String eq) {
        int pos = 0;
        int parenthese = 1;
        for (int i = eq.indexOf("(") + 1; i < eq.length(); i++) {
            if (eq.charAt(i) == '(') {
                parenthese++;
            } else if (eq.charAt(i) == ')') {
                parenthese--;
            }
            if (parenthese == 0) {
                System.out.println("pos=" + i);
                pos = i;
                break;
            }
        }
        return pos;
    }
}
