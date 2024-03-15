package com.mj.framework.handler;

import com.mj.framework.constants.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.concurrent.Callable;

/**
 * @author anyangdp
 *
 */
@Slf4j
public class ServiceTemplate {

	public static <RQ, RS> Callable<GenericResponse<RS>> spawnAction(ServiceCallback<RS> callback) {
		return spawnAction(null, callback);
	}

	public static <RQ, RS> Callable<GenericResponse<RS>> spawnAction(BindingResult bindingResult,
                                                                     ServiceCallback<RS> callback) {
		return () -> call(bindingResult, callback);
	}

	public static <RQ, RS> GenericResponse<RS> call(ServiceCallback<RS> callback) throws Exception {
		return ServiceTemplate.call(null, callback);
	}

	public static <RQ, RS> GenericResponse<RS> call(BindingResult bindingResult, ServiceCallback<RS> callback)
			throws Exception {

		GenericResponse<RS> response = new GenericResponse<RS>();

		if (null != bindingResult) {

			log.info("@@@ PROC: action with validator @@@");

			boolean result = validate(bindingResult, response);

			if (result && null != callback) {

				callback.execute(response);
			}
		} else {
			log.info("@@@ PROC: action without validator @@@");

			if (null != callback) {
				callback.execute(response);
			}
		}

		return response;
	}

	protected static <RS> boolean validate(BindingResult bindingResult, GenericResponse<RS> response) {

		boolean result = !bindingResult.hasErrors();

		if (!result) {
			response.setResult(false);
			ErrorDTO errorDTO = new ErrorDTO(ErrorCodeEnum.PARAM_IS_INVALID.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
			response.setError(errorDTO);
		}

		return result;
	}
}
