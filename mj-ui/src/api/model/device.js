import config from "@/config"
import http from "@/utils/request"

const path = 'iotDevice';
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
	one: {
		url: `${config.API_URL}/${path}`,
		name: "id查询",
		get: async function(id){
			return await http.get(this.url+ `/retrieve/${id}`, {},{
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	history: {
		url: `${config.API_URL}/${path}`,
		name: "历史分页",
		post: async function(page, pageSize, data){
			return await http.post(this.url+ `/${data.deviceId}/${data.property}/history/${page}/${pageSize}`, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	syncState: {
		url: `${config.API_URL}/${path}`,
		name: "同步设备状态",
		get: async function(deviceId){
			return await http.get(this.url+ `/${deviceId}/latest/state`, {}, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	log: {
		url: `${config.API_URL}/${path}`,
		name: "日志分页",
		post: async function(page, pageSize, data){
			return await http.post(this.url+ `/${data.deviceId}/log/${page}/${pageSize}`, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	read: {
		url: `${config.API_URL}/${path}`,
		name: "读最新属性",
		post: async function(deviceId, property){
			return await http.post(this.url+ `/${deviceId}/read/${property}`, {}, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	write: {
		url: `${config.API_URL}/${path}`,
		name: "写属性值",
		post: async function(deviceId, data){
			return await http.post(this.url+ `/${deviceId}/write`, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	latest: {
		url: `${config.API_URL}/${path}`,
		name: "获取设备最新属性数据",
		post: async function(deviceId, data){
			return await http.post(this.url+ `/${deviceId}/latest/properties`, data, {
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
	delete: {
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
		name: "启用",
		get: async function(id, active){
			return await http.get(this.url+ `/${id}/${active}`, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	deActive: {
		url: `${config.API_URL}/${path}/deActive`,
		name: "停用",
		get: async function(id){
			return await http.get(this.url+ `/${id}`, {
				headers: {
					//'response-status': 401
				}
			});
		}
	}
}
