package grapheur;

public class Noeud {

	String valeur;
	Noeud filsGauche;
	Noeud filsDroit;

	public Noeud(String valeur) {
		this.valeur = valeur;
		this.filsDroit = null;
		this.filsGauche = null;
	}

	public Noeud(String valeur, Noeud FD) {
		this.valeur = valeur;
		this.filsDroit = FD;
		this.filsGauche = null;
	}

	public Noeud(String valeur, Noeud FG, Noeud FD) {
		this.valeur = valeur;
		this.filsDroit = FD;
		this.filsGauche = FG;
	}

	public String getValeur() {
		return valeur;
	}

	public Noeud getFilsGauche() {
		return filsGauche;
	}

	public Noeud getFilsDroit() {
		return filsDroit;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public void setFilsGauche(Noeud filsGauche) {
		this.filsGauche = filsGauche;
	}

	public void setFilsDroit(Noeud filsDroit) {
		this.filsDroit = filsDroit;
	}

	@Override
	public String toString(){
		String res= "";
		if(filsGauche!=null){
			res+= filsGauche.getValeur()+" ";
		}
		if(valeur!=null){
			res+= valeur+" ";
		}
		if(filsDroit!=null){
			res+= filsDroit.getValeur()+" ";
		}
		return "Noeud = "+res;
	}

	public boolean onlyVal(){
		return valeur !=null && filsDroit==null && filsGauche==null;
	}
}
