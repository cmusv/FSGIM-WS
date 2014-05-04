package edu.cmu.sv.fsgim.data.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	private static final String EMF_NAME = "fsgim-db";
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory(EMF_NAME);

	private EMF() {
	}

	/**
	 * Singleton instance of the class to obtain a copy.
	 * 
	 * @return - EntityMangerFactory instance.
	 */
	public static EntityManagerFactory get() {
		return emfInstance;
	}
}
