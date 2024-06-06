import axios from 'axios';
import { ElMessageBox, ElNotification } from 'element-plus';
import sysConfig from "@/config";
import tool from '@/utils/tool';
import router from '@/router';

axios.defaults.baseURL = ''

axios.defaults.timeout = sysConfig.TIMEOUT

// HTTP request 拦截器
axios.interceptors.request.use(
	(config) => {
		let token = tool.cookie.get("TOKEN");
		if (token) {
			config.headers[sysConfig.TOKEN_NAME] = sysConfig.TOKEN_PREFIX + token;
		}
		if (!sysConfig.REQUEST_CACHE && config.method === 'get') {
			config.params = config.params || {};
			config.params['_'] = new Date().getTime();
		}
		Object.assign(config.headers, sysConfig.HEADERS);
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);

// HTTP response 拦截器
axios.interceptors.response.use(
	(response) => {
		// eslint-disable-next-line no-useless-catch
		try {
			let res = { data: response.data };

			if (res.data.result === false) {
				if (!response.data.error) {
					ElNotification.error({
						title: '请求错误',
						message: "Status:500，服务器发生错误！"
					});
				} else {
					if (response.data.error.code === 1002 || response.data.error.code === 1000) {
						ElNotification.error({
							title: '401错误',
							message: res.data.error.message || "401错误"
						});
						router.replace({ path: '/login' });
					} else {
						ElNotification.error({
							title: '请求错误',
							message: res.data.error.message || "Status:500，服务器发生错误！"
						});
					}
				}
				throw res.data;
			} else {
				return res.data;
			}
		} catch (e) {
			throw e;
		}
	},
	(error) => {
		let message = "请求服务器无响应！";

		if (error.response) {
			const status = error.response.status;
			message = error.response.data.message || `Status:${status}，未知错误！`;

			switch (status) {
				case 400:
					message = "请求错误，客户端请求语法错误！";
					break;
				case 404:
					message = "Status:404，正在请求不存在的服务器记录！";
					break;
				case 500:
					message = "Status:500，服务器发生错误！";
					break;
				case 401:
					ElMessageBox.confirm('当前用户已被登出或无权限访问当前资源，请尝试重新登录后再操作。', '无权限访问', {
						type: 'error',
						closeOnClickModal: false,
						center: true,
						confirmButtonText: '重新登录'
					}).then(() => {
						router.replace({ path: '/login' });
					}).catch(() => {});
					return Promise.reject(error);
				default:
					message = `Status:${status}，未知错误！`;
			}
		}

		ElNotification.error({
			title: '请求错误',
			message: message
		});

		return Promise.reject(error);
	}
);

var http = {

	/** get 请求
	 * @param  {接口地址} url
	 * @param  {请求参数} params
	 * @param  {参数} config
	 */
	get: function (url, params = {}, config = {}) {
		return new Promise((resolve, reject) => {
			axios({
				method: 'get',
				url: url,
				params: params,
				...config
			}).then((response) => {
				resolve(response.data);
			}).catch((error) => {
				reject(error);
			});
		});
	},

	/** post 请求
	 * @param  {接口地址} url
	 * @param  {请求参数} data
	 * @param  {参数} config
	 */
	post: function (url, data = {}, config = {}) {
		return new Promise((resolve) => {
			axios({
				method: 'post',
				url: url,
				data: data,
				...config
			}).then((response) => {
				resolve(response.data);
			}).catch((error) => {
				console.log("error: ", error);
				ElNotification.error({
					title: '请求错误',
					message: error.message
				});
				// reject(error);
			})
		})
	},

	/** put 请求
	 * @param  {接口地址} url
	 * @param  {请求参数} data
	 * @param  {参数} config
	 */
	put: function (url, data = {}, config = {}) {
		return new Promise((resolve, reject) => {
			axios({
				method: 'put',
				url: url,
				data: data,
				...config
			}).then((response) => {
				resolve(response.data);
			}).catch((error) => {
				reject(error);
			})
		})
	},

	/** patch 请求
	 * @param  {接口地址} url
	 * @param  {请求参数} data
	 * @param  {参数} config
	 */
	patch: function (url, data = {}, config = {}) {
		return new Promise((resolve, reject) => {
			axios({
				method: 'patch',
				url: url,
				data: data,
				...config
			}).then((response) => {
				resolve(response.data);
			}).catch((error) => {
				reject(error);
			})
		})
	},

	/** delete 请求
	 * @param  {接口地址} url
	 * @param  {请求参数} data
	 * @param  {参数} config
	 */
	delete: function (url, data = {}, config = {}) {
		return new Promise((resolve, reject) => {
			axios({
				method: 'delete',
				url: url,
				data: data,
				...config
			}).then((response) => {
				resolve(response.data);
			}).catch((error) => {
				reject(error);
			})
		})
	},

	/** jsonp 请求
	 * @param  {接口地址} url
	 * @param  {JSONP回调函数名称} name
	 */
	jsonp: function (url, name = 'jsonp') {
		return new Promise((resolve) => {
			var script = document.createElement('script')
			var _id = `jsonp${Math.ceil(Math.random() * 1000000)}`
			script.id = _id
			script.type = 'text/javascript'
			script.src = url
			window[name] = (response) => {
				resolve(response)
				document.getElementsByTagName('head')[0].removeChild(script)
				try {
					delete window[name];
				} catch (e) {
					window[name] = undefined;
				}
			}
			document.getElementsByTagName('head')[0].appendChild(script)
		})
	}
}

export default http;
