package com.mj.framework.handler;

/**
 * @author anyangdp
 *
 */
@FunctionalInterface
public interface ControllerCallback<RS> {

	void execute(GenericResponse<RS> response) throws Exception;
}
