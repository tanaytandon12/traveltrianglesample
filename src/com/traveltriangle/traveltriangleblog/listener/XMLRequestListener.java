package com.traveltriangle.traveltriangleblog.listener;

public interface XMLRequestListener {

	/*
	 * This must be called before the execution of the network request
	 */
	public void preRequest();

	/*
	 * This must be called during the network request to parse the data
	 */
	public Object inRequest(Object request);

	/*
	 * This is called after the network request has been executed
	 */
	public void postRequest(Object response);
}
