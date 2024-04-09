import config from "@/config"
import http from "@/utils/request"

const path = 'dashboard';
export default {
	product: {
		url: `${config.API_URL}/${path}`,
		name: "仪表盘",
		get: async function(){
			return await http.get(this.url+ `/product/detail`, {}, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	device: {
		url: `${config.API_URL}/${path}`,
		name: "仪表盘",
		get: async function(){
			return await http.get(this.url+ `/device/detail`, {}, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	network: {
		url: `${config.API_URL}/${path}`,
		name: "仪表盘",
		get: async function(){
			return await http.get(this.url+ `/network/detail`, {}, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	protocol: {
		url: `${config.API_URL}/${path}`,
		name: "仪表盘",
		get: async function(){
			return await http.get(this.url+ `/protocol/detail`, {}, {
				headers: {
					//'response-status': 401
				}
			});
		}
	}
}
