import config from "@/config"
import http from "@/utils/request"

export default {
	token: {
		url: `${config.API_URL}/doLogin`,
		name: "登录获取TOKEN",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	userInfo: {
		url: `${config.API_URL}/currentUser`,
		name: "获取当前用户信息",
		get: async function(){
			return await http.get(this.url);
		}
	},
	logout: {
		url: `${config.API_URL}/doLogout`,
		name: "注销",
		post: async function(){
			return await http.get(this.url, null, null);
		}
	}
}
