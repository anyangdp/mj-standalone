import config from "@/config"
import http from "@/utils/request"

const path = 'processDefinition';
export default {
	process: {
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
			url: `${config.API_URL}/${path}/retrieve`,
			name: "获取当前流程",
			post: async function(id){
				return await http.get(this.url + `${id}`, {});
			}
		},
		batchDel: {
			url: `${config.API_URL}/${path}`,
			name: "删除",
			delete: async function(ids){
				return await http.delete(this.url+ `/`, ids, {
					headers: {
						//'response-status': 401
					}
				});
			}
		}
	}
}
