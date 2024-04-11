import config from "@/config"
import http from "@/utils/request"

const path = 's/tenant';
export default {
	list: {
		url: `${config.API_URL}/${path}`,
		name: "分页列表",
		post: async function(page, pageSize, data){
			return await http.post(this.url+ `/page/${page}/${pageSize}`, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	userList: {
		url: `${config.API_URL}/${path}/user`,
		name: "租户用户分页列表",
		post: async function(page, pageSize, data){
			return await http.post(this.url+ `/page/${page}/${pageSize}`, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	resourceList: {
		url: `${config.API_URL}/${path}/resource`,
		name: "租户资产分页列表",
		post: async function(page, pageSize, data){
			return await http.post(this.url+ `/page/${page}/${pageSize}`, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	create: {
		url: `${config.API_URL}/${path}/create`,
		name: "创建",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	userBind: {
		url: `${config.API_URL}/${path}/user/bind`,
		name: "用户绑定",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	userBatchBind: {
		url: `${config.API_URL}/${path}/user/batch/bind`,
		name: "用户批量绑定",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	userUnbind: {
		url: `${config.API_URL}/${path}/user/unbind`,
		name: "用户解绑",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	resourceBind: {
		url: `${config.API_URL}/${path}/resource/bind`,
		name: "资产绑定",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	resourceBatchBind: {
		url: `${config.API_URL}/${path}/resource/batch/bind`,
		name: "资产批量绑定",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	resourceUnbind: {
		url: `${config.API_URL}/${path}/resource/unbind`,
		name: "资产解绑",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	del: {
		url: `${config.API_URL}/${path}`,
		name: "删除",
		delete: async function(id){
			return await http.delete(this.url+ `/${id}`, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	update: {
		url: `${config.API_URL}/${path}/update`,
		name: "更新",
		put: async function(data){
			return await http.put(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	active: {
		url: `${config.API_URL}/${path}/active`,
		name: "启用/禁用",
		get: async function(id, active){
			return await http.get(this.url+ `/${id}/${active}`, {
				headers: {
					//'response-status': 401
				}
			});
		}
	}
}
