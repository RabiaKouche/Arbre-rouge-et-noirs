/*  travaillé en Binome  */
/*  lounes Kaci */
/* Rabia Kouche */



import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Collection;
import java.util.Comparator;


public class ARBRE_RN<E> extends AbstractCollection<E> 
{
	private int size;
	private Noeud racine;
	private Comparator<? super E> cmp;
	private Noeud sentinelle;

	private class Noeud 
 	{
		E clef;
		Noeud left;
		Noeud right;
		Noeud pere;
		Color clr;

		/* un noeud vide de couleur noir*/

		Noeud() {
			this.clef =null;
			this.left=this.right=this.pere=null;
			this.clr = Color.black;
		}
		/* un constructeur avec un seul parametre, un noeud de couleur rouge */
		Noeud(E element)
		{
			clr = Color.red;
			clef = element;
			left = sentinelle;
			right = sentinelle;
			pere = sentinelle;
		}
		

		
		Noeud maximum() 
		{
			
			Noeud noeud=this;
			while(noeud.right != sentinelle)
			{
				noeud=noeud.right;
			}
			return noeud;
		}



		Noeud minimum() 
		{
			
			Noeud noeud=this;
			while(noeud.left != sentinelle)
			 {
				noeud=noeud.left;
			 }
			return noeud;
		}
		
		

		Noeud suivant()
		{
			
			Noeud noeud = this;
			if(noeud.right != sentinelle) return noeud.right.minimum();
			Noeud pr = this.pere;
			while(pr != sentinelle && noeud == pr.right) 
			{
				noeud = pr;
				pr = noeud.pere;
			}
			return pr;
		}
	  }
	
	
	
	public ARBRE_RN(Comparator<? super E> cmp) 
	{
		this();
		this.cmp = cmp;
	}
	
	public ARBRE_RN(Collection<? extends E> c) 
	{
		this();
		addAll(c);
	}

	public ARBRE_RN() 
		{
			size = 0;
			sentinelle = new Noeud();
			racine = sentinelle;
			cmp=(clef1,clef2)->((Comparable<E>) clef1).compareTo(clef2);
		}


	private void Rotation_Droite(Noeud noeud) 
	{
		Noeud g = noeud.left;
		noeud.left = g.right;
		if (g.right != sentinelle)
			g.right.pere = noeud;
		g.pere = noeud.pere;
		if (noeud.pere == sentinelle)
			racine = g;
		else if (noeud.pere.right == noeud)
			noeud.pere.right = g;
		else
			noeud.pere.left = g;
		g.right = noeud;
		noeud.pere = g;
	}

	
	private void Rotation_Gauche(Noeud noeud) 
	{
		Noeud d = noeud.right;
		noeud.right = d.left;
		if (d.left != sentinelle)
			noeud.left.pere = noeud;
		d.pere = noeud.pere;
		if (noeud.pere == sentinelle)
			racine = d;
		else if (noeud.pere.left == noeud)
			noeud.pere.left = d;
		else
			noeud.pere.right = d;
		d.left = noeud;
		noeud.pere = d;
	}


	
	public void Insertion(Noeud c) {
		Noeud b = sentinelle;
		Noeud a = racine;
		while (a != sentinelle) {
			b = a;
			if (cmp.compare(c.clef, a.clef) < 0) {
				a = a.left;
			} else
				a = a.right;
		}
		c.pere = b;
		if (b == sentinelle) {
			racine = c;
		} else {
			if (cmp.compare(c.clef, b.clef) < 0) {
				b.left = c;
			} else
				b.right = c;
		}
		c.left = c.right = sentinelle;
		size++;
		Organiser(c);
	}



	private Noeud Recherche(Object o) {
		
	Noeud n=racine;
		while(n != sentinelle && (cmp.compare(n.clef,(E) o) != 0)) {
			if((cmp.compare(n.clef,(E) o)<0)) {
				n=n.right;
			}else{
				n=n.left;
			}
		}
		return n;
	}

	
	private void Organiser(Noeud noeud) 
	{
		while (noeud != racine && noeud.pere.clr == Color.red) 
		{
			if (noeud.pere == noeud.pere.pere.left) 
			{
				Noeud pd = noeud.pere.pere.right;
				if (pd.clr == Color.red) 
				{
					noeud.pere.clr = Color.black;
					pd.clr = Color.black;
					noeud.pere.pere.clr = Color.red;
					noeud = noeud.pere.pere;
				} else {
					if (noeud == noeud.pere.right) 
					{
						noeud = noeud.pere;
						Rotation_Gauche(noeud);
					}
					noeud.pere.clr = Color.black;
					noeud.pere.pere.clr = Color.red;
					Rotation_Droite(noeud.pere.pere);
				}
			} else {
				Noeud pg = noeud.pere.pere.left;
				if (pg.clr == Color.red) {
					noeud.pere.clr = Color.black;
					pg.clr = Color.black;
					noeud.pere.pere.clr = Color.red;
					noeud = noeud.pere.pere;
				} else {
					if (noeud == noeud.pere.left) {
						noeud = noeud.pere;
						Rotation_Droite(noeud);
					}
					noeud.pere.clr = Color.black;
					noeud.pere.pere.clr = Color.red;
					Rotation_Gauche(noeud.pere.pere);
				}
			}
		}
		racine.clr = Color.black;
	}

	
	private Noeud Suppression(Noeud c)
	{
		Noeud a, b;

		if(c.left == sentinelle || c.left == sentinelle ) 
		{
			b = c;		
		}else {
			b = c.suivant();
		}
		if(b.left != sentinelle) 
		{
			a = b.left;
		}
		else {
			a = b.right;
		}
		a.pere = b.pere;
		if(b.pere == sentinelle) 
		{
			racine = a;
		}
		else{
			if(b == b.pere.left) 
			{
				b.pere.left = a;
			}
			else {
				b.pere.right = a;
			}
		}
		if(b != c) {
			 c.clef = b.clef;
		}

		if(b.clr == Color.black) 
		{
			Organiser_Supp(a);
		}
		return c.suivant();
	}    

      
	 

	private void Organiser_Supp(Noeud noeud) {
		while (noeud != racine && noeud.clr == Color.black) {
			if (noeud == noeud.pere.left) {
				Noeud y = noeud.pere.right;
				if (y.clr == Color.red) {
					y.clr = Color.black;
					noeud.pere.clr = Color.red;
					Rotation_Gauche(noeud.pere);
					y = noeud.pere.right;
				}
				if (y.left == sentinelle && y.right == sentinelle){
						System.out.println("sentille ********");
				}
				if (y.left.clr == Color.black && y.right.clr == Color.black) {
					y.clr = Color.red;
					noeud = noeud.pere;
				} else {
					if (y.right.clr == Color.black) {
						y.left.clr = Color.black;
						y.clr = Color.red;
						Rotation_Droite(y);
						y = noeud.pere.right;
					}
					y.clr = noeud.pere.clr;
					noeud.pere.clr = Color.black;
					y.right.clr = Color.black;
					Rotation_Gauche(noeud.pere);
					noeud=racine;
				}
			} else {
				Noeud y = noeud.pere.left;
				if (y.clr == Color.red) {
					y.clr = Color.black;
					noeud.pere.clr = Color.red;
					Rotation_Droite(noeud.pere);
					y = noeud.pere.left;
				}
				if (y.right.clr == Color.black && y.left.clr == Color.black) {
					y.clr = Color.red;
					noeud = noeud.pere;
				} else {
					if (y.left.clr == Color.black) {
						y.right.clr = Color.black;
						y.clr = Color.red;
						Rotation_Gauche(y);
						y = noeud.pere.left;
					}
					y.clr = noeud.pere.clr;
					noeud.pere.clr = Color.black;
					y.left.clr = Color.black;
					Rotation_Droite(noeud.pere);
					noeud=racine;
				}
			}
		}
		racine.clr=Color.black;
	}



	@Override
	public boolean contains(Object obj) 
	{
		return (Recherche(obj) != sentinelle);
	}

	 @Override
	public Iterator<E> iterator() 
	{
		return new ABRIterator();
	}

	 @Override
	public int size() 
	{
		return size;
	}

	@Override
	public boolean remove(Object obj)
	{
		Noeud c=Recherche(obj);
		if(c == sentinelle) return false;
		Suppression(c);
		return true;
	}


	public boolean add(E element) 
	{
		Noeud c = new Noeud(element);
		Insertion(c);
		Organiser(c);
		return true;
	}


	private class ABRIterator implements Iterator<E> 
	{
		private Noeud pos;
		private Noeud prec;

		public ABRIterator() 
		{
			if(!isEmpty()) 
			{
				pos = racine.minimum();
				prec = sentinelle;
			}else {
				pos =  sentinelle;
				prec = sentinelle;
			}
		}

		


		@Override
		public void remove() 
		{
			if (prec == sentinelle)
				throw new IllegalStateException(" next() n'a pas été appelée ");
			if(prec != racine.maximum()) 
			{
				pos  = Suppression(prec);
			}
			else {
				 Suppression(prec);
				 pos =sentinelle;
			}
			prec = sentinelle;
		}

		@Override
		public boolean hasNext() 
		{
			return (pos != sentinelle);
		}

		@Override
		public E next() 
		{
			if (pos == sentinelle)
				throw new NoSuchElementException("il n'ya pas de suivant !! ");
			if(hasNext()) 
			{
				prec = pos;
				pos = pos.suivant();
				return prec.clef;
			}else return null;
		}

	}

	@Override
	public String toString() 
	{
		StringBuffer buf = new StringBuffer();
		toString(racine, buf, "", maxStrLen(racine));
		return buf.toString();
	}

	private void toString(Noeud x, StringBuffer buf, String path, int len) 
	{
		if (x == sentinelle) 
		{
			return;
		}
		toString(x.right, buf, path + "D", len);
		for (int i = 0; i < path.length(); i++) 
		{
			for (int j = 0; j < len + 6; j++)
				buf.append(' ');
			char c = ' ';
			if (i == path.length() - 1)
				c = '+';
			else if (path.charAt(i) != path.charAt(i + 1))
				c = '|';
			buf.append(c);
		}
		if (x.clr == Color.black)
			buf.append("-- " + x.clef.toString() + " noir ");
		else
			buf.append("-- " + x.clef.toString() + " rouge ");
		if (x.left != sentinelle || x.right != sentinelle) 
		{
			buf.append(" --");
			for (int j = x.clef.toString().length(); j < len; j++)
				buf.append('-');
			buf.append('|');
		}
		buf.append("\n");
		toString(x.left, buf, path + "G", len);
	}

	private int maxStrLen(Noeud x) 
	{
		return x == sentinelle ? 0
				: Math.max(x.clef.toString().length(), Math.max(maxStrLen(x.left), maxStrLen(x.right)));
	}


	public static void main(String[] args) 
	{
		ArrayList<Integer> tab = new ArrayList();
		tab.add(1);
		tab.add(8);
		tab.add(9);
		tab.add(7);
		tab.add(2);
		tab.add(3);
		tab.add(5);
		tab.add(4);
		tab.add(6);

		ARBRE_RN<Integer> a = new ARBRE_RN<Integer>(tab);
              
		System.out.println(a);

		Iterator<Integer> it = a.iterator();
		System.out.println(it.next());
		it.remove();
		
		System.out.println(a);
		System.out.println(it.next());
		it.remove();
		
		System.out.println(a);
		System.out.println(it.next());
		it.remove();
		
		System.out.println(a);
		System.out.println(it.next());
		it.remove();
		
		System.out.println(a);
		System.out.println(it.next());
		it.remove();
		
		System.out.println(a);
		System.out.println(it.next());
		it.remove();
		
		System.out.println(a);

		
		System.out.println(a);
		tab.addAll(tab);
		System.out.println(a);

	
	}
}
