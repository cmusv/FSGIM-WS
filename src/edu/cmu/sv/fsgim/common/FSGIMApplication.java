package edu.cmu.sv.fsgim.common;

import org.glassfish.jersey.server.ResourceConfig;

import edu.cmu.sv.fsgim.common.cors.CORSResponseFilter;

public class FSGIMApplication extends ResourceConfig {
	public FSGIMApplication() {
		super();
		register(CORSResponseFilter.class);
	}
}