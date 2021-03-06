package pkgClasse;

public class Avion extends Thread 
{
	String name = "Avion";
	String etat = "Inactif";
	String ressourceAcquise = "Aucune";
	String nom = "Avion";
	// int action = 0; 
	Integer index;
	
	static Fuels fuels;
	static Gates gates;
	static Pistes pistes;
	static Techniques techniques;
	
	static Integer fuelsLock = 0;
	static Integer gatesLock = 0;
	static Integer pistesLock = 0;
	static Integer techniquesLock = 0;
	
	public static Integer compteur = 0;
	public static Integer maxAvions;
	
	public static Boolean waitForDebut = false;
	public static Boolean waitForAnalyse = false;
	public static Boolean waitForFin = false;
	
	Integer delay = 100;
	Integer delayLoop = 300;
		
	public Avion(int i, Integer nFuels, Integer nGates, Integer nPistes, 
			Integer nTechniques, Integer maxA) 
	{						
		this.index = i;		
		this.nom = "Avion" + i;
		maxAvions = maxA;
		
		fuels = new Fuels(nFuels); 
		gates = new Gates(nGates);
		pistes = new Pistes(nPistes);
		techniques = new Techniques(nTechniques);			
	}
		
	public void arrive() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		etat = "Arrive";
		logStart(method);
		
		logEnd(method);
	}
	
	public void atterit() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		logStart(method);	
		
		Integer i = -1;
						
		while(i == -1)
		{		
			synchronized (pistes) 
			{				
				Pistes pistes = this.getPistes();
				i = pistes.get(this.index);
				
				if (i != -1)
				{
					etat = "Atterit sur piste " + i;
					ressourceAcquise = "Piste " + i; 
					pistesLock += 1;
				}
				else
				{
					try
					{
						logSleep();
						Thread.sleep(delayLoop);	
					}
					catch (Throwable e)
					{
						
					}
				}
			}							
		}			
		
		increaseCompteur();
		
		try
		{
			Thread.sleep(delay);	
		}
		catch (Throwable e)
		{
			
		}				
		
		synchronized (pistes) 
		{
			pistesLock -= 1;
			Pistes pistes = this.getPistes();
			pistes.put(this.index, i);
			etat = "En attente de porte...";
			ressourceAcquise = "Aucune";
		}						
		
		logEnd(method);
	}

	public void debarque() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		logStart(method);
		
		Integer i = -1;
		
		while(i == -1)
		{		
			synchronized (gates) 
			{				
				Gates gates = this.getGates();
				i = gates.get(this.index);

				if (i != -1)
				{
					etat = "Debarque par la porte " + i;
					ressourceAcquise = "Poste " + i;
					gatesLock += 1;
				}
				else
				{
					try
					{
						logSleep();
						Thread.sleep(delayLoop);	
					}
					catch (Throwable e)
					{
						
					}
				}
			}							
		}
		
		try
		{
			Thread.sleep(delay);	
		}
		catch (Throwable e)
		{
			
		}
		
		synchronized (gates) 
		{
			gatesLock -= 1;
			gates.put(this.index, i);

			etat = "D�barquement complet...";
			ressourceAcquise = "Aucune";
		}
				
		logEnd(method);
	}	
	
	public void gare() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		etat = "Se gare";
		logStart(method);
		
		logEnd(method);
		etat = "Inactif";
	}	
	
	public void sort() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		etat = "Sort";
		logStart(method);
		
		logEnd(method);
	}
	
	public void controle() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		logStart(method);
		
		Integer i = -1;
		
		while(i == -1)
		{		
			synchronized (techniques) 
			{				
				Techniques techniques = this.getTechniques();
				i = techniques.get(this.index);
				
				if (i != -1)
				{
					etat = "Passe au contr�le " + i;
					ressourceAcquise = "Technique " + i;
					techniquesLock += 1;
				}
				else
				{
					try
					{
						logSleep();
						Thread.sleep(delayLoop);	
					}
					catch (Throwable e)
					{
						
					}
				}
			}							
		}
		
		try
		{
			Thread.sleep(delay);	
		}
		catch (Throwable e)
		{
			
		}
		
		synchronized (techniques) 
		{
			etat = "En attente de porte... ";
			ressourceAcquise = "Aucune";
			techniquesLock -= 1;
			Techniques techniques = this.getTechniques();
			techniques.put(this.index, i);
		}	
		
		logEnd(method);

	}

	public void remplit() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		logStart(method);
		
		Integer i = -1;
		
		while(i == -1)
		{		
			synchronized (fuels) 
			{				
				Fuels fuels = this.getFuels();
				i = fuels.get(this.index);
				
				if (i != -1)
				{
					etat = "Remplit avec fuels " + i;
					ressourceAcquise = "Fuel " + i;
					fuelsLock += 1;
				}
				else
				{
					try
					{
						logSleep();
						Thread.sleep(delayLoop);	
					}
					catch (Throwable e)
					{
						
					}
				}
			}							
		}		
		
		try
		{
			Thread.sleep(delay);	
		}
		catch (Throwable e)
		{
			
		}
		
		synchronized (fuels) 
		{
			etat = "En attente de contr�le ... ";
			ressourceAcquise = "Aucune";
			fuelsLock -= 1;
			Fuels fuels = this.getFuels();
			fuels.put(this.index, i);
		}
		
		logEnd(method);
	}	
	
	public void embarque() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		logStart(method);
		
		Integer i = -1;
		
		while(i == -1)
		{		
			synchronized (gates) 
			{				
				Gates gates = this.getGates();
				i = gates.get(this.index);
				
				if (i != -1)
				{
					etat = "Embarque par la porte " + i;
					ressourceAcquise = "Gate " + i;
					gatesLock += 1;
				}
				else
				{
					try
					{
						logSleep();
						Thread.sleep(delayLoop);	
					}
					catch (Throwable e)
					{
						
					}
				}
			}							
		}
		
		try
		{
			Thread.sleep(delay);	
		}
		catch (Throwable e)
		{
			
		}
		
		synchronized (gates) 
		{
			etat = "En attente de piste... ";
			ressourceAcquise = "Aucune";
			gatesLock -= 1;
			gates.put(this.index, i);
		}
		
		logEnd(method);
	}

	public void decolle() 
	{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();		
		logStart(method);
		
		Integer i = -1;
		
		while(i == -1)
		{		
			synchronized (pistes) 
			{				
				Pistes pistes = this.getPistes();
				i = pistes.get(this.index);
				
				if (i != -1)
				{
					etat = "Decolle sur la piste " + i;
					ressourceAcquise = "Piste " + i;
					pistesLock += 1;
				}
				else
				{
					try
					{
						logSleep();
						Thread.sleep(delayLoop);	
					}
					catch (Throwable e)
					{
						
					}
				}
			}							
		}
		
		increaseCompteur();
		
		try
		{
			Thread.sleep(delay);	
		}
		catch (Throwable e)
		{
			
		}
		
		synchronized (pistes) 
		{
			pistesLock -= 1;
			Pistes pistes = this.getPistes();
			pistes.put(this.index, i);
			ressourceAcquise = "Aucune"; 
			etat = "Inactif";
		}		
		
		logEnd(method);
	}
	
	public void atterir() 
	{
		arrive();
		atterit();
		debarque();
		gare();		
	}
	
	public void decoller() 
	{
		sort();
		controle();
		remplit();
		embarque();
		decolle();			
	}
	
	public void exec() 
	{
		atterir();		
		decoller();		
	}
	
	public void execDecollage() 
	{
		decoller();		
	}	
	
	public void execAtterrissage() 
	{
		atterir();		
	}	
	
	public void log(String action)
	{
		System.out.println(this.index + "." + this.name + " : " + action);			
	}
	
	public void logStart(String action)
	{
		log("D�bute - " + action);
	}
	
	public void logEnd(String action)
	{
		log("Termine - " + action);
	}
	
	public void logSleep()
	{
		log("Sleep/wait");
	}
	
	/*
	public static void main(String[] args) {
		Avion avion = new Avion(0, 2 ,2, 2, 2);			
		avion.exec();
	}
	*/	
	
	public Fuels getFuels()
	{
		return fuels;
	}
	
	public Gates getGates()
	{
		return gates;
	}
	
	public Pistes getPistes()
	{
		return pistes;
	}
	
	public Techniques getTechniques()
	{
		return techniques;
	}
	
	public String getNom()
	{
		return nom;
	}
	
	public void setNom(String newNom)
	{
		this.nom = newNom;
	}
	
	public String getEtat()
	{
		return etat;
	}
	
	public static void increaseCompteur()
	{
		synchronized (compteur)
		{
			compteur++;
			
			if (compteur >= maxAvions)
			{
				waitForDebut = true;
			}
		}
	}
	
	public static void resetCompteur()
	{
		synchronized (compteur)
		{
			compteur = 0;
		}
	}
	
	public static void debut()
	{
		resetCompteur();
		
		synchronized (waitForDebut) {
			waitForDebut = false;			
		}
	}
		
	public static void analyse()
	{
		synchronized (waitForAnalyse) {
			waitForAnalyse = false;			
		}
	}
	
	public static void fin()
	{
		synchronized (waitForFin) {
			waitForDebut = false;
			waitForAnalyse = false;
			waitForFin = false;			
		}
	}
	
	public String getRessourceAcquise()
	{
		return ressourceAcquise;
	}
		
	@Override
	public void run()
	{
		System.out.println("�tat des locks (avant " + this.index + ".Avion) : " 
				+ Avion.fuelsLock + " (F) " + Avion.gatesLock + " (G) " 
				+ Avion.pistesLock + " (P) " + Avion.techniquesLock + " (T)");
		
		this.exec();
		/*
		if (action == 0){
			this.exec();
		}
		else if (action == 1){
			this.execAtterrissage();
		}
		else{
			this.execDecollage();
		}
		*/
		
		System.out.println("�tat des locks (apr�s " + this.index + ".Avion) : " 
				+ Avion.fuelsLock + " (F) " + Avion.gatesLock + " (G) " 
				+ Avion.pistesLock + " (P) " + Avion.techniquesLock + " (T)");			
	}

	/*
	public void setAction(int action) {
		this.action = action;
	}
	*/	
}

