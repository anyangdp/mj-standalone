import config from "@/config"
import http from "@/utils/request"

const path = 'iotDeviceTag';
export default {
	list: {
		url: `${config.API_URL}/${path}`,
		name: "列表",
		post: async function(data){
			return await http.post(this.url+ `/list`, data, {
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
			return await http.get(this.url+ `/retrieve/${id}`, {
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
					'Content-Type': 'application/json'
				}
			});
		}
	},
	batchCreate: {
		url: `${config.API_URL}/${path}/batch/create`,
		name: "批量创建更新",
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
	}
}
